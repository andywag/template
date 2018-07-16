package org.simplifide.template

import java.io.File
import java.nio.file.Files

import org.scalatest.FlatSpec
import org.simplifide.dart.binding.Binding
import org.simplifide.dart.binding.Binding._
import org.simplifide.dart.web.DataModel.DataModelImpl
import org.simplifide.dart.web.Routes.RoutePath
import org.simplifide.dart.web.{DartCommands, DartComponent, DartWebProject, DartWebStyles}
import org.simplifide.template.model.Model
import org.simplifide.template.model.css.CssModel.CssFile
import org.simplifide.template.model.dart.{DartConstants, DartProject}
import org.simplifide.template.model.dart.DartProject.Dependency
import org.simplifide.template.model.html.HtmlModel
import org.simplifide.utils.Utils
import scalatags.Text.all._


object DartProjectExample extends DartWebProject {
  val name        = "dart_example"
  val description = "basic dart starting point example"
  val sources = List()
  val dependencies = List(
    Dependency("angular", "^5.0.0-alpha+15"),
    Dependency("angular_forms", "^2.0.0-alpha"),
    Dependency("angular_router", "^2.0.0-alpha"),
    Dependency("http", "^0.11.0"),
    Dependency("stream_transform", "^0.0.6")
  )
  val devDependencies = List(
    Dependency("angular_test",        "^2.0.0-alpha"),
    Dependency("build_runner",        "^0.8.10"),
    Dependency("build_test",          "^0.10.2"),
    Dependency("build_web_compilers", "^0.4.0"),
    Dependency("mockito",             "^3.0.0-beta+1"),
    Dependency("pageloader",          "^3.0.0-alpha"),
    Dependency("test",                "^1.0.0")
  )

  val components = List(TestComponent1, TestComponent2, TestComponent3)
  val routes     = List(RoutePath("test1",TestComponent1),
    RoutePath("test2",TestComponent2),
    RoutePath("test3",TestComponent3),
  )

  val models = ProjectModel.models

}

object ProjectModel {
  import io.circe.generic.auto._

  case class Person(x: String, d: Option[String], id: String = "Person")
  case class Result(x: String, y: Person, id: String = "Result")
  case class Event(name: String, event: String, results: List[Result], id: String = "Event")

  val person = Person(B_STRING,Some(B_STRING))
  val result = Result(B_STRING,person)
  val event = Event(B_STRING,B_STRING,List(result))

  val models = Binding.createClasses(event).map(x => DataModelImpl(x))

}
object TestComponent1 extends DartComponent {
  val name     = "test_a"
  override val style = Some(CssFile(DartWebStyles.MyInline))
  val template:HtmlModel = h1(p("Here I am 1"))
  val directives:List[Model] = List(DartConstants.ROUTER_DIRECTIVE)
  val providers:List[Model] = List()
}

object TestComponent2 extends DartComponent {
  val name     = "test_b"

  val template:HtmlModel = h1(p("Here I am 12"))
  val directives:List[Model] = List(DartConstants.ROUTER_DIRECTIVE)
  val providers:List[Model] = List()
}

object TestComponent3 extends DartComponent {
  val name     = "test_c"

  val template:HtmlModel = h1(p("Here I am 13"))
  val directives:List[Model] = List(DartConstants.ROUTER_DIRECTIVE)
  val providers:List[Model] = List()
}


class DartProjectTest extends FlatSpec{
  val base =  s"C:\\dart_projects\\generated\\${DartProjectExample.name}"

  FileModel.create(DartProjectExample.create,new File(base).getParentFile)

  DartCommands.setup(s"$base")
  DartCommands.build(base)
  DartCommands.run(22222,s"$base")

  assert (true === true)

}
