package org.simplifide.template.model

import org.simplifide.template.model.Model.MClass


/** Trait which describes a class which is contained inside of a file */
trait MClassFile {
  val className:String
  val classPath:String = ""
  val mClass:MClass

}
