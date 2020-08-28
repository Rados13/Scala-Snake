package example


object SpeedLevel extends Enumeration{
  type SpeedLevel = Value
  val Easy = Value("Easy")
  val Medium = Value("Medium")
  val Hard=Value("Hard")

  def toValue(speed:SpeedLevel): Int = speed match {
      case Easy => 125
      case Medium => 75
      case Hard => 25
  } 
}