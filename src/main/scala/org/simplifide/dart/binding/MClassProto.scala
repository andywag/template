package org.simplifide.dart.binding

import org.simplifide.template
import org.simplifide.template.model
import org.simplifide.template.model.MType._
import org.simplifide.template.model.MVar.VarDec
import org.simplifide.template.model.Model.{DotPrefix, Line, MClass, MapIndex, SemiEnd, Str}
import org.simplifide.template.model.{MFunction, MType, MVar, Model}

trait MClassProto extends Model {
  val name:String
  val vars:List[MVar.Var]
  val fields:List[Model]

  def declarations = {
    val a = types
    vars.map(x => VarDec(x,None))
  }

  def imports = {

    //types.map(x => Model.Import(s"${x.toLowerCase}.dart"))
    val typs = types
    List()
  }

  /*
  def types = {
    val typs = vars.map(x => x.typ)
    val allTypes = typs.flatMap(x => x.user_types_used)
    allTypes
  }
*/

  def types = {
    def decode(typ:MType):List[MType] = {
      typ match {
        case x: BaseTypes => {
          List()
        }
        case SType(x) => List(typ)
        case x: Generic => decode(x.o) ::: x.i.flatMap(y => decode(y))
        case TypeAnd(r, l) => decode(r) ::: decode(l)
      }
    }
    val typs = vars.flatMap(x => decode(x.typ))
    typs
  }


  def constructor = {
    new model.MFunction.Call(this.name,this.vars.map(x => x.copy(name = DotPrefix("this",x.name))))
  }

  def create = new MClassProto.Cla(this)

}

object MClassProto {

  case class MClassProtoImpl(name:String,
                             vars:List[MVar.Var],
                             fields:List[Model]) extends MClassProto

  class Cla(cla:MClassProto) extends MClass(cla.name) {
    cla.declarations.foreach(x => -->(x))
    -->(Line)
    -->(new SemiEnd(cla.constructor))
    -->(Line)
    this.items.append(new From(cla))
    -->(Line)
    this.items.append(new To(cla))

  }


  class From(cla:MClassProto) extends MFunction.MFunc(s"${cla.name}.fromJson",TFactory) {
    val v = MType.TMap(TString,TDynamic) ~ "value"
    val args = List(v.v)

    val cargs = cla.vars.map(x => MapIndex("value",x.name))
    -->(Model.Return(MFunction.Call(s"${cla.name}",cargs)))
  }

  class To(cla:MClassProto) extends MFunction.MFunc(s"toJson",TSMap) {
    val args = List()

    val cargs = cla.vars.map(x => Model.ModelTuple(x.name,x.name))
    val dict  = Model.Dictionary(cargs)
    -->(Model.Return(dict))
  }



}
