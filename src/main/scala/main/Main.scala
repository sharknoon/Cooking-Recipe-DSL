package main

import html.Html
import parser.RecipeParser

import scala.io.Source

object Main extends RecipeParser {

  def main(args: Array[String]): Unit = {
    val content = getFile(args(0))
    parse(recipeParser, content) match {
      case Success(matched, _) => {
        println(s"SUCCESS: $matched")
        Html.writeRecipe(matched)
      }
      case Failure(msg, _) => println(s"FAILURE: $msg")
      case Error(msg, _) => println(s"ERROR: $msg")
    }
  }

  def getFile(file: String): String = Source.fromFile(file).mkString("")

}
