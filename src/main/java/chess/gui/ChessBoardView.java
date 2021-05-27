package chess.gui;

import chess.game.*;
import chess.game.Game;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;
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

    Image BlackPawnOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackPawnOnWhite.png")));
    Image BlackPawnOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackPawnOnBlack.png")));
    Image WhitePawnOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhitePawnOnWhite.png")));
    Image WhitePawnOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhitePawnOnBlack.png")));
    Image BlackRookOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackRookOnWhite.png")));
    Image BlackRookOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackRookOnBlack.png")));
    Image WhiteRookOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteRookOnWhite.png")));
    Image WhiteRookOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteRookOnBlack.png")));
    Image BlackKnightOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKnightOnWhite.png")));
    Image BlackKnightOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKnightOnBlack.png")));
    Image WhiteKnightOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKnightOnWhite.png")));
    Image WhiteKnightOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKnightOnBlack.png")));
    Image BlackBishopOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackBishopOnWhite.png")));
    Image BlackBishopOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackBishopOnBlack.png")));
    Image WhiteBishopOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteBishopOnWhite.png")));
    Image WhiteBishopOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteBishopOnBlack.png")));
    Image BlackQueenOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackQueenOnWhite.png")));
    Image BlackQueenOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackQueenOnBlack.png")));
    Image WhiteQueenOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteQueenOnWhite.png")));
    Image WhiteQueenOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteQueenOnBlack.png")));
    Image BlackKingOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKingOnWhite.png")));
    Image BlackKingOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKingOnBlack.png")));
    Image WhiteKingOnWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKingOnWhite.png")));
    Image WhiteKingOnBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKingOnBlack.png")));
    Image EmptyWhite = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("emptyWhite.png")));
    Image EmptyBlack = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("emptyBlack.png")));

    Image BlackPawnOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackPawnOnWhiteX.png")));
    Image BlackPawnOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackPawnOnBlackX.png")));
    Image WhitePawnOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhitePawnOnWhiteX.png")));
    Image WhitePawnOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhitePawnOnBlackX.png")));
    Image BlackRookOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackRookOnWhiteX.png")));
    Image BlackRookOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackRookOnBlackX.png")));
    Image WhiteRookOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteRookOnWhiteX.png")));
    Image WhiteRookOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteRookOnBlackX.png")));
    Image BlackKnightOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKnightOnWhiteX.png")));
    Image BlackKnightOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKnightOnBlackX.png")));
    Image WhiteKnightOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKnightOnWhiteX.png")));
    Image WhiteKnightOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKnightOnBlackX.png")));
    Image BlackBishopOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackBishopOnWhiteX.png")));
    Image BlackBishopOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackBishopOnBlackX.png")));
    Image WhiteBishopOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteBishopOnWhiteX.png")));
    Image WhiteBishopOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteBishopOnBlackX.png")));
    Image BlackQueenOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackQueenOnWhiteX.png")));
    Image BlackQueenOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackQueenOnBlackX.png")));
    Image WhiteQueenOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteQueenOnWhiteX.png")));
    Image WhiteQueenOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteQueenOnBlackX.png")));
    Image BlackKingOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKingOnWhiteX.png")));
    Image BlackKingOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKingOnBlackX.png")));
    Image WhiteKingOnWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKingOnWhiteX.png")));
    Image WhiteKingOnBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKingOnBlackX.png")));
    Image EmptyWhiteX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("emptyWhiteX.png")));
    Image EmptyBlackX = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("emptyBlackX.png")));



    public VBox generateRightMarginColumn(Game game){
        Button btnOptions = new Button("Options");
        btnOptions.setOnAction(event -> {
            String isBoardRotationStatus;
            String highlightPossibleMoveStatus;
            String allowedChangeSelectedPieceStatus;
            String hintInCheckStatus;
            if(game.isRotatingBoard){
                isBoardRotationStatus = "ON";
            }
            else isBoardRotationStatus = "OFF";
            if(game.highlightPossibleMoves){
                highlightPossibleMoveStatus = "ON";
            }
            else highlightPossibleMoveStatus = "OFF";
            if(game.allowedToChangeSelectedPiece){
                allowedChangeSelectedPieceStatus = "ON";
            }
            else allowedChangeSelectedPieceStatus = "OFF";
            if(game.hintInCheck){
                hintInCheckStatus = "ON";
            }
            else hintInCheckStatus = "OFF";

            Alert alerti = new Alert(Alert.AlertType.CONFIRMATION);
            alerti.setTitle("Game-Settings");
            alerti.setHeaderText(" ChessBoard-Rotation is " + isBoardRotationStatus + "\n Highlighting of possible Moves is " + highlightPossibleMoveStatus + "\n Allowed to change selected Piece is " + allowedChangeSelectedPieceStatus + "\n Check-Notifications are " + hintInCheckStatus);
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
                    Game game1 = new Game();
                }  //user chose CANCEL or closed the dialog

            }  //user chose CANCEL or closed the dialog


        });
        Button btnMoveHistory = new Button("Move-History");
        btnMoveHistory.setOnAction(event -> {
            ArrayList<Move> history = game.moveHistory;
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
        if (game.hintInCheck){
            if(game.currentPlayer.isInCheck()){
                currentPlayerIsInCheck = currentPlayerIsInCheck + game.currentPlayer.getColour() + " is in Check!!!";
            }
        }
        Label checkLabel = new Label(currentPlayerIsInCheck);
        currentPlayerIsInCheck = "       ";
        return new HBox(label, checkLabel);
    }

    public int processingMovement(Game currentGame) {
        if(!currentGame.currentPlayer.isLoser() || !currentGame.isADraw()) {
            if (currentGame.getSquareStart() != null && currentGame.getSquareFinal() != null) {
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
                box.getChildren().add(chooseImage(piece.getType(),piece.getColour()));
            }
        }
        return box;
    }

    public GridPane generateButtonGrid(Game game){
        GridPane grid = new GridPane();
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                Button btn = new Button();
                btn.setGraphic(chooseImage(game.chessBoard.getSquareAt(x, y)));
                int finalX = x;
                int finalY = y;
                btn.setOnAction(event -> {
                    game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                    if(game.getSquareStart() != null && game.getSquareFinal() != null){
                        int result = processingMovement(game);
                        if(result == 0){
                            setCenter(chooseButtonGridGeneration(game));
                            setBottom(generateBeatenPieces(game));
                            setTop(generatePlayersMoveLabelBox(game));
                        }
                        else if (result == 1){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Movement-Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Move not allowed: Would be Check");
                            alert.showAndWait();
                        }
                        else if (result == 2){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Movement-Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Move not allowed: Not possible");
                            alert.showAndWait();
                        }
                        else if (result == 3){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game-Information");
                            alert.setHeaderText(null);
                            alert.setContentText("CheckMate");
                            alert.showAndWait();
                        }
                        else if (result == 4){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game-Information");
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
                    if(game.getSquareStart() != null && game.getSquareFinal() == null){
                        setCenter(chooseButtonGridGeneration(game));
                    }
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
        ArrayList<Square> allowedSquares = new ArrayList<>();
        for (int j = 0; j < 8; j++){
            for (int i = 0; i < 8; i++){
                if (game.isMoveAllowed(game.getSquareStart().getOccupiedBy(), game.chessBoard.getSquareAt(i, j))){
                    if(game.getSquareStart().getOccupiedBy().getType() != Type.KING){
                        allowedSquares.add(game.chessBoard.getSquareAt(i, j));
                    }
                    else {
                        if(game.isSafeSquare(game.chessBoard.getSquareAt(i, j))){
                            allowedSquares.add(game.chessBoard.getSquareAt(i, j));
                        }
                    }
                }
            }
        }
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
                    btn.setGraphic(chooseHighlightedImage(game.chessBoard.getSquareAt(x, y)));
                }
                else {
                    btn.setGraphic(chooseImage(game.chessBoard.getSquareAt(x, y)));
                }
                int finalX = x;
                int finalY = y;
                btn.setOnAction(event -> {
                    game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                    if(game.getSquareStart() != null && game.getSquareFinal() != null){
                        int result = processingMovement(game);
                        if(result == 0){
                            setCenter(chooseButtonGridGeneration(game));
                            setBottom(generateBeatenPieces(game));
                            setTop(generatePlayersMoveLabelBox(game));
                        }
                        else if (result == 1){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Movement-Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Move not allowed: Would be Check");
                            alert.showAndWait();
                        }
                        else if (result == 2){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Movement-Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Move not allowed: Not possible");
                            alert.showAndWait();
                        }
                        else if (result == 3){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game-Information");
                            alert.setHeaderText(null);
                            alert.setContentText("CheckMate");
                            alert.showAndWait();
                        }
                        else if (result == 4){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game-Information");
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
                    if(game.getSquareStart() != null && game.getSquareFinal() == null){
                        setCenter(chooseButtonGridGeneration(game));
                    }
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
                btn.setGraphic(chooseImage(game.chessBoard.getSquareAt(x, y)));
                int finalX = x;
                int finalY = y;
                btn.setOnAction(event -> {
                    game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                    if(game.getSquareStart() != null && game.getSquareFinal() != null){
                        int result = processingMovement(game);
                        if(result == 0){
                            setCenter(chooseButtonGridGeneration(game));
                            setBottom(generateBeatenPieces(game));
                            setTop(generatePlayersMoveLabelBox(game));
                        }
                        else if (result == 1){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Movement-Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Move not allowed: Would be Check");
                            alert.showAndWait();
                        }
                        else if (result == 2){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Movement-Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Move not allowed: Not possible");
                            alert.showAndWait();
                        }
                        else if (result == 3){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game-Information");
                            alert.setHeaderText(null);
                            alert.setContentText("CheckMate");
                            alert.showAndWait();
                        }
                        else if (result == 4){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game-Information");
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
                    if(game.getSquareStart() != null && game.getSquareFinal() == null){
                        setCenter(chooseButtonGridGeneration(game));
                    }
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
        ArrayList<Square> allowedSquares = new ArrayList<>();
        for (int j = 0; j < 8; j++){
            for (int i = 0; i < 8; i++){
                if (game.isMoveAllowed(game.getSquareStart().getOccupiedBy(), game.chessBoard.getSquareAt(i, j))){
                    if(game.getSquareStart().getOccupiedBy().getType() != Type.KING){
                        allowedSquares.add(game.chessBoard.getSquareAt(i, j));
                    }
                    else {
                        if(game.isSafeSquare(game.chessBoard.getSquareAt(i, j))){
                            allowedSquares.add(game.chessBoard.getSquareAt(i, j));
                        }
                    }
                }
            }
        }
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
                    btn.setGraphic(chooseHighlightedImage(game.chessBoard.getSquareAt(x, y)));
                }
                else {
                    btn.setGraphic(chooseImage(game.chessBoard.getSquareAt(x, y)));
                }
                int finalX = x;
                int finalY = y;
                btn.setOnAction(event -> {
                    game.setBothMovingSquares(game.chessBoard.getSquareAt(finalX, finalY));
                    if(game.getSquareStart() != null && game.getSquareFinal() != null){
                        int result = processingMovement(game);
                        if(result == 0){
                            setCenter(chooseButtonGridGeneration(game));
                            setBottom(generateBeatenPieces(game));
                            setTop(generatePlayersMoveLabelBox(game));
                        }
                        else if (result == 1){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Movement-Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Move not allowed: Would be Check");
                            alert.showAndWait();
                        }
                        else if (result == 2){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Movement-Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Move not allowed: Not possible");
                            alert.showAndWait();
                        }
                        else if (result == 3){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game-Information");
                            alert.setHeaderText(null);
                            alert.setContentText("CheckMate");
                            alert.showAndWait();
                        }
                        else if (result == 4){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game-Information");
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
                    if(game.getSquareStart() != null && game.getSquareFinal() == null){
                        setCenter(chooseButtonGridGeneration(game));
                    }
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
                            }
                            else {
                                //alert Piece is not your Colour
                                game.setSquareStart(null);
                                Alert alertia = new Alert(Alert.AlertType.INFORMATION);
                                alertia.setTitle("Piece Problem");
                                alertia.setHeaderText(null);
                                alertia.setContentText("Selected Piece is not your Colour!");

                                alertia.showAndWait();
                            }
                        }
                        else {
                            //alert no Piece
                            game.setSquareStart(null);
                            Alert alertis = new Alert(Alert.AlertType.INFORMATION);
                            alertis.setTitle("Piece Problem");
                            alertis.setHeaderText(null);
                            alertis.setContentText("There is no Piece to Move!");

                            alertis.showAndWait();
                        }
                    }
                    return generateButtonGrid(game);
                }
                else {
                    if (game.getSquareStart() != null && game.getSquareFinal() == null && game.highlightPossibleMoves) {
                        if (game.getSquareStart().getOccupiedBy() != null) {
                            if (game.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK) {
                                return generateHighlightedButtonGridBlackDown(game);
                            } else {
                                //alert Piece is not your Colour
                                game.setSquareStart(null);
                                Alert alertia = new Alert(Alert.AlertType.INFORMATION);
                                alertia.setTitle("Piece Problem");
                                alertia.setHeaderText(null);
                                alertia.setContentText("Selected Piece is not your Colour!");

                                alertia.showAndWait();
                            }
                        } else {
                            //alert no Piece
                            game.setSquareStart(null);
                            Alert alertis = new Alert(Alert.AlertType.INFORMATION);
                            alertis.setTitle("Piece Problem");
                            alertis.setHeaderText(null);
                            alertis.setContentText("There is no Piece to Move!");

                            alertis.showAndWait();
                        }
                    }
                    return generateButtonGridBlackDown(game);
                }
            } else {
                if (game.getSquareStart() != null && game.getSquareFinal() == null && game.highlightPossibleMoves) {
                    if (game.getSquareStart() != null && game.getSquareFinal() == null && game.highlightPossibleMoves) {
                        if (game.getSquareStart().getOccupiedBy() != null) {
                            if (game.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK) {
                                return generateHighlightedButtonGridBlackDown(game);
                            } else {
                                //alert Piece is not your Colour
                                game.setSquareStart(null);
                                Alert alertia = new Alert(Alert.AlertType.INFORMATION);
                                alertia.setTitle("Piece Problem");
                                alertia.setHeaderText(null);
                                alertia.setContentText("Selected Piece is not your Colour!");

                                alertia.showAndWait();
                            }
                        } else {
                            //alert no Piece
                            game.setSquareStart(null);
                            Alert alertis = new Alert(Alert.AlertType.INFORMATION);
                            alertis.setTitle("Piece Problem");
                            alertis.setHeaderText(null);
                            alertis.setContentText("There is no Piece to Move!");

                            alertis.showAndWait();
                        }
                    }
                }
                return generateButtonGridBlackDown(game);
            }
        }
        else {
            if(game.userColour == Colour.WHITE){
                if (game.getSquareStart() != null && game.getSquareFinal() == null && game.highlightPossibleMoves) {
                    if(game.getSquareStart().getOccupiedBy() != null){
                        if(game.getSquareStart().getOccupiedBy().getColour() == Colour.WHITE){
                            return generateHighlightedButtonGrid(game);
                        }
                        else {
                            //alert Piece is not your Colour
                            game.setSquareStart(null);
                            Alert alertia = new Alert(Alert.AlertType.INFORMATION);
                            alertia.setTitle("Piece Problem");
                            alertia.setHeaderText(null);
                            alertia.setContentText("Selected Piece is not your Colour!");

                            alertia.showAndWait();
                        }
                    }
                    else {
                        //alert no Piece
                        game.setSquareStart(null);
                        Alert alertis = new Alert(Alert.AlertType.INFORMATION);
                        alertis.setTitle("Piece Problem");
                        alertis.setHeaderText(null);
                        alertis.setContentText("There is no Piece to Move!");

                        alertis.showAndWait();
                    }
                }
                return generateButtonGrid(game);
            }
            else {
                if (game.getSquareStart() != null && game.getSquareFinal() == null && game.highlightPossibleMoves) {
                    if(game.getSquareStart().getOccupiedBy() != null){
                        if(game.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK){
                            return generateHighlightedButtonGridBlackDown(game);
                        }
                        else {
                            //alert Piece is not your Colour
                            game.setSquareStart(null);
                            Alert alertia = new Alert(Alert.AlertType.INFORMATION);
                            alertia.setTitle("Piece Problem");
                            alertia.setHeaderText(null);
                            alertia.setContentText("Selected Piece is not your Colour!");

                            alertia.showAndWait();
                        }
                    }
                    else {
                        //alert no Piece
                        game.setSquareStart(null);
                        Alert alertis = new Alert(Alert.AlertType.INFORMATION);
                        alertis.setTitle("Piece Problem");
                        alertis.setHeaderText(null);
                        alertis.setContentText("There is no Piece to Move!");

                        alertis.showAndWait();
                    }
                }
                return generateButtonGridBlackDown(game);
            }
        }
    }

    public ImageView chooseImage(Type type, Colour colour){

        ImageView blackPawnOnWhiteViewMini = new ImageView();
        blackPawnOnWhiteViewMini.setFitHeight(40);
        blackPawnOnWhiteViewMini.setFitWidth(40);
        blackPawnOnWhiteViewMini.setImage(BlackPawnOnWhite);

        ImageView whitePawnOnWhiteViewMini = new ImageView();
        whitePawnOnWhiteViewMini.setFitHeight(40);
        whitePawnOnWhiteViewMini.setFitWidth(40);
        whitePawnOnWhiteViewMini.setImage(WhitePawnOnWhite);

        ImageView blackRookOnWhiteViewMini = new ImageView();
        blackRookOnWhiteViewMini.setFitHeight(40);
        blackRookOnWhiteViewMini.setFitWidth(40);
        blackRookOnWhiteViewMini.setImage(BlackRookOnWhite);

        ImageView whiteRookOnWhiteViewMini = new ImageView();
        whiteRookOnWhiteViewMini.setFitHeight(40);
        whiteRookOnWhiteViewMini.setFitWidth(40);
        whiteRookOnWhiteViewMini.setImage(WhiteRookOnWhite);

        ImageView blackKnightOnWhiteViewMini = new ImageView();
        blackKnightOnWhiteViewMini.setFitHeight(40);
        blackKnightOnWhiteViewMini.setFitWidth(40);
        blackKnightOnWhiteViewMini.setImage(BlackKnightOnWhite);

        ImageView whiteKnightOnWhiteViewMini = new ImageView();
        whiteKnightOnWhiteViewMini.setFitHeight(40);
        whiteKnightOnWhiteViewMini.setFitWidth(40);
        whiteKnightOnWhiteViewMini.setImage(WhiteKnightOnWhite);

        ImageView blackBishopOnWhiteViewMini = new ImageView();
        blackBishopOnWhiteViewMini.setFitHeight(40);
        blackBishopOnWhiteViewMini.setFitWidth(40);
        blackBishopOnWhiteViewMini.setImage(BlackBishopOnWhite);

        ImageView whiteBishopOnWhiteViewMini = new ImageView();
        whiteBishopOnWhiteViewMini.setFitHeight(40);
        whiteBishopOnWhiteViewMini.setFitWidth(40);
        whiteBishopOnWhiteViewMini.setImage(WhiteBishopOnWhite);

        ImageView blackQueenOnWhiteViewMini = new ImageView();
        blackQueenOnWhiteViewMini.setFitHeight(40);
        blackQueenOnWhiteViewMini.setFitWidth(40);
        blackQueenOnWhiteViewMini.setImage(BlackQueenOnWhite);

        ImageView whiteQueenOnWhiteViewMini = new ImageView();
        whiteQueenOnWhiteViewMini.setFitHeight(40);
        whiteQueenOnWhiteViewMini.setFitWidth(40);
        whiteQueenOnWhiteViewMini.setImage(WhiteQueenOnWhite);

        ImageView blackKingOnWhiteViewMini = new ImageView();
        blackKingOnWhiteViewMini.setFitHeight(40);
        blackKingOnWhiteViewMini.setFitWidth(40);
        blackKingOnWhiteViewMini.setImage(BlackKingOnWhite);

        ImageView whiteKingOnWhiteViewMini = new ImageView();
        whiteKingOnWhiteViewMini.setFitHeight(40);
        whiteKingOnWhiteViewMini.setFitWidth(40);
        whiteKingOnWhiteViewMini.setImage(WhiteKingOnWhite);

        if (colour == Colour.WHITE){
            if (type == Type.PAWN){
                return whitePawnOnWhiteViewMini;
            }
            else if (type == Type.ROOK){
                return whiteRookOnWhiteViewMini;
            }
            else if (type == Type.KNIGHT){
                return whiteKnightOnWhiteViewMini;
            }
            else if (type == Type.BISHOP){
                return whiteBishopOnWhiteViewMini;
            }
            else if (type == Type.QUEEN){
                return whiteQueenOnWhiteViewMini;
            }
            else if (type == Type.KING){
                return whiteKingOnWhiteViewMini;
            }
        }
        else {
            if (type == Type.PAWN){
                return blackPawnOnWhiteViewMini;
            }
            else if (type == Type.ROOK){
                return blackRookOnWhiteViewMini;
            }
            else if (type == Type.KNIGHT){
                return blackKnightOnWhiteViewMini;
            }
            else if (type == Type.BISHOP){
                return blackBishopOnWhiteViewMini;
            }
            else if (type == Type.QUEEN){
                return blackQueenOnWhiteViewMini;
            }
            else if (type == Type.KING){
                return blackKingOnWhiteViewMini;
            }
        }
        return null;
    }

    public ImageView chooseImage(Square square){

        int imageHeight = 60;
        int imageWidth = 60;

        ImageView blackPawnOnWhiteView = new ImageView();
        blackPawnOnWhiteView.setFitHeight(imageHeight);
        blackPawnOnWhiteView.setFitWidth(imageWidth);
        blackPawnOnWhiteView.setImage(BlackPawnOnWhite);

        ImageView blackPawnOnBlackView = new ImageView();
        blackPawnOnBlackView.setFitHeight(imageHeight);
        blackPawnOnBlackView.setFitWidth(imageWidth);
        blackPawnOnBlackView.setImage(BlackPawnOnBlack);

        ImageView whitePawnOnWhiteView = new ImageView();
        whitePawnOnWhiteView.setFitHeight(imageHeight);
        whitePawnOnWhiteView.setFitWidth(imageWidth);
        whitePawnOnWhiteView.setImage(WhitePawnOnWhite);

        ImageView whitePawnOnBlackView = new ImageView();
        whitePawnOnBlackView.setFitHeight(imageHeight);
        whitePawnOnBlackView.setFitWidth(imageWidth);
        whitePawnOnBlackView.setImage(WhitePawnOnBlack);

        ImageView blackRookOnWhiteView = new ImageView();
        blackRookOnWhiteView.setFitHeight(imageHeight);
        blackRookOnWhiteView.setFitWidth(imageWidth);
        blackRookOnWhiteView.setImage(BlackRookOnWhite);

        ImageView blackRookOnBlackView = new ImageView();
        blackRookOnBlackView.setFitHeight(imageHeight);
        blackRookOnBlackView.setFitWidth(imageWidth);
        blackRookOnBlackView.setImage(BlackRookOnBlack);

        ImageView whiteRookOnWhiteView = new ImageView();
        whiteRookOnWhiteView.setFitHeight(imageHeight);
        whiteRookOnWhiteView.setFitWidth(imageWidth);
        whiteRookOnWhiteView.setImage(WhiteRookOnWhite);

        ImageView whiteRookOnBlackView = new ImageView();
        whiteRookOnBlackView.setFitHeight(imageHeight);
        whiteRookOnBlackView.setFitWidth(imageWidth);
        whiteRookOnBlackView.setImage(WhiteRookOnBlack);

        ImageView blackKnightOnWhiteView = new ImageView();
        blackKnightOnWhiteView.setFitHeight(imageHeight);
        blackKnightOnWhiteView.setFitWidth(imageWidth);
        blackKnightOnWhiteView.setImage(BlackKnightOnWhite);

        ImageView blackKnightOnBlackView = new ImageView();
        blackKnightOnBlackView.setFitHeight(imageHeight);
        blackKnightOnBlackView.setFitWidth(imageWidth);
        blackKnightOnBlackView.setImage(BlackKnightOnBlack);

        ImageView whiteKnightOnWhiteView = new ImageView();
        whiteKnightOnWhiteView.setFitHeight(imageHeight);
        whiteKnightOnWhiteView.setFitWidth(imageWidth);
        whiteKnightOnWhiteView.setImage(WhiteKnightOnWhite);

        ImageView whiteKnightOnBlackView = new ImageView();
        whiteKnightOnBlackView.setFitHeight(imageHeight);
        whiteKnightOnBlackView.setFitWidth(imageWidth);
        whiteKnightOnBlackView.setImage(WhiteKnightOnBlack);

        ImageView blackBishopOnWhiteView = new ImageView();
        blackBishopOnWhiteView.setFitHeight(imageHeight);
        blackBishopOnWhiteView.setFitWidth(imageWidth);
        blackBishopOnWhiteView.setImage(BlackBishopOnWhite);

        ImageView blackBishopOnBlackView = new ImageView();
        blackBishopOnBlackView.setFitHeight(imageHeight);
        blackBishopOnBlackView.setFitWidth(imageWidth);
        blackBishopOnBlackView.setImage(BlackBishopOnBlack);

        ImageView whiteBishopOnWhiteView = new ImageView();
        whiteBishopOnWhiteView.setFitHeight(imageHeight);
        whiteBishopOnWhiteView.setFitWidth(imageWidth);
        whiteBishopOnWhiteView.setImage(WhiteBishopOnWhite);

        ImageView whiteBishopOnBlackView = new ImageView();
        whiteBishopOnBlackView.setFitHeight(imageHeight);
        whiteBishopOnBlackView.setFitWidth(imageWidth);
        whiteBishopOnBlackView.setImage(WhiteBishopOnBlack);

        ImageView blackQueenOnWhiteView = new ImageView();
        blackQueenOnWhiteView.setFitHeight(imageHeight);
        blackQueenOnWhiteView.setFitWidth(imageWidth);
        blackQueenOnWhiteView.setImage(BlackQueenOnWhite);

        ImageView blackQueenOnBlackView = new ImageView();
        blackQueenOnBlackView.setFitHeight(imageHeight);
        blackQueenOnBlackView.setFitWidth(imageWidth);
        blackQueenOnBlackView.setImage(BlackQueenOnBlack);

        ImageView whiteQueenOnWhiteView = new ImageView();
        whiteQueenOnWhiteView.setFitHeight(imageHeight);
        whiteQueenOnWhiteView.setFitWidth(imageWidth);
        whiteQueenOnWhiteView.setImage(WhiteQueenOnWhite);

        ImageView whiteQueenOnBlackView = new ImageView();
        whiteQueenOnBlackView.setFitHeight(imageHeight);
        whiteQueenOnBlackView.setFitWidth(imageWidth);
        whiteQueenOnBlackView.setImage(WhiteQueenOnBlack);

        ImageView blackKingOnWhiteView = new ImageView();
        blackKingOnWhiteView.setFitHeight(imageHeight);
        blackKingOnWhiteView.setFitWidth(imageWidth);
        blackKingOnWhiteView.setImage(BlackKingOnWhite);

        ImageView blackKingOnBlackView = new ImageView();
        blackKingOnBlackView.setFitHeight(imageHeight);
        blackKingOnBlackView.setFitWidth(imageWidth);
        blackKingOnBlackView.setImage(BlackKingOnBlack);

        ImageView whiteKingOnWhiteView = new ImageView();
        whiteKingOnWhiteView.setFitHeight(imageHeight);
        whiteKingOnWhiteView.setFitWidth(imageWidth);
        whiteKingOnWhiteView.setImage(WhiteKingOnWhite);

        ImageView whiteKingOnBlackView = new ImageView();
        whiteKingOnBlackView.setFitHeight(imageHeight);
        whiteKingOnBlackView.setFitWidth(imageWidth);
        whiteKingOnBlackView.setImage(WhiteKingOnBlack);

        ImageView emptyWhiteView = new ImageView();
        emptyWhiteView.setFitHeight(imageHeight);
        emptyWhiteView.setFitWidth(imageWidth);
        emptyWhiteView.setImage(EmptyWhite);

        ImageView emptyBlackView = new ImageView();
        emptyBlackView.setFitHeight(imageHeight);
        emptyBlackView.setFitWidth(imageWidth);
        emptyBlackView.setImage(EmptyBlack);

        if (square.getColour() == Colour.WHITE){
            if(square.getOccupiedBy() == null){
                return emptyWhiteView;
            }
            if(square.getOccupiedBy().getColour() == Colour.WHITE){
                if (square.getOccupiedBy().getType() == Type.PAWN){
                    return whitePawnOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.ROOK){
                    return whiteRookOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.KNIGHT){
                    return whiteKnightOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.BISHOP){
                    return whiteBishopOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.QUEEN){
                    return whiteQueenOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.KING){
                    return whiteKingOnWhiteView;
                }
            }
            else {
                if (square.getOccupiedBy().getType() == Type.PAWN){
                    return blackPawnOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.ROOK){
                    return blackRookOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.KNIGHT){
                    return blackKnightOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.BISHOP){
                    return blackBishopOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.QUEEN){
                    return blackQueenOnWhiteView;
                }
                else if (square.getOccupiedBy().getType() == Type.KING){
                    return blackKingOnWhiteView;
                }
            }
        }
        else {
            if(square.getOccupiedBy() == null){
                return emptyBlackView;
            }
            if(square.getOccupiedBy().getColour() == Colour.WHITE){
                if (square.getOccupiedBy().getType() == Type.PAWN){
                    return whitePawnOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.ROOK){
                    return whiteRookOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.KNIGHT){
                    return whiteKnightOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.BISHOP){
                    return whiteBishopOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.QUEEN){
                    return whiteQueenOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.KING){
                    return whiteKingOnBlackView;
                }
            }
            else {
                if (square.getOccupiedBy().getType() == Type.PAWN){
                    return blackPawnOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.ROOK){
                    return blackRookOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.KNIGHT){
                    return blackKnightOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.BISHOP){
                    return blackBishopOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.QUEEN){
                    return blackQueenOnBlackView;
                }
                else if (square.getOccupiedBy().getType() == Type.KING){
                    return blackKingOnBlackView;
                }
            }
        }
        return null;
    }
    public ImageView chooseHighlightedImage(Square square){

        int imageHeight = 60;
        int imageWidth = 60;

        ImageView blackPawnOnWhiteViewX = new ImageView();
        blackPawnOnWhiteViewX.setFitHeight(imageHeight);
        blackPawnOnWhiteViewX.setFitWidth(imageWidth);
        blackPawnOnWhiteViewX.setImage(BlackPawnOnWhiteX);

        ImageView blackPawnOnBlackViewX = new ImageView();
        blackPawnOnBlackViewX.setFitHeight(imageHeight);
        blackPawnOnBlackViewX.setFitWidth(imageWidth);
        blackPawnOnBlackViewX.setImage(BlackPawnOnBlackX);

        ImageView whitePawnOnWhiteViewX = new ImageView();
        whitePawnOnWhiteViewX.setFitHeight(imageHeight);
        whitePawnOnWhiteViewX.setFitWidth(imageWidth);
        whitePawnOnWhiteViewX.setImage(WhitePawnOnWhiteX);

        ImageView whitePawnOnBlackViewX = new ImageView();
        whitePawnOnBlackViewX.setFitHeight(imageHeight);
        whitePawnOnBlackViewX.setFitWidth(imageWidth);
        whitePawnOnBlackViewX.setImage(WhitePawnOnBlackX);

        ImageView blackRookOnWhiteViewX = new ImageView();
        blackRookOnWhiteViewX.setFitHeight(imageHeight);
        blackRookOnWhiteViewX.setFitWidth(imageWidth);
        blackRookOnWhiteViewX.setImage(BlackRookOnWhiteX);

        ImageView blackRookOnBlackViewX = new ImageView();
        blackRookOnBlackViewX.setFitHeight(imageHeight);
        blackRookOnBlackViewX.setFitWidth(imageWidth);
        blackRookOnBlackViewX.setImage(BlackRookOnBlackX);

        ImageView whiteRookOnWhiteViewX = new ImageView();
        whiteRookOnWhiteViewX.setFitHeight(imageHeight);
        whiteRookOnWhiteViewX.setFitWidth(imageWidth);
        whiteRookOnWhiteViewX.setImage(WhiteRookOnWhiteX);

        ImageView whiteRookOnBlackViewX = new ImageView();
        whiteRookOnBlackViewX.setFitHeight(imageHeight);
        whiteRookOnBlackViewX.setFitWidth(imageWidth);
        whiteRookOnBlackViewX.setImage(WhiteRookOnBlackX);

        ImageView blackKnightOnWhiteViewX = new ImageView();
        blackKnightOnWhiteViewX.setFitHeight(imageHeight);
        blackKnightOnWhiteViewX.setFitWidth(imageWidth);
        blackKnightOnWhiteViewX.setImage(BlackKnightOnWhiteX);

        ImageView blackKnightOnBlackViewX = new ImageView();
        blackKnightOnBlackViewX.setFitHeight(imageHeight);
        blackKnightOnBlackViewX.setFitWidth(imageWidth);
        blackKnightOnBlackViewX.setImage(BlackKnightOnBlackX);

        ImageView whiteKnightOnWhiteViewX = new ImageView();
        whiteKnightOnWhiteViewX.setFitHeight(imageHeight);
        whiteKnightOnWhiteViewX.setFitWidth(imageWidth);
        whiteKnightOnWhiteViewX.setImage(WhiteKnightOnWhiteX);

        ImageView whiteKnightOnBlackViewX = new ImageView();
        whiteKnightOnBlackViewX.setFitHeight(imageHeight);
        whiteKnightOnBlackViewX.setFitWidth(imageWidth);
        whiteKnightOnBlackViewX.setImage(WhiteKnightOnBlackX);

        ImageView blackBishopOnWhiteViewX = new ImageView();
        blackBishopOnWhiteViewX.setFitHeight(imageHeight);
        blackBishopOnWhiteViewX.setFitWidth(imageWidth);
        blackBishopOnWhiteViewX.setImage(BlackBishopOnWhiteX);

        ImageView blackBishopOnBlackViewX = new ImageView();
        blackBishopOnBlackViewX.setFitHeight(imageHeight);
        blackBishopOnBlackViewX.setFitWidth(imageWidth);
        blackBishopOnBlackViewX.setImage(BlackBishopOnBlackX);

        ImageView whiteBishopOnWhiteViewX = new ImageView();
        whiteBishopOnWhiteViewX.setFitHeight(imageHeight);
        whiteBishopOnWhiteViewX.setFitWidth(imageWidth);
        whiteBishopOnWhiteViewX.setImage(WhiteBishopOnWhiteX);

        ImageView whiteBishopOnBlackViewX = new ImageView();
        whiteBishopOnBlackViewX.setFitHeight(imageHeight);
        whiteBishopOnBlackViewX.setFitWidth(imageWidth);
        whiteBishopOnBlackViewX.setImage(WhiteBishopOnBlackX);

        ImageView blackQueenOnWhiteViewX = new ImageView();
        blackQueenOnWhiteViewX.setFitHeight(imageHeight);
        blackQueenOnWhiteViewX.setFitWidth(imageWidth);
        blackQueenOnWhiteViewX.setImage(BlackQueenOnWhiteX);

        ImageView blackQueenOnBlackViewX = new ImageView();
        blackQueenOnBlackViewX.setFitHeight(imageHeight);
        blackQueenOnBlackViewX.setFitWidth(imageWidth);
        blackQueenOnBlackViewX.setImage(BlackQueenOnBlackX);

        ImageView whiteQueenOnWhiteViewX = new ImageView();
        whiteQueenOnWhiteViewX.setFitHeight(imageHeight);
        whiteQueenOnWhiteViewX.setFitWidth(imageWidth);
        whiteQueenOnWhiteViewX.setImage(WhiteQueenOnWhiteX);

        ImageView whiteQueenOnBlackViewX = new ImageView();
        whiteQueenOnBlackViewX.setFitHeight(imageHeight);
        whiteQueenOnBlackViewX.setFitWidth(imageWidth);
        whiteQueenOnBlackViewX.setImage(WhiteQueenOnBlackX);

        ImageView blackKingOnWhiteViewX = new ImageView();
        blackKingOnWhiteViewX.setFitHeight(imageHeight);
        blackKingOnWhiteViewX.setFitWidth(imageWidth);
        blackKingOnWhiteViewX.setImage(BlackKingOnWhiteX);

        ImageView blackKingOnBlackViewX = new ImageView();
        blackKingOnBlackViewX.setFitHeight(imageHeight);
        blackKingOnBlackViewX.setFitWidth(imageWidth);
        blackKingOnBlackViewX.setImage(BlackKingOnBlackX);

        ImageView whiteKingOnWhiteViewX = new ImageView();
        whiteKingOnWhiteViewX.setFitHeight(imageHeight);
        whiteKingOnWhiteViewX.setFitWidth(imageWidth);
        whiteKingOnWhiteViewX.setImage(WhiteKingOnWhiteX);

        ImageView whiteKingOnBlackViewX = new ImageView();
        whiteKingOnBlackViewX.setFitHeight(imageHeight);
        whiteKingOnBlackViewX.setFitWidth(imageWidth);
        whiteKingOnBlackViewX.setImage(WhiteKingOnBlackX);

        ImageView emptyWhiteViewX = new ImageView();
        emptyWhiteViewX.setFitHeight(imageHeight);
        emptyWhiteViewX.setFitWidth(imageWidth);
        emptyWhiteViewX.setImage(EmptyWhiteX);

        ImageView emptyBlackViewX = new ImageView();
        emptyBlackViewX.setFitHeight(imageHeight);
        emptyBlackViewX.setFitWidth(imageWidth);
        emptyBlackViewX.setImage(EmptyBlackX);

        if (square.getColour() == Colour.WHITE){
            if(square.getOccupiedBy() == null){
                return emptyWhiteViewX;
            }
            if(square.getOccupiedBy().getColour() == Colour.WHITE){
                if (square.getOccupiedBy().getType() == Type.PAWN){
                    return whitePawnOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.ROOK){
                    return whiteRookOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.KNIGHT){
                    return whiteKnightOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.BISHOP){
                    return whiteBishopOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.QUEEN){
                    return whiteQueenOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.KING){
                    return whiteKingOnWhiteViewX;
                }
            }
            else {
                if (square.getOccupiedBy().getType() == Type.PAWN){
                    return blackPawnOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.ROOK){
                    return blackRookOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.KNIGHT){
                    return blackKnightOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.BISHOP){
                    return blackBishopOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.QUEEN){
                    return blackQueenOnWhiteViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.KING){
                    return blackKingOnWhiteViewX;
                }
            }
        }
        else {
            if(square.getOccupiedBy() == null){
                return emptyBlackViewX;
            }
            if(square.getOccupiedBy().getColour() == Colour.WHITE){
                if (square.getOccupiedBy().getType() == Type.PAWN){
                    return whitePawnOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.ROOK){
                    return whiteRookOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.KNIGHT){
                    return whiteKnightOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.BISHOP){
                    return whiteBishopOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.QUEEN){
                    return whiteQueenOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.KING){
                    return whiteKingOnBlackViewX;
                }
            }
            else {
                if (square.getOccupiedBy().getType() == Type.PAWN){
                    return blackPawnOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.ROOK){
                    return blackRookOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.KNIGHT){
                    return blackKnightOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.BISHOP){
                    return blackBishopOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.QUEEN){
                    return blackQueenOnBlackViewX;
                }
                else if (square.getOccupiedBy().getType() == Type.KING){
                    return blackKingOnBlackViewX;
                }
            }
        }
        return null;
    }
}
