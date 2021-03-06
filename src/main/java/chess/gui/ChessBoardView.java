package chess.gui;

import chess.game.*;
import chess.pieces.Piece;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.List;


/**
 * The class generating the chess board as a GUI.
 */
public class ChessBoardView extends BorderPane {

    private final GuiGame guiGame;
    final GermanGame germanGame;
    final EnglishGame englishGame;

    private final String highlightBackground = "-fx-background-color: SKYBLUE";
    private final int buttonHeight = 85;
    private final int buttonWidth = 85;


    /**
     * Constructor for GuiGame-Class.
     *
     * @param guiGame The State of the current Game the View needs to display it.
     * @param germanGame instance of game in german language
     * @param englishGame instance of game in english language
     */
    public ChessBoardView(GuiGame guiGame, GermanGame germanGame, EnglishGame englishGame) {
        this.guiGame = guiGame;
        this.germanGame = germanGame;
        this.englishGame = englishGame;

        generatePane();
    }



    void generatePane() {
        HBox heading;
        if (guiGame.game.getLanguage() == Language.German) {
            heading = germanGame.generatePlayersMoveLabelBox();
        } else {
            heading = englishGame.generatePlayersMoveLabelBox();
        }
        heading.setAlignment(Pos.TOP_CENTER);
        heading.setPadding(new Insets(5));
        setTop(heading);

        GridPane chessBoard = generateGrid();
        chessBoard.setAlignment(Pos.TOP_CENTER);
        chessBoard.setPadding(new Insets(0, 10, 10, 30));
        setCenter(chessBoard);

        HBox bottom = generateBeatenPieces();
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setPadding(new Insets(5, 0, 5, 40));
        setBottom(bottom);

        if (guiGame.game.isNetworkGame() && guiGame.game.currentPlayer.getColour() != guiGame.game.getUserColour()) {
            guiGame.doNetworkMove();
        }
    }


    GridPane generateGrid(){
        if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null) {
            // player selected first square
            if (guiGame.getSquareStart().getOccupiedBy() != null) {
                // selected first square is occupied
                if (guiGame.highlightPossibleMoves) {
                    // 'highlighting' is turned on
                    if (guiGame.game.currentPlayer.getColour() == guiGame.getSquareStart().getOccupiedBy().getColour() ) {
                        return generateHighlightedButtonGrid();
                    } else {
                        // selected Piece is not players colour
                        guiGame.setSquareStartNull();
                        popUpBoxLanguage(1);
                    }
                } else if (guiGame.getSquareStart().getOccupiedBy().getColour() != guiGame.game.currentPlayer.getColour()) {
                    // 'highlighting' is turned off and selected Piece is not players colour
                    guiGame.setSquareStartNull();
                    popUpBoxLanguage(1);
                }
            } else {
                // selected first square is empty
                guiGame.setSquareStartNull();
                popUpBoxLanguage(2);
            }
        }
        // no highlighted moves
        return generateButtonGrid();
    }


    private void popUpBoxLanguage(int answer){
        if (guiGame.game.getLanguage() == Language.German){
            germanGame.gridAnswer(answer);
        } else {
            englishGame.gridAnswer(answer);
        }
    }


    private HBox generateBeatenPieces(){
        HBox box = new HBox();
        if(!guiGame.game.beatenPieces.isEmpty()){
            for (Piece piece: guiGame.game.beatenPieces){
                box.getChildren().add(SetImages.getBeatenPieces(piece.getType(),piece.getColour()));
            }
        }
        return box;
    }


    GridPane generateButtonGrid() {
        GridPane grid = new GridPane();
        selectButtons(grid);
        addIndices(grid);
        return grid;
    }


    GridPane generateHighlightedButtonGrid() {
        GridPane grid = new GridPane();
        selectHighlightedButtons(grid);
        addIndices(grid);
        return grid;
    }


    private void selectButtons(GridPane grid) {
        for (int y = 0; y < guiGame.game.chessBoard.getHeight(); y++) {
            for (int x = 0; x < guiGame.game.chessBoard.getWidth(); x++) {
                Button button = new Button();
                button.setMinHeight(buttonHeight);
                button.setMinWidth(buttonWidth);
                if (guiGame.getSquareStart() != null && guiGame.game.chessBoard.getSquareAt(x, y) == guiGame.getSquareStart()){
                    button.setStyle(highlightBackground);
                }
                if ((y + x) % 2 != 0) {
                    button.setStyle(guiGame.white);
                } else {
                    button.setStyle(guiGame.black);
                }
                if (guiGame.game.chessBoard.getSquareAt(x, y) == guiGame.getSquareStart()){
                    button.setStyle(highlightBackground);
                }
                setButtonsOnGrid(button,grid,x,y);
            }
        }
    }


    private void selectHighlightedButtons(GridPane grid) {
        List<Square> allowedSquares = guiGame.computePossibleSquares();

        if (guiGame.game.getLanguage() == Language.German){
            germanGame.noAllowedSquares(allowedSquares);
        } else {
            englishGame.noAllowedSquares(allowedSquares);
        }
        String highlightBorder = "-fx-border-color: SKYBLUE";
        String border = "-fx-border-width: 3px";
        for (int y = 0; y < guiGame.game.chessBoard.getHeight(); y++) {
            for (int x = 0; x < guiGame.game.chessBoard.getWidth(); x++) {
                Button button = new Button();
                button.setMinHeight(buttonHeight);
                button.setMinWidth(buttonWidth);
                if ((y + x) % 2 != 0) {
                    button.setStyle(guiGame.white);
                } else {
                    button.setStyle(guiGame.black);
                }
                if (guiGame.game.chessBoard.getSquareAt(x, y) == guiGame.getSquareStart()){
                    button.setStyle(highlightBackground);
                }
                if (allowedSquares.contains(guiGame.game.chessBoard.getSquareAt(x, y)) && (y + x) % 2 != 0) {
                    button.setStyle(highlightBorder + ";" + border + ";" + guiGame.white);
                } else if (allowedSquares.contains(guiGame.game.chessBoard.getSquareAt(x, y))) {
                    button.setStyle(highlightBorder + ";" + border + ";" + guiGame.black);
                }
                setButtonsOnGrid(button,grid,x,y);
            }
        }
    }

    // Adds the row and column indices to the chessboard GUI
    private void addIndices(GridPane grid) {
        String[] columns = {"A","B","C","D","E","F","G","H"};
        String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8"};
        int fontSize = 17;
        if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.isRotatingBoard
                || guiGame.game.isArtificialEnemy() && guiGame.game.getUserColour() == Colour.BLACK) {
            int c = 0;
            for (int i = columns.length - 1; i >= 0; i--) {
                Label letter = new Label(columns[i]);
                letter.setFont(new Font(fontSize));
                GridPane.setHalignment(letter, HPos.CENTER);
                grid.add(letter,c,rows.length);

                Label number = new Label(rows[i]);
                number.setFont(new Font(fontSize));
                grid.add(number,columns.length,i);
                c++;
            }
        } else {
            for (int i = 0; i <= columns.length - 1; i++) {
                Label letter = new Label(columns[i]);
                letter.setFont(new Font(fontSize));
                GridPane.setHalignment(letter, HPos.CENTER);
                grid.add(letter,i,rows.length);

                Label number = new Label(rows[rows.length-1 - i]);
                number.setFont(new Font(fontSize));
                grid.add(number,columns.length,i);
            }
        }
    }


    private void setButtonsOnGrid(Button button, GridPane grid, int x, int y) {
        button.setGraphic(SetImages.chooseImage(guiGame.game.chessBoard.getSquareAt(x, y)));

        if (guiGame.game.isArtificialEnemy()) {
            if (guiGame.game.getUserColour() == Colour.BLACK) {
                grid.add(button, guiGame.game.chessBoard.getWidth()-1 - x, guiGame.game.chessBoard.getHeight()-1 - y);
            } else {
                grid.add(button, x, y);
            }
        } else if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.isRotatingBoard) {
            grid.add(button, guiGame.game.chessBoard.getWidth()-1 - x, guiGame.game.chessBoard.getHeight()-1 - y);
        } else {
            grid.add(button, x, y);
        }

        if (guiGame.game.isArtificialEnemy() && guiGame.turnAI) {
            guiGame.makeAIMove(this);
        }
        if (!guiGame.game.isADraw() && !guiGame.game.isCheckMate()) {
            button.setOnAction(event -> {
                guiGame.setBothSquares(guiGame.game.chessBoard.getSquareAt(x, y));
                guiGame.setButtonAction(this);
            });
        }
    }

}
