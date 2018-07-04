package org.simplifide.dart.binding

trait Prototypable[T] {
  def createProto[T](x:T):MProto
}

object Prototypable {

  implicit object StringPrototypable extends Prototypable[String] {
    def createProto[String](x:String) = MProto.PString
  }

  implicit object IntPrototypable extends Prototypable[Int]   {
    def createProto[Int](x:Int) = MProto.PInt
  }

  def create[T](input:T)(implicit creator: Prototypable[T]) = {
    creator.createProto(input)
  }

}

object TestProto {
  def main(args: Array[String]): Unit = {
    val alpha = "asdfads"
    val result = Prototypable.create(alpha)
    println(result)
  }
}