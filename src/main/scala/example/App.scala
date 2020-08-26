package example

import org.scalajs.dom
import org.scalajs.dom.document
import scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html


object SpeedLevel extends Enumeration{
  type SpeedLevel = Value
  val Easy = Value("Easy")
  val Medium = Value("Medium")
  val Hard=Value("Hard")
}

object App {

  import SpeedLevel._

  var speedLevel:SpeedLevel = Easy;
  setSpeedOnEasy();
  var mapSize: Int = 50;

  def main(args: Array[String]): Unit = {
    println("Hello world!")
  }

  def changeSelectedButton(which:SpeedLevel){
    document.getElementById(this.speedLevel.toString()).setAttribute("style","background-color: GhostWhite;");
    document.getElementById(which.toString()).setAttribute("style","background-color: DarkGray;");
    this.speedLevel = which;
  }

  @JSExportTopLevel("setSpeedOnEasy")
  def setSpeedOnEasy():Unit = changeSelectedButton(Easy);

  @JSExportTopLevel("setSpeedOnMedium")
  def setSpeedOnMedium():Unit = changeSelectedButton(Medium);

  @JSExportTopLevel("setSpeedOnHard")
  def setSpeedOnHard():Unit = changeSelectedButton(Hard);

  @JSExportTopLevel("startGame")
  def startGame():Unit = {
    this.mapSize = 50;
    try{
      this.mapSize = document.getElementById("size").asInstanceOf[html.Input].value.toInt;
    }catch{
       case e:NumberFormatException => println(s"Not getting input!");
    }

    if(this.mapSize.isFinite && this.mapSize>0){
      dom.window.alert(s"Start a game ${this.mapSize}");
      val canvas = document.getElementById("map").asInstanceOf[html.Canvas];
      Game(canvas,this.mapSize).play();
    }else{
      dom.window.alert("You give incorrect map size");
    }

    
  }
}