package org.simplifide.template.model

import org.simplifide.template.Template
import org.simplifide.template.model.Model.{Str, Sym, WInt, WOpt}

object ModelGenerator {
  def create(o: Model): Template = {
    o match {
      case Str(x)  => x
      case Sym(x)  => x.name
      case WInt(x) => x.toString
      case WOpt(x)  => Template.Opt(x.map(y => create(y)))
      case Model.Quotes(x) => Template.quotes(create(x))
      case Model.Quotes3(x) => Template.quotes(create(x))

      case _      => Template.Empty
    }
  }
}
