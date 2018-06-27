package org.simplifide.utils

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.Files

object Utils {

  def createFile(file:File, text:String) = {
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(text)
    bw.close()
  }

  def deleteFile(path:File) {
    if (path.exists()) {
      val children = path.listFiles()
      if (children != null) children.map(x => deleteFile(x))
      Files.delete(path.toPath)
    }
  }

}
