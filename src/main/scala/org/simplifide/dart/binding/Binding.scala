package org.simplifide.dart.binding

import org.simplifide.template.model.{MClassProto, MFunction, MVar}
import shapeless.{HList, LabelledGeneric}
import shapeless.ops.hlist.ToTraversable
import shapeless.ops.record.Keys
import io.circe.{JsonObject, _}
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._


object Binding {

  val B_STRING = "String"
  val B_INT    = "Int"

  def opt(x:String) = s"Option[$x]"

  def rq(input:String) = input.replace(""""""","")

  import Json._

  def getId(json:Json) = {
    val down = json.hcursor.downField("id").focus
    down
  }

  def idOrBasic(json:Json):String = {
    getId(json).getOrElse(json).toString()
  }

  def createField(json:Json,field:String) =   {
    def getType(typeJson:Json) = {
      val array = typeJson.asArray
      array.map(x => {
        val id = getId(x(0))
        MVar.Generic(MVar.SType("List"),MVar.SType(rq(id.get.toString())))
      }).getOrElse(
        MVar.SType(rq(idOrBasic(typeJson)))
      )
    }
    val down = json.hcursor.downField(field).focus
    println(s"Field : $field : $down")

    MVar.Var(field,getType(down.get))

  }

  def walkField(json:Json,field:String):List[MClassProto] = {
    val down = json.hcursor.downField(field).focus
    println(s"Field : $field : $down")

    walk(down.get)
  }



  private def walk(doc: Json):List[MClassProto]= {
    val array = doc.asArray
    val arrayClasses = array.map(x => x.flatMap(y => walk(y)))
    val arrayFilter = arrayClasses.getOrElse(Vector()).toList


    val keys = doc.hcursor.keys.getOrElse(List())
    val other = keys.filter(x => x != "id")
    val classes = arrayFilter ::: other.flatMap(walkField(doc,_)).toList


    val id = getId(doc)

    id.map(x => {
      println(s"ID $x")
      val fields = other.map(createField(doc, _))
      val cla = MClassProto.MClassProtoImpl(rq(id.get.toString()), fields.toList, List())
      val ret = cla :: classes
      ret
    }).getOrElse(classes)
  }

  def createClasses[T](input:T)(implicit encoder:Encoder[T]) = {
    val json = input.asJson
    val result = walk(json)
    result
  }

  sealed trait Bindings


  case class Person(x:String, d:Option[String], id:String = "Person")
  case class Result(x:String, y:Person, id:String = "Result") extends Bindings
  case class Event(name:String, event:String, results:List[Result], id:String="Event") extends Bindings






  def main(args: Array[String]): Unit = {
    val person = Person(B_STRING,Some(opt(B_STRING)))
    val result = Result("String",person)
    val event = Event("String","String",List(result))
    val json = event.asJson

    val classes = walk(json)
    println(json.noSpaces)

    val decodedFoo = decode[Event](json.noSpaces)
    println(decodedFoo)
  }
}
