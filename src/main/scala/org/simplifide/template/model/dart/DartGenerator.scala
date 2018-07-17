package org.simplifide.template.model.dart

import org.simplifide.template.model.dart.DartModel.{DartImport, Package}
import org.simplifide.template.Template._
import org.simplifide.template.model.cpp.CppGenerator.create
import org.simplifide.template.model._
import org.simplifide.template.{Template, TemplateGenerator}
import Model._
import MVar._
import org.simplifide.template.model.MFunction.{Call, Lambda, SFunction}
import org.simplifide.template.model.ModelGenerator.create
import org.simplifide.template.model.cpp.CppModel.CLASS
import org.simplifide.template.model.html.HtmlModel.HtmlTag


object DartGenerator {

  val IMPORT = "import "

  implicit def ModelToTemplate(x:Model) = create(x)


  def create(o:Model):Template = {
    o match {
      // Basic
      case Model.Semi      => ";"
      case Model.Quotes(x) => "'" ~ x ~ "'"
      case Model.Quotes3(x) => "'''" ~ x ~ "'''"
      case Model.FixList(x) => "[" ~ commaSep(x.map(y => create(y))) ~ "]"
      case Model.FixListLine(x) => "[" ~ commaSep(x.map(y => create(y))) ~ "]"
      case DotPrefix(l,r) => create(l) ~ "." ~ create(r)
      case SemiEnd(l) => create(l) ~ ";" ~ NL
      // Core
      case x:DartConstants.Directive => x.name

      case Import(x,None,_) => IMPORT ~ singlequotes(x) ~ ";" ~NL
      case Import(x,Some(y),_) => IMPORT ~ singlequotes(x) ~ " as " ~ y ~ ";" ~NL
      case Package(p,x)   => "package" ~ COLON ~ p ~ "/" ~ x
      case x:MAttribute   => {
        val result = if (x.body.size > 0) "@" ~ x.name ~ parenIndentNL(x.body.map(y => create(y) ~ "," ~ NL)) ~NL
        else  "@" ~ x.name ~ "()" ~NL
        result
      }

      // Type Section
      case TypeAnd(x,y) => {
        x ~ SP ~ y
      }
      case NoType    => ""
      case SType(x)  => x
      case $auto   => "var"
      case $final  => "final"
      case Generic(outer,inner) => {
        create(outer) ~ "<" ~ commaSep(inner.map(create(_))) ~ ">"
      }

      // Variable Section
      case VarDec(v,x)    => {
        v.typ ~ SP ~ v.name ~ x.map(y => " = " ~ y) ~ SEMI ~ NL
      }
      case Var(name,_) => name
      // Function Section
      case x:MFunction => {
        x.output ~ SP ~ x.name ~ parenComma(x.args.map(y => create(y))) ~ curlyIndent(x.body.map(y=>create(y))) ~ NL
      }
      case x:Lambda => { //   RoutePath get heroes => paths.heroes;
        x.typ ~ SP ~ x.name ~ SP ~ x.input ~ " => " ~ x.func ~ SEMI ~ NL
      }
      case Call(name,args) => name ~ parenComma(args.map(y => create(y)))
      case ModelTuple(x)   => x._1 ~ " : " ~ x._2

      case HtmlTag(x) => {
        x.render
      }

      case x:MClass         => CLASS ~ x.name ~ curlyIndent(x.items.toList.map(create(_)))
      case x:MClassProto    => create(x.create)


      // Component Generation

      case _           => ModelGenerator.create(o)
    }
  }


}
