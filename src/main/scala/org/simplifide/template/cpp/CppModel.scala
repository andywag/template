package org.simplifide.template.cpp

import org.simplifide.template
import org.simplifide.template.{Container, Template, TemplateGenerator}
import org.simplifide.template.dart.DartModel
import org.simplifide.template.model.Model



object CppModel {

  def cWriter(x:Model) = CppGenerator.create(x)


  val PRAGMA    = "pragma "
  val IMPORT    = "import "
  val USING     = "using "
  val NAMESPACE = "namespace "
  val CLASS     = "class "
  val PUBLIC    = "public "
  val PRIVATE   = "private "
  val TEMPLATE  = "template "

  case class Pragma(name:Model) extends Model
  object $pragma {
    def ~(x:Model) = Pragma(x)
  }

  case class Using(name:Model) extends Model
  object $using {
    def ~(x:Model) = Using(x)
  }


  //implicit def TemplateToWrap(x:Template) = Wrap(x)


  //case class Wrap(t:Template) extends Model
  case class ImportQ(name:Model) extends Model
  case class ImportG(name:Model) extends Model

  // Template Calls
  case class CTemplate(items:Model) extends Model
  // Class Funtions
  case class Cla(name:Template,items:List[Model]) extends Model
  case object ClaPublic extends Model
  case object ClaPrivate extends Model

  /*
  trait VarType extends Model
  case class VarTypeStr(template:Template) extends VarType
  case class Var(typ:VarType,name:Template)
  case class VarDec(v:Var) extends Model
  def VarDec(t:String, v:String) = {
    val newVar = Var(VarTypeStr(Template.StringValue(t)),Template.StringValue(v))
    new VarDec(newVar)
  }
*/

  def Cla(name:Template)(items:Model*) = new Cla(name,items.toList)
}


