package org.simplifide.template.model

import org.simplifide.template.model.MType.{BaseTypes, Generic, SType, TypeAnd}
import org.simplifide.template.model.MVar._
import org.simplifide.template.model.Model.Str

trait MType extends Model {
  def ~(name:Model) = VarDec(Var(name,this))

  /*def user_types_used:List[String] = {
    def filterTypes(typ:String) = {
      typ match {
        case "String" => List()
        case "Int"    => List()
        case "List"   => List()
        case _        => List(typ)
      }
    }

    this match {
      case SType(x)     => {
        x match {
          case Str(x) => filterTypes(x)
          case _      => List(x.toString)
        }
      }
      case x:BaseTypes => List()
      case x:Generic => x.o.user_types_used ::: x.i.flatMap(x => x.user_types_used)
      case TypeAnd(r,l) => r.user_types_used ::: l.user_types_used
    }
  }
  */
}

object MType {

  class BaseTypes(val name:String) extends MType
  case object TString extends BaseTypes("String")
  case object TInt extends BaseTypes("Int")
  case object TDouble extends BaseTypes("Double")
  case object TBoolean extends BaseTypes("Boolean")
  case object TDynamic extends BaseTypes("dynamic")
  case object TFactory extends BaseTypes("factory")

  object TSList extends BaseTypes("List")
  object TSMap extends BaseTypes("Map")
  object TSFuture extends BaseTypes("Future")

  case class SType(name:String) extends MType


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
