package org.simplifide.template.model.css

import org.simplifide.template.Container
import org.simplifide.template.FileModel.GFile
import org.simplifide.template.model.Model
import scalacss.DevDefaults._  // Always use dev settings


sealed trait CssModel

object CssModel extends Model {

  case class CssInline(input:StyleSheet.Standalone) extends CssModel
  case class CssFile(input:StyleSheet.Standalone) extends CssModel

  def createFile(x:CssModel, name:String) = {
    x match {
      case CssFile(x)   => {
        val contents = x.render.toString
        Some(GFile(name,contents))
      }
      case CssInline(x) => None
    }
  }

  //  styleUrls: ['app_component.css'],
  import Model._
  def createStyle(x:CssModel, name:Model) = {
    x match {
      case CssFile(x)   => 'styleUrls ~> Model.FixList(List(Model.Quotes(name)))
      case CssInline(x) => 'style ~> name
    }
  }


}
