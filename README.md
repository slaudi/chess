# ChessGame

This chess game has been developed for the Software Engineering-course in 2021 and is written in Java 8. 
It has a console-based and a graphical interface where you can play either against a simple AI, or against another 
person either at the same computer or in a local network. 


# How to run

* `mvn javafx:run` starts the GUI-based frontend
* `mvn javafx:run -Dargs="--no-gui"` starts the CLI-based frontend


# Maven

Kurzübersicht nützlicher Maven-Befehle. Weitere Informationen finden sich im Tutorial:

* `mvn clean` löscht alle generierten Dateien.
* `mvn compile` übersetzt den Code.
* `mvn javafx:jlink` packt den gebauten Code als modulare Laufzeit-Image. Das Projekt kann danach gestartet werden mit `target/chess/bin/chess`.
* `mvn test` führt die Tests aus.
* `mvn compile site` baut den Code, die Dokumentation und die Tests und führt alle Tests, sowie JaCoCo und PMD inklusive CPD aus. Die Datei `target/site/index.html` bietet eine Übersicht über alle Reports.
* `mvn javafx:run` führt das Projekt aus.
* `mvn javafx:run -Dargs="--no-gui"` führt das Projekt mit Command-Line-Parameter `--no-gui` aus.


# Artefakte

Download-Link der generierten Site des aktuellsten Commits auf dem Main-Branch:

https://projects.isp.uni-luebeck.de/christian.stelter/schach/-/jobs/artifacts/main/download?job=test