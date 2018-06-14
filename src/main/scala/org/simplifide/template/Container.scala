package org.simplifide.template

import scala.collection.mutable.ListBuffer

trait Container[T] extends FileModel.Generator{

  def template(x:T):Template

  val items = new ListBuffer[T]()

  def ->(x:T)     = {items.append(x); x}
  def -->(x:T)     = { x}

  def contents = {
    val templates = items.toList.map(x => template(x))
    templates.map(x => TemplateGenerator.create(x)).mkString("")
  }
    //items.map(TemplateGenerator.create(_)).mkString("")


}
