package org.simplifide.template.dart

sealed trait DartModel {

}

object DartModel {

  case class DartImport (name:String) extends DartModel

}
