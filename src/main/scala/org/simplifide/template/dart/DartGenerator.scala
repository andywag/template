package org.simplifide.template.dart

import org.simplifide.template.dart.DartModel.DartImport
import org.simplifide.template.Template._
import org.simplifide.template.{Template, TemplateGenerator}

object DartGenerator {

  val IMPORT = "import "

  def create(o:DartModel):Template = {
    o match {
      case DartImport(x) => IMPORT ~ x ~ ";"
    }
  }

}
