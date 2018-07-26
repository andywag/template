package org.simplifide.template.model.dart

import org.simplifide.template.model.{MClass, MFunction}

trait DartClass extends MClass {

}

object DartClass {
  case class DartClassBase(name:String) extends DartClass {

    def body = {
      val declarations = members.map(x => x.createDec)
      declarations
    }
  }

  case class DartConstructor(cla:DartClass)  {
    val mem = cla.members.map(x => s"x.name)
    MFunction.Call(cla.name,)
  }

}
