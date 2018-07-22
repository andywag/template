# Template_Combinator

This project is a templating library based on combinators rather than the more standard string interpolation. This technique has advantages for code generation where the output code contains a well defined structure. In this case the grammar can be directly encoded in the template making both the text generation easier as well as the internal generation of the structure. 

The project is currently used to generate some basic scala code for a embedded DSL project but is most likey going to be expanded as an experiment in machine learning driven code generation and potentially ported to python. 

## Scala Generator

A snippet of the grammar portion of the code is shown below. The structure of the code is very similar to a parser-combinator and is easy to create and expand based on the grammar of the language. 

```scala

def scalaFile(name:Template, imports:Template, items:Template) =
    "package\n\n" ~ name ~ sep(imports,"\n") ~ "\n" ~ sep(items,"\n") ~ "\n"

  def scalaObject(name:String, items:List[Template]) =
    "object " ~ name ~ "{\n" ~ indent(L(items)) ~ "\n}"

  def caseClass(name:String, ports:List[Template], values:List[Template], sup:List[Template]) = {
    "case class " ~ name ~ "(" ~ sep(L(ports), ",") ~ ")" ??(sup.size > 0, " extends " ~ sep(L(sup)," with ")) ?? (values.size > 0, "{\n" ~ "}")
  }
```

## XML Generator

The code below supports XML generation. This isn't the best strategy for XML generation but was done as an experiment to test the complexity of the generator. 

```scala
object XmlTemplate {

  def tagOpen(name:String,attr:Seq[Attr]) = "<" ~ name ~ " " ~attr ~ ">"

  def tagEnd(name:String)  = "<" ~ name ~ "/>"

  def tag(name:String, attr:Seq[Attr], children:Seq[Template]) =
    tagOpen(name, attr) ~ children ~ tagEnd(name)

  case class Attr(name:String, value:String) extends TemplateBuilder {
    val creator = (name ~ "=" ~ Template.quotes(value))
  }
  case class Node(name:String, children:Seq[Node],attr:Seq[Attr]=Seq()) extends TemplateBuilder {
    val creator = tag(name,attr,children)
    def apply(children:Node*) = this.copy(children = children)
    def att(attr:Attr*)      = this.copy(attr = attr)
  }

  implicit def symbolToNode(symbol:Symbol) = new Node(symbol.name,Seq(),Seq())
  implicit def arrowToAttr(a:(String,String)) = new Attr(a._1,a._2)
}

object Test {
  val creator = 'root('a('c.att("a"->"b"),'d),'b)
  creator.create() 
  // Results in 
  // <root ><a ><c a="b"><c/><d ><d/><a/><b ><b/><root/>

}
```
