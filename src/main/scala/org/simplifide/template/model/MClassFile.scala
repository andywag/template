package org.simplifide.template.model

import org.simplifide.template.model.Model.MClass
import org.simplifide.template.model.dart.DartModel.Package
import org.simplifide.utils.Utils


/** Trait which describes a class which is contained inside of a file */
trait MClassFile {
  val mClass:MClass
  val classPath:String = ""

  def className:String = mClass.name.string
  lazy val fileName = Utils.camelToUnderscores(className) +".dart"

  def importCommand              = Model.Import(classPath + fileName)
  def importPackage(pack:String) = Model.Import(Package(pack,classPath + fileName))


}
