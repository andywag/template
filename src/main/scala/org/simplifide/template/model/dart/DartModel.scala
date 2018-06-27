package org.simplifide.template.model.dart

import org.simplifide.template.model.cpp.CppGenerator
import org.simplifide.template.model.Model
import org.simplifide.template.{Container, Template}

sealed trait DartModel {

}

object DartModel {

  def dartWriter(x:Model) = DartGenerator.create(x)

  class CppContainer extends Container[Model] {
    def create(x:Model) = CppGenerator.create(x)
  }

  implicit def TemplateToWrap(x:Template) = Wrap(x)

  case class Wrap(t:Template) extends DartModel
  case class DartImport (name:String) extends DartModel
  case class DartConst  (name:String) extends DartModel

  case class Package(packName:String, name:String) extends Model


}
