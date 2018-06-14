package org.simplifide.template

import Template._

object TemplateGenerator {

  def create(template:Template):String = {
    template match {
      case Template.Empty =>          ""
      case Template.StringValue(x)  =>  x
      case Template.And(l,r)        => s"${create(l)}${create(r)}"
      case Template.Indent(x)       => s"   ${create(x)}"
      case Template.ListTemplate(x) => {
        x.map(create(_)).mkString("")
      }
    }
  }

}
