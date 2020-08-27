package example

import org.scalajs.dom
import org.scalajs.dom.document
import scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import SpeedLevel._



object App {

  var speedLevel:SpeedLevel = Easy
  setSpeedOnEasy()
  var mapSize: Int = 50
  var game:Game = null;


  def main(args: Array[String]): Unit = {
    println("Hello world!")
  }

  def changeSelectedButton(which:SpeedLevel){
    document.getElementById(this.speedLevel.toString()).setAttribute("style","background-color: GhostWhite")
    document.getElementById(which.toString()).setAttribute("style","background-color: DarkGray")
    this.speedLevel = which
  }

  @JSExportTopLevel("setSpeedOnEasy")
  def setSpeedOnEasy():Unit = changeSelectedButton(Easy)

  @JSExportTopLevel("setSpeedOnMedium")
  def setSpeedOnMedium():Unit = changeSelectedButton(Medium)

  @JSExportTopLevel("setSpeedOnHard")
  def setSpeedOnHard():Unit = changeSelectedButton(Hard)

  @JSExportTopLevel("startGame")
  def prepareGame():Unit = {
    this.mapSize = 50
    try{
      this.mapSize = document.getElementById("size").asInstanceOf[html.Input].value.toInt
    }catch{
       case e:NumberFormatException => println(s"Not getting input!")
    }

    if(this.mapSize.isFinite && this.mapSize>0){
      dom.window.alert(s"Start a game ${this.mapSize}")
      val canvas = document.getElementById("map").asInstanceOf[html.Canvas]
      game = Game(canvas,this.mapSize,speedLevel)
      startGame
    }else{
      dom.window.alert("You give incorrect map size")
    }    
  }

  def startGame:Unit = {
    document.getElementById("params").setAttribute("style","display: none")
    document.getElementById("pause").innerText = "Pause game"
    println(document.getElementById("game").setAttribute("style","display: flex"))
    game.play
  }

  @JSExportTopLevel("pauseGame")
  def pauseGame():Unit = {
    val doc = document.getElementById("pause").innerText
    doc match {
      case "Start game" => 
        document.getElementById("pause").innerText = "Pause game"
        game.play
      case "Pause game" =>
        document.getElementById("pause").innerText = "Start game"
        game.pause
    }
  }

}