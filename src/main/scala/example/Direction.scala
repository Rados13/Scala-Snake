package example

object Direction extends Enumeration{
    type Direction = Value
    val North = Value("North")
    val East = Value("East")
    val South = Value("South")
    val West=Value("West")
  
    def opposite(dir:Direction):Direction = dir match {
        case East => West
        case West => East
        case North => South
        case South => North
    }
}