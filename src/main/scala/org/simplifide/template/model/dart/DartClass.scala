package org.simplifide.template.model.dart

import org.simplifide.template.Container
import org.simplifide.template.model.{MClass, MFunction, Model}
import org.simplifide.template.model.Model.Str

trait DartClass extends MClass  {

}

object DartClass {
  class DartClassBase(val name:String) extends DartClass with Container[Model] with DartParser {

    def body = {
      val declarations = members.map(x => x.createDec)
      declarations ::: List(dartConstructor(this)) ::: items.toList
    }
  }

  def dartConstructor(cla:DartClass) =  {
    val mem = cla.members.filter(x => x.default==None).map(x => Model.DotPrefix(s"this",x.name))
    MFunction.Call(cla.name,mem, eol = true)
  }

}
