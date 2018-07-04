package org.simplifide.dart.binding

trait MProto {

}

object MProto {

  case object PString extends MProto
  case object PInt       extends MProto

  case class Value[T](x:T) extends MProto

  case class ProtoArg(name:String, typ:Any)
  case class ProtoClass(name:String, args:List[Any]) extends MProto

}
