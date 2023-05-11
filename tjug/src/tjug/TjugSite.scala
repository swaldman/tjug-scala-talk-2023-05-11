package tjug

import scala.collection.*

import unstatic.*
import unstatic.ztapir.*
import unstatic.ztapir.simple.*

import unstatic.*, UrlPath.*

import java.nio.file.Path as JPath

import untemplate.Untemplate.AnyUntemplate

object TjugSite extends ZTSite.SingleRootComposite( JPath.of("tjug/static") ):

  // edit this to where your site will actually be served!
  override val serverUrl : Abs    = Abs("https://www.interfluidity.com/")
  override val basePath  : Rooted = Rooted("/uploads/2023/05/tjug-scala-talk-2023-05-11/")

  // customize this to what the layout you want requires!
  case class MainLayoutInput( renderLocation : SiteLocation, mbTitle : Option[String] )

  // get rid of this -- modify it into something useful and/or include something like a SimpleBlog defined as a singleton object
  object RevealPresentationPage extends ZTEndpointBinding.Source:
    val location = TjugSite.location("/index.html")
    val task = zio.ZIO.attempt {
      layout_main_html( MainLayoutInput( location, Some("TJUG Java Talk — 2023-05-11") ) ).text
    }
    val endpointBindings = ZTEndpointBinding.publicReadOnlyHtml( location, task, None, immutable.Set("hello-world") ) :: Nil
  end RevealPresentationPage

  object Markdown:
    lazy val coverPageHtml         = Flexmark.defaultMarkdownToHtml( cover_page_md().text, None )
    lazy val bleedingEdgeJavaHtml  = Flexmark.defaultMarkdownToHtml( bleeding_edge_java_md().text, None )
    lazy val bleedingEdgeJava2Html = Flexmark.defaultMarkdownToHtml( bleeding_edge_java_2_md().text, None )
    lazy val concision1Html        = Flexmark.defaultMarkdownToHtml( concision_1_md().text, None )
    lazy val concision2Html        = Flexmark.defaultMarkdownToHtml( concision_2_md().text, None )
    lazy val concision3Html        = Flexmark.defaultMarkdownToHtml( concision_3_md().text, None )
    lazy val concision4Html        = Flexmark.defaultMarkdownToHtml( concision_4_md().text, None )
    lazy val safetyHtml            = Flexmark.defaultMarkdownToHtml( safety_md().text, None )
    lazy val safety2Html           = Flexmark.defaultMarkdownToHtml( safety_2_md().text, None )
    lazy val abstractionHtml       = Flexmark.defaultMarkdownToHtml( abstraction_md().text, None )
    lazy val hyperscaleHtml        = Flexmark.defaultMarkdownToHtml( hyperscale_md().text, None )
    lazy val simplicityHtml        = Flexmark.defaultMarkdownToHtml( simplicity_md().text, None )
    lazy val simplicity2Html       = Flexmark.defaultMarkdownToHtml( simplicity_2_md().text, None )
    lazy val compatibleHtml        = Flexmark.defaultMarkdownToHtml( compatible_md().text, None )
    lazy val interestingHtml       = Flexmark.defaultMarkdownToHtml( interesting_md().text, None )
    lazy val scalaCli1Html         = Flexmark.defaultMarkdownToHtml( scala_cli_1_md().text, None )
    lazy val scalaCli2Html         = Flexmark.defaultMarkdownToHtml( scala_cli_2_md().text, None )
    lazy val demosHtml             = Flexmark.defaultMarkdownToHtml( demos_md().text, None )
    lazy val cheatsheetHtml        = Flexmark.defaultMarkdownToHtml( cheatsheet_md().text, None )
    lazy val learnScalaHtml        = Flexmark.defaultMarkdownToHtml( learn_scala_md().text, None )
    lazy val colophonHtml          = Flexmark.defaultMarkdownToHtml( colophon_md().text, None )
  end Markdown

  object WordSalad:
    val words = immutable.Seq(
      "multiline <code>String</code>",
      "<code>String</code> interpolation",
      "rich regex support",
      "pattern matching with deep destructuring",
      "typeclasses / <i>ad hoc</i> polymorphism",
      "inline, macros, and metaprogramming",
      "higher kinded types",
      "rich collections library",
      "for comprehensions, convenient monad sequencing",
      "opaque types",
      "<code>case class</code>",
      "by-name params + multiple arg lists => user-defined control flow constructs",
      "infix function notation => operator overloading",
      "flexible, concise syntax",
      "<code>apply(...)</code> method makes anything callable",
      "<code>if/then</code> and codeblocks as expressions",
      "<code>Nothing</code> as type hierarchy bottom",
      "singleton <code>object</code>, much saner than <code>static</code>",
      "extension methods",
      "strong immutability preference",
      "strong expressive typing with convenient type inference",
      "multiplatform: Scala JVM, Scala JS, Scala native",
      "top-level declarations",
    )

    val threshold = 90

    def randomCssColor() : String =
      def r255 = math.floor( math.random * 256 ).toInt
      val (red, green, blue) = (r255, r255, r255)
      if red < threshold && green < threshold && blue < threshold then randomCssColor() // too dark
      else s"rgb($red,$green,$blue)"

    def html = word_salad_html().text
  end WordSalad

  // avoid conflicts, but early items in the lists take precedence over later items
  override val endpointBindingSources : immutable.Seq[ZTEndpointBinding.Source] = immutable.Seq( RevealPresentationPage )

object TjugSiteGenerator extends ZTMain(TjugSite, "tjug-site")

