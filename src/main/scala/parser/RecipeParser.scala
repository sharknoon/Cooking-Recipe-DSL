package parser

import model.Difficulty.Difficulty
import model.{Difficulty, Ingredient, Recipe}

import scala.concurrent.duration.Duration
import scala.util.parsing.combinator.RegexParsers

class RecipeParser extends RegexParsers {

  def recipeParser: Parser[Recipe] =
    "Titel:" ~ line ~
      "Dauer:" ~ duration ~
      "Personen:" ~ integer ~
      "Autor:" ~ line ~
      "Kategorien:" ~ categories ~
      "Schwierigkeit:" ~ difficulty ~
      "Zutaten:" ~ ingredients ~
      "Schritte:" ~ steps ^^ {
      case _ ~ title ~
        _ ~ duration ~
        _ ~ persons ~
        _ ~ authors ~
        _ ~ categories ~
        _ ~ difficulty ~
        _ ~ ingredients ~
        _ ~ steps =>
        Recipe(title, duration, persons, authors, categories, difficulty, ingredients, steps)
    }

  private def line: Parser[String] =
    """.+""".r ^^ (_.toString)

  // every char until line break
  private def string: Parser[String] =
    """\w+""".r ^^ (_.toString)

  private def integer: Parser[Int] =
    """\d+""".r ^^ (_.toInt)

  private def duration: Parser[Duration] =
    """(\d)+( )?(min|hour|h)""".r ^^ (d => Duration.apply(d))

  private def categories: Parser[List[String]] =
    rep(categoriesWithComma) ~ line ^^ (cats => cats._1.appended(cats._2))

  private def categoriesWithComma: Parser[String] =
    string ~ ", " ^^ (_._1)

  private def difficulty: Parser[Difficulty] =
    string ^^ {
      case "einfach" => Difficulty.easy
      case "fortgeschritten" => Difficulty.well_advanced
      case "schwer" => Difficulty.heavy
      case s => Difficulty.withName(s)
    }

  private def ingredients: Parser[List[Ingredient]] =
    rep(ingredient)

  private def ingredient: Parser[Ingredient] =
    ">" ~ opt(" ") ~ integer ~ string ~ opt(" ") ~ line ^^ {
      case _ ~ _ ~ amount ~ unit ~ _ ~ name =>
        Ingredient(name, amount.toDouble, unit)
    }

  private def steps: Parser[List[String]] =
    rep(step)

  private def step: Parser[String] = line
}
