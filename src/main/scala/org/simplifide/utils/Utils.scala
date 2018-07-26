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

  def firstLetterLower(name:String) = {
    s"${Character.toLowerCase(name(0))}${name.substring(1)}"
  }

  def camelToUnderscoresInternal(name: String) = "[A-Z\\d]".r.replaceAllIn(name, {
    m => "_" + m.group(0).toLowerCase()
  })

  def camelToUnderscores(name1: String) = {
    val name = camelToUnderscoresInternal(name1)
    if (name.indexOf("_") == 0) name.substring(1) else name
  }


  def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
  })

}
