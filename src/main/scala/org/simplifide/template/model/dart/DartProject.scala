package org.simplifide.template.model.dart

import org.simplifide.template.{Container, FileModel}
import org.simplifide.template.FileModel.{GCopy, GDir, GFile}
import org.simplifide.template.model.MAttribute.MAttr
import org.simplifide.template.model.MFunction.MFunc
import org.simplifide.template.model.Model
import org.simplifide.template.model.Model.{$import, Comment, Quotes}
import org.simplifide.template.model.dart.DartProject.{DartMain, Dependency}
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
        GDir("src",sources),
      ),
      GDir("test"),
      GDir("web",List(
        GCopy("favicon.png","C:\\scala_projects\\first_dart\\web\\favicon.png"),
        GCopy("index.html","C:\\scala_projects\\first_dart\\web\\index.html"),
        GFile("main.dart",DartMain.contents(DartModel.dartWriter)),
        GCopy("styles.css","C:\\scala_projects\\first_dart\\web\\styles.css")
      )),
      PubSpec(PubSpec.Description(this.name,""),dependencies,devDependencies).create
    )
  }

}

object DartProject {

  case class Dependency(name: String, version: String)


  /*
  import 'package:angular/angular.dart';
  import 'package:first_dart/app_component.template.dart' as ng;

  void main() {
    runApp(ng.AppComponentNgFactory);
  }
   */
  import org.simplifide.template.model.MVar._
  import org.simplifide.template.model.Model._

  object DartMain extends Container[Model] with DartParser {
    IMPORT_ANGULAR
    IMPORT_ANGULAR_ROUTER
    IMPORT_SELF
    -->(Line)
    --@("GeneratorInjector")(
      "routerProvidersHash"
    )
    -->(Line)
    val inj = dec($final ~ T("InjectorFactory") ~ "injector" ~= "self.injector$Injector")
    -->(Line)
    object Func extends MFunc("main",SType("void")) with DartParser {
      val args = List()

      call("runApp",List("ng.AppComponentNgFactory","createInjector"~>inj))
    }
    -->(Func)

  }


}
