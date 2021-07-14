# ChessGame

This chess game has been developed for the Software Engineering-course in 2021 and is written in Java 8.<br> 
It has a console-based and a graphical 2D-interface where you can play either against a simple AI, or against 
another person on the same computer or in a local network.


# How to run

* `mvn javafx:run` starts the GUI-based frontend
* `mvn javafx:run -Dargs="--no-gui"` starts the CLI-based frontend


# Known bugs
* when playing a network GUI-game your own move will only be shown after the other player sent their move and a button 
  has been clicked


# Maven

Kurzübersicht nützlicher Maven-Befehle. Weitere Informationen finden sich im Tutorial:

* `mvn clean` löscht alle generierten Dateien.
* `mvn compile` übersetzt den Code.
* `mvn javafx:jlink` packt den gebauten Code als modulare Laufzeit-Image. Das Projekt kann danach gestartet werden mit `target/chess/bin/chess`.
* `mvn test` führt die Tests aus.
* `mvn compile site` baut den Code, die Dokumentation und die Tests und führt alle Tests, sowie JaCoCo und PMD inklusive CPD aus. Die Datei `target/site/index.html` bietet eine Übersicht über alle Reports.
* `mvn javafx:run` führt das Projekt aus.
* `mvn javafx:run -Dargs="--no-gui"` führt das Projekt mit Command-Line-Parameter `--no-gui` aus.
