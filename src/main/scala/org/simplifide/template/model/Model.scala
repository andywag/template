package org.simplifide.template.model

import org.simplifide.template.Container
import org.simplifide.template.model.Model.ModelTuple
import shapeless.{:+:, CNil}

trait Model {
  def -->(implicit container:Container[Model]) = {
    val ret = container.-->(this)
  }
  def ~>(x:Model) = ModelTuple(this,x)

  def string = {
    this match {
      case Model.Str(x) => x
      case Model.Sym(x) => x.name
      case _ => this.toString
    }
  }

}

object Model {

  def Line = Str("\n")

  case object Semi extends Model
  case class Str(name:String)    extends Model
  case class Sym(name:Symbol)    extends Model
  case class WInt(int:Int)       extends Model
  case class WOpt[T <: Model](x:Option[T]) extends Model
  case class Quotes(name:Model)  extends Model
  case class Quotes3(name:Model)  extends Model
  case class FixList(items:List[Model]) extends Model
  case class FixListLine(items:List[Model]) extends Model

  case class MapIndex(o:Model, i:Model) extends Model
  case class Dictionary(i:List[ModelTuple]) extends Model

  case class DotPrefix(r:Model,l:Model) extends Model
  case class SemiEnd(r:Model) extends Model

  case class Comment(x:Model) extends Model
  case class ModelTuple(x:(Model,Model)) extends Model

  case class Return(x:Model) extends Model

  implicit def StringToModel(x:String) = Str(x)
  implicit def SymbolToModel(x:Symbol) = Sym(x)
  implicit def IntToModel(x:Int)       = WInt(x)
  implicit def OptToModel[T <: Model](x:Option[T]) = WOpt(x)
  implicit def TupleToModel(x:(Model,Model)) = ModelTuple(x)

  def ??(x:Option[Model]) = WOpt(x)

  // Statement Section
  case class Import(name:Model, alias:Option[Model] = None, gt:Boolean = false) extends Model {
    def as (model:Model) = copy(alias = Some(model) )
  }


  case object $import extends Model {
    def ~(x:Model) = Import(x)
    def ~~(x:Model) = Import(x,None, true)
  }
  // Variable Section




  // Class Section
  class MClass(val name:Model) extends Container[Model] with Model




  //case class Class(name:Model) extends Container[Model]

}
