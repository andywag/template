package org.simplifide.template.model.dart

import org.simplifide.template.model.dart.DartModel.{DartImport, Package}
import org.simplifide.template.Template._
import org.simplifide.template.model.cpp.CppGenerator.create
import org.simplifide.template.model.{MAttribute, MVar, Model, ModelGenerator}
import org.simplifide.template.{Template, TemplateGenerator}
import Model._
import org.simplifide.template.model.MVar.{SType, VarDec}
import MVar.$auto


object DartGenerator {

  val IMPORT = "import "

  implicit def ModelToTemplate(x:Model) = create(x)


  def create(o:Model):Template = {
    o match {
      case Import(x,None,_) => IMPORT ~ singlequotes(x) ~ ";" ~NL
      case Import(x,Some(y),_) => IMPORT ~ singlequotes(x) ~ " as " ~ y ~ ";" ~NL
      case Package(p,x)   => "package" ~ COLON ~ p ~ "/" ~ x
      case x:MAttribute   => "@" ~ x.name ~ parenIndent(x.body.map(y => create(y) ~ ","))

      // Variable Section
      case SType(x)  => x
      case $auto  => "var"

      case VarDec(v,x)    => v.typ ~ SP ~ v.name ~ x.map(y => " = " ~ y) ~ SEMI ~ NL
      case _           => ModelGenerator.create(o)
    }
  }


}
