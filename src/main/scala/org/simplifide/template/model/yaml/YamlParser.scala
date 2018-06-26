package org.simplifide.template.model.yaml

import org.simplifide.template.Container
import org.simplifide.template.model.Model

trait YamlParser {
  self:Container[Model] =>

  implicit def SymbolToBuilder(symbol:Symbol) = KeyBuilder(symbol)
  implicit def StringToBuilder(symbol:String) = KeyBuilder(symbol)

  case class KeyBuilder(symbol:Model) {
    def ->> (value:Model) = {
      val result = new YamlModel.KeyPair(symbol,value)
      -->(result)
      result
    }

    def >>> (value:Model) = {
      val result = new YamlModel.KeyPair(symbol,value)
      result
    }


    def ->> (values:List[Model]) = {
      val result = new YamlModel.KeyList(symbol,values)
      -->(result)
         result
    }


  }


}
