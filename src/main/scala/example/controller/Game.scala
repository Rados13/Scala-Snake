package example

import org.scalajs.dom
import org.scalajs.dom.document
import scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D
import Direction._
import SpeedLevel._
import org.scalajs.dom.raw.TouchList
import org.scalajs.dom.raw.Touch
import org.scalajs.dom.ext.TouchEvents
import scala.annotation.switch


object Game{
    def apply(mapHtml:html.Canvas,mapSize:Int,speedLevel:SpeedLevel,isTwoPlayer:Boolean):Game = {
        val startGame = new Game(mapHtml,mapSize,speedLevel,isTwoPlayer)
        startGame.generateNewFood
        startGame.generateNewFood
        startGame
    }
}

case class Game(mapHtml:html.Canvas,mapSize:Int,speedLevel:SpeedLevel,isTwoPlayer:Boolean){

    val snakes:Array[Snake] = if (isTwoPlayer) Array(Snake(mapSize),Snake(mapSize)) else Array(Snake(mapSize))
    val turnPerFoodGeneration = 25
    val mapPainter = MapPainter(mapHtml,mapSize)
    
    var handler = 0
    var turn = 0
    var foodPositions:Array[Position] = Array()

    var xDown:Double = _
    var yDown:Double = _

    val turnFunction = () => {
        if(snakes.forall(_.notEatYourself) && mapPainter!=null && notEatOtherSnake){
            if(turn==0) generateNewFood
            mapPainter.printMap(snakes,foodPositions)
            snakes.foreach(_.move)
            eatCheck
            turn = (turn+1)%turnPerFoodGeneration
        }else{
            dom.window.clearInterval(handler)
            mapPainter.printMap(snakes,foodPositions)
            if(isTwoPlayer && isSecondWin)dom.window.alert("Second player win")
            else if(isTwoPlayer) dom.window.alert("First player win")     
            else dom.window.alert("You eat yourself")
        }
    }


    def play:Unit = {
        dom.window.addEventListener("keydown",matchingKey)
        dom.window.addEventListener("touchstart",handleTouchStart)
        dom.window.addEventListener("touchmove",handleTouchMove)
        handler = dom.window.setInterval(turnFunction,SpeedLevel.toValue(speedLevel))
    }

    def pause:Unit = {
        dom.window.removeEventListener("keydown",matchingKey)
        dom.window.clearInterval(handler)
    }

    def isSecondWin:Boolean = {
        val snakeFirstHead = snakes(0).positions.head
        !snakes(0).notEatYourself || snakes(1).positions.tail.contains(snakeFirstHead) || 
        (snakes(1).positions.head == snakeFirstHead && snakes(1).score > snakes(0).score)
    }

    def notEatOtherSnake:Boolean = {
        if(isTwoPlayer){
            val firstSnake = snakes(0)
            val secondSnake = snakes(1)
            !secondSnake.positions.contains(firstSnake.positions.head) && !firstSnake.positions.contains(secondSnake.positions.head)
        }else true
    }

    def eatCheck:Unit = {
        snakes.foreach(snake => {
            val snakeHead = snake.positions.head
            if(foodPositions.contains(snakeHead)){
                foodPositions = foodPositions.filterNot(_==snakeHead)
                snake.grow
            }
        })
    }

    def generateNewFood:Unit = {
        var newPosition = Position()
        while(snakes.exists(_.positions.contains(newPosition)) || foodPositions.contains(newPosition))newPosition=Position()
        this.foodPositions=this.foodPositions.:+(newPosition)
    }

    def matchingKey(event:dom.KeyboardEvent):Unit = {
        event.keyCode match {
            case 37 => snakes(0).changeDirection(West)
            case 38 => snakes(0).changeDirection(North)
            case 39 => snakes(0).changeDirection(East)
            case 40 => snakes(0).changeDirection(South)
            case x => ()
        }
        val whichSnakeListen = if(isTwoPlayer) 1 else 0
        event.keyCode match {
            case 65 => snakes(whichSnakeListen).changeDirection(West)
            case 87 => snakes(whichSnakeListen).changeDirection(North)
            case 68 => snakes(whichSnakeListen).changeDirection(East)
            case 83 => snakes(whichSnakeListen).changeDirection(South)
            case x => ()
        }
    }

    def getTouches(evt:dom.TouchEvent): TouchList = {
        if(evt.touches.length>0)evt.touches
        else evt.asInstanceOf[js.Dynamic].originalEvent.asInstanceOf[dom.TouchEvent].touches
    }

    def handleTouchStart(evt:dom.TouchEvent) = {
        val firstTouch: Touch = getTouches(evt)(0)
        xDown = firstTouch.clientX
        yDown = firstTouch.clientY
    }

    def handleTouchMove(evt:dom.TouchEvent){
        if (xDown == 0.0 || yDown == 0.0)return
        
        var xUp:Double = evt.touches(0).clientX
        var yUp:Double = evt.touches(0).clientY

        var xDiff = xDown - xUp;
        var yDiff = yDown - yUp;
        val isHorizontalMove = Math.abs(xDiff)>Math.abs(yDiff) 
        (isHorizontalMove,xDiff>0,yDiff>0) match {
            case (true,true,_) => snakes(0).changeDirection(West)
            case (true,false,_) => snakes(0).changeDirection(East)
            case (false,_,true) => snakes(0).changeDirection(North)
            case (false,_,false) => snakes(0).changeDirection(South)
        }
        xDown = 0.0;
        yDown = 0.0;   
    }
}