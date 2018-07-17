package org.simplifide.template.model

import org.simplifide.template.Container
import org.simplifide.template.model.MVar.{MType, Var}
import shapeless.{HList, LabelledGeneric}
import shapeless.ops.hlist.ToTraversable
import shapeless.ops.record.Keys
import Model._

trait MFunction extends Model{
  val name:Model
  val output:MType
  val args:List[Var]
  def body:List[Model]
}

object MFunction {

  abstract case class MFunc(name:Model, output:MType) extends Container[Model] with MFunction {
    def body = items.toList
  }
  case class Call(name:Model, args:List[Model]) extends Model
  case class Lambda(name:String, typ:MType, input:Model, func:Model) extends Model



  trait SFunction extends Model

  def generic[T, HL<:HList, KL<:HList](path:T)(implicit gen: LabelledGeneric.Aux[T, HL],
                                            keys: Keys.Aux[HL, KL],
                                            t1: ToTraversable.Aux[HL, List, Model],
                                            t2: ToTraversable.Aux[KL, List, Symbol]) = {
    val name = path.getClass.getName
    val temp = LabelledGeneric[T]
    val result = temp.to(path)
    //(name, keys().toList, result.toList)
    val args = (keys().toList zip result.toList).map(x => x._1.name ~> x._2)
    val realIndex = name.indexOf("$")
    val realName = name.substring(realIndex+1)

    MFunction.Call(realName,args)
  }

  def walk[T, HL<:HList, KL<:HList](path:T)(implicit gen: LabelledGeneric.Aux[T, HL],
                                               keys: Keys.Aux[HL, KL],
                                               t1: ToTraversable.Aux[HL, List, Any],
                                               t2: ToTraversable.Aux[KL, List, Symbol]) = {
    val name = path.getClass.getName
    val temp = LabelledGeneric[T]
    val result = temp.to(path)
    //(name, keys().toList, result.toList)
    (name,keys().toList,result.toList)

  }



}


