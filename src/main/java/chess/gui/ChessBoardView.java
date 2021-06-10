package chess.gui;

import chess.cli.Cli;
import chess.engine.Engine;
import chess.game.*;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.List;


/**
 *
 */
public class ChessBoardView extends BorderPane {

    public GuiGame guiGame;
    public GermanGame germanGame;
    public EnglishGame englishGame;
    String white;
    String black;
    String highlight;
    int buttonHeight = 85;
    int buttonWidth = 85;
    int fontSize = 17;

    public ChessBoardView(GuiGame guiGame) {
        this.guiGame = guiGame;
        this.white = "-fx-background-color: floralwhite";
        this.black = "-fx-background-color: slategray";
        this.highlight = "-fx-border-color: skyblue";

        this.germanGame = new GermanGame(guiGame,this);
        this.englishGame = new EnglishGame(guiGame,this);

        generatePane();
    }


    void generatePane() {
        HBox heading;
        GridPane chessBoard;
        if (guiGame.game.isGerman()) {
            heading = germanGame.generatePlayersMoveLabelBox();
            chessBoard = germanGame.generateGrid();
        } else {
            heading = englishGame.generatePlayersMoveLabelBox();
            chessBoard = englishGame.generateGrid();
        }

        heading.setAlignment(Pos.TOP_CENTER);
        heading.setPadding(new Insets(5));
        setTop(heading);

        chessBoard.setAlignment(Pos.TOP_CENTER);
        chessBoard.setPadding(new Insets(0, 10, 10, 30));
        setCenter(chessBoard);

        HBox bottom = generateBeatenPieces();
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setPadding(new Insets(5, 0, 5, 40));
        setBottom(bottom);
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
        setButtons(grid);
        addIndices(grid);
        return grid;
    }

    GridPane generateHighlightedButtonGrid() {
        GridPane grid = new GridPane();
        setHighlightedButtons(grid);
        addIndices(grid);
        return grid;
    }

    private void setButtons(GridPane grid) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Button button = new Button();
                button.setMinHeight(buttonHeight);
                button.setMinWidth(buttonWidth);
                if ((y+x) %2 == 0) {
                    button.setStyle(white);
                } else {
                    button.setStyle(black);
                }
                setButtonsOnGrid(button,grid,x,y);
            }
        }
    }

    private void setHighlightedButtons(GridPane grid) {
        List<Square> allowedSquares = guiGame.computePossibleSquares();

        if (guiGame.game.isGerman()){
            germanGame.noAllowedSquares(allowedSquares);
        } else {
            englishGame.noAllowedSquares(allowedSquares);
        }

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Button button = new Button();
                button.setMinHeight(buttonHeight);
                button.setMinWidth(buttonWidth);
                if ((y + x) % 2 == 0) {
                    button.setStyle(white);
                } else {
                    button.setStyle(black);
                }
                String border = "-fx-border-width: 3px";
                if (allowedSquares.contains(guiGame.game.chessBoard.getSquareAt(x, y)) && (y + x) % 2 == 0) {
                    button.setStyle(highlight + ";" + border + ";" + white);
                } else if (allowedSquares.contains(guiGame.game.chessBoard.getSquareAt(x, y))) {
                    button.setStyle(highlight + ";" + border + ";" + black);
                }
                setButtonsOnGrid(button,grid,x,y);
            }
        }
    }

    // Adds the row and column indices to the chessboard GUI
    private void addIndices(GridPane grid) {
        String[] columns = {"A","B","C","D","E","F","G","H"};
        String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8"};
        if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.isRotatingBoard
                || !guiGame.game.enemyIsHuman && guiGame.game.userColour == Colour.BLACK) {
            int c = 0;
            for (int i = columns.length - 1; i >= 0; i--) {
                Label letter = new Label(columns[i]);
                letter.setFont(new Font(fontSize));
                grid.add(letter,c,8);

                Label number = new Label(rows[i]);
                number.setFont(new Font(fontSize));
                grid.add(number,8,i);
                c++;
            }
        } else {
            for (int i = 0; i <= columns.length - 1; i++) {
                Label letter = new Label(columns[i]);
                letter.setFont(new Font(fontSize));
                grid.add(letter,i,8);

                Label number = new Label(rows[7-i]);
                number.setFont(new Font(fontSize));
                grid.add(number,8,i);
            }
        }
    }

    private void setButtonsOnGrid(Button button, GridPane grid, int x, int y) {
        button.setGraphic(SetImages.chooseImage(guiGame.game.chessBoard.getSquareAt(x, y)));

        if (!guiGame.game.enemyIsHuman) {
            if (guiGame.game.userColour == Colour.BLACK) {
                grid.add(button, 7 - x, 7 - y);
            } else {
                grid.add(button, x, y);
            }
        } else if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.isRotatingBoard) {
            grid.add(button, 7 - x, 7 - y);
        } else {
            grid.add(button, x, y);
        }

        if (!guiGame.game.enemyIsHuman && guiGame.turnAI) {
            makeAIMove();
        }
        if (!guiGame.game.isADraw() && !guiGame.game.isCheckMate()) {
            button.setOnAction(event -> {
                guiGame.setBothMovingSquares(guiGame.game.chessBoard.getSquareAt(x, y));
                setButtonAction();
            });
        }
    }

    private void setButtonAction() {
        if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() != null) {
            int result = processingMovement();
            if (result == 0) {
                // Move is allowed
                guiGame.setSquareStart(null);
                guiGame.setSquareFinal(null);
                guiGame.game.isInCheck();
                guiGame.game.isCheckMate();
                guiGame.turnAI = true;
                generatePane();
                guiGame.game.isInCheck();
                guiGame.game.isCheckMate();
                if (guiGame.game.isGerman()){
                    germanGame.generateAnswer(result);
                } else {
                    englishGame.generateAnswer(result);
                }
            } else if (result == 3){
                // you're allowed to change your selected Piece
                guiGame.setSquareStart(guiGame.getSquareFinal());
                guiGame.setSquareFinal(null);
                generatePane();
            } else {
                // not an allowed Move
                notAllowedMove(result);
            }
        } else if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null) {
            generatePane();
        }
    }

    private void makeAIMove(){
        if (!guiGame.game.isCheckMate() && !guiGame.game.isADraw()) {
            // generate move of AI
            int AI_result;
            do {
                Move AIMove = Engine.nextBestMove(guiGame.game);
                if(AIMove == null){
                    guiGame.setDraw(true);
                    break;
                }
                guiGame.setSquareStart(AIMove.getStartSquare());
                guiGame.setSquareFinal(AIMove.getFinalSquare());
                AI_result = processingMovement();
                guiGame.setSquareStart(null);
                guiGame.setSquareFinal(null);
            } while (AI_result != 0);
            guiGame.turnAI = false;
            generatePane();
        }
    }

    private void notAllowedMove(int result){
        // show why it's not allowed
        if (guiGame.game.isGerman()) {
            germanGame.generateAnswer(result);
        } else {
            englishGame.generateAnswer(result);
        }
        if (!guiGame.allowedToChangeSelectedPiece && !guiGame.game.isInCheck()) {
            // not allowed to change Piece after having selected it
            guiGame.setSquareStart(guiGame.getSquareStart());
        } else {
            guiGame.setSquareStart(null);
        }
        guiGame.setSquareFinal(null);
        generatePane();
    }


    private int processingMovement() {
        if(!guiGame.game.currentPlayer.isLoser() || !guiGame.game.isADraw() || !guiGame.game.isCheckMate()
                && guiGame.getSquareStart() != null && guiGame.getSquareFinal() != null) {
            return isMoveAllowed();
        }
        if(guiGame.game.isCheckMate()){
            // player is check mate
            return 5;
        }
        if(guiGame.game.isADraw()){
            return 6;
        }
        return 7;
    }

    private int isMoveAllowed() {
        Piece selectedPiece = guiGame.getSquareStart().getOccupiedBy();
        Square startSquare = guiGame.getSquareStart();
        Square finalSquare = guiGame.getSquareFinal();
        if (!guiGame.allowedToChangeSelectedPiece && finalSquare.getOccupiedBy() != null
                && selectedPiece.getColour() == finalSquare.getOccupiedBy().getColour() && finalSquare != startSquare){
            // you can't change selected Piece to another one
            return 1;
        }

        if (guiGame.game.isMoveAllowed(selectedPiece, finalSquare)) {
            char key = 'Q';
            key = checkPromotion(selectedPiece,finalSquare,key);
            if (!guiGame.game.processMove(startSquare, finalSquare, key)) {
                // wouldn't free King from check
                return 2;
            }
        } else if (finalSquare.getOccupiedBy() != null && selectedPiece.getColour() == finalSquare.getOccupiedBy().getColour()){
            // you're allowed to change selected Piece to another one
            return 3;
        } else {
            // move is not allowed
            return 4;
        }
        // move is allowed
        return 0;
    }

    private char checkPromotion(Piece selectedPiece, Square finalSquare, char key){
        if(selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).promotionPossible(finalSquare)){
            if (guiGame.game.isGerman()) {
                key = germanGame.promotionSelection();
            } else {
                key = englishGame.promotionSelection();
            }
        }
        return key;
    }

}
