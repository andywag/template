package org.simplifide.dart.binding

import org.simplifide.dart.binding
import org.simplifide.dart.web.DartFile
import org.simplifide.template.FileModel.GFile
import org.simplifide.template.model
import org.simplifide.template.model.MType._
import org.simplifide.template.model.MVar.Var
import org.simplifide.template.model.Model.{DotPrefix, MClass, MapIndex, SemiEnd}
import org.simplifide.template.model.dart.{DartGenerator, DartParser}
import org.simplifide.template.model._

trait ModelService {
  val proto:MClassProto
  val functions:Map[String,ResponseFunction]


  def file = {
    val file = new binding.ModelService.ModelServiceFile(this)
    new GFile(file.filename + "_service.dart",file.contents(DartGenerator.create))
  }

}

object ModelService {
  val extractData:String = "extractData"

  case class ModelServiceI(proto:MClassProto, functions:Map[String,ResponseFunction]) extends ModelService
  val client = $final ~ SType("Client") ~ "_http"

  def all(proto:MClassProto, address:String) = new ModelServiceI(proto,
    Map(proto.name->ResponseFunction.All(proto.typ,address,proto.fromFunction,client.v)))

  class ModelServiceFile(service:ModelService) extends DartFile {
    val proto = service.proto
    override val filename: String = proto.name.toLowerCase

    IMPORT_DART_ASYNC
    IMPORT_DART_CONVERT
    IMPORT_HTTP
    IMPORT_ANGULAR
    imp(s"../models/${proto.name}.dart")
    -->(Model.Line)
    $_INJECTABLE
    -->(ModelServiceClass(service))


  }

  case class ModelServiceClass(service:ModelService) extends MClass(s"${service.proto.name}Service") {
    -->($static ~ $final ~ "_headers" ~= Model.Dictionary(List(Model.ModelTuple("Content-Type","application/json"))))
    //-->($static ~ $final ~ "_url" ~= Model.Quotes(service.baseAddress))
    -->(Model.Line)
    val client = -->($final ~ SType("Client") ~ "_http")
    -->(Model.Line)
    -->(new SemiEnd(DartParser.constructor(this.name.string,List(client.v))))
    -->(Model.Line)
    this.items.append(ExtractData)
    -->(Model.Line)
    service.functions.map(x => -->(x._2))


  }

  //   dynamic _extractData(Response resp) => json.decode(resp.body)['data'];
  object ExtractData extends MFunction.MFunc(s"_extractData",TDynamic) {
    val resp = Var("resp",SType("Response"))
    val args = List(resp)

    -->(Model.Return(MFunction.Call(s"json.decode",List(resp.d("body"))).s("data")))
  }





}
