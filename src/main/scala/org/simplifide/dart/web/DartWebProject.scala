package org.simplifide.dart.web

import org.simplifide.dart.binding.DataModel
import org.simplifide.dart.web.Routes.RoutePath
import org.simplifide.template.FileModel
import org.simplifide.template.FileModel.{GCopy, GDir, GFile, GList}
import org.simplifide.template.model.dart.DartProject.Dependency
import org.simplifide.template.model.dart.{DartModel, DartProject, PubSpec}

trait DartWebProject  {
  val name:String
  val description:String

  val components:List[DartComponent]

  val sources:List[FileModel]
  val dependencies:List[Dependency]
  val devDependencies:List[Dependency]
  val routes:List[RoutePath]
  val models:List[DataModel]

  private lazy val componentSources = components.flatMap(x => x.createFiles)


  private lazy val main = DartMain(name)
  private lazy val routeFile     = Routes.RouteFile(routes)
  private lazy val routePathsFile = Routes.RoutePathsFile(routes)


  def create = {

    GDir(name) (
      GDir("lib") (
        GDir("src",
          GDir("models",models.map(x => x.createFile)) ::
          routeFile.createFile ::
          routePathsFile.createFile ::
          componentSources :::
          sources ),
        GList(DartAppNew(routes).createFiles)
      ),
      GDir("test"),
      GDir("web",List(
        GCopy("favicon.png","C:\\dart_projects\\toh2\\web\\favicon.png"),
        GCopy("index.html","C:\\dart_projects\\toh2\\web\\index.html"),
        main.createFile,
        GCopy("styles.css","C:\\dart_projects\\toh2\\web\\styles.css")
      )),
      PubSpec(PubSpec.Description(this.name,""),dependencies,devDependencies).create
    )
  }
}

object DartWebProject {
  val defaultDependencies = List(
    Dependency("angular", "^5.0.0-alpha+15"),
    Dependency("angular_forms", "^2.0.0-alpha"),
    Dependency("angular_router", "^2.0.0-alpha"),
    Dependency("http", "^0.11.0"),
    Dependency("stream_transform", "^0.0.6")
  )
  val defaultDevDependencies = List(
    Dependency("angular_test",        "^2.0.0-alpha"),
    Dependency("build_runner",        "^0.8.10"),
    Dependency("build_test",          "^0.10.2"),
    Dependency("build_web_compilers", "^0.4.0"),
    Dependency("mockito",             "^3.0.0-beta+1"),
    Dependency("pageloader",          "^3.0.0-alpha"),
    Dependency("test",                "^1.0.0")
  )
}