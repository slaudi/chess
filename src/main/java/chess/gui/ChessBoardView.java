package chess.gui;

import chess.engine.EvaluatePieces;
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
public class ChessBoardView extends BorderPane{

    String white;
    String black;
    String highlight;
    int buttonHeight = 85;
    int buttonWidth = 85;
    int fontSize = 17;


    /**
     *
     * @param guiGame
     */
    public ChessBoardView(GuiGame guiGame) {
        this.white = "-fx-background-color: floralwhite";
        this.black = "-fx-background-color: slategray";
        this.highlight = "-fx-border-color: skyblue";
        generatePane(guiGame);
    }

    private void generatePane(GuiGame guiGame) {
        HBox heading = generatePlayersMoveLabelBox(guiGame);
        heading.setAlignment(Pos.TOP_CENTER);
        heading.setPadding(new Insets(5));
        setTop(heading);

        GridPane chessBoard = chooseButtonGridGeneration(guiGame);
        chessBoard.setAlignment(Pos.TOP_CENTER);
        chessBoard.setPadding(new Insets(0,10,10,30));
        setCenter(chessBoard);

        HBox bottom = generateBeatenPieces(guiGame);
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setPadding(new Insets(5,0,5,40));
        setBottom(bottom);
    }

    private HBox generatePlayersMoveLabelBox(GuiGame guiGame){
        Label label = new Label(guiGame.game.currentPlayer.getColour().toString() + "s Turn");
        if (guiGame.game.isCheckMate()) {
            label = new Label(" -- " + guiGame.game.currentPlayer.getColour().toString() + " has lost the Game!");
        } else if (guiGame.game.isADraw()) {
            label = new Label("The Game ended in a draw!");
        } else if (guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            label = new Label(guiGame.game.currentPlayer.getColour().toString() + "s Turn -- " + guiGame.game.currentPlayer.getColour().toString() + " is in Check!");
            AlertBox.display("Check Hint",null, guiGame.game.currentPlayer.getColour().toString() + " is in Check!");
        }
        label.setFont(new Font(fontSize));
        return new HBox(label);
    }

    private HBox generateBeatenPieces(GuiGame guiGame){
        HBox box = new HBox();
        if(!guiGame.game.beatenPieces.isEmpty()){
            for (Piece piece: guiGame.game.beatenPieces){
                box.getChildren().add(SetImages.getBeatenPieces(piece.getType(),piece.getColour()));
            }
        }
        return box;
    }

    private GridPane chooseButtonGridGeneration(GuiGame guiGame){
        if (!guiGame.game.enemyIsHuman && guiGame.game.userColour != Colour.WHITE && guiGame.freshGame) {
            guiGame.game.processMove(guiGame.game.chessBoard.getSquareAt(4, 6), guiGame.game.chessBoard.getSquareAt(4, 4), 'Q');
            guiGame.freshGame = false;
            System.out.println("tst");
            generatePane(guiGame);
        }
        return generateGrid(guiGame);
    }

    private GridPane generateGrid(GuiGame guiGame){
        if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null && guiGame.highlightPossibleMoves) {
            if(guiGame.getSquareStart().getOccupiedBy() != null){
                if(guiGame.game.currentPlayer.getColour() == Colour.WHITE && guiGame.getSquareStart().getOccupiedBy().getColour() == Colour.WHITE){
                    return generateHighlightedButtonGrid(guiGame);
                }
                // no rotation and blacks turn
                else if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK) {
                    return generateHighlightedButtonGrid(guiGame);
                } else {
                    AlertBox.display("Piece problem",null,"Selected Piece is not your Colour!");
                }
            } else {
                AlertBox.display("Piece Problem",null,"There is no Piece to move!");
            }
        }
        // no highlighted moves
        return generateButtonGrid(guiGame);
    }

    private GridPane generateButtonGrid(GuiGame guiGame) {
        GridPane grid = new GridPane();
        setButtons(grid, guiGame);
        if (guiGame.game.currentPlayer.getColour() == Colour.WHITE || !guiGame.isRotatingBoard) {
            addLetters(grid, "white");
        } else {
            addLetters(grid,"black");
        }
        return grid;
    }

    private GridPane generateHighlightedButtonGrid(GuiGame guiGame){
        GridPane grid = new GridPane();
        setHighlightedButtons(grid, guiGame);
        if (guiGame.game.currentPlayer.getColour() == Colour.WHITE || !guiGame.isRotatingBoard) {
            addLetters(grid, "white");
        } else {
            addLetters(grid,"black");
        }
        return grid;
    }

    // Adds the row and column indices to the chessboard GUI
    private void addLetters(GridPane grid, String colour) {
        String[] columns = {"A","B","C","D","E","F","G","H"};
        String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8"};
        if (colour.equals("black")) {
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

    private void setButtons(GridPane grid, GuiGame guiGame) {
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
                setButtonsOnGrid(button,guiGame,grid,x,y);
            }
        }
    }

    private void setHighlightedButtons(GridPane grid, GuiGame guiGame) {
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
                setButtonsOnGrid(button,guiGame,grid,x,y);
            }
        }
    }

    private void setButtonsOnGrid(Button button, GuiGame guiGame, GridPane grid, int x, int y) {
        button.setGraphic(SetImages.chooseImage(guiGame.game.chessBoard.getSquareAt(x, y)));
        if (!guiGame.game.isADraw() && !guiGame.game.isCheckMate()) {
            button.setOnAction(event -> {
                guiGame.setBothMovingSquares(guiGame.game.chessBoard.getSquareAt(x, y));
                setButtonAction(guiGame);
            });
        }
        if (guiGame.game.currentPlayer.getColour() == Colour.WHITE || !guiGame.isRotatingBoard) {
            grid.add(button, x, y);
        } else {
            grid.add(button, 7 - x, 7 - y);
        }
    }

    private void setButtonAction(GuiGame guiGame){
            if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() != null) {
                int result = processingMovement(guiGame);
                if (result == 0) {
                    // Move is allowed
                    if (!guiGame.game.enemyIsHuman) {
                        // generate move of AI
                        Move AIMove = EvaluatePieces.nextBestMove(guiGame.game);
                        guiGame.setSquareStart(AIMove.getStartSquare());
                        guiGame.setSquareFinal(AIMove.getFinalSquare());
                        processingMovement(guiGame);
                    }
                    generatePane(guiGame);
                } else {
                    // not an allowed Move, in check afterwards etc.
                    generateAnswer(result, guiGame); // show why it's not allowed
                    guiGame.setSquareStart(null);
                    guiGame.setSquareFinal(null);
                    chooseButtonGridGeneration(guiGame);
                }
            } else if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null) {
                generatePane(guiGame);
            }
    }

    private void generateAnswer(int result, GuiGame guiGame) {
        if (result == 1){
            AlertBox.display("Movement Error",null,"Move not allowed: Would be Check");
        }
        else if (result == 2){
            AlertBox.display("Movement Error",null,"Move not allowed: Not possible");
        }
        else if (result == 3){
            AlertBox.display("Game Information","CheckMate",guiGame.game.currentPlayer.getColour().toString() + " has lost the Game!");
        }
        else if (result == 4){
            AlertBox.display("Game Information","Draw","The Game ended in a draw!");
        }
        else if (result == 5){
            AlertBox.display("Game-Error",null,"Something unexpected happened!?");
        }
    }

    private int processingMovement(GuiGame guiGame) {
        if(!guiGame.game.currentPlayer.isLoser() || !guiGame.game.isADraw() || !guiGame.game.isCheckMate()
                && guiGame.getSquareStart() != null && guiGame.getSquareFinal() != null) {
            return isMoveAllowed(guiGame);
        }
        if(guiGame.game.isCheckMate()){
            // player is check mate
            return 3;
        }
        if(guiGame.game.isADraw()){
            return 4;
        }
        return 5;
    }

    private int isMoveAllowed(GuiGame guiGame) {
        Piece selectedPiece = guiGame.getSquareStart().getOccupiedBy();
        Square startSquare = guiGame.getSquareStart();
        Square finalSquare = guiGame.getSquareFinal();
        if (guiGame.game.isMoveAllowed(selectedPiece, finalSquare)) {
            char key = 'Q';
            if(selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).promotionPossible(finalSquare)){
                key = promotionSelection();
            }
            if (!guiGame.game.processMove(startSquare, finalSquare, key) && guiGame.game.currentPlayer.isInCheck()) {
                guiGame.setSquareStart(null);
                guiGame.setSquareFinal(null);
                return 1;
            }
        } else {
            guiGame.setSquareStart(null);
            guiGame.setSquareFinal(null);
            return 2;
        }
        // TODO: Warum so oft?
        guiGame.setSquareStart(null);
        guiGame.setSquareFinal(null);
        guiGame.game.isInCheck();
        guiGame.game.isCheckMate();

        guiGame.game.changePlayer();
        guiGame.game.isInCheck();
        guiGame.game.isCheckMate();

        guiGame.game.changePlayer();
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
