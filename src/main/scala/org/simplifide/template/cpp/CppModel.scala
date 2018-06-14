package org.simplifide.template.cpp

import org.simplifide.template
import org.simplifide.template.Template
import org.simplifide.template.dart.DartModel

sealed trait CppModel {

}

object CppModel {

  val PRAGMA    = "pragma "
  val IMPORT    = "import "
  val USING     = "using "
  val NAMESPACE = "namespace "
  val CLASS     = "class "
  val PUBLIC    = "public "
  val PRIVATE   = "private "
  val TEMPLATE  = "template "

  implicit def TemplateToWrap(x:Template) = Wrap(x)


  case class Wrap(t:Template) extends CppModel
  case class Pragma(name:Template) extends CppModel
  case class ImportQ(name:Template) extends CppModel
  case class ImportG(name:Template) extends CppModel
  case class Using(name:Template) extends CppModel

  // Template Calls
  case class CTemplate(items:CppModel) extends CppModel
  // Class Funtions
  case class Cla(name:Template,items:List[CppModel]) extends CppModel
  case object ClaPublic extends CppModel
  case object ClaPrivate extends CppModel

  trait VarType extends CppModel
  case class VarTypeStr(template:Template) extends VarType
  case class Var(typ:VarType,name:Template)
  case class VarDec(v:Var) extends CppModel
  def VarDec(t:String, v:String) = {
    val newVar = Var(VarTypeStr(Template.StringValue(t)),Template.StringValue(v))
    new VarDec(newVar)
  }

  def Cla(name:Template)(items:CppModel*) = new Cla(name,items.toList)
}


