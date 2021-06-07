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
public class ChessBoardView extends BorderPane {

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

        System.out.println("\nKonstruktor");
        generatePane(guiGame);
    }

    private void generatePane(GuiGame guiGame) {
        System.out.println("generatePane: userColour " + guiGame.game.userColour);
        System.out.println("generatePane: playerColour " + guiGame.game.currentPlayer.getColour());
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
            AlertBox.display("Game Information","CheckMate",guiGame.game.currentPlayer.getColour().toString() + " has lost the Game!");
            label = new Label(guiGame.game.currentPlayer.getColour().toString() + " lost the Game!");
        } else if (guiGame.game.isADraw()) {
            AlertBox.display("Game Information","Draw","The Game ended in a draw!");
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
        System.out.println("chooseButtonGridGeneration");
        System.out.println("userColour " + guiGame.game.userColour);
        System.out.println("playerColour " + guiGame.game.currentPlayer.getColour());
        System.out.println("FreshGame " + guiGame.freshGame);
        System.out.println("Enemy Human: " + guiGame.game.enemyIsHuman);
        if (!guiGame.game.enemyIsHuman && guiGame.game.userColour != Colour.WHITE && guiGame.freshGame) {
            // enemy is AI, player chose BLACK, firstMove?
            System.out.println("AI in chooseButtonGridGeneration: calls generatePane");
            guiGame.setSquareStart(guiGame.game.chessBoard.getSquareAt(4, 6));
            guiGame.setSquareFinal(guiGame.game.chessBoard.getSquareAt(4, 4));
            int result = processingMovement(guiGame);
            if (result == 0) {
                guiGame.freshGame = false;
                System.out.println("tst");
                guiGame.setSquareStart(null);
                guiGame.setSquareFinal(null);
                generatePane(guiGame);
            }
        }
        System.out.println("calls GenerateGrid");
        return generateGrid(guiGame);
    }

    private GridPane generateGrid(GuiGame guiGame){
        System.out.println("generateGrid");
        if (!guiGame.game.enemyIsHuman && guiGame.game.userColour == Colour.WHITE){

        }
        if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null && guiGame.highlightPossibleMoves) {
            System.out.println(guiGame.getSquareStart().getLabel());
            if(guiGame.getSquareStart().getOccupiedBy() != null){
                System.out.println(guiGame.getSquareStart().getOccupiedBy());
                if(guiGame.game.currentPlayer.getColour() == Colour.WHITE && guiGame.getSquareStart().getOccupiedBy().getColour() == Colour.WHITE) {
                    System.out.println("Spielerfarbe in generateGrid für Highlights: " + guiGame.game.currentPlayer.getColour());
                    return generateHighlightedButtonGrid(guiGame);
                } else if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK) {
                    return generateHighlightedButtonGrid(guiGame);
                } else {
                    guiGame.setSquareStart(null);
                    AlertBox.display("Piece problem",null,"Selected Piece is not your Colour!");
                }
            } else {
                guiGame.setSquareStart(null);
                AlertBox.display("Piece Problem",null,"There is no Piece to move!");
            }
        }
        // no highlighted moves
        System.out.println("Letzter Return: calls generateButtonGrid");
        return generateButtonGrid(guiGame);
    }

    private GridPane generateButtonGrid(GuiGame guiGame) {
        System.out.println("generateButtonGrid");
        // test
        if (guiGame.getSquareStart() != null) {
            System.out.println(guiGame.getSquareStart().getLabel());
        }
        if (guiGame.getSquareFinal() != null){
            System.out.println(guiGame.getSquareFinal().getLabel());
        }
        GridPane grid = new GridPane();
        setButtons(grid, guiGame);
        if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.isRotatingBoard || guiGame.game.userColour == Colour.BLACK && !guiGame.game.enemyIsHuman){
            System.out.println("Black indices");
            addIndices(grid,"black");
        }
        if (guiGame.game.currentPlayer.getColour() == Colour.WHITE || !guiGame.isRotatingBoard || guiGame.game.userColour == Colour.WHITE && !guiGame.game.enemyIsHuman) {
            System.out.println("White indices");
            addIndices(grid, "white");
        }
        return grid;
    }

    private GridPane generateHighlightedButtonGrid(GuiGame guiGame){
        System.out.println("generateHighlightedButtonGrid");
        GridPane grid = new GridPane();
        setHighlightedButtons(grid, guiGame);
        if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.isRotatingBoard || guiGame.game.userColour == Colour.BLACK && !guiGame.game.enemyIsHuman){
            System.out.println("Highlight: black indices");
            addIndices(grid,"black");
        } else if (guiGame.game.currentPlayer.getColour() == Colour.WHITE || !guiGame.isRotatingBoard || guiGame.game.userColour == Colour.WHITE && !guiGame.game.enemyIsHuman) {
            System.out.println("Highlight: White indices");
            addIndices(grid, "white");
        }
        return grid;
    }

    // Adds the row and column indices to the chessboard GUI
    private void addIndices(GridPane grid, String colour) {
        System.out.println("addIndices");
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
        System.out.println("setButtons");
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
        System.out.println("Done: setButtonsOnGrid");
    }

    private void setHighlightedButtons(GridPane grid, GuiGame guiGame) {
        System.out.println("setHighlightedButtons");
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
        System.out.println("Done: setButtonsOnGrid");
    }

    private void setButtonsOnGrid(Button button, GuiGame guiGame, GridPane grid, int x, int y) {
        button.setGraphic(SetImages.chooseImage(guiGame.game.chessBoard.getSquareAt(x, y)));
        if (!guiGame.game.isADraw() && !guiGame.game.isCheckMate()) {
            button.setOnAction(event -> {
                guiGame.setBothMovingSquares(guiGame.game.chessBoard.getSquareAt(x, y));
                try {
                    setButtonAction(guiGame);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.isRotatingBoard || guiGame.game.userColour == Colour.BLACK && !guiGame.game.enemyIsHuman) {
            grid.add(button, 7 - x, 7 - y);
        }
        if (guiGame.game.currentPlayer.getColour() == Colour.WHITE || !guiGame.isRotatingBoard || guiGame.game.userColour == Colour.WHITE && !guiGame.game.enemyIsHuman) {
            grid.add(button, x, y);
        }
    }

    private void setButtonAction(GuiGame guiGame) throws InterruptedException {
        System.out.println("setButtonAction");
        if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() != null) {
            System.out.println(guiGame.getSquareStart().getLabel() + ", " + guiGame.getSquareFinal().getLabel());
            int result = processingMovement(guiGame);
            System.out.println(result);
            if (result == 0) {
                // Move is allowed
                System.out.println("Move is allowed");
                guiGame.setSquareStart(null);
                guiGame.setSquareFinal(null);
                generatePane(guiGame);
                if (!guiGame.game.enemyIsHuman) {
                    // generate move of AI
                    System.out.println("Generate Move of AI");
                    Move AIMove = EvaluatePieces.nextBestMove(guiGame.game);
                    System.out.println(AIMove.getStartSquare().getLabel() +", "+AIMove.getFinalSquare().getLabel());
                    guiGame.setSquareStart(AIMove.getStartSquare());
                    guiGame.setSquareFinal(AIMove.getFinalSquare());
                    int AI_result = processingMovement(guiGame);
                    guiGame.setSquareStart(null);
                    guiGame.setSquareFinal(null);
                    while (AI_result != 0) {
                        AIMove = EvaluatePieces.nextBestMove(guiGame.game);
                        System.out.println(AIMove.getStartSquare().getLabel() +", "+AIMove.getFinalSquare().getLabel());
                        guiGame.setSquareStart(AIMove.getStartSquare());
                        guiGame.setSquareFinal(AIMove.getFinalSquare());
                        AI_result = processingMovement(guiGame);
                    }
                    guiGame.setSquareStart(null);
                    guiGame.setSquareFinal(null);
                    generatePane(guiGame);
                }
                guiGame.game.isInCheck();
                guiGame.game.isCheckMate();
            } else {
                // not an allowed Move
                System.out.println("Move not allowed");
                generateAnswer(result); // show why it's not allowed
                if (!guiGame.allowedToChangeSelectedPiece) {
                    // not allowed to change Piece after having selected it
                    guiGame.setSquareStart(guiGame.getSquareStart());
                } else {
                    guiGame.setSquareStart(null);
                }
                guiGame.setSquareFinal(null);
                generatePane(guiGame);
            }
        } else if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null) {
            System.out.println("Letzte if-condition in setButtonAction");
            generatePane(guiGame);
        }
    }

    private void generateAnswer(int result) {
        if (result == 1){
            AlertBox.display("Movement Error",null,"Move not allowed: Would be Check");
        }
        else if (result == 2){
            AlertBox.display("Movement Error",null,"Move not allowed: Not possible");
        }
        else if (result == 5){
            AlertBox.display("Game-Error",null,"Something unexpected happened!?");
        }
    }

    private int processingMovement(GuiGame guiGame) {
        System.out.println("processingMovement");
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
        System.out.println("isMoveAllowed");
        Piece selectedPiece = guiGame.getSquareStart().getOccupiedBy();
        Square startSquare = guiGame.getSquareStart();
        Square finalSquare = guiGame.getSquareFinal();
        if (guiGame.game.isMoveAllowed(selectedPiece, finalSquare)) {
            char key = 'Q';
            if(selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).promotionPossible(finalSquare)){
                key = promotionSelection();
            }
            if (!guiGame.game.processMove(startSquare, finalSquare, key) && guiGame.game.currentPlayer.isInCheck()) {
                return 1;
            }
        } else {
            return 2;
        }
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
