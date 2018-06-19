package org.simplifide.template.model

import org.simplifide.template.Container
import org.simplifide.template.model.MVar.SType
import shapeless.{:+:, CNil}

trait Model {
  def -->(implicit container:Container[Model]) = {
    val ret = container.-->(this)
  }
}

object Model {

  def Line = Str("\n")
  def T(s:Model) = SType(s)

  case class Str(name:String)    extends Model
  case class Sym(name:Symbol)    extends Model
  case class WInt(int:Int)       extends Model
  case class WOpt[T <: Model](x:Option[T]) extends Model

  implicit def StringToModel(x:String) = Str(x)
  implicit def SymbolToModel(x:Symbol) = Sym(x)
  implicit def IntToModel(x:Int)       = WInt(x)
  implicit def OptToModel[T <: Model](x:Option[T]) = WOpt(x)

  def ??(x:Option[Model]) = WOpt(x)

  // Statement Section
  case class Import(name:Model, gt:Boolean = false) extends Model
  case object $import extends Model {
    def ~(x:Model) = Import(x)
    def ~~(x:Model) = Import(x,true)
  }
  // Variable Section




  // Class Section
  case class MClass(name:Model) extends Container[Model] with Model










  case class Class(name:Model) extends Container[Model]

}
