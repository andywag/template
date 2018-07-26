package org.simplifide.template.model.dart

import org.simplifide.template.{Container, model}
import org.simplifide.template.model.MAttribute.MAttr
import org.simplifide.template.model.MFunction.Lambda
import org.simplifide.template.model._
import org.simplifide.template.model.Model.{$import, DotPrefix, Import, Str}
import org.simplifide.template.model.dart.DartModel.DartPackage
import org.simplifide.template.model.MVar._
import org.simplifide.template.model.MType._

trait DartParser extends ModelParser {
  self:Container[Model] =>

  def T(x:String) = SType(x)

  // Imports
  def IMPORT_DART_ASYNC        = imp("dart:async")
  def IMPORT_DART_CONVERT     = imp("dart:convert")
  def IMPORT_DART_MATH     = imp("dart:math")

  def IMPORT_HTTP        = imp(DartPackage("http","http.dart"))
  def IMPORT_PACKAGE_TESTING        = imp(DartPackage("http","testing.dart"))

  def IMPORT_ANGULAR        = imp(DartPackage("angular","angular.dart"))
  def IMPORT_ANGULAR_ROUTER = imp(DartPackage("angular_router","angular_router.dart"))
  def IMPORT_SELF           = -->(Import("main.template.dart",Some("self")))

  def import_pack(pack:String, file:String, alias:Option[Model]) = {
    -->(Import(DartPackage(pack,file),alias))
  }

  // Attributes
  def $_INJECTABLE = --@("Injectable")()


  // Constructure Creation Function


  //  RoutePath get heroes => paths.heroes;
  def lambda(name:String, out:MType, input:Model, func:Model) ={
    -->(Lambda (name,out,input,func))
  }

}

object DartParser {
  def constructor(name:String,vars:List[Var]) = {
    new model.MFunction.Call(name,vars.map(x => x.copy(name = DotPrefix("this",x.name))))
  }


}
