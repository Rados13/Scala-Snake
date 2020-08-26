package example

import scala.util.Random
import Direction._

object Snake{  
    def apply(mapSize:Int):Snake = {
        Position.changeMapSize(mapSize);
        val snakeElems:Array[Position] = (0 to 4).foldLeft(Array(Position(mapSize)))(
            (acc:Array[Position],_) => acc.:+(acc.last.nextPosition(East)))
        new Snake(snakeElems)
    }
}

class Snake(var positions:Array[Position]){
    var direction:Direction = West;

    def changeDirection(newDirection:Direction):Unit = if(Direction.opposite(direction)!=newDirection)direction = newDirection
    def move:Unit = {
        val firstPositions:Position = positions.head
        this.positions = firstPositions.nextPosition(direction) +: positions.init
        println(s"size: ${this.positions.size}");
    }

    def grow = {
        val lastPositions:Position = positions.last;
        this.positions = positions :+ lastPositions.nextPosition(Direction.opposite(direction)) 
    }

    def notEatYourself:Boolean = positions.distinct.size==positions.size
}