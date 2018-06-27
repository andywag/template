package org.simplifide.template.model.dart

import org.simplifide.template.Container
import org.simplifide.template.model.MAttribute.MAttr
import org.simplifide.template.model.{MAttribute, Model}
import org.simplifide.template.model.Model.{$import, Import}
import org.simplifide.template.model.dart.DartModel.Package

trait DartParser {
  self:Container[Model] =>

  def imp(input:Model) = {
    -->(Model.Import(input))
  }


  def IMPORT_ANGULAR        = imp(Package("angular","angular.dart"))
  def IMPORT_ANGULAR_ROUTER = imp(Package("angular_router","angular_router.dart"))
  def IMPORT_SELF                        = -->(Import("main.template.dart",Some("self")))

  def --@(name:Model)(items:Model*) = {
    -->(MAttribute.Impl(name,items.toList))
  }


}
