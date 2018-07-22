package org.simplifide.template.model.html

import org.simplifide.template.model.Model
import scalatags.Text.all._

trait HtmlModel extends Model {

}

object HtmlModel {

  implicit def FragToHtmlTag(x:Frag) = HtmlTag(x)

  case class HtmlFile(location:String) extends HtmlModel
  case class HtmlTag(input:Frag) extends HtmlModel
}


