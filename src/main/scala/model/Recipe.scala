package model

import model.Difficulty.Difficulty

import scala.concurrent.duration.Duration

case class Recipe(
                   title: String,
                   preparationTime: Duration,
                   persons: Int,
                   author: String,
                   categories: List[String],
                   difficulty: Difficulty,
                   ingredients: List[Ingredient],
                   steps: List[String]
                 )

