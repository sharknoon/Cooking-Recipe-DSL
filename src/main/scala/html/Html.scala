package html

import java.awt.Desktop
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import model.Recipe
import scalatags.Text.all._

object Html {

  def writeRecipe(recipe: Recipe): Unit = {
    val htmlString = html(
      body(
        h1(recipe.title),
        div(
          "⏲️", recipe.preparationTime.toString, "    ",
          "⚙️", recipe.difficulty.toString, "    ",
          "\uD83E\uDDD1\u200D\uD83E\uDD1D\u200D\uD83E\uDDD1", recipe.persons, "    ",
          "\uD83D\uDC69\u200D\uD83C\uDF73", recipe.author, "    ",
          "\uD83C\uDF54", recipe.categories.mkString(", "), "    "
        ),
        h2("Zutaten"),
        ul(
          for (i <- recipe.ingredients) yield
            li(i.quantity + i.unit + " " + i.name)

        ),
        h2("Zubereitung"),
        ul(
          for (s <- recipe.steps) yield
            li(s)

        )
      )
    )
    writeFile(htmlString.toString())
  }

  def writeFile(html: String): Unit = {
    val path = Paths.get("index.html")
    val bytes = html.getBytes(StandardCharsets.UTF_16)
    Files.write(path, bytes)
    Desktop.getDesktop.open(path.toFile)
  }

}
