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
