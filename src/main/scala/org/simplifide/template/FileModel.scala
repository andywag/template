package org.simplifide.template

import java.io.File
import java.nio.file.{Files, Path}

import org.simplifide.template.model.Model
import org.simplifide.utils.Utils

sealed trait FileModel {}

object FileModel {




  case class GCopy(location:String, source:String) extends FileModel
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
      case GCopy(x,y) => {
        val dstPath = new File(base,x).toPath
        val srcPath = new File(y).toPath
        System.out.println(s"Moving $srcPath to $dstPath")
        Files.copy(srcPath,dstPath)
      }
    }
  }

}
