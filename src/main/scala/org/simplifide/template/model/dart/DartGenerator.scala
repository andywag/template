package org.simplifide.template.model.dart

import org.simplifide.template.model.dart.DartModel.{DartBuiltIn, DartImport, DartPackage}
import org.simplifide.template.Template._
import org.simplifide.template.model.cpp.CppGenerator.create
import org.simplifide.template.model._
import org.simplifide.template.{Template, TemplateGenerator}
import Model._
import MVar._
import MType._
import org.simplifide.template.model.MFunction.{AnonLambda, Call, Lambda, SFunction}
import org.simplifide.template.model.MType.{Generic, NoType, SType, TypeAnd}
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
      case Model.MArray(x) => "[" ~ commaSep(x.map(create(_))) ~ "]"

      case Import(x,None,_) => IMPORT ~ singlequotes(x) ~ ";" ~NL
      case Import(x,Some(y),_) => IMPORT ~ singlequotes(x) ~ " as " ~ y ~ ";" ~NL
      case DartPackage(p,x)   => "package" ~ COLON ~ p ~ "/" ~ x
      case DartBuiltIn(x) => "dart:" ~ x
      case x:MAttribute   => {
        val result = if (x.body.size > 0) "@" ~ x.name ~ parenIndentNL(x.body.map(y => create(y) ~ "," ~ NL)) ~NL
        else  "@" ~ x.name ~ "()" ~NL
        result
      }

      // Type Section
      case TypeAnd(x,y) => {
        x ~ SP ~ y
      }
      case x:BaseTypes => x.name
      case NoType    => ""
      case SType(x)        => x
      case x:DefinedType   => x.name
      case x:MClassType   => x.cla.name

      case $auto   => "var"
      case $final  => "final"
      case $static  => "static"

      case DotSelect(l,r) => l ~ "."  ~ r
      case BrackSelect(l,r) => l ~ "[" ~ singlequotes(r) ~ "]"

      case x:Generic => {
        create(x.o) ~ "<" ~ commaSep(x.i.map(create(_))) ~ ">"
      }

      case Wait(x) => "await" ~ SP ~create(x)
      case As(x,y)   =>  x ~ " as " ~ y

      case x:MTryCatch.Try => "try" ~ SP ~ curlyIndent(x.items.map(create(_)))
      case x:MTryCatch.Catch => "catch" ~ paren(x.e) ~ curlyIndent(x.items.map(create(_)))

      // Variable Section
      // FIXME : Need to Fix the Default Section
      case VarDec(v,x,eol)    => {
        def or(s:Option[Model],t:Option[Model]) = {
          (s,t) match {
            case (Some(_),_) => s
            case (_,Some(_)) => t
            case _           => None
          }
        }
        if (eol) v.typ ~ SP ~ v.name ~ or(x,v.default).map(y => " = " ~ y) ~ SEMI ~ NL
        else     v.typ ~ SP ~ v.name ~ or(x,v.default).map(y => " = " ~ y)
      }

      case Var(name,_,_) => name


      case MapIndex(o,i) => create(o) ~ "[" ~ singlequotes(i) ~ "]"
      // Function Section
      case x:MFunction.Constructor => {
        val s = if (x.sup.size > 0) SP ~ ":" ~ SP ~ create(MFunction.Call("super",x.sup))
        else SP
        x.name ~ parenComma(x.argList.map(y => create(y))) ~ s ~ curlyIndent(x.body.map(y=>create(y)))
      }
      case x:MFunction => {
        val post = x.postfix.map(SP ~ _ ~ SP).getOrElse(SP)
        val pre  = x.prefix.map(SP ~ _ ~ SP ).getOrElse(SP)
        pre ~ x.output ~ SP  ~ x.name ~ parenComma(x.argList.map(y => create(y))) ~post~ curlyIndent(x.body.map(y=>create(y)))
      }

      case x:Lambda => { //   RoutePath get heroes => paths.heroes;
        x.typ ~ SP ~ x.name ~ SP ~ x.input ~ " => " ~ x.func ~ SEMI ~ NL
      }
      case AnonLambda(x,y) => paren(create(x)) ~ " => " ~ y
      case Call(name,args,eol) => name ~ parenComma(args.map(y => create(y))) ~ (if (eol) ";" ~ Model.Line else Model.Str(""))

      case ModelTuple(x)   => x._1 ~ " : " ~ x._2

      case HtmlTag(x) => {
        x.render
      }

      case x:MClass        => {

        val parent = if (x.parents.size == 0) SP
        else if (x.parents.size == 1) SP ~ "extends" ~ SP ~ create(x.parents(0).name) ~ SP
        else SP ~ "extends" ~ SP ~ create(x.parents(0).name) ~ SP // FIXME Not Completed


        CLASS ~ x.name ~ parent ~ curlyIndent(x.body.toList.map(create(_)))
      }

      //case x:MClassProto    => create(x.create)
      case Dictionary(x)    => {
        val tuples = x.map(y => (singlequotes(y.x._1),y.x._2))
        "{" ~ commaSep(tuples.map(x => x._1 ~ " : " ~ x._2)) ~ "}"
      }
      case Return(x)        => "return " ~ create(x) ~ SEMI ~ NL

      // Component Generation

      case _           => ModelGenerator.create(o)
    }
  }


}
