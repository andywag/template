package org.simplifide.template.model.dart

import org.simplifide.template.Container
import org.simplifide.template.FileModel.GFile
import org.simplifide.template.model.Model
import org.simplifide.template.model.Model.{Comment, Quotes}
import org.simplifide.template.model.dart.DartProject.Dependency
import org.simplifide.template.model.dart.PubSpec.Description
import org.simplifide.template.model.yaml.{YamlModel, YamlParser}

/**
  * Class used to create the pubspec.yaml file
  */
case class PubSpec(description:Description,
                   dependencies:List[Dependency],
                   devDependencies:List[Dependency],
                   version:String=">=2.0.0-dev.64.1 <2.0.0") extends Container[Model] with YamlParser {

  'name ->> description.name
  'description ->> description.description
   -->(Comment(s"Version: ${description.version}"))
  description.homepage.map(y => -->(Comment(s"Homepage: $y")))
  description.author.map(y => -->(Comment(s"Author: $y")))
  -->(Model.Line)
  'environment ->> List(
    'sdk >>> Quotes(version))
  -->(Model.Line)
  if (dependencies.length > 0) {
    val deps = dependencies.map(x => x.name >>> x.version)
    'dependencies ->> deps
  }

  -->(Model.Line)
  if (devDependencies.length > 0) {
    'dev_dependencies ->> devDependencies.map(x => x.name >>> x.version)
  }

  def create = {
    val contents = this.contents(YamlModel.cWriter)
    new GFile("pubspec.yaml", contents)
  }
}

object PubSpec {
  case class Description(
    val name:String,
    val description:String,
    val version:String = "1.0",
    val author:Option[String] = None,
    val homepage:Option[String] = None)

}

