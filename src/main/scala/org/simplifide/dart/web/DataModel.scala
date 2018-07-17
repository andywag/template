package org.simplifide.dart.web

import org.simplifide.template.Container
import org.simplifide.template.FileModel.GFile
import org.simplifide.template.model.{MClassProto, Model}
import org.simplifide.template.model.dart.{DartGenerator, DartParser}

trait DataModel {
  val cla:MClassProto

  def create = {
    new Container[Model] with DartParser {
      cla.imports.foreach(x => -->(x))

      -->(cla)

    }
  }

  def createFile = {
    val gen = create
    val className = cla.name.toLowerCase()
    new GFile(className + ".dart",gen.contents(DartGenerator.create))
  }

}

object DataModel {
  case class DataModelImpl(cla:MClassProto) extends DataModel
}
