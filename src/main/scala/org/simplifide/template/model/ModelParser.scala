package org.simplifide.template.model

import org.simplifide.template.Container
import org.simplifide.template.model.MVar.VarDec
import org.simplifide.template.model.Model.Semi

trait ModelParser {
  self:Container[Model] =>

  def blankLine = {
    -->(Model.Line)
  }

  def imp(input:Model) = {
    -->(Model.Import(input))
  }
  def --@(name:Model)(items:Model*) = {
    -->(MAttribute.Impl(name,items.toList))
  }

  def fun(name:Model, args:List[Model]) = MFunction.Call(name,args)

  def call(name:Model, args:List[Model], eol:Boolean = false) = {
    -->(fun(name,args))
    if (eol) -->(Semi)

  }

  def dec(v:VarDec) = {
    -->(v)
    v.v
  }

}
