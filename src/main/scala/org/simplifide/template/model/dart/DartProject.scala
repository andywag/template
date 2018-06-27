package org.simplifide.template.model.dart

import org.simplifide.template.{Container, FileModel}
import org.simplifide.template.FileModel.{GCopy, GDir, GFile}
import org.simplifide.template.model.MAttribute.MAttr
import org.simplifide.template.model.Model
import org.simplifide.template.model.Model.{$import, Comment, Quotes}
import org.simplifide.template.model.dart.DartProject.{DartMain, Dependency, PubSpec}
import org.simplifide.template.model.yaml.{YamlModel, YamlParser}

trait DartProject {

  val name:String
  val description:String

  val sources:List[FileModel]
  val dependencies:List[Dependency]
  val devDependencies:List[Dependency]


  def create = {

    GDir(name) (
      GDir("lib") (
        GDir("src",sources)
      ),
      GDir("test"),
      GDir("web",List(
        GCopy("favicon.png","C:\\scala_projects\\first_dart\\web\\favicon.png"),
        GCopy("index.html","C:\\scala_projects\\first_dart\\web\\index.html"),
        GFile("main.dart",DartMain.contents(DartModel.dartWriter)),
        GCopy("styles.css","C:\\scala_projects\\first_dart\\web\\styles.css")
      )),
      PubSpec(this).create
    )
  }

}

object DartProject {

  case class Dependency(name: String, version: String)

  case class PubSpec(project: DartProject) extends Container[Model] with YamlParser {

    'name ->> project.name
    'description ->> project.description
    -->(Comment("Version: 1.0"))
    -->(Comment("Homepage: "))
    -->(Comment("Author : Andy Wagner"))
    -->(Model.Line)
    'environment ->> List(
      'sdk >>> Quotes(">=2.0.0-dev.55.0 <2.0.0"))
    -->(Model.Line)
    if (project.dependencies.length > 0) {
      val deps = project.dependencies.map(x => x.name >>> x.version)
      'dependencies ->> deps
    }
    -->(Model.Line)
    if (project.devDependencies.length > 0) {
      'dev_dependencies ->> project.devDependencies.map(x => x.name >>> x.version)
    }

    def create = {
      val contents = this.contents(YamlModel.cWriter)
      new GFile("pubspec.yaml", contents)
    }
  }

  /*
  import 'package:angular/angular.dart';
  import 'package:first_dart/app_component.template.dart' as ng;

  void main() {
    runApp(ng.AppComponentNgFactory);
  }
   */
  object DartMain extends Container[Model] with DartParser {
    IMPORT_ANGULAR
    IMPORT_ANGULAR_ROUTER
    IMPORT_SELF

    --@("GeneratorInjector")(
      "routerProvidersHash"
    )

  }


}
