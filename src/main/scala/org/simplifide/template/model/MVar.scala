package org.simplifide.template.model

import org.simplifide.template.model.MVar.{BrackSelect, DotSelect, VarDec}
import org.simplifide.template.model.Model.Str

trait MVar extends Model{

  val name:Model
  val typ:MType
  val default:Option[Model]

  def createDec = VarDec(this)

  def f(name:Model,x:Model*) = DotSelect(this,MFunction.Call(name,x.toList))
  def d(x:Model) = DotSelect(this,x)
  def s(x:Model) = BrackSelect(this,x)




}

object MVar {


  case class Var(name:Model, typ:MType, default:Option[Model]=None) extends MVar with Model {}
  class EmptyVar extends MVar {
    val name:Model = Str("NA")
    val typ  = MType.NoType
    val default = None
  }


  case class DotSelect(l:Model, r:Model) extends EmptyVar
  case class BrackSelect(o:Model, i:Model) extends EmptyVar

  case class VarDec(v:MVar, value:Option[Model]=None, eol:Boolean = true) extends Model {
    def ~=(x:Model) = VarDec(v,Some(x))
  }

}
