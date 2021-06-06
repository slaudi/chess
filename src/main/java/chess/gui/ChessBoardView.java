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



public class ChessBoardView extends BorderPane{

    String white;
    String black;
    String highlight;
    int buttonHeight = 85;
    int buttonWidth = 85;
    int fontSize = 17;

    public ChessBoardView(GuiGame guiGame) {
        this.white = "-fx-background-color: floralwhite";
        this.black = "-fx-background-color: slategray";
        this.highlight = "-fx-border-color: skyblue";

        generatePane(guiGame);
    }

    void generatePane(GuiGame guiGame) {
        HBox heading = generatePlayersMoveLabelBox(guiGame);
        heading.setAlignment(Pos.TOP_CENTER);
        heading.setPadding(new Insets(10));
        setTop(heading);

        GridPane chessBoard = chooseButtonGridGeneration(guiGame);
        chessBoard.setAlignment(Pos.CENTER);
        setCenter(chessBoard);

        HBox bottom = generateBeatenPieces(guiGame);
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setPadding(new Insets(20));
        setBottom(bottom);
    }

    HBox generateBeatenPieces(GuiGame guiGame){
        HBox box = new HBox();
        if(!guiGame.game.beatenPieces.isEmpty()){
            for (Piece piece: guiGame.game.beatenPieces){
                box.getChildren().add(SetImages.getBeatenPieces(piece.getType(),piece.getColour()));
            }
        }
        return box;
    }

    HBox generatePlayersMoveLabelBox(GuiGame guiGame){
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


    private int processingMovement(GuiGame guiGame) {
        if((!guiGame.game.currentPlayer.isLoser() || !guiGame.game.isADraw()) && guiGame.getSquareStart() != null
                && guiGame.getSquareFinal() != null) {
            return isMoveAllowed(guiGame);
        }
        if(guiGame.game.isCheckMate()){
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

    private GridPane generateButtonGrid(GuiGame guiGame){
        GridPane grid = new GridPane();
        setButtons(grid, guiGame);
        for (int y = 0; y <8; y++){
            String[] columns = {"1","2","3","4","5","6","7","8"};
            Label label = new Label(columns[7-y]);
            label.setFont(new Font(fontSize));
            grid.add(label, 8, y);
        }
        addLetters(grid,"white");
        return grid;
    }

    private GridPane generateHighlightedButtonGrid(GuiGame guiGame){
        GridPane grid = new GridPane();
        setHighlightedButtons(grid, guiGame);
        for (int y = 0; y < 8; y++){
            String[] columns = {"1","2","3","4","5","6","7","8"};
            Label label = new Label(columns[7-y]);
            label.setFont(new Font(fontSize));
            grid.add(label, 8, y);
        }
        addLetters(grid,"white");
        return grid;
    }

    private GridPane generateButtonGridBlackDown(GuiGame guiGame){
        GridPane grid = new GridPane();
        setButtons(grid, guiGame);
        for (int y = 0; y < 8; y++) {
            String[] columns = {"1","2","3","4","5","6","7","8"};
            Label label = new Label(columns[y]);
            label.setFont(new Font(fontSize));
            grid.add(label, 8, y);
        }
        addLetters(grid,"black");
        return grid;
    }

    private GridPane generateHighlightedButtonGridBlackDown(GuiGame guiGame){
        GridPane grid = new GridPane();
        setHighlightedButtons(grid, guiGame);
        for (int y = 0; y < 8; y++){
            String[] columns = {"1","2","3","4","5","6","7","8"};
            Label label = new Label(columns[y]);
            label.setFont(new Font(fontSize));
            grid.add(label, 8, y);
        }
        addLetters(grid,"black");
        return grid;
    }

    private void addLetters(GridPane grid, String colour) {
        String[] letters = {"A","B","C","D","E","F","G","H"};
        if (colour.equals("black")) {
            int c = 0;
            for (int i = letters.length - 1; i >= 0; i--) {
                Label label = new Label(letters[i]);
                label.setFont(new Font(fontSize));
                grid.add(label,c,8);
                c++;
            }
        } else {
            for (int i = 0; i <= letters.length - 1; i++) {
                Label label = new Label(letters[i]);
                label.setFont(new Font(fontSize));
                grid.add(label,i,8);
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
                button.setGraphic(SetImages.chooseImage(guiGame.game.chessBoard.getSquareAt(x, y)));
                int finalX = x;
                int finalY = y;
                if (!guiGame.game.isADraw() && !guiGame.game.isCheckMate()) {
                    button.setOnAction(event -> {
                        guiGame.setBothMovingSquares(guiGame.game.chessBoard.getSquareAt(finalX, finalY));
                        button.setStyle("-fx-background-color: #5F537BFF");
                        setButtonAction(guiGame);
                    });
                }
                if (guiGame.game.currentPlayer.getColour() == Colour.WHITE || !guiGame.isRotatingBoard) {
                    grid.add(button, x, y);
                } else {
                    grid.add(button, 7 - x, 7 - y);
                }
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
                Button btn = new Button();
                btn.setMinHeight(buttonHeight);
                btn.setMinWidth(buttonWidth);
                if ((y + x) % 2 == 0) {
                    btn.setStyle(white);
                } else {
                    btn.setStyle(black);
                }
                String border = "-fx-border-width: 3px";
                if (allowedSquares.contains(guiGame.game.chessBoard.getSquareAt(x, y)) && (y + x) % 2 == 0) {
                    btn.setStyle(highlight + ";" + border + ";" + white);
                } else if (allowedSquares.contains(guiGame.game.chessBoard.getSquareAt(x, y))) {
                    btn.setStyle(highlight + ";" + border + ";" + black);
                }
                btn.setGraphic(SetImages.chooseImage(guiGame.game.chessBoard.getSquareAt(x, y)));
                int finalX = x;
                int finalY = y;
                if (!guiGame.game.isADraw() && !guiGame.game.isCheckMate()) {
                    btn.setOnAction(event -> {
                        guiGame.setBothMovingSquares(guiGame.game.chessBoard.getSquareAt(finalX, finalY));
                        setButtonAction(guiGame);
                    });
                }
                if (guiGame.game.currentPlayer.getColour() == Colour.WHITE || !guiGame.isRotatingBoard) {
                    grid.add(btn, x, y);
                } else {
                    grid.add(btn, 7 - x, 7 - y);
                }
            }
        }
    }

    private void setButtonAction(GuiGame guiGame) {
        if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() != null) {
            int result = processingMovement(guiGame);
            if (result == 0) {
                if (!guiGame.game.enemyIsHuman) {
                    Move AIMove = EvaluatePieces.nextBestMove(guiGame.game);
                    guiGame.setSquareStart(AIMove.getStartSquare());
                    guiGame.setSquareFinal(AIMove.getFinalSquare());
                    processingMovement(guiGame);
                }
                generatePane(guiGame);
            } else {
                // not an allowed Move
                generateAnswer(result, guiGame);
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


    private GridPane chooseButtonGridGeneration(GuiGame guiGame){
        if(guiGame.game.enemyIsHuman) {
            if (guiGame.isRotatingBoard) {
                if(guiGame.game.currentPlayer.getColour() == Colour.WHITE) {
                    return whitePlayersGrid(guiGame);
                } else {
                    return blackPlayersGrid(guiGame);
                }
            } else {
                // Board doesn't rotate
                return whitePlayersGrid(guiGame);
            }
        } else {
            // enemy is AI
            if(guiGame.game.userColour == Colour.WHITE){
                return whitePlayersGrid(guiGame);
            } else {
                if(guiGame.freshGame){
                    guiGame.game.processMove(guiGame.game.chessBoard.getSquareAt(4, 6), guiGame.game.chessBoard.getSquareAt(4, 4), 'Q');

                    guiGame.freshGame = false;
                    System.out.println("tst");
                    generatePane(guiGame);
                }
                return blackPlayersGrid(guiGame);
            }
        }
    }

    private GridPane whitePlayersGrid(GuiGame guiGame) {
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

    private GridPane blackPlayersGrid(GuiGame guiGame){
        if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null && guiGame.highlightPossibleMoves) {
            if (guiGame.getSquareStart().getOccupiedBy() != null) {
                if (guiGame.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK) {
                    return generateHighlightedButtonGridBlackDown(guiGame);
                } else {
                    AlertBox.display("Piece problem",null,"Selected Piece is not your Colour!");
                }
            } else {
                AlertBox.display("Piece Problem",null,"There is no Piece to move!");
            }
        }
        return generateButtonGridBlackDown(guiGame);
    }

}
