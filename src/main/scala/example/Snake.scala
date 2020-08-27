package example

import scala.util.Random
import Direction._

object Snake{  
    def apply(mapSize:Int):Snake = {
        Position.changeMapSize(mapSize)
        val snakeElems:Array[Position] = (0 to 4).foldLeft(Array(Position(mapSize)))(
            (acc:Array[Position],_) => acc.:+(acc.last.nextPosition(East)))
        new Snake(snakeElems)
    }
}

class Snake(var positions:Array[Position]){
    var direction:Direction = West
    var lastMoveDirection:Direction = East
    var score:Int = 0
    var last:Int = 0

    def changeDirection(newDirection:Direction):Unit = if(Direction.opposite(lastMoveDirection)!=newDirection)direction = newDirection

    def getScore():Int = this.score

    def move:Unit = {
        val firstPositions:Position = positions.head
        this.positions = firstPositions.nextPosition(direction) +: positions.init
        lastMoveDirection = direction
        println(s"size: ${this.positions.size}")
    }

    override def toString(): String = s"[${this.positions.map(elem => elem.toString).mkString(",")}]"

    def grow:Unit = {
        last+=2
        score+=last
        val lastPositions:Array[Position] = positions.takeRight(2)
        val eitherDirection = lastPositions(0).neighbourDirection(lastPositions(1))
        eitherDirection match{
            case Right(dir) => this.positions = positions :+ lastPositions(1).nextPosition(dir)
            case Left(value) => println(s"Something go wrong $value")
        }
    }

    def notEatYourself:Boolean = positions.distinct.size==positions.size
}