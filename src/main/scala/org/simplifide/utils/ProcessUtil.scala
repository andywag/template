package org.simplifide.utils

import java.io.File

import sys.process._

object ProcessUtil {

  val logger = ProcessLogger(
    (o: String) => println("out " + o),
    (e: String) => println("err " + e))

  def runCommand(cmd:String, location:Option[String]) = {
    println(s"Running $cmd")
    val proc = location.map(x => Process(cmd,new File(x))).getOrElse(Process(cmd))
    val run = proc.run(logger)
    println(s"Returned Exit Code ${run.exitValue()}")
  }


}
