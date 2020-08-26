package example

import Direction._
import scala.util.Random

object Position{
    private var mapSize:Int = 50;
    val rand = new Random();
    def apply(mapSize:Int = this.mapSize):Position = Position(rand.nextInt(mapSize),rand.nextInt(mapSize))
    def changeMapSize (size:Int) = this.mapSize = size;
}

case class Position(positionX:Int,positionY:Int){
    def nextPosition(direction:Direction):Position ={
        direction match {
            case East => if (positionX + 1 >= Position.mapSize) Position(0,positionY) else Position(positionX+1,positionY) 
            case West => if (positionX - 1 < 0) Position(Position.mapSize - 1,positionY) else Position(positionX-1,positionY)
            case South => if (positionY + 1 >= Position.mapSize) Position(positionX,0) else Position(positionX,positionY+1)
            case North => if (positionY - 1 < 0)Position(positionX,Position.mapSize - 1) else Position(positionX,positionY-1)
        }
    }
}
