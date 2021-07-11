package chess.gui;

import chess.game.Colour;
import chess.game.Language;
import chess.network.NetworkGame;
import chess.savegame.LoadGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {

    public GuiGame guiGame;
    public EnglishGame englishGame;
    public GermanGame germanGame;
    static Scene startScene, chessScene;
    Color background = Color.FLORALWHITE;

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
        englishGame = new EnglishGame(guiGame,this);
        germanGame = new GermanGame(guiGame,this);

        // Start
        startScene = startWindow(primaryStage, guiGame);

        primaryStage.setScene(startScene);
        primaryStage.show();
    }


    Scene startWindow(Stage primaryStage, GuiGame guiGame){
        BorderPane pane = new BorderPane();
        Image chessboard = new Image(Objects.requireNonNull(Gui.class.getResourceAsStream("chessboard.jpeg")));
        pane.setBackground(new Background(new BackgroundImage(chessboard,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(40,40,
                false,false,false,true))));

        AtomicReference<Scene> startScene = new AtomicReference<>(new Scene(pane, 500, 480));
        startScene.get().getStylesheets().add(Objects.requireNonNull(Gui.class.getResource("style.css")).toString());

        // Define buttons
        Button welcome = new Button();
        Button startLocalGame = new Button();
        Button startNetworkGame = new Button();
        Button loadGame = new Button();
        Button language = new Button();

        List<Button> startButtons = new ArrayList<>();
        Collections.addAll(startButtons,welcome,startLocalGame,startNetworkGame,loadGame,language);
        if (guiGame.game.getLanguage() == Language.German) {
            germanGame.startButtonsGerman(startButtons);
        } else {
            englishGame.startButtonsEnglish(startButtons);
        }

        welcome.getStyleClass().add("startLabel");
        BorderPane.setAlignment(welcome,Pos.TOP_CENTER);
        BorderPane.setMargin(welcome,new Insets(50,0,60,0));
        pane.setTop(welcome);

        String startButtonStyle = "startButtons";
        startLocalGame.getStyleClass().add(startButtonStyle);
        startLocalGame.setOnAction(e -> {
            if (guiGame.game.getLanguage() == Language.German) {
                germanGame.chooseEnemyGerman();
            } else {
                englishGame.chooseEnemyEnglish();
            }
            chessScene = chessWindow(primaryStage,guiGame);
            primaryStage.setScene(chessScene);
        });

        startNetworkGame.getStyleClass().add(startButtonStyle);
        startNetworkGame.setOnAction(e -> startNetworkGame(primaryStage));

        loadGame.getStyleClass().add(startButtonStyle);
        loadGame.setOnAction(e -> {
            if (guiGame.game.getLanguage() == Language.German) {
                germanGame.loadGermanGame(primaryStage);
            } else {
                englishGame.loadEnglishGame(primaryStage);
            }
        });

        language.getStyleClass().add(startButtonStyle);
        language.setOnAction(e -> {
            if (guiGame.game.getLanguage() == Language.German) {
                germanGame.chooseLanguage();
            } else{
                englishGame.chooseLanguage();
            }
            startScene.set(startWindow(primaryStage, guiGame));
            primaryStage.setScene(startScene.get());
            primaryStage.show();
        });

        VBox layout1 = new VBox(25);
        layout1.getChildren().addAll( startLocalGame, startNetworkGame, loadGame, language);
        layout1.setAlignment(Pos.TOP_CENTER);
        pane.setCenter(layout1);

        return startScene.get();
    }


    void loadGame(ChoiceDialog<String> dialog, Stage primaryStage){
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            File loadingFile = new File("src/main/resources/saves/" + result.get());

            guiGame.game = LoadGame.loadFile(loadingFile);

            chessScene = chessWindow(primaryStage, guiGame);
            primaryStage.setScene(chessScene);
        }
    }


    void startNetworkGame(Stage primaryStage) {
        String ipAddress = "Enter IP Address of Enemy: ";
        String startConnection = "Start Connection";
        String cancel = "Cancel";
        String netError = "Network-Error";
        String connectionError = "Not possible to connect to entered IP-Address.";
        if (guiGame.game.getLanguage() == Language.German){
            ipAddress = "Gib die IP-Adresse deines Gegners ein: ";
            startConnection = "Starte Verbindung";
            cancel = "Abbrechen";
            netError = "Netzwerk-Fehler";
            connectionError = "Keine Verbindung zu eingegebener IP-Addresse möglich";
        }
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        Scene IP_scene = new Scene(grid, 350,150);
        grid.add(new Label(ipAddress), 0, 0);

        TextField IPAddress = new TextField();
        grid.add(IPAddress,0,2);

        HBox btn = new HBox();
        btn.setPadding(new Insets(5));
        btn.setSpacing(10);
        btn.setAlignment(Pos.BOTTOM_CENTER);

        // Define buttons
        Button btnStartGame = new Button(startConnection);
        String finalNetError = netError;
        String finalConnectionError = connectionError;
        btnStartGame.setOnAction(event -> {
            String ipAddressText = IPAddress.getText();
            if (ipAddressText.equals("0")){
                guiGame.connectionSocket = NetworkGame.startServer();
                guiGame.game.setNetworkServer(true);
                guiGame.game.setUserColour(Colour.WHITE);
                chessScene = chessWindow(primaryStage,guiGame);
                primaryStage.setScene(chessScene);
            } else {
                if (NetworkGame.sendPingRequest(ipAddressText)) {
                    guiGame.connectionSocket = NetworkGame.startClient();
                    guiGame.game.setNetworkClient(true);
                    guiGame.game.setUserColour(Colour.BLACK);
                    chessScene = chessWindow(primaryStage,guiGame);
                    primaryStage.setScene(chessScene);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(finalNetError);
                    alert.setHeaderText(null);
                    alert.setContentText(finalConnectionError);
                    alert.showAndWait();
                }
            }
        });

        Button btnCancel = new Button(cancel);
        btnCancel.setOnAction(e -> primaryStage.setScene(startScene));

        btn.getChildren().addAll(btnStartGame,btnCancel);
        grid.add(btn,0,4);

        primaryStage.setScene(IP_scene);
        primaryStage.show();
    }


    Scene chessWindow(Stage primaryStage, GuiGame guiGame) {
        BorderPane pane = new BorderPane();

        ChessBoardView chessBoardView = new ChessBoardView(guiGame,this,germanGame,englishGame);
        MenuBar menuBar = chessBoardView.createMenu(primaryStage);

        pane.setTop(menuBar);
        pane.setCenter(chessBoardView);
        pane.setBackground(new Background(new BackgroundFill(background,CornerRadii.EMPTY,Insets.EMPTY)));

        return new Scene(pane, 800, 825);
    }


}
