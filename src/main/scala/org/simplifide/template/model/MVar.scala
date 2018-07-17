package org.simplifide.template.model

import org.simplifide.template.model.Model.{ Str}

trait MVar {

}

object MVar {

  // Types

  trait MType extends Model {
    def ~(name:Model) = VarDec(Var(name,this))
    def user_types_used:List[String] = {
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
        case Generic(o,i) => o.user_types_used ::: i.flatMap(x => x.user_types_used)
        case TypeAnd(r,l) => r.user_types_used ::: l.user_types_used
      }
    }
  }

  case object NoType extends MType
  case class SType(name:Model) extends MType
  case class TypeAnd(r:MType,l:MType) extends MType
  case class Generic(o:MType, i:List[MType]) extends MType

  trait TypeId extends MType {
    def ~(x:MType) = TypeAnd(this,x)
  }
  case object $auto extends TypeId
  case object $final extends TypeId

  /** Simple Type */
  def T(x:String) = SType(Str(x))


  case class Var(name:Model, typ:MType) extends MVar with Model
  case class VarDec(v:Var, value:Option[Model]=None) extends Model {
    def ~=(x:Model) = VarDec(v,Some(x))
  }
}
