# minesweeper

##General Info
This game is based on basic minesweeper game.
If all safe cells are open, then win.
If the user open a mine or time is up, then lose.

User can drag to collect stars and click to gain time.

##Technologies
java version 11.0.8 <br>
javafx-sdk-11.0.2

##Setup
To run this project, you should first setup Javafx:<br/>
How to get JavaFX and Java 11 working in IntelliJ IDEA:<br/>
https://www.jetbrains.com/help/idea/javafx.html

Where is the .jar:<br> minesweeper -> out -> artifacts -> MineSweeper_jar -> MineSweeper.jar <br/>
How to run the jar:<br/>
java --module-path %PATH_TO_YOUR_JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar %PATH_TO_JAR%

##Reference
Some ideas of developing the basic minesweeper game was refer to this java swing project:
https://github.com/liuyubobobo/Play-with-Algorithm-Visualization/tree/master/07-Mine-Sweeper/Chapter-07-Completed-Codes/Mine-Sweeper-Core




