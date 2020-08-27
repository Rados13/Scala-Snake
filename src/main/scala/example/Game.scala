package example

import org.scalajs.dom
import org.scalajs.dom.document
import scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D
import Direction._
import SpeedLevel._


object Game{
    def apply(mapHtml:html.Canvas,mapSize:Int,speedLevel:SpeedLevel):Game = {
        val startGame = new Game(mapHtml,mapSize,speedLevel)
        startGame.generateNewFood
        startGame.generateNewFood
        startGame
    }
}

case class Game(mapHtml:html.Canvas,mapSize:Int,speedLevel:SpeedLevel){


    
    val snake:Snake = Snake(mapSize)
    val turnPerFoodGeneration = 25
    val mapPainter = MapPainter(mapHtml,mapSize)
    
    var handler = 0
    var turn = 0
    var foodPositions:Array[Position] = Array()

    val turnFunction = () => {
        if(snake.notEatYourself && mapPainter!=null){
                if(turn==0) generateNewFood
                mapPainter.printMap(snake,foodPositions)
                snake.move
                eatCheck
                turn = (turn+1)%turnPerFoodGeneration
            }else{
                dom.window.clearInterval(handler)
                dom.window.alert("You eat yourself")
                println(snake.toString)
                mapPainter.printMap(snake,foodPositions)
        }
    }


    def play:Unit = {
        dom.window.addEventListener("keydown",matchingKey)
        handler = dom.window.setInterval(turnFunction,SpeedLevel.toValue(speedLevel))
    }

    def pause:Unit = {
        dom.window.removeEventListener("keydown",matchingKey)
        dom.window.clearInterval(handler)
    }

    def eatCheck = {
        val snakeHead = snake.positions.head
        if(foodPositions.contains(snakeHead)){
            foodPositions = foodPositions.filterNot(_==snakeHead)
            snake.grow
        }
    }

    def matchingKey(event:dom.KeyboardEvent):Unit = {
        event.keyCode match {
            case 37 => snake.changeDirection(West)
            case 38 => snake.changeDirection(North)
            case 39 => snake.changeDirection(East)
            case 40 => snake.changeDirection(South)
            case x => println(s"Some else keydown $x")
        }
    }

    def generateNewFood:Unit = {
        var newPosition = Position()
        while(snake.positions.contains(newPosition) || foodPositions.contains(newPosition))newPosition=Position()
        this.foodPositions=this.foodPositions.:+(newPosition)
    }
}