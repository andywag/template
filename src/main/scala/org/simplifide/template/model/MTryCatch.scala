package org.simplifide.template.model

import org.simplifide.template.Container

trait MTryCatch {

}

object MTryCatch {
  class Try extends Container[Model] with Model {

  }
  class Catch(val e:Model) extends Container[Model] with Model {

  }
}
