package chess.gui;

import chess.game.Colour;
import chess.game.Language;
import chess.game.Move;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import chess.savegame.SaveGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {

    public GuiGame guiGame;
    static Scene startScene, chessScene;
    String chess = "Chess!";
    String schach = "Schach!";

    /**
     * The entry point of the GUI application.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called by the Application to start the GUI.
     *
     * @param primaryStage The initial root stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        guiGame = new GuiGame();

        // Start
        if (guiGame.game.getLanguage() == Language.German) {
            startScene = startWindowGerman(primaryStage);
            primaryStage.setTitle(schach);
        } else {
            startScene = startWindowEnglish(primaryStage);
            primaryStage.setTitle(chess);
        }

        // Chess board
        chessScene = chessWindow(primaryStage);

        primaryStage.setScene(startScene);
        primaryStage.show();
    }


    private Scene startWindowEnglish(Stage primaryStage) {
        Label label = new Label("Welcome to a new Game of Chess!");

        // Define Start Game-Button
        Button startLocalGame = new Button("Start Game");
        startLocalGame.setOnAction(e -> {
            chooseEnemyEnglish();
            chessScene = chessWindow(primaryStage);
            primaryStage.setScene(chessScene);
        });

        // Define Network Game-Button
        Button startNetworkGame = new Button("Network Game");
        startNetworkGame.setOnAction(e -> startNetworkGame(primaryStage));

        // Define Load Game-Button
        Button loadGame = new Button("Load Game");
        loadGame.setOnAction(e -> {
                boolean result = ConfirmationBox.display("Load Game","Do you want to load a saved Game?");

                if (result) {
                    File f = new File("src/main/resources/saves");
                    String[] fileArray = f.list();
                    if(fileArray.length != 0) {
                        List<String> choices = new ArrayList<>();
                        Collections.addAll(choices, fileArray);
                        ChoiceDialog<String> dialog = new ChoiceDialog<>(fileArray[0], choices);
                        dialog.setTitle("Savegame-Choice");
                        dialog.setHeaderText(null);
                        dialog.setContentText("Choose a Savegame:");

                        Optional<String> result2 = dialog.showAndWait();
                        if (result2.isPresent()) {
                            File loadingFile = new File("src/main/resources/saves/" + result2.get());
                            Scanner sc = null;
                            try {
                                sc = new Scanner(loadingFile);
                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            }
                            ArrayList<String> loadingGame = new ArrayList<>();
                            while (true) {
                                assert sc != null;
                                if (!sc.hasNextLine()) break;
                                loadingGame.add(sc.nextLine());
                            }
                            guiGame.game = SaveGame.load(loadingGame);
                        }
                    }
                    // TODO: implement loading a game
                    primaryStage.setScene(chessScene);
                }
        });

        // Define Language-Button
        Button language = new Button("Language");
        language.setOnAction(e -> chooseLanguage(primaryStage, "start"));

        VBox layout1 = new VBox(25);
        layout1.getChildren().addAll(label, startLocalGame, startNetworkGame, loadGame, language);
        layout1.setAlignment(Pos.CENTER);

        return new Scene(layout1,300,300);
    }

    private Scene startWindowGerman(Stage primaryStage) {
        Label label = new Label("Willkommen zu einer neuen Partie Schach!");

        // Define Start Game-Button
        Button startLocalGame = new Button("Starte Spiel");
        startLocalGame.setOnAction(e -> {
            chooseEnemyGerman();
            chessScene = chessWindow(primaryStage);
            primaryStage.setScene(chessScene);
        });

        // Define Network Game-Button
        Button startNetworkGame = new Button("Netzwerk-Spiel");
        startNetworkGame.setOnAction(e -> startNetworkGame(primaryStage));

        // Define Load Game-Button
        Button loadGame = new Button("Lade Spiel");
        loadGame.setOnAction(e -> {
            boolean result = ConfirmationBox.display("Lade Spiel","Möchtest du ein gespeichertes Spiel laden?");

            if (result) {
                File f = new File("src/main/resources/saves");
                String[] fileArray = f.list();
                if(fileArray.length != 0) {
                    List<String> choices = new ArrayList<>();
                    Collections.addAll(choices, fileArray);
                    ChoiceDialog<String> dialog = new ChoiceDialog<>(fileArray[0], choices);
                    dialog.setTitle("Speicherstand-Auswahl");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Wähle einen Speicherstand:");

                    Optional<String> result2 = dialog.showAndWait();
                    if (result2.isPresent()) {
                        File loadingFile = new File("src/main/resources/saves/" + result2.get());
                        Scanner sc = null;
                        try {
                            sc = new Scanner(loadingFile);
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        ArrayList<String> loadingGame = new ArrayList<>();
                        while (true) {
                            assert sc != null;
                            if (!sc.hasNextLine()) break;
                            loadingGame.add(sc.nextLine());
                        }
                        guiGame.game = SaveGame.load(loadingGame);
                    }
                }
                // TODO: implement loading a game
                primaryStage.setScene(chessScene);
            }
        });

        // Define Language-Button
        Button language = new Button("Sprache");
        language.setOnAction(e -> chooseLanguage(primaryStage, "start"));

        VBox layout1 = new VBox(25);
        layout1.getChildren().addAll(label, startLocalGame, startNetworkGame, loadGame, language);
        layout1.setAlignment(Pos.CENTER);

        return new Scene(layout1,300,300);
    }


    private void chooseEnemyEnglish() {
        ButtonType human = new ButtonType("Person");
        ButtonType computer = new ButtonType("AI");

        List<ButtonType> enemy = new ArrayList<>();
        Collections.addAll(enemy,human,computer);

        ButtonType enemyResult = OptionBox.display("Enemy Selection",null,"Choose your Enemy",enemy);
        if (enemyResult == computer){
            guiGame.game.setArtificialEnemy(true);
            guiGame.isRotatingBoard = false;
        }

        if(guiGame.game.isArtificialEnemy()) {
            ButtonType white = new ButtonType("White");
            ButtonType black = new ButtonType("Black");

            List<ButtonType> colour = new ArrayList<>();
            Collections.addAll(colour,white,black);

            ButtonType colourResult = OptionBox.display("Colour Selection",null,"Choose your Colour", colour);
            if (colourResult == white) {
                guiGame.game.setUserColour(Colour.WHITE);
            } else {
                guiGame.game.setUserColour(Colour.BLACK);
                guiGame.turnAI = true;
            }
        }
    }


    private void chooseEnemyGerman() {
        ButtonType human = new ButtonType("Mensch");
        ButtonType computer = new ButtonType("KI");

        List<ButtonType> enemy = new ArrayList<>();
        Collections.addAll(enemy,human,computer);

        ButtonType enemyResult = OptionBox.display("Gegner-Auswahl",null,"Wähle deinen Gegner:",enemy);
        if (enemyResult == computer){
            guiGame.game.setArtificialEnemy(true);
            guiGame.isRotatingBoard = false;
        }

        if(guiGame.game.isArtificialEnemy()) {
            ButtonType white = new ButtonType("Weiß");
            ButtonType black = new ButtonType("Schwarz");

            List<ButtonType> colour = new ArrayList<>();
            Collections.addAll(colour,white,black);

            ButtonType colourResult = OptionBox.display("Farb-Auswahl",null,"Wähle deine Farbe:", colour);
            if (colourResult == white) {
                guiGame.game.setUserColour(Colour.WHITE);
            } else {
                guiGame.game.setUserColour(Colour.BLACK);
                guiGame.turnAI = true;
            }
        }
    }


    private void startNetworkGame(Stage primaryStage) {
        String ipAddress;
        String startConnection;
        String cancel;
        if (guiGame.game.getLanguage() == Language.German){
            ipAddress = "Gebe die IP-Adresse deines Gegners ein: ";
            startConnection = "Starte Verbindung";
            cancel = "Abbrechen";
        } else {
            ipAddress = "Enter IP Address of Enemy: ";
            startConnection = "Start Connection";
            cancel = "Cancel";
        }
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        Scene IP_scene = new Scene(grid, 350,150);
        grid.add(new Label(ipAddress), 0, 0);

        TextField IPAddress = new TextField();
        grid.add(IPAddress,1,2);

        HBox btn = new HBox();
        btn.setPadding(new Insets(5));
        btn.setSpacing(10);
        btn.setAlignment(Pos.BOTTOM_CENTER);
        // Define buttons
        Button btnStartGame = new Button(startConnection);
        Button btnCancel = new Button(cancel);
        btnCancel.setOnAction(e -> primaryStage.setScene(startScene));
        btn.getChildren().addAll(btnStartGame,btnCancel);
        grid.add(btn,1,4);

        primaryStage.setScene(IP_scene);
        primaryStage.show();
    }

    private void chooseLanguage(Stage primaryStage, String source) {
        ButtonType german = new ButtonType("Deutsch");
        ButtonType english = new ButtonType("English");

        List<ButtonType> language = new ArrayList<>();
        Collections.addAll(language,german,english);
        ButtonType result;
        if (guiGame.game.getLanguage() == Language.German) {
            result = OptionBox.display("Sprachauswahl",null,"Wähle Sprache",language);
        } else {
            result = OptionBox.display("Language Selection", null, "Choose Language", language);
        }
        if (result == german) {
            guiGame.game.setLanguage(Language.German);
        } else {
            guiGame.game.setLanguage(Language.English);
        }

        if (source.equals("start")) {
            if (guiGame.game.getLanguage() == Language.German) {
                startScene = startWindowGerman(primaryStage);
                primaryStage.setTitle(schach);
            } else {
                startScene = startWindowEnglish(primaryStage);
                primaryStage.setTitle(chess);
            }
            primaryStage.setScene(startScene);
        } else {
            chessScene = chessWindow(primaryStage);
            if (guiGame.game.getLanguage() == Language.German){
                primaryStage.setTitle(schach);
            } else {
                primaryStage.setTitle(chess);
            }
            primaryStage.setScene(chessScene);
        }
        primaryStage.show();

    }

    private Scene chessWindow(Stage primaryStage) {
        BorderPane pane = new BorderPane();

        ChessBoardView chessBoardView = new ChessBoardView(guiGame);
        VBox right;
        if (guiGame.game.getLanguage() == Language.German) {
            right = generateRightMarginColumnGerman(primaryStage);
        } else {
            right = generateRightMarginColumnEnglish(primaryStage);
        }

        right.setAlignment(Pos.CENTER);
        right.setPadding(new Insets(30));
        pane.setRight(right);
        pane.setCenter(chessBoardView);

        return new Scene(pane, 900, 800);
    }


    private VBox generateRightMarginColumnEnglish(Stage primaryStage){
        //Define New Game-Button
        Button btnNewGame = new Button("New Game");
        btnNewGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("New Game", "Do you really want to start a new Game?");

            if (result) {
                guiGame = new GuiGame();
                startScene = startWindowEnglish(primaryStage);
                chessScene = chessWindow(primaryStage);

                primaryStage.setScene(startScene);
                primaryStage.setTitle(chess);
                primaryStage.show();
            }
        });

        //Define New Game-Button
        Button btnSaveGame = new Button("Save Game");
        btnSaveGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Save Game", "Do you really want to save this Game?");

            if (result) {
                SaveGame.save(guiGame.game);
            }
        });

        // Define Option-Button
        Button btnOptions = new Button("Options");
        btnOptions.setOnAction(event -> {
            ButtonType rotation = new ButtonType("Rotation");
            ButtonType highlight = new ButtonType("Highlight");
            ButtonType changeSelection = new ButtonType("Change Selection");
            ButtonType check = new ButtonType("Check");
            ButtonType cancel = new ButtonType("Cancel");

            ButtonType buttonType;
            do {
                String isBoardRotationStatus;
                String highlightPossibleMoveStatus;
                String allowedChangeSelectedPieceStatus;
                String hintInCheckStatus;
                String on = "ON";
                String off = "OFF";
                if(guiGame.isRotatingBoard){
                    isBoardRotationStatus = on;
                } else {
                    isBoardRotationStatus = off;
                }
                if(guiGame.highlightPossibleMoves){
                    highlightPossibleMoveStatus = on;
                } else {
                    highlightPossibleMoveStatus = off;
                }
                if(guiGame.allowedToChangeSelectedPiece){
                    allowedChangeSelectedPieceStatus = on;
                } else {
                    allowedChangeSelectedPieceStatus = off;
                }
                if(guiGame.hintInCheck){
                    hintInCheckStatus = on;
                } else {
                    hintInCheckStatus = off;
                }

                List<ButtonType> options = new ArrayList<>();
                Collections.addAll(options,rotation,highlight,changeSelection,check,cancel);
                buttonType = OptionBox.display("Game-Settings",
                        " ChessBoard-Rotation: " + isBoardRotationStatus
                                + "\n Highlighting of Moves: " + highlightPossibleMoveStatus
                                + "\n Change a selected Piece: " + allowedChangeSelectedPieceStatus
                                + "\n Player is in Check-Notification: " + hintInCheckStatus,
                        "Choose Option you want to Change:", options);
                if (buttonType == rotation) {
                    guiGame.isRotatingBoard = !guiGame.isRotatingBoard;
                } else if (buttonType == highlight) {
                    guiGame.highlightPossibleMoves = !guiGame.highlightPossibleMoves;
                } else if (buttonType == changeSelection) {
                    guiGame.allowedToChangeSelectedPiece = !guiGame.allowedToChangeSelectedPiece;
                } else if (buttonType == check) {
                    guiGame.hintInCheck = !guiGame.hintInCheck;
                }
            } while (buttonType != cancel); // user chose CANCEL
        });

        // Define Move History-Button
        Button btnMoveHistory = new Button("Move History");
        btnMoveHistory.setOnAction(event -> {
            List<Move> history = guiGame.game.moveHistory;
            StringBuilder historyAsString = new StringBuilder();
            if(!history.isEmpty()){
                for (Move move : history) {
                    historyAsString.append(move.getStartSquare().getLabel().toString()).append("-").append(move.getFinalSquare().getLabel().toString()).append("\n");
                }
            } else {
                historyAsString = new StringBuilder(" ");
            }
            AlertBox.display("Move History", null, historyAsString.toString());
        });

        // Define Language-Button
        Button btnLanguage = new Button("Language");
        btnLanguage.setOnAction(event -> chooseLanguage( primaryStage, "else"));

        VBox box = new VBox(20);
        box.getChildren().addAll(btnOptions, btnNewGame, btnSaveGame, btnMoveHistory, btnLanguage);
        return box;
    }

    private VBox generateRightMarginColumnGerman(Stage primaryStage){
        //Define New Game-Button
        Button btnNewGame = new Button("Neues Spiel");
        btnNewGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Neues Spiel", "Möchtest du wirklich ein neues Spiel starten?");

            if (result) {
                guiGame = new GuiGame();
                startScene = startWindowGerman(primaryStage);
                chessScene = chessWindow(primaryStage);

                primaryStage.setScene(startScene);
                primaryStage.setTitle(schach);
                primaryStage.show();
            }
        });

        // Define Option-Button
        Button btnOptions = new Button("Optionen");
        btnOptions.setOnAction(event -> {
            ButtonType buttonTypeOne = new ButtonType("Rotieren");
            ButtonType buttonTypeTwo = new ButtonType("Hervorheben");
            ButtonType buttonTypeThree = new ButtonType("Auswahl ändern");
            ButtonType buttonTypeFour = new ButtonType("Schach");
            ButtonType buttonTypeFive = new ButtonType("Abbrechen");

            ButtonType buttonType;
            do {
                String isBoardRotationStatus;
                String highlightPossibleMoveStatus;
                String allowedChangeSelectedPieceStatus;
                String hintInCheckStatus;
                String on = "AN";
                String off = "AUS";
                if(guiGame.isRotatingBoard){
                    isBoardRotationStatus = on;
                } else {
                    isBoardRotationStatus = off;
                }
                if(guiGame.highlightPossibleMoves){
                    highlightPossibleMoveStatus = on;
                } else {
                    highlightPossibleMoveStatus = off;
                }
                if(guiGame.allowedToChangeSelectedPiece){
                    allowedChangeSelectedPieceStatus = on;
                } else {
                    allowedChangeSelectedPieceStatus = off;
                }
                if(guiGame.hintInCheck){
                    hintInCheckStatus = on;
                } else {
                    hintInCheckStatus = off;
                }

                List<ButtonType> options = new ArrayList<>();
                Collections.addAll(options,buttonTypeOne,buttonTypeTwo,buttonTypeThree,buttonTypeFour,buttonTypeFive);
                buttonType = OptionBox.display("Spiel-Einstellungen",
                        " Schachbrett rotiert: " + isBoardRotationStatus
                                + "\n Mögliche Züge hervorheben: " + highlightPossibleMoveStatus
                                + "\n Ausgewählte Figur ändern: " + allowedChangeSelectedPieceStatus
                                + "\n Hinweis: Spieler befindet sich im Schach: " + hintInCheckStatus,
                        "Wähle Option, die du ändern möchtest:", options);
                if (buttonType == buttonTypeOne) {
                    guiGame.isRotatingBoard = !guiGame.isRotatingBoard;
                } else if (buttonType == buttonTypeTwo) {
                    guiGame.highlightPossibleMoves = !guiGame.highlightPossibleMoves;
                } else if (buttonType == buttonTypeThree) {
                    guiGame.allowedToChangeSelectedPiece = !guiGame.allowedToChangeSelectedPiece;
                } else if (buttonType == buttonTypeFour) {
                    guiGame.hintInCheck = !guiGame.hintInCheck;
                }
            } while (buttonType != buttonTypeFive); // user chose CANCEL
        });

        // Define Move History-Button
        Button btnMoveHistory = new Button("Zug-Historie");
        btnMoveHistory.setOnAction(event -> {
            List<Move> history = guiGame.game.moveHistory;
            StringBuilder historyAsString = new StringBuilder();
            if(!history.isEmpty()){
                for (Move move : history) {
                    historyAsString.append(move.getStartSquare().getLabel().toString()).append("-").append(move.getFinalSquare().getLabel().toString()).append("\n");
                }
            } else {
                historyAsString = new StringBuilder(" ");
            }
            AlertBox.display("Zug-Historie", null, historyAsString.toString());
        });

        // Define Language-Button
        Button btnLanguage = new Button("Sprache");
        btnLanguage.setOnAction(event -> chooseLanguage(primaryStage, "else"));

        VBox box = new VBox(20);
        box.getChildren().addAll(btnOptions, btnNewGame, btnMoveHistory, btnLanguage);
        return box;
    }



}
