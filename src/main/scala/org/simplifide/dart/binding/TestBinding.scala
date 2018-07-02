package org.simplifide.dart.binding

import org.simplifide.template.model.{MFunction, MProto}
import shapeless.{HList, LabelledGeneric}
import shapeless.ops.hlist.ToTraversable
import shapeless.ops.record.Keys

object TestBinding {

  case class Person(x:String)
  case class Result(x:String, y:Person)
  case class Event(name:String, event:String, results:List[Result])


  def createProto[T, HL<:HList, KL<:HList](input:T)(implicit gen: LabelledGeneric.Aux[T, HL],
                                                   keys: Keys.Aux[HL, KL],
                                                   t1: ToTraversable.Aux[HL, List, Any],
                                                   t2: ToTraversable.Aux[KL, List, Symbol]):MProto ={
    input match {
      case x:String => MProto.PString
      case x:Int    => MProto.PInt
      case _        => {
        val t = MFunction.walk(input)
        //val proto = t._3.map(createProto(_))
        val args = (t._2 zip t._3).map(x => MProto.ProtoArg(x._1.name, x._2))
        MProto.ProtoClass(t._1, args)
      }
    }
  }


  def main(args: Array[String]): Unit = {
    //val t = MFunction.walk(Event("b","c",List(Result("d",Person("e")))))
    //val t = MFunction.walk(Result("c","d"))
    //val res = t._2.zip(t._3)
    val result = createProto(Event("b","c",List(Result("d",Person("e")))))
    //val result = LabelledGeneric[Event].to(Event("b","c",List(Result("d",Person("e")))))

    println(result)
  }
}
