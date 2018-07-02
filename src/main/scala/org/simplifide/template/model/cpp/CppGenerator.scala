package org.simplifide.template.model.cpp

import org.simplifide.template.Template
import org.simplifide.template.Template._
import org.simplifide.template.model.dart.DartModel.DartImport
import Template._
import CppModel._
import org.simplifide.template.model.MVar.{SType, VarDec}
import org.simplifide.template.model.Model._
import org.simplifide.template.model.dart.DartGenerator.IMPORT
import org.simplifide.template.model.{MFunction, MVar, Model, ModelGenerator}
import org.simplifide.template.model.Model.{Import, MClass, Str}
import MVar.$auto


object CppGenerator {

  implicit def ModelToTemplate(x:Model) = create(x)

  def create(o:Model):Template = {
    o match {
      //case Import(x)   => IMPORT ~ quotes(x) ~ NL
      case Import(x,_,c) => IMPORT ~ (if (c) surroundGt(x) else quotes(x))  ~NL
      case Pragma(x)   => PRAGMA ~ x ~ NL
      case Using(x)   => USING ~ x ~ SEMI ~ NL

      // Variable Declaration Section
      case SType(x)  => x
      case $auto  => "auto"
      case VarDec(v,x)    => v.typ ~ SP ~ v.name ~ x.map(y => " = " ~ y) ~ SEMI ~ NL

      // Function Declaration
      case x:MFunction => {
        x.output ~ SP ~ x.name ~ parenComma(x.args.map(y => create(y))) ~ curlyIndent(x.body.map(y=>create(y))) ~ NL
      } // curlyIndent(x.body.map(_.create))
      //case VarDec(v,Some(x)) => {
      //  v.typ ~ SP ~ v.name ~ " = " ~ x ~ SEMI ~ NL
     // }


      // Class Section
      case x:MClass    => CLASS ~ x.name ~ curlyIndent(x.items.toList.map(create(_)))



      //case Wrap(x)    => x
      case ImportQ(x) => IMPORT ~ surroundGt(x) ~ NL
      // Template Argument
      case CTemplate(x) => TEMPLATE ~ surroundGt( create(x))
      // Classes
      case Cla(x,y)  => CLASS ~ x ~ curlyIndent(y.map(create(_)))
      case ClaPrivate => PRIVATE ~ ":" ~NL
      case ClaPublic  => PUBLIC ~ ":" ~NL
      // Variables
      case _ => ModelGenerator.create(o)
    }
  }



}
