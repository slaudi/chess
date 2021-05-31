package chess.gui;

import chess.engine.EvaluatePieces;
import chess.game.*;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Optional;



public class ChessBoardView extends BorderPane{

    GenerateButtons generateButtons;
    int fontSize = 17;

    public ChessBoardView(Game game) {
        HBox heading = generatePlayersMoveLabelBox(game);
        HBox bottom = generateBeatenPieces(game);
        VBox right = generateRightMarginColumn(game);
        GridPane center = generateButtons.generateButtonGrid(game);

        //TODO: bottom (beatenPieces) höher
        heading.setAlignment(Pos.TOP_CENTER);
        heading.setPadding(new Insets(20));
        bottom.setAlignment(Pos.BOTTOM_LEFT);
        bottom.setPadding(new Insets(30));
        right.setAlignment(Pos.CENTER);
        right.setPadding(new Insets(20));

        setCenter(center);
        setRight(right);
        setTop(heading);
        setBottom(bottom);

    }

    private HBox generateBeatenPieces(Game game){
        HBox box = new HBox();
        box.getChildren().add(new Label("Beaten Pieces:"));
        if(!game.beatenPieces.isEmpty()){
            for (Piece piece: game.beatenPieces){
                box.getChildren().add(SetImages.getBeatenPieces(piece.getType(),piece.getColour()));
            }
        }
        return box;
    }

    private VBox generateRightMarginColumn(Game game){
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
            alerti.setHeaderText(" ChessBoard-Rotation: " + isBoardRotationStatus
                    + "\n Highlighting of Moves: " + highlightPossibleMoveStatus
                    + "\n Change selected Piece: " + allowedChangeSelectedPieceStatus
                    + "\n Player is in Check-Notification: " + hintInCheckStatus);
            alerti.setContentText("Choose Option you want to Change:");

            ButtonType buttonTypeOne = new ButtonType("Rotation");
            ButtonType buttonTypeTwo = new ButtonType("Highlight Moves");
            ButtonType buttonTypeThree = new ButtonType("Change Selected Piece");
            ButtonType buttonTypeFour = new ButtonType("Check Notification");
            ButtonType buttonTypeFive = new ButtonType("Start New Game");
            ButtonType buttonTypeSix = new ButtonType("Cancel");

            alerti.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour, buttonTypeFive, buttonTypeSix);

            Optional<ButtonType> result = alerti.showAndWait();
            if (result.isPresent()) {
                if (result.get() == buttonTypeOne) {
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
                    if (resulto.isPresent()) {
                        if (resulto.get() == ButtonType.OK) {
                            new Game(); //TODO: neues Spiel
                        }  //user chose CANCEL or closed the dialog
                    }
                }  //user chose CANCEL or closed the dialog
            }


        });
        Button btnMoveHistory = new Button("Move-History");
        btnMoveHistory.setOnAction(event -> {
            List<Move> history = game.moveHistory;
            StringBuilder historyAsString = new StringBuilder();
            if(!history.isEmpty()){
                for (Move move : history) {
                    historyAsString.append(move.getStartSquare().getLabel().toString()).append("-").append(move.getFinalSquare().getLabel().toString()).append("\n");
                }
            } else {
                historyAsString = new StringBuilder(" ");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Move-History");
            alert.setHeaderText(null);
            alert.setContentText(historyAsString.toString());

            alert.showAndWait();
        });
        VBox box = new VBox(15);
        box.getChildren().addAll(btnOptions, btnMoveHistory);
        return box;
    }

    private HBox generatePlayersMoveLabelBox(Game game){
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

    void setButtonAction(Game game) {
        if (game.getSquareStart() != null && game.getSquareFinal() != null) {
            int result = processingMovement(game);
            if (result == 0) {
                if (!game.enemyIsHuman) {
                    Move AIMove = EvaluatePieces.nextBestMove(game);
                    game.setSquareStart(AIMove.getStartSquare());
                    game.setSquareFinal(AIMove.getFinalSquare());
                    processingMovement(game);
                }
                setCenter(generateButtons.chooseButtonGridGeneration(game));
                setBottom(generateBeatenPieces(game));
                setTop(generatePlayersMoveLabelBox(game));
            } else {
                generateAnswer(result);
            }
        }
        if (game.getSquareStart() != null && game.getSquareFinal() == null) {
            setCenter(generateButtons.chooseButtonGridGeneration(game));
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


}
