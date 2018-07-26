package org.simplifide.template.model

import org.simplifide.template.model.MType._
import org.simplifide.template.model.MVar._
import org.simplifide.template.model.Model.{Empty, Str}
import org.simplifide.utils.Utils

trait MType extends Model {

  val iName:String = this match {
    case SType(x)   => x
    case x:BaseTypes => x.name
    case x:DefinedType => x.name
    case x:MClassType => x.cla.name
    case _ =>"Not Defined"
  }

  def ~(name:Model) = VarDec(Var(name,this))
  def cVar= Var(Utils.firstLetterLower(iName),this)
  def cVar(extra:MType) = {
    val ret = Var(Utils.firstLetterLower(iName),TypeAnd(extra,this))
    ret
  }

}

object MType {

  class BaseTypes(val name:String) extends MType
  case object TString extends BaseTypes("String")
  case object TInt extends BaseTypes("Int")
  case object TDouble extends BaseTypes("Double")
  case object TBoolean extends BaseTypes("Boolean")
  case object TDynamic extends BaseTypes("dynamic")
  case object TFactory extends BaseTypes("factory ")
  case object TResponse extends BaseTypes("Response")

  object TSList extends BaseTypes("List")
  object TSMap extends BaseTypes("Map")
  object TSFuture extends BaseTypes("Future")

  case class SType(name:String) extends MType

  class DefinedType(val name:String) extends MType {
    def create(args:(String,Model)*) = {
      def check(x:(String,Model)) = if (x._2 == Model.Empty) None else Some(Model.ModelTuple(x._1,x._2))
      val a = args.map(x => check(x)).flatten.toList
      MFunction.Call(name,a)
    }
  }

  class MClassType(val cla:MClass) extends MType


  def decodeType(typ:String) = {
    typ match {
      case TString.name => TString
      case TInt.name => TInt
      case TDouble.name => TDouble
      case TBoolean.name => TBoolean
      case TDynamic.name => TDynamic
      case TFactory.name => TFactory
      case TSList.name   => TSList
      case TSMap.name => TSMap
      case _          => new SType(typ)
    }
  }

  case class TList(base:MType) extends Generic(TSList,List(base))
  case class TMap(key:MType, value:MType) extends Generic(TSMap,List(key,value))
  case class TFuture(typ:MType) extends Generic(TSFuture,List(typ))

  case object NoType extends MType
  case class TypeAnd(r:MType,l:MType) extends MType
  class Generic(val o:MType, val i:List[MType]) extends MType

  trait TypeId extends MType {
    def ~(x:MType) = TypeAnd(this,x)
  }
  case object $auto extends TypeId
  case object $final extends TypeId
  case object $static extends TypeId

  /** Simple Type */
  def T(x:String) = SType(x)
}
