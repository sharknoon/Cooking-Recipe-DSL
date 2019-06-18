package parser

import

.Category

import model.Recipe

import scala.concurrent.duration.Duration
import scala.util.parsing.combinator.RegexParsers

class ReceipeParser extends RegexParsers {

  private def receipeParser: Parser[Recipe] =
    "Titel:" ~ string ~
      "Dauer:" ~ duration ~
      "Personen:" ~ integer ~
      "Autor:" ~ string ~
      "Kategorien:" ~ categories ~
      "" ^^ {
      case _ ~ t ~ _ ~ n ~ _ ~ c ~ _ ~ ch =>
        Topic(t, n, c, ch)
    }

  // every char until line break
  private def string: Parser[String] =
    """.*""".r ^^ (_.toString)

  private def integer: Parser[Int] =
    """\d+""".r ^^ (_.toInt)

  private def duration: Parser[Duration] =
    """(\d)+( )?(min|hour|h)""".r ^^ (d => Duration.apply(d))

  private def categories: Parser[List[String]] =
    rep(categoriesWithComma) ~ string ^^ (cats => cats._1.appended(cats._2))

  private def categoriesWithComma: Parser[String] =
    string ~ ", " ^^ (_.toString().replace(", ", ""))
}
