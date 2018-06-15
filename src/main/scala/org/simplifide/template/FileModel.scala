package org.simplifide.template

import java.io.File

import org.simplifide.template.model.Model

sealed trait FileModel {}

object FileModel {




  case class GFile(location:String,contents:String) extends FileModel
  case class GDir(location:String,children:List[FileModel] = List()) extends FileModel
  object GDir {
    def apply(location:String)(children:FileModel*) = new GDir(location,children.toList)
  }

  def create[T](obj:FileModel,base:File)(implicit x:(T)=>Template){
    obj match {
      case GDir(x,y) => {
        val next = new File(base,x)
        next.mkdirs()
        y.foreach(z => create(z,next))
      }
      case GFile(x,y) => {
        Utils.createFile(new File(base,x),y)
      }
    }
  }

}
