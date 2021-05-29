package chess.gui;

import chess.engine.EvaluatePieces;
import chess.game.*;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Optional;


public class ChessBoardView extends BorderPane{

    public ChessBoardView(Game game) {
        HBox heading = generatePlayersMoveLabelBox(game);
        HBox bottom = generateBeatenPieces(game);
        VBox right = generateRightMarginColumn(game);
        GridPane center = generateButtonGrid(game);

        heading.setAlignment(Pos.CENTER);
        bottom.setAlignment(Pos.CENTER);
        right.setAlignment(Pos.CENTER);

        setCenter(center);
        setRight(right);
        setTop(heading);
        setBottom(bottom);
    }


    public VBox generateRightMarginColumn(Game game){
        Button btnOptions = new Button("Options");
        btnOptions.setOnAction(event -> {
            String isBoardRotationStatus;
            String highlightPossibleMoveStatus;
            String allowedChangeSelectedPieceStatus;
            String hintInCheckStatus;
            String on = "ON";
            String off = "OFF";
            if(game.isRotatingBoard){
                isBoardRotationStatus = on;
            } else {
                isBoardRotationStatus = off;
            }
            if(game.highlightPossibleMoves){
                highlightPossibleMoveStatus = on;
            } else {
                highlightPossibleMoveStatus = off;
            }
            if(game.allowedToChangeSelectedPiece){
                allowedChangeSelectedPieceStatus = on;
            } else {
                allowedChangeSelectedPieceStatus = off;
            }
            if(game.hintInCheck){
                hintInCheckStatus = on;
            } else {
                hintInCheckStatus = off;
            }

            Alert alerti = new Alert(Alert.AlertType.CONFIRMATION);
            alerti.setTitle("Game-Settings");
            alerti.setHeaderText(" ChessBoard-Rotation is " + isBoardRotationStatus
                    + "\n Highlighting of possible Moves is " + highlightPossibleMoveStatus
                    + "\n Allowed to change selected Piece is " + allowedChangeSelectedPieceStatus
                    + "\n Check-Notifications are " + hintInCheckStatus);
            alerti.setContentText("Choose Option you want to Change:");

            ButtonType buttonTypeOne = new ButtonType("Rotation");
            ButtonType buttonTypeTwo = new ButtonType("MoveHighlighting");
            ButtonType buttonTypeThree = new ButtonType("Change Selected Piece");
            ButtonType buttonTypeFour = new ButtonType("CheckHints");
            ButtonType buttonTypeFive = new ButtonType("Start New Game");
            ButtonType buttonTypeSix = new ButtonType("Cancel");

            alerti.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour, buttonTypeFive, buttonTypeSix);

            Optional<ButtonType> result = alerti.showAndWait();
            if (result.get() == buttonTypeOne){
                game.isRotatingBoard = !game.isRotatingBoard;
            } else if (result.get() == buttonTypeTwo) {
                game.highlightPossibleMoves = !game.highlightPossibleMoves;
            } else if (result.get() == buttonTypeThree) {
                game.allowedToChangeSelectedPiece = !game.allowedToChangeSelectedPiece;
            } else if (result.get() == buttonTypeFour) {
                game.hintInCheck = !game.hintInCheck;
            } else if (result.get() == buttonTypeFive) {
                Alert alerto = new Alert(Alert.AlertType.CONFIRMATION);
                alerto.setTitle("New Game?");
                alerto.setHeaderText(null);
                alerto.setContentText("Do you really want to start a new Game?");

                Optional<ButtonType> resulto = alerto.showAndWait();
                if (resulto.get() == ButtonType.OK){
                    Game newGame = new Game();
                }  //user chose CANCEL or closed the dialog

            }  //user chose CANCEL or closed the dialog


        });
        Button btnMoveHistory = new Button("Move-History");
        btnMoveHistory.setOnAction(event -> {
            List<Move> history = game.moveHistory;
            StringBuilder historyAsString = new StringBuilder();
            if(!history.isEmpty()){
                for (Move move : history) {
                    historyAsString.append(move.getStartSquare().getLabel().toString()).append("-").append(move.getFinalSquare().getLabel().toString()).append("\n");
                }
            }
            else {
                historyAsString = new StringBuilder("new Game");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Move-History");
            alert.setHeaderText(null);
            alert.setContentText(historyAsString.toString());

            alert.showAndWait();
        });
        return new VBox(btnOptions, btnMoveHistory);
    }

    public HBox generatePlayersMoveLabelBox(Game game){
        if(game.freshGame){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Welcome to a new Game of Chess");
            alert.setHeaderText(null);
            alert.setContentText("Choose your Enemy:");

            ButtonType buttonTypeOne = new ButtonType("Human");
            ButtonType buttonTypeTwo = new ButtonType("Computer");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            game.enemyIsHuman = result.get() == buttonTypeOne;
            if(!game.enemyIsHuman) {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Welcome to a new Game of Chess");
                alert2.setHeaderText(null);
                alert2.setContentText("Choose your Colour:");

                ButtonType buttonTypeThree = new ButtonType("White");
                ButtonType buttonTypeFour = new ButtonType("Black");

                alert2.getButtonTypes().setAll(buttonTypeThree, buttonTypeFour);

                Optional<ButtonType> result2 = alert2.showAndWait();
                if (result2.get() == buttonTypeThree) {
                    game.userColour = Colour.WHITE;
                } else {
                    game.userColour = Colour.BLACK;
                }
            }
            game.freshGame = false;
        }
        Label label = new Label("CHESS --- " + game.currentPlayer.getColour().toString() + "'s Turn");
        String currentPlayerIsInCheck = "       ";
        if (game.hintInCheck && game.currentPlayer.isInCheck()){
                currentPlayerIsInCheck = currentPlayerIsInCheck + game.currentPlayer.getColour() + " is in Check!!!";
            }
        Label checkLabel = new Label(currentPlayerIsInCheck);
        currentPlayerIsInCheck = "       ";
        return new HBox(label, checkLabel);
    }

    public int processingMovement(Game currentGame) {
        if((!currentGame.currentPlayer.isLoser() || !currentGame.isADraw()) && currentGame.getSquareStart() != null
                && currentGame.getSquareFinal() != null) {
                Piece selectedPiece = currentGame.getSquareStart().getOccupiedBy();
                Square startSquare = currentGame.getSquareStart();
                Square finalSquare = currentGame.getSquareFinal();
                if (currentGame.isMoveAllowed(selectedPiece, finalSquare)) {
                    char key = 'Q';
                    if(selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).promotionPossible(finalSquare)){
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
                        if (result.get() == buttonTypeOne){
                            key = 'R';
                        } else if (result.get() == buttonTypeTwo) {
                            key = 'N';
                        } else if (result.get() == buttonTypeThree) {
                            key = 'B';
                        } else {
                            key = 'Q';
                        }
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
        if(currentGame.currentPlayer.isLoser()){
            return 3;
        }
        if(currentGame.isADraw()){
            return 4;
        }
        return 5;
    }

    public HBox generateBeatenPieces(Game game){
        HBox box = new HBox();
        box.getChildren().add(new Label("Beaten Pieces:"));
        if(!game.beatenPieces.isEmpty()){
            for (Piece piece: game.beatenPieces){
                box.getChildren().add(SetImages.chooseImage(piece.getType(),piece.getColour()));
            }
        }
        return box;
    }

    public GridPane generateButtonGrid(Game game){
        GridPane grid = new GridPane();
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                Button btn = new Button();
                btn.setGraphic(SetImages.chooseImage(game.chessBoard.getSquareAt(x, y)));
                int finalX = x;
                int finalY = y;
                btn.setOnAction(event -> {
                    game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                    buttonSetAction(game);
                });
                grid.add(btn, x, y);
            }
            grid.add(new Label("TODO"), 8 , y);
        }
        grid.add(new Label("A"), 0, 8);
        grid.add(new Label("B"), 1, 8);
        grid.add(new Label("C"), 2, 8);
        grid.add(new Label("D"), 3, 8);
        grid.add(new Label("E"), 4, 8);
        grid.add(new Label("F"), 5, 8);
        grid.add(new Label("G"), 6, 8);
        grid.add(new Label("H"), 7, 8);
        return grid;
    }

    public GridPane generateHighlightedButtonGrid(Game game){
        GridPane grid = new GridPane();

       /* for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if ((8*y+x) % 2 == 0) {
                    grid.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }*/
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
                Button btn = new Button();
                if(allowedSquares.contains(game.chessBoard.getSquareAt(x, y))){
                    btn.setGraphic(SetImages.chooseHighlightedImage(game.chessBoard.getSquareAt(x, y)));
                }
                else {
                    btn.setGraphic(SetImages.chooseImage(game.chessBoard.getSquareAt(x, y)));
                }
                int finalX = x;
                int finalY = y;
                btn.setOnAction(event -> {
                    game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                    buttonSetAction(game);
                });
                grid.add(btn, x, y);
            }
            grid.add(new Label("TODO"), 8 , y);
        }
        grid.add(new Label("A"), 0, 8);
        grid.add(new Label("B"), 1, 8);
        grid.add(new Label("C"), 2, 8);
        grid.add(new Label("D"), 3, 8);
        grid.add(new Label("E"), 4, 8);
        grid.add(new Label("F"), 5, 8);
        grid.add(new Label("G"), 6, 8);
        grid.add(new Label("H"), 7, 8);
        return grid;
    }

    public GridPane generateButtonGridBlackDown(Game game){
        GridPane grid = new GridPane();
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                Button btn = new Button();
                btn.setGraphic(SetImages.chooseImage(game.chessBoard.getSquareAt(x, y)));
                int finalX = x;
                int finalY = y;
                btn.setOnAction(event -> {
                    game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                    buttonSetAction(game);
                });
                grid.add(btn, 7 - x, 7 - y);
            }
            grid.add(new Label("TODO"), 8 , y);
        }
        grid.add(new Label("H"), 0, 8);
        grid.add(new Label("G"), 1, 8);
        grid.add(new Label("F"), 2, 8);
        grid.add(new Label("E"), 3, 8);
        grid.add(new Label("D"), 4, 8);
        grid.add(new Label("C"), 5, 8);
        grid.add(new Label("B"), 6, 8);
        grid.add(new Label("A"), 7, 8);
        return grid;
    }

    public GridPane generateHighlightedButtonGridBlackDown(Game game){
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
                Button btn = new Button();
                if(allowedSquares.contains(game.chessBoard.getSquareAt(x, y))){
                    btn.setGraphic(SetImages.chooseHighlightedImage(game.chessBoard.getSquareAt(x, y)));
                }
                else {
                    btn.setGraphic(SetImages.chooseImage(game.chessBoard.getSquareAt(x, y)));
                }
                int finalX = x;
                int finalY = y;
                btn.setOnAction(event -> {
                    game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                    buttonSetAction(game);
                });
                grid.add(btn, 7 - x, 7 - y);
            }
            grid.add(new Label("TODO"), 8 , y);
        }
        grid.add(new Label("H"), 0, 8);
        grid.add(new Label("G"), 1, 8);
        grid.add(new Label("F"), 2, 8);
        grid.add(new Label("E"), 3, 8);
        grid.add(new Label("D"), 4, 8);
        grid.add(new Label("C"), 5, 8);
        grid.add(new Label("B"), 6, 8);
        grid.add(new Label("A"), 7, 8);
        return grid;
    }

    public GridPane chooseButtonGridGeneration(Game game){
        if(game.enemyIsHuman) {
            if (game.isRotatingBoard) {
                if(game.currentPlayer.getColour() == Colour.WHITE){
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
                // current player is black
                else {
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
            }
            // Board doesn't rotate
            else {
                if (game.getSquareStart() != null && game.getSquareFinal() == null && game.highlightPossibleMoves) {
                        if (game.getSquareStart().getOccupiedBy() != null) {
                            if (game.getSquareStart().getOccupiedBy().getColour() == game.currentPlayer.getColour()) {
                                return generateHighlightedButtonGrid(game);
                            } else {
                                alertPieceWrongColour(game);
                            }
                        } else {
                            alertNoPiece(game);
                        }
                }
                // No rotation: no highlighted moves
                return generateButtonGrid(game);
            }
        }
        // enemy is AI
        else {
            if(game.userColour == Colour.WHITE){
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
                return generateButtonGrid(game);
            }
            // AI: user colour is black
            else {
                if (game.getSquareStart() != null && game.getSquareFinal() == null && game.highlightPossibleMoves) {
                    if(game.getSquareStart().getOccupiedBy() != null){
                        if(game.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK){
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
        }
    }

    private void buttonSetAction(Game game) {
        if(game.getSquareStart() != null && game.getSquareFinal() != null) {
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
        if(game.getSquareStart() != null && game.getSquareFinal() == null){
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
