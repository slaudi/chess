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

    public ChessBoardView(Game game) {
        this.white = "-fx-background-color: floralwhite";
        this.black = "-fx-background-color: slategray";
        this.highlight = "-fx-border-color: skyblue";

        generatePane(game);
    }

    void generatePane(Game game) {
        HBox heading = generatePlayersMoveLabelBox(game);
        heading.setAlignment(Pos.TOP_CENTER);
        heading.setPadding(new Insets(10));
        setTop(heading);

        GridPane chessBoard = chooseButtonGridGeneration(game);
        chessBoard.setAlignment(Pos.TOP_CENTER);
        setCenter(chessBoard);

        HBox bottom = generateBeatenPieces(game);
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setPadding(new Insets(20));
        setBottom(bottom);
    }

    HBox generateBeatenPieces(Game game){
        HBox box = new HBox();
        if(!game.beatenPieces.isEmpty()){
            for (Piece piece: game.beatenPieces){
                box.getChildren().add(SetImages.getBeatenPieces(piece.getType(),piece.getColour()));
            }
        }
        return box;
    }

    HBox generatePlayersMoveLabelBox(Game game){
        Label label = new Label(game.currentPlayer.getColour().toString() + "s Turn");
        if (game.isCheckMate()) {
            label = new Label(" -- " + game.currentPlayer.getColour().toString() + " has lost the Game!");
            AlertBox.display("Check Mate",null,game.currentPlayer.getColour().toString() + "is Checkmate!");
        } else if (game.isADraw()) {
            label = new Label("The Game ended in a draw!");
            AlertBox.display("Draw",null,"The Game ended in a draw!");
        } else if (game.hintInCheck && game.currentPlayer.isInCheck()){
            label = new Label(game.currentPlayer.getColour().toString() + "s Turn -- " + game.currentPlayer.getColour().toString() + " is in Check!");
            AlertBox.display("Check Hint",null,game.currentPlayer.getColour().toString() + " is in Check!");
        }
        label.setFont(new Font(fontSize));
        return new HBox(label);
    }


    private int processingMovement(Game currentGame) {
        if((!currentGame.currentPlayer.isLoser() || !currentGame.isADraw()) && currentGame.getSquareStart() != null
                && currentGame.getSquareFinal() != null) {
            return isMoveAllowed(currentGame);
        }
        if(currentGame.isCheckMate()){
            return 3;
        }
        if(currentGame.isADraw()){
            return 4;
        }
        return 5;
    }

    private int isMoveAllowed(Game currentGame) {
        Piece selectedPiece = currentGame.getSquareStart().getOccupiedBy();
        Square startSquare = currentGame.getSquareStart();
        Square finalSquare = currentGame.getSquareFinal();
        if (currentGame.isMoveAllowed(selectedPiece, finalSquare)) {
            char key = 'Q';
            if(selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).promotionPossible(finalSquare)){
                key = promotionSelection();
            }
            if (!currentGame.processMove(startSquare, finalSquare, key) && currentGame.currentPlayer.isInCheck()) {
                currentGame.setSquareStart(null);
                currentGame.setSquareFinal(null);
                return 1;
            }
        } else {
            currentGame.setSquareStart(null);
            currentGame.setSquareFinal(null);
            return 2;
        }
        currentGame.setSquareStart(null);
        currentGame.setSquareFinal(null);
        currentGame.isInCheck();
        currentGame.isCheckMate();

        currentGame.changePlayer();
        currentGame.isInCheck();
        currentGame.isCheckMate();

        currentGame.changePlayer();
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

    private GridPane generateButtonGrid(Game game){
        GridPane grid = new GridPane();
        setButtons(grid,game);
        for (int y = 0; y <8; y++){
            String[] columns = {"1","2","3","4","5","6","7","8"};
            Label label = new Label(columns[7-y]);
            label.setFont(new Font(fontSize));
            grid.add(label, 8, y);
        }
        addLetters(grid,"white");
        return grid;
    }

    private GridPane generateHighlightedButtonGrid(Game game){
        GridPane grid = new GridPane();
        setHighlightedButtons(grid,game);
        for (int y = 0; y < 8; y++){
            String[] columns = {"1","2","3","4","5","6","7","8"};
            Label label = new Label(columns[7-y]);
            label.setFont(new Font(fontSize));
            grid.add(label, 8, y);
        }
        addLetters(grid,"white");
        return grid;
    }

    private GridPane generateButtonGridBlackDown(Game game){
        GridPane grid = new GridPane();
        setButtons(grid,game);
        for (int y = 0; y < 8; y++) {
            String[] columns = {"1","2","3","4","5","6","7","8"};
            Label label = new Label(columns[y]);
            label.setFont(new Font(fontSize));
            grid.add(label, 8, y);
        }
        addLetters(grid,"black");
        return grid;
    }

    private GridPane generateHighlightedButtonGridBlackDown(Game game){
        GridPane grid = new GridPane();
        setHighlightedButtons(grid,game);
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

    private void setButtons(GridPane grid, Game game) {
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
                button.setGraphic(SetImages.chooseImage(game.chessBoard.getSquareAt(x, y)));
                int finalX = x;
                int finalY = y;
                if (!game.isADraw() || !game.isCheckMate()) {
                    button.setOnAction(event -> {
                        game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                        setButtonAction(game);
                    });
                }
                if (game.currentPlayer.getColour() == Colour.WHITE) {
                    grid.add(button, x, y);
                } else {
                    grid.add(button, 7 - x, 7 - y);
                }
            }
        }
    }

    private void setHighlightedButtons(GridPane grid, Game game) {
        List<Square> allowedSquares = game.computePossibleSquares();

        if(allowedSquares.isEmpty()){
            game.setSquareStart(null);
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
                String border = "-fx-border-width: 4px";
                if (allowedSquares.contains(game.chessBoard.getSquareAt(x, y)) && (y + x) % 2 == 0) {
                    btn.setStyle(highlight + ";" + border + ";" + white);
                } else if (allowedSquares.contains(game.chessBoard.getSquareAt(x, y))) {
                    btn.setStyle(highlight + ";" + border + ";" + black);
                }
                btn.setGraphic(SetImages.chooseImage(game.chessBoard.getSquareAt(x, y)));
                int finalX = x;
                int finalY = y;
                if (!game.isADraw() && !game.isCheckMate()) {
                    btn.setOnAction(event -> {
                        game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                        setButtonAction(game);
                    });
                }
                if (game.currentPlayer.getColour() == Colour.WHITE) {
                    grid.add(btn, x, y);
                } else {
                    grid.add(btn, 7 - x, 7 - y);
                }
            }
        }
    }

    private void setButtonAction(Game game) {
        if (game.getSquareStart() != null && game.getSquareFinal() != null) {
            int result = processingMovement(game);
            if (result == 0) {
                if (!game.enemyIsHuman) {
                    Move AIMove = EvaluatePieces.nextBestMove(game);
                    game.setSquareStart(AIMove.getStartSquare());
                    game.setSquareFinal(AIMove.getFinalSquare());
                    processingMovement(game);
                }
                generatePane(game);
            } else {
                // not an allowed Move
                generateAnswer(result);
                chooseButtonGridGeneration(game);
            }
        } else if (game.getSquareStart() != null && game.getSquareFinal() == null) {
            generatePane(game);
        }
    }

    private void generateAnswer(int result) {
        if (result == 1){
            AlertBox.display("Movement Error",null,"Move not allowed: Would be Check");
        }
        else if (result == 2){
            AlertBox.display("Movement Error",null,"Move not allowed: Not possible");
        }
        else if (result == 3){
            AlertBox.display("Game Information",null,"CheckMate");
        }
        else if (result == 4){
            AlertBox.display("Game Information",null,"Draw");
        }
        else if (result == 5){
            AlertBox.display("Game-Error",null,"Something unexpected happened!?");
        }
    }


    private GridPane chooseButtonGridGeneration(Game game){
        if(game.enemyIsHuman) {
            if (game.isRotatingBoard) {
                if(game.currentPlayer.getColour() == Colour.WHITE) {
                    return whitePlayersGrid(game);
                } else {
                    return blackPlayersGrid(game);
                }
            } else {
                // Board doesn't rotate
                return whitePlayersGrid(game);
            }
        } else {
            // enemy is AI
            if(game.userColour == Colour.WHITE){
                return whitePlayersGrid(game);
            } else {
                return blackPlayersGrid(game);
            }
        }
    }

    private GridPane whitePlayersGrid(Game game) {
        if (game.getSquareStart() != null && game.getSquareFinal() == null && game.highlightPossibleMoves) {
            if(game.getSquareStart().getOccupiedBy() != null){
                if(game.currentPlayer.getColour() == Colour.WHITE && game.getSquareStart().getOccupiedBy().getColour() == Colour.WHITE){
                    return generateHighlightedButtonGrid(game);
                }
                // no rotation and blacks turn
                else if (game.currentPlayer.getColour() == Colour.BLACK && game.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK) {
                    return generateHighlightedButtonGrid(game);
                } else {
                    AlertBox.display("Piece problem",null,"Selected Piece is not your Colour!");
                }
            } else {
                AlertBox.display("Piece Problem",null,"There is no Piece to move!");
            }
        }
        // no highlighted moves
        return generateButtonGrid(game);
    }

    private GridPane blackPlayersGrid(Game game){
        if (game.getSquareStart() != null && game.getSquareFinal() == null && game.highlightPossibleMoves) {
            if (game.getSquareStart().getOccupiedBy() != null) {
                if (game.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK) {
                    return generateHighlightedButtonGridBlackDown(game);
                } else {
                    AlertBox.display("Piece problem",null,"Selected Piece is not your Colour!");
                }
            } else {
                AlertBox.display("Piece Problem",null,"There is no Piece to move!");
            }
        }
        return generateButtonGridBlackDown(game);
    }

}
