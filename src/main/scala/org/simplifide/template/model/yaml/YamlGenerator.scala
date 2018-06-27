package org.simplifide.template.model.yaml

import org.simplifide.template.model.dart.DartModel.DartImport
import org.simplifide.template.Template._
import org.simplifide.template.model.cpp.CppGenerator.create
import org.simplifide.template.model.{MVar, Model, ModelGenerator}
import org.simplifide.template.{Template, TemplateGenerator}
import Model._
import org.simplifide.template.model.MVar.{SType, VarDec}

object YamlGenerator {
  val IMPORT = "import "

  implicit def ModelToTemplate(x:Model) = create(x)


  def create(o:Model):Template = {
    o match {
      case Model.Sym(x)           => x.name
      case Model.Str(x)           => x
      case Model.Quotes(x)        => "'" ~ x ~ "'"

      case YamlModel.KeyPair(x,y) => x ~ " : " ~y ~ NL
      case YamlModel.KeyList(x,y) => {
        x ~ " : " ~ NL ~ y.map(z => indent(create(z)))
      }

      case Model.Comment(x)          => "#" ~ x ~ NL
    }
  }

}
