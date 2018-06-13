package org.simplifide.template.dart

import org.simplifide.template.{Container, Template}

sealed trait DartModel {

}

object DartModel {

  case class DartImport (name:String) extends DartModel
  case class DartConst  (name:String) extends DartModel
  case class DartFile   (name:String) extends Container[DartModel] {
    def template(x:DartModel) = DartGenerator.create(x)
  }

}
