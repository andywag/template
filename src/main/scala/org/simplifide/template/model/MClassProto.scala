package org.simplifide.template.model

import org.simplifide.template.model.MVar.VarDec

trait MClassProto extends Model {
  val name:String
  val vars:List[MVar.Var]
  val fields:List[Model]

  def declarations = {
    val a = types
    vars.map(x => VarDec(x,None))
  }

  def types = {
    val typs = vars.map(x => x.typ)
    val allTypes = typs.flatMap(x => x.types_used)
    None
  }

}

object MClassProto {

  case class MClassProtoImpl(name:String, vars:List[MVar.Var], fields:List[Model]) extends MClassProto

}
