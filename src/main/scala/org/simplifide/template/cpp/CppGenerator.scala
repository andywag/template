package org.simplifide.template.cpp

import org.simplifide.template.Template
import org.simplifide.template.Template._
import org.simplifide.template.dart.DartModel.DartImport

import Template._
import CppModel._

object CppGenerator {



  def create(o:CppModel):Template = {
    o match {
      case CppModel.Wrap(x)    => x
      case CppModel.Pragma(x)  => PRAGMA ~ x ~ NL
      case CppModel.ImportG(x) => IMPORT ~ quotes(x) ~ NL
      case CppModel.ImportQ(x) => IMPORT ~ surroundGt(x) ~ NL
      case CppModel.Using(x)   => USING ~ x ~ NL
      // Template Argument
      case CppModel.CTemplate(x) => TEMPLATE ~ surroundGt( create(x))
      // Classes
      case CppModel.Cla(x,y)  => CLASS ~ x ~ curlyIndent(y.map(create(_)))
      case CppModel.ClaPrivate => PRIVATE ~ ":" ~NL
      case CppModel.ClaPublic  => PUBLIC ~ ":" ~NL
      // Variables
      case CppModel.VarTypeStr(x) => x
      case CppModel.VarDec(v) => create(v.typ) ~ " " ~ v.name ~ ";" ~ NL


    }
  }

}
