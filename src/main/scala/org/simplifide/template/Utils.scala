package org.simplifide.template

import java.io.{BufferedWriter, File, FileWriter}

object Utils {

  def createFile(file:File, text:String) = {
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(text)
    bw.close()
  }

}
