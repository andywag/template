package org.simplifide.template.model.yaml

import org.simplifide.template.model.Model
import org.simplifide.template.model.cpp.CppGenerator



object YamlModel {

  def cWriter(x:Model) = YamlGenerator.create(x)

  case class KeyPair(key:Model, value:Model) extends Model
  case class KeyList(key:Model, value:List[Model]) extends Model
}
