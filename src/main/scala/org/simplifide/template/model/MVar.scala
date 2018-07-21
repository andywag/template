package org.simplifide.template.model

import org.simplifide.template.model.MVar.{BrackSelect, DotSelect}
import org.simplifide.template.model.Model.Str

trait MVar extends Model{
  def f(name:Model,x:Model*) = DotSelect(this,MFunction.Call(name,x.toList))
  def d(x:Model) = DotSelect(this,x)
  def s(x:Model) = BrackSelect(this,x)
}

object MVar {

  // Types






  case class Var(name:Model, typ:MType) extends MVar with Model {


  }
  case class VarDec(v:Var, value:Option[Model]=None, eol:Boolean = true) extends Model {
    def ~=(x:Model) = VarDec(v,Some(x))
  }

  case class DotSelect(l:Model, r:Model) extends MVar with Model
  case class BrackSelect(o:Model, i:Model) extends MVar with Model

}
