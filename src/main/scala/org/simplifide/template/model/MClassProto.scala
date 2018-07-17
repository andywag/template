package org.simplifide.template.model

import org.simplifide.template
import org.simplifide.template.model
import org.simplifide.template.model.MVar.{SType, VarDec}
import org.simplifide.template.model.Model.{DotPrefix, MClass, SemiEnd}

trait MClassProto extends Model {
  val name:String
  val vars:List[MVar.Var]
  val fields:List[Model]

  def declarations = {
    val a = types
    vars.map(x => VarDec(x,None))
  }

  def imports = {
    types.map(x => Model.Import(s"${x.toLowerCase}.dart"))
  }

  def types = {
    val typs = vars.map(x => x.typ)
    val allTypes = typs.flatMap(x => x.user_types_used)
    allTypes
  }

  def constructor = {
    new model.MFunction.Call(this.name,this.vars.map(x => x.copy(name = DotPrefix("this",x.name))))
  }

  def create = new template.model.MClassProto.Cla(this)

}

object MClassProto {

  case class MClassProtoImpl(name:String,
                             vars:List[MVar.Var],
                             fields:List[Model]) extends MClassProto

  class Cla(cla:MClassProto) extends MClass(cla.name) {
    cla.declarations.foreach(x => -->(x))
    -->(new SemiEnd(cla.constructor))
    val json = new JsonConverter(cla)
    -->(json.from.)
  }

  /*
  factory Hero.fromJson(Map<String, dynamic> hero) =>
      Hero(_toInt(hero['id']), hero['name']);
   */

  class JsonConverter(cla:MClassProto) {

    class From extends MFunction.MFunc(cla.name,SType("factory")) {
      val typ = MVar.Generic(MVar.SType("Map"),List(SType("String"),SType("dynamic")))
      val args = List(MVar.Var("value",typ))
    }

    def from = new From()

    def to = {

    }
  }

}
