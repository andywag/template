package org.simplifide.template.model.dart

import org.simplifide.template.{Container, FileModel}
import org.simplifide.template.FileModel.{GDir, GFile}
import org.simplifide.template.model.Model
import org.simplifide.template.model.Model.{Comment, Quotes}
import org.simplifide.template.model.dart.DartProject.{Dependency, PubSpec}
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
      GDir("web"),
      PubSpec(this).create
    )
  }

}

object DartProject {

  case class Dependency(name:String, version:String)

  case class PubSpec(project:DartProject) extends Container[Model] with YamlParser {

    'name ->> project.name
    'description ->> project.description
    -->(Comment("Version: 1.0"))
    -->(Comment("Homepage: "))
    -->(Comment("Author : Andy Wagner"))
    -->(Model.Line)
    'environment ->> List(
        'sdk >>> Quotes("2.0"))
    -->(Model.Line)
    if (project.dependencies.length>0) {
      val deps = project.dependencies.map(x => x.name >>> x.version)
      'dependencies     ->> deps
    }
    if (project.devDependencies.length>0) {
      'dev_dependencies ->> project.devDependencies.map(x => x.name >>> x.version)
    }

    def create = {
      val contents = this.contents(YamlModel.cWriter)
      new GFile("pubspec.yaml",contents)
    }
  }

}
