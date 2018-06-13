package org.simplifide.template

import java.io.File

import org.scalatest.FlatSpec
import org.simplifide.template.FileModel.{GDir, GFile}
import org.simplifide.template.dart.{DartGenerator, DartModel}
import org.simplifide.template.dart.DartModel.{DartFile, DartImport}

class DartTest extends FlatSpec {

  object DartBase extends Container[DartModel] {
    ->(DartImport("First"))
    ->(DartImport("Second"))

    override def template(x: DartModel): Template = DartGenerator.create(x)
  }

  val project = GDir("base")(
    GDir("src")(
      GFile("temp.dart",DartBase)
    ),
    GDir("test")
  )

  FileModel.create(project,new File("C:\\scala_projects\\template\\test_results"))

}
