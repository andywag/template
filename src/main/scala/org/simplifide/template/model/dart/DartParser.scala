package org.simplifide.template.model.dart

import org.simplifide.template.Container
import org.simplifide.template.model.MAttribute.MAttr
import org.simplifide.template.model.MFunction.Lambda
import org.simplifide.template.model._
import org.simplifide.template.model.Model.{$import, Import, Str}
import org.simplifide.template.model.dart.DartModel.Package
import org.simplifide.template.model.MVar._
import org.simplifide.template.model.MType._

trait DartParser extends ModelParser {
  self:Container[Model] =>

  def T(x:String) = SType(x)

  def IMPORT_ANGULAR        = imp(Package("angular","angular.dart"))
  def IMPORT_ANGULAR_ROUTER = imp(Package("angular_router","angular_router.dart"))
  def IMPORT_SELF           = -->(Import("main.template.dart",Some("self")))

  def import_pack(pack:String, file:String, alias:Option[Model]) = {
    -->(Import(Package(pack,file),alias))
  }

  //  RoutePath get heroes => paths.heroes;
  def lambda(name:String, out:MType, input:Model, func:Model) ={
    -->(Lambda (name,out,input,func))
  }



}
