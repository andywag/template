package org.simplifide.template.model.dart

import org.simplifide.template.model.Model

object DartConstants {

  class Directive(val name:Model) extends Model
  case object ROUTER_DIRECTIVE extends Directive("routerDirectives")
  case object CORE_DIRECTIVE extends Directive("coreDirectives")
  case object FORM_DIRECTIVE extends Directive("formDirectives")

}
