package org.simplifide.template

import org.simplifide.template
import org.simplifide.template.Template.Repeater

/**
 * Created by andy.wagner on 9/18/2014.
 */
trait Template {

  def ~(input:Template)                               = new Template.And(this,input)
  def ??(condition:Boolean, input:Template)           = new Template.Qu(this,condition,input)
  def ??(x:Option[Template])                          = new template.Template.Opt(x)
}

object Template   {
  implicit def StringToTemplate(value:String) = new StringValue(value)
  implicit def ListToTemplate(value:Seq[Template]) = new ListTemplate(value)
  implicit def OptToTemplate(value:Option[Template]) = new Opt(value)

  case class Opt(val input:Option[Template]) extends Template

  val SEMI = StringValue(";")
  val SP   = StringValue(" ")
  val NL = StringValue("\n")

  def sep(input:List[Template],seperator:Template) = new Repeater(input,seperator)
  def commaSep(input:List[Template]) = sep(input,",")
  def parenComma(input:List[Template]) = {
    paren(commaSep(input))
  }

  def rep(input:List[Template])                   = new Template.Repeater(input,"")
  def indent(input:Template) = new Indent(input)

  def surround(input:Template, front:Template) = front ~ input ~ front
  def surround(input:Template, front:Template, back:Template) = front ~ input ~ back
  def curly(input:Template) = surround(input,"{","}")
  def paren(input:Template) = surround(input,"(",")")
  def brack(input:Template) = surround(input,"[","]")

  def quotes(input:Template) = surround(input,"\"","\"")
  def singlequotes(input:Template) = surround(input,"'","'")

  def surroundGt(input:Template) = surround(input,"<",">")

  def qu(condition:Boolean, tr:Template, fa:Template) = new Question(condition,tr, fa)

  def curlyIndent(x:Template*) = "{" ~ NL ~ x.map(y => indent(y)) ~ NL ~ "}"
  def curlyIndent(x:List[Template]) = "{" ~ NL ~ x.map(y => indent(y)) ~ NL ~ "}"

  //def opt(option:Option[Template])                 = new Template.Opt(option)
  //def opt(template:Template, condition:Boolean)    = new Template.Opt(if (condition) Some(template) else None)


  def L(templates:List[Template]) = new ListTemplate(templates)

  case object Empty extends Template
  case class StringValue(value:String) extends Template
  case class ListTemplate(input:Seq[Template]) extends Template
  case class And(l:Template,r:Template) extends Template
  case class Indent(val input:Template) extends Template
  case class Qu(input1:Template, condition:Boolean, input:Template) extends Template
  case class Question(input:Boolean, in1:Template, in2:Template) extends Template
  case class Repeater(val input:List[Template],val seperator:Template) extends Template {

    /*
    def create = {
      input match {
        case ListTemplate(List())   => ""
        case ListTemplate(input)    => input.reduceLeft(_~seperator~_).create
        case _                      => input.create
      }
    }
    */
  }

  /*


  case class Surround(input:Template,first:Template, last:Template) extends Template {
    def create = (first ~ input ~ last).create
  }
  */

}
