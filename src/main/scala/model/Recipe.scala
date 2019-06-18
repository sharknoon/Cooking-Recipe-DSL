package model

import .Category
import model.Difficulty.Difficulty

import scala.concurrent.duration.Duration

case class Recipe(
                   title: String,
                   preparationTime: Duration,
                   persons: Int,
                   author: String,
                   categories: List[Category],
                   difficulty: Difficulty,
                   ingredients: List[Ingredient],
                   steps: List[String]
                 )

