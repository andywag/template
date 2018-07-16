package org.simplifide.template.model

import org.simplifide.template.model.Model.{ Str}

trait MVar {

}

object MVar {

  // Types

  trait MType extends Model {
    def ~(name:Model) = VarDec(Var(name,this))
    def types_used:List[String] = {
      this match {
        case SType(x)     => List(x.toString)
        case Generic(o,i) => o.types_used ::: i.types_used
        case TypeAnd(r,l) => r.types_used ::: l.types_used
      }
    }
  }


  case class SType(name:Model) extends MType
  case class TypeAnd(r:MType,l:MType) extends MType
  case class Generic(o:MType, i:MType) extends MType

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
