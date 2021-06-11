package chess.gui;

import chess.engine.Engine;
import chess.game.*;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 */
public class ChessBoardView extends BorderPane {

    public GuiGame guiGame;

    String white;
    String black;
    String highlight;
    int buttonHeight = 85;
    int buttonWidth = 85;
    int fontSize = 17;


    /**
     * Constructor for GUIgame-Class
     * @param guiGame The State of Current Game the View needs to display
     */
    public ChessBoardView(GuiGame guiGame) {
        this.guiGame = guiGame;

        this.white = "-fx-background-color: floralwhite";
        this.black = "-fx-background-color: slategray";
        this.highlight = "-fx-border-color: skyblue";

        generatePane();
    }


    private void generatePane() {
        HBox heading = generatePlayersMoveLabelBox();
        heading.setAlignment(Pos.TOP_CENTER);
        heading.setPadding(new Insets(5));
        setTop(heading);

        GridPane chessBoard = generateGrid();
        chessBoard.setAlignment(Pos.TOP_CENTER);
        chessBoard.setPadding(new Insets(0,10,10,30));
        setCenter(chessBoard);

        HBox bottom = generateBeatenPieces();
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setPadding(new Insets(5,0,5,40));
        setBottom(bottom);
    }


    private HBox generatePlayersMoveLabelBox(){
        Label label = new Label(guiGame.game.currentPlayer.getColour().toString() + "s Turn");
        if (guiGame.game.isCheckMate()) {
            AlertBox.display("Game Information","CheckMate",guiGame.game.currentPlayer.getColour().toString() + " has lost the Game!");
            label = new Label(guiGame.game.currentPlayer.getColour().toString() + " lost the Game!");
        } else if (guiGame.game.isADraw() || guiGame.draw) {
            AlertBox.display("Game Information","Draw","The Game ended in a draw!");
            label = new Label("The Game ended in a draw!");
        } else if (guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            label = new Label(guiGame.game.currentPlayer.getColour().toString() + "s Turn -- " + guiGame.game.currentPlayer.getColour().toString() + " is in Check!");
            AlertBox.display("Check Hint",null, guiGame.game.currentPlayer.getColour().toString() + " is in Check!");
        }
        label.setFont(new Font(fontSize));
        return new HBox(label);
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


    private GridPane generateGrid(){
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
                        guiGame.setSquareStart(null);
                        AlertBox.display("Piece problem", null, "Selected Piece is not your Colour!");
                    }
                } else if (guiGame.getSquareStart().getOccupiedBy().getColour() != guiGame.game.currentPlayer.getColour()) {
                    // 'highlighting' is turned off and selected Piece is not players colour
                    guiGame.setSquareStart(null);
                    AlertBox.display("Piece problem", null, "Selected Piece is not your Colour!");
                }
            } else {
                // selected first square is empty
                guiGame.setSquareStart(null);
                AlertBox.display("Piece Problem", null, "There is no Piece to move!");
            }
        }
        // no highlighted moves
        return generateButtonGrid();
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

        if(allowedSquares.isEmpty()){
            guiGame.setSquareStart(null);
            AlertBox.display("No Moves Possible",null,"This Piece cannot move. Try another!");
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
                generatePane();

                makeAIMove();
                System.out.println(guiGame.game.isCheckMate());

                guiGame.game.isInCheck();
                guiGame.game.isCheckMate();
            } else {
                // not an allowed Move
                generateAnswer(result); // show why it's not allowed
                if (!guiGame.allowedToChangeSelectedPiece && !guiGame.game.isInCheck()) {
                    // not allowed to change Piece after having selected it
                    guiGame.setSquareStart(guiGame.getSquareStart());
                } else {
                    guiGame.setSquareStart(null);
                }
                guiGame.setSquareFinal(null);
                generatePane();
            }
        } else if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null) {
            generatePane();
        }
    }

    private void makeAIMove(){
        if (!guiGame.game.enemyIsHuman && !guiGame.game.isCheckMate()) {
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
            generatePane();
        }
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
            key = promotionSelection();
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


    private char promotionSelection() {
        ButtonType buttonTypeOne = new ButtonType("Rook");
        ButtonType buttonTypeTwo = new ButtonType("Knight");
        ButtonType buttonTypeThree = new ButtonType("Bishop");
        ButtonType buttonTypeFour = new ButtonType("Queen");

        List<ButtonType> options = new ArrayList<>();
        Collections.addAll(options,buttonTypeOne,buttonTypeTwo,buttonTypeThree,buttonTypeFour);

        ButtonType buttonType = OptionBox.display("Promotion Options",null,"Your Pawn changes to:",options);

        if (buttonType == buttonTypeOne) {
            return 'R';
        } else if (buttonType == buttonTypeTwo) {
            return 'N';
        } else if (buttonType == buttonTypeThree) {
            return 'B';
        } else {
            return 'Q';
        }
    }


}
