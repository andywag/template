package org.simplifide.template.model

import org.simplifide.template.model.Model.{ Str}

trait MVar {

}

object MVar {

  // Types






  case class Var(name:Model, typ:MType) extends MVar with Model
  case class VarDec(v:Var, value:Option[Model]=None, eol:Boolean = true) extends Model {
    def ~=(x:Model) = VarDec(v,Some(x))
  }
}
