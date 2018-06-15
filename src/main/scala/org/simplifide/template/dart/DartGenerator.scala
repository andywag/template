package org.simplifide.template.dart

import org.simplifide.template.dart.DartModel.DartImport
import org.simplifide.template.Template._
import org.simplifide.template.cpp.CppGenerator.create
import org.simplifide.template.model.{Model, ModelGenerator}
import org.simplifide.template.model.Model.{Import, SType, VarDec}
import org.simplifide.template.{Template, TemplateGenerator}
import Model._

object DartGenerator {

  val IMPORT = "import "

  implicit def ModelToTemplate(x:Model) = create(x)


  def create(o:Model):Template = {
    o match {
      case Import(x,_) => IMPORT ~ singlequotes(x) ~ ";" ~NL

      // Variable Section
      case SType(x)  => x
      case $auto  => "var"

      case VarDec(v,x)    => v.typ ~ SP ~ v.name ~ x.map(y => " = " ~ y) ~ SEMI ~ NL

      case _           => ModelGenerator.create(o)
    }
  }


}
