package org.simplifide.template

import java.io.File

import org.scalatest.FlatSpec
import org.simplifide.template.model.dart.DartProject
import org.simplifide.template.model.dart.DartProject.Dependency

object DartProjectExample extends DartProject {
  val name        = "dart_example"
  val description = "basic dart starting point example"
  val sources = List()
  val dependencies = List(
    Dependency("angular","^5.0.0-alpha+13"),
    Dependency("angular_components","^0.9.0-alpha+13"),
  )
  val devDependencies = List(
    Dependency("angular_test", "^2.0.0-alpha+11"),
    Dependency("build_runner", "^0.8.9"),
    Dependency("build_test", "^0.10.2"),
    Dependency("build_web_compilers","^0.4.0"),
    Dependency("test","^0.12.38")
  )



}

class DartProjectTest extends FlatSpec{
  FileModel.create(DartProjectExample.create,new File("C:\\scala_projects\\template\\dart_project_results"))
  assert (true === true)
}
