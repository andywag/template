package org.simplifide.dart.binding

import org.simplifide.template.model.MFunction
import shapeless.{HList, LabelledGeneric}
import shapeless.ops.hlist.ToTraversable
import shapeless.ops.record.Keys
import io.circe.{JsonObject, _}
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._


object TestBinding {

  sealed trait Bindings

  case class Person(x:String, d:Option[String], id:String = "Person")
  case class Result(x:String, y:Person, id:String = "Result") extends Bindings
  case class Event(name:String, event:String, results:List[Result], id:String="Event") extends Bindings


  Event("String","String",List())


  import Json._

  def walkField(json:Json,field:String)  {
    println(s"Field : $field")
    walk(json.hcursor.downField(field).focus.get)
  }

  def walk(doc: Json) {
    val keys = doc.hcursor.keys.getOrElse(List())
    keys.foreach(walkField(doc,_))

  }


  def main(args: Array[String]): Unit = {
    val result = Event("b","c",List(Result("d",Person("e",Some("a")))))
    val json = result.asJson

    walk(json)
    println(json.noSpaces)

    val decodedFoo = decode[Event](json.noSpaces)
    println(decodedFoo)
  }
}
