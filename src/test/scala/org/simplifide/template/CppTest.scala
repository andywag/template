package org.simplifide.template

import java.io.File

import org.scalatest.FlatSpec
import org.simplifide.template.FileModel.{GDir, GFile}
import org.simplifide.template.model.cpp.{CppGenerator, CppModel}
import org.simplifide.template.model.dart.DartModel.DartImport
import org.simplifide.template.model.dart.{DartGenerator, DartModel}
import org.simplifide.template.model.Model
import org.simplifide.template.model.Model.Import

import Model._
import org.simplifide.template.model.MVar._

import org.simplifide.template.model.MFunction.MFunc
import org.simplifide.template.model.MVar.{$auto, SType, Var}
import org.simplifide.template.model.cpp.CppModel.{$pragma, $using, Pragma}

object TestClass extends Container[Model] {

  $pragma ~ "once" -->;
  $import ~ "test2.temp" -->;
  Line -->;
  $using ~ "namespace std" -->;
  Line -->;

  object Func extends MFunc("base",SType("int")) {
    val args = List(Var("int",T("t")))

  }
  Func -->

  val foo  = --> (T("int") ~ "foo" ~= 10)
  val base = --> ($auto ~ "foo2")

  object Internal extends MClass("Basic") {
    val alpha = -->(T("int") ~ "alpha")
  }

  Internal -->


}

class CppTest extends FlatSpec {

  val project = GDir("base")(
    GDir("src")(
      GFile("temp.cpp",TestClass.contents(CppModel.cWriter))
    ),
    GDir("test")
  )
  FileModel.create(project,new File("C:\\scala_projects\\template\\cpp_results"))

  val project2 = GDir("base")(
    GDir("src")(
      GFile("temp.dart",TestClass.contents(DartModel.dartWriter))
    ),
    GDir("test")
  )
  FileModel.create(project2,new File("C:\\scala_projects\\template\\dart_results"))

}

class DartTest extends FlatSpec {


}