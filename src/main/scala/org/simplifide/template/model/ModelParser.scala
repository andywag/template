package org.simplifide.template.model

import org.simplifide.template.Container
import org.simplifide.template.model.MVar.VarDec
import org.simplifide.template.model.Model.{Import, Semi}

trait ModelParser {
  self:Container[Model] =>

  def blankLine = {
    -->(Model.Line)
  }

  def importClass(dart:MClassFile, local:Option[MClassFile]) = {
    def relative(dest:String,source:String) = {
      val s = source.split("/")
      val d = dest.split("/")

      val comb = d.zipWithIndex.map(x => {
        if (x._2 >= s.size) Some(x._1)
        else if (x._1 == s(x._2)) Some("..")
        else Some(x._1)
      }).flatten
      comb.mkString("/") + "/"
    }
    val res1 = local.map(x => relative(dart.classPath,x.classPath)).getOrElse(dart.classPath)
    val res = res1.replace("src/","")
    -->(Model.Import(res+ dart.fileName))
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
