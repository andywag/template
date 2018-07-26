package org.simplifide.template.model


import org.simplifide.template.model.MClass.MClassBase
import org.simplifide.template.model.dart.DartModel.DartPackage
import org.simplifide.utils.Utils


/** Trait which describes a class which is contained inside of a file */
trait MClassFile {
  val mClass:MClass
  val classPath:String = ""

  def className:String = mClass.name
  lazy val fileName = Utils.camelToUnderscores(className) +".dart"

  def importCommand              = Model.Import(classPath + fileName)
  def importPackage(pack:String) = Model.Import(DartPackage(pack,classPath + fileName))


}
