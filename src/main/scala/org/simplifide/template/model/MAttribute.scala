package org.simplifide.template.model

import org.simplifide.template.Container
import org.simplifide.template.model.MVar.MType

trait MAttribute extends Model {
  val name:Model
  def body:List[Model]
}

object MAttribute {

  class MAttr(val name:Model) extends Container[Model] with MAttribute {
    def body = items.toList
  }

  case class Impl(name:Model, body:List[Model]) extends MAttribute


  def -@(name:Model)(items:Model*) = Impl(name,items.toList)


}