package example

import org.scalajs.dom
import org.scalajs.dom.document
import scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D
import Direction._


case class Game(mapHtml:html.Canvas,mapSize:Int){
    
    var isNotEnd = true
    val snake:Snake = Snake(mapSize)
    val fieldSize = 10;

    var foodPositions:Array[Position] = Array();

    def getContext: Either[String,CanvasRenderingContext2D] = mapHtml.getContext("2d") match {
            case context: CanvasRenderingContext2D => Right(context)
            case other => 
                dom.window.alert(s"Error $other");
                Left(s"Error $other")
        } 

    def play():Int = {
        val ctx:CanvasRenderingContext2D = getContext.getOrElse(null);

        mapHtml.height = mapSize*fieldSize;
        mapHtml.width = mapSize*fieldSize;

        generateNewFood;
        generateNewFood;

        
        var turn = 0;
        dom.window.addEventListener("keydown",matchingKey)
        val handler = dom.window.setInterval(() => {
            if(snake.notEatYourself && ctx!=null){
                printMap(ctx);
                snake.move;
                eatCheck;
                turn+=1;
            }else{
                dom.window.alert("You eat yourself");
                return -1
            }
        },100);

        printMap(ctx);
        
        handler;
    }

    def eatCheck = {
        val snakeHead = snake.positions.head
        if(foodPositions.contains(snakeHead)){
            foodPositions = foodPositions.filterNot(_==snakeHead)
            snake.grow;
        }
    }

    def printMap(ctx:CanvasRenderingContext2D) ={
        ctx.clearRect(0,0,mapSize*fieldSize,mapSize*fieldSize);
        paintSnake(ctx);
        paintFood(ctx);
        
    }

    def paintSnake(ctx:CanvasRenderingContext2D):Unit ={
        ctx.fillStyle = "green";
        snake.positions.foreach(elem => ctx.fillRect(elem.positionX*fieldSize,elem.positionY*fieldSize,fieldSize,fieldSize));
    }

    def paintFood(ctx:CanvasRenderingContext2D):Unit = {
        ctx.fillStyle = "red";
        this.foodPositions.foreach(elem => ctx.fillRect(elem.positionX*fieldSize,elem.positionY*fieldSize,fieldSize,fieldSize))
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

    def generateNewFood:Unit = this.foodPositions=this.foodPositions.:+(Position());




}