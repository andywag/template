package org.simplifide.template

import java.io.{File, FileOutputStream}
import java.nio.file.{Files, Path}

import org.simplifide.template.model.Model
import org.simplifide.utils.Utils

import scala.io.Source

sealed trait FileModel {}

object FileModel {



  case class GResource(location:String, source:String) extends FileModel
  case class GCopy(location:String, source:String) extends FileModel
  case class GFile(location:String,contents:String) extends FileModel
  case class GDir(location:String,children:List[FileModel] = List()) extends FileModel
  case class GList(children:List[FileModel]) extends FileModel

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
      case GList(x) => {
        x.foreach(z => create(z,base))
      }
      case GFile(x,y) => {
        Utils.createFile(new File(base,x),y)
      }
      case GCopy(x,y) => {
        val dstPath = new File(base,x).toPath
        val srcPath = new File(y).toPath
        System.out.println(s"Moving $srcPath to $dstPath")
        if (Files.exists(dstPath)) Files.delete(dstPath)
        Files.copy(srcPath,dstPath)
      }
      case GResource(x,y) => {
        val stream = ClassLoader.getSystemResourceAsStream(y)
        val path = Source.fromResource(y)
        val dstPath = new File(base,x).toPath
        if (Files.exists(dstPath)) Files.delete(dstPath)
        Files.copy(stream,dstPath)
      }
    }
  }

}
