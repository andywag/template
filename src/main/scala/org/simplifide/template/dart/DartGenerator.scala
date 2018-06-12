package org.simplifide.template.dart

import org.simplifide.template.dart.DartModel.DartImport

import org.simplifide.template.Template._

object DartGenerator {

  val IMPORT = "import "

  def create(o:DartModel): Unit = {
    o match {
      case DartImport(x) => IMPORT ~ x ~ ";"
    }
  }

}
