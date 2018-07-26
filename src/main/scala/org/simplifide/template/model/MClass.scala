package org.simplifide.template.model

import org.simplifide.template.Container
import org.simplifide.template.model.MClass.MClassBase


trait MClass extends Model{
  val name:String
  def body:List[Model]

  val members:List[MVar] = List()
  val parents:List[MClass] = List()
}

object MClass {
  class MClassBase(val name:String) extends Container[Model] with MClass {
    def body = items.toList
  }
}


