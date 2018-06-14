package org.simplifide.template

import java.io.File

import org.scalatest.FlatSpec
import org.simplifide.template.FileModel.{GDir, GFile}
import org.simplifide.template.cpp.{CppGenerator, CppModel}
import org.simplifide.template.dart.DartModel.DartImport
import org.simplifide.template.dart.{DartGenerator, DartModel}
import CppModel._
import Template._

class CppTest extends FlatSpec {

  object CppBase extends Container[CppModel] {
    ->(Pragma("once"))
    ->(ImportG("blitz_array.h"))
    ->(ImportG("vector"))
    ->(ImportG("iostream"))
    ->(ImportG("string"))
    ->(USING ~ NAMESPACE ~ "helium_phy")
    ->(Template.NL)
    ->(Template.NL)

    val a = Cla("RxConstructStruct")(
      CppModel.VarDec("std::string","here"),
      CppModel.VarDec("int","test"),
      CppModel.VarDec("int","test")
    )



    override def template(x: CppModel): Template = CppGenerator.create(x)
  }

  val project = GDir("base")(
    GDir("src")(
      GFile("temp.cpp",CppBase)
    ),
    GDir("test")
  )

  FileModel.create(project,new File("C:\\scala_projects\\template\\cpp_results"))

}
