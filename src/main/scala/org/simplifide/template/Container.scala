package org.simplifide.template

import org.simplifide.template.model.Model

import scala.collection.mutable.ListBuffer

trait Container[T] {
  implicit val container:Container[T] = this

  val items = new ListBuffer[T]()

  def -->[S <: T](x:S):S     = {items.append(x); x}

  def contents(extra:List[T]=List())(implicit create:(T)=>Template) = {
    val all = extra ::: items.toList
    val templates = all.map(x => create(x))
    templates.map(x => TemplateGenerator.create(x)).mkString("")
  }


}
