# Scala-Snake

## Introduction

This project is snake game created with use of Scala.js.
<br>
If you want play this game [click here](https://rados13.github.io/Scala-Snake/)

## Documentation

I divided created classes on three folders (Trying to use of MVC Pattern):

- Controller:
    - [App](src/main/scala/example/controller/App.scala) - this object handle inputs which are get from user to set up parameters of game
    - [Game](src/main/scala/example/controller/Game.scala) - this case class is responsible of preparing workflow and handle user inputs during game
- Model:
    - [Direction](src/main/scala/example/model/Direction.scala) - enum declaration of possible snake move direction
    - [Position](src/main/scala/example/model/Position.scala) - case class declaring a type of position on map
    - [Snake](src/main/scala/example/model/Snake.scala) - class with all poible change of snake state for example change of his positions, growing, change direction
    - [SpeedLevel](src/main/scala/example/model/SpeedLevel.scala) - enum declaration of possible game speed
- View:
    - [MapPainter](src/main/scala/example/view/MapPainer.scala) - case class responsible of painting map after each turn(amount of time)

In [index.html](index.html) is file with page tags and [main.css](styles/main.css) is style sheet for this page
