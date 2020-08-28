# Scala-Snake

## Introduction

This project is snake game created with use of scalaJs.

## Documentation

I divided created classes on three folders (Trying to use of MVC Pattern):

- Controller:
    - App - this object handle inputs which are get from user to set up parameters of game
    - Game - this case class is responsible of preparing workflow and handle user inputs during game
- Model:
    - Direction - enum declaration of possible snake move direction
    - Position - case class declaring a type of position on map
    - Snake - class with all poible change of snake state for example change of his positions, growing, change direction
    - SpeedLevel - enum declaration of possible game speed
- View:
    - MapPainter - case class responsible of painting map after each turn(amount of time)

In index.html is file with page tags and styles/main.css is style sheet for this page