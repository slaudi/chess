package chess.gui;

import chess.engine.EvaluatePieces;
import chess.game.*;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Optional;



public class ChessBoardView extends BorderPane{

    public Gui gui;
    String white;
    String black;
    String highlight;
    int buttonHeight = 90;
    int buttonWidth = 90;
    int fontSize = 17;

    public ChessBoardView(Game game) {
        this.white = "-fx-background-color: floralwhite";
        this.black = "-fx-background-color: slategray";
        this.highlight = "-fx-border-color: skyblue";

        GridPane center = chooseButtonGridGeneration(game);//generateButtonGrid(game);
        setCenter(center);
    }

    HBox generateBeatenPieces(Game game){
        HBox box = new HBox();
        box.getChildren().add(new Label("Beaten Pieces:"));
        if(!game.beatenPieces.isEmpty()){
            for (Piece piece: game.beatenPieces){
                box.getChildren().add(SetImages.getBeatenPieces(piece.getType(),piece.getColour()));
            }
        }
        return box;
    }

    HBox generatePlayersMoveLabelBox(Game game){
        Label label = new Label("CHESS --- " + game.currentPlayer.getColour().toString() + "'s Turn");
        label.setFont(new Font(fontSize));
        String currentPlayerIsInCheck = "       ";
        if (game.hintInCheck && game.currentPlayer.isInCheck()){
            currentPlayerIsInCheck = currentPlayerIsInCheck + game.currentPlayer.getColour() + " is in Check!!!";
        }
        Label checkLabel = new Label(currentPlayerIsInCheck);
        return new HBox(label, checkLabel);
    }


    private int processingMovement(Game currentGame) {
        if((!currentGame.currentPlayer.isLoser() || !currentGame.isADraw()) && currentGame.getSquareStart() != null
                && currentGame.getSquareFinal() != null) {
            return isMoveAllowed(currentGame);
        }
        if(currentGame.currentPlayer.isLoser()){
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Promotion-Option");
        alert.setHeaderText(null);
        alert.setContentText("Your Pawn should change to:");

        ButtonType buttonTypeOne = new ButtonType("Rook");
        ButtonType buttonTypeTwo = new ButtonType("Knight");
        ButtonType buttonTypeThree = new ButtonType("Bishop");
        ButtonType buttonTypeFour = new ButtonType("Queen");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == buttonTypeOne) {
                return 'R';
            } else if (result.get() == buttonTypeTwo) {
                return 'N';
            } else if (result.get() == buttonTypeThree) {
                return 'B';
            } else {
                return 'Q';
            }
        }
        return ' ';
    }

    private GridPane generateButtonGrid(Game game){
        GridPane grid = new GridPane();
        // TODO: startFormation: squares mit verschiedenen Farben
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                /*if ((y+x) %2 == 0) {
                    grid.setStyle(white);
                } else {
                    grid.setStyle(black);
                }*/
                Button btn = setButton(game,x,y);
                grid.add(btn, x, y);
            }
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

        List<Square> allowedSquares = game.computePossibleSquares();
        if(allowedSquares.isEmpty()){
            game.setSquareStart(null);
            Alert alertu = new Alert(Alert.AlertType.INFORMATION);
            alertu.setTitle("No Moves possible");
            alertu.setHeaderText(null);
            alertu.setContentText("This Piece cannot move. Try another!");

            alertu.showAndWait();
        }
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                Button btn = setHighlightedButton(game,x,y,allowedSquares);
                grid.add(btn, x, y);
            }
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
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                Button btn = setButton(game, x, y);
                grid.add(btn, 7 - x, 7 - y);
            }
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
        List<Square> allowedSquares = game.computePossibleSquares();

        if(allowedSquares.isEmpty()){
            game.setSquareStart(null);
            Alert alertu = new Alert(Alert.AlertType.INFORMATION);
            alertu.setTitle("No Moves possible");
            alertu.setHeaderText(null);
            alertu.setContentText("This Piece cannot move. Try another!");

            alertu.showAndWait();
        }
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                Button btn = setHighlightedButton(game,x,y,allowedSquares);
                grid.add(btn, 7 - x, 7 - y);
            }
            String[] columns = {"1","2","3","4","5","6","7","8"};
            Label label = new Label(columns[7-y]);
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

    private Button setButton(Game game, int x, int y) {
        Button btn = new Button();
        btn.setMinHeight(buttonHeight);
        btn.setMinWidth(buttonWidth);
        if ((y+x) %2 == 0) {
            btn.setStyle(white);
        } else {
            btn.setStyle(black);
        }
        btn.setGraphic(SetImages.chooseImage(game.chessBoard.getSquareAt(x, y)));
        btn.setOnAction(event -> {
            game.setBothMovingSquares(game.chessBoard.getSquareAt(x, y));
            setButtonAction(game);
        });
        return btn;
    }

    private Button setHighlightedButton(Game game, int x, int y, List<Square> allowedSquares) {
        Button btn = new Button();
        btn.setMinHeight(buttonHeight);
        btn.setMinWidth(buttonWidth);
        if ((y+x) %2 == 0) {
            btn.setStyle(white);
        } else {
            btn.setStyle(black);
        }
        String border = "-fx-border-width: 4px";
        if(allowedSquares.contains(game.chessBoard.getSquareAt(x, y)) && (y+x) %2 == 0){
            btn.setStyle(highlight + ";" + border + ";" + white);
        } else if(allowedSquares.contains(game.chessBoard.getSquareAt(x, y))) {
            btn.setStyle(highlight + ";" + border + ";" + black);
        }
        btn.setGraphic(SetImages.chooseImage(game.chessBoard.getSquareAt(x, y)));
        btn.setOnAction(event -> {
            game.setBothMovingSquares(game.chessBoard.getSquareAt(x, y));
            setButtonAction(game);
        });
        return btn;
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
                setCenter(chooseButtonGridGeneration(game));
                setBottom(generateBeatenPieces(game));
                setTop(generatePlayersMoveLabelBox(game));
            } else {
                generateAnswer(result);
            }
        }
        if (game.getSquareStart() != null && game.getSquareFinal() == null) {
            setCenter(chooseButtonGridGeneration(game));
        }
    }

    private void generateAnswer(int result) {
        if (result == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Movement Error");
            alert.setHeaderText(null);
            alert.setContentText("Move not allowed: Would be Check");
            alert.showAndWait();
        }
        else if (result == 2){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Movement Error");
            alert.setHeaderText(null);
            alert.setContentText("Move not allowed: Not possible");
            alert.showAndWait();
        }
        else if (result == 3){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Information");
            alert.setHeaderText(null);
            alert.setContentText("CheckMate");
            alert.showAndWait();
        }
        else if (result == 4){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Information");
            alert.setHeaderText(null);
            alert.setContentText("Draw");
            alert.showAndWait();
        }
        else if (result == 5){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game-Error");
            alert.setHeaderText(null);
            alert.setContentText("Something unexpected happened!?");
            alert.showAndWait();
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
                if(game.getSquareStart().getOccupiedBy().getColour() == Colour.WHITE){
                    return generateHighlightedButtonGrid(game);
                } else {
                    alertPieceWrongColour(game);
                }
            } else {
                alertNoPiece(game);
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
                    alertPieceWrongColour(game);
                }
            } else {
                alertNoPiece(game);
            }
        }
        return generateButtonGridBlackDown(game);
    }

    private void alertPieceWrongColour(Game game) {
        game.setSquareStart(null);
        Alert alertia = new Alert(Alert.AlertType.INFORMATION);
        alertia.setTitle("Piece Problem");
        alertia.setHeaderText(null);
        alertia.setContentText("Selected Piece is not your Colour!");

        alertia.showAndWait();
    }

    private void alertNoPiece(Game game) {
        game.setSquareStart(null);
        Alert alertis = new Alert(Alert.AlertType.INFORMATION);
        alertis.setTitle("Piece Problem");
        alertis.setHeaderText(null);
        alertis.setContentText("There is no Piece to Move!");

        alertis.showAndWait();
    }

}
