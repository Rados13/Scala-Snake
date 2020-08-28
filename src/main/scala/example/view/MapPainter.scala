package example

import org.scalajs.dom.html
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.raw.CanvasRenderingContext2D
import Position._

object MapPainter{



    def getContext(mapHtml:html.Canvas): Either[String,CanvasRenderingContext2D] = mapHtml.getContext("2d") match {
            case context: CanvasRenderingContext2D => Right(context)
            case other => 
                dom.window.alert(s"Error $other")
                Left(s"Error $other")
    } 

    def apply(mapHtml:html.Canvas,mapSize:Int):MapPainter = {
        val ctx:CanvasRenderingContext2D = getContext(mapHtml).getOrElse(null)
        if(ctx!=null)MapPainter(ctx,mapSize,mapHtml.height / mapSize) else null
    }
}


case class MapPainter(ctx:CanvasRenderingContext2D,mapSize:Int,fieldSize:Int){

    def printMap(snakes:Array[Snake],foodPositions:Array[Position]) ={
        ctx.clearRect(0,0,mapSize*fieldSize,mapSize*fieldSize)
        snakes.zipWithIndex.foreach{case (snake,i) =>   document.getElementById(s"score$i").innerText = snake.getScore().toString}
        paintField()
        snakes.zipWithIndex.foreach{case (snake,i) => paintSnake(snake,i)}
        paintFood(foodPositions)        
    }

    def paintField():Unit ={
        ctx.fillStyle = "yellow"
        ctx.fillRect(0,0,mapSize*fieldSize,mapSize*fieldSize)
    }

    def paintSnake(snake:Snake,idx:Int):Unit ={
        if(idx==0)ctx.fillStyle = "green"
        else ctx.fillStyle = "blue"
        snake.positions.foreach(elem => ctx.fillRect(elem.positionX*fieldSize,elem.positionY*fieldSize,fieldSize,fieldSize))
    }

    def paintFood(foodPositions:Array[Position]):Unit = {
        foodPositions.foreach(elem => {
            ctx.beginPath()
            ctx.arc(elem.positionX*fieldSize+fieldSize/2,elem.positionY*fieldSize+fieldSize/2,fieldSize/2,0,2*Math.PI)
            ctx.fillStyle = "red"
            ctx.fill() 
        })

    }


}