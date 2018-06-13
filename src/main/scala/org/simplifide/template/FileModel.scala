package org.simplifide.template

import java.io.File

sealed trait FileModel {}

object FileModel {
  trait Generator {
    def contents:String
  }



  case class GFile(location:String,generator: Generator) extends FileModel
  case class GDir(location:String,children:List[FileModel] = List()) extends FileModel
  object GDir {
    def apply(location:String)(children:FileModel*) = new GDir(location,children.toList)
  }

  def create(obj:FileModel,base:File){
    obj match {
      case GDir(x,y) => {
        val next = new File(base,x)
        next.mkdirs()
        y.foreach(z => create(z,next))
      }
      case GFile(x,y) => {
        Utils.createFile(new File(base,x),y.contents)
      }
    }
  }

}
