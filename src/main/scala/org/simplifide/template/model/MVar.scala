package org.simplifide.template.model

import org.simplifide.template.model.Model.{ Str}

trait MVar {

}

object MVar {

  // Types

  trait MType extends Model {
    def ~(name:Model) = VarDec(Var(name,this))
  }
  case class SType(name:Model) extends MType
  case object $auto extends MType

  def T(x:String) = SType(Str(x))

  case class Var(name:Model, typ:MType) extends Model
  case class VarDec(v:Var, value:Option[Model]=None) extends Model {
    def ~=(x:Model) = VarDec(v,Some(x))
  }
}
