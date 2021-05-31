package chess.gui;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Square;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.List;

public class GenerateButtons {

    ChessBoardView chessBoardView;

    int buttonHeight = 90;
    int buttonWidth = 90;
    String white = "-fx-background-color: floralwhite";
    String black = "-fx-background-color: slategray";
    String highlight = "-fx-border-color: skyblue";
    int fontSize = 17;


    GridPane generateButtonGrid(Game game){
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
            chessBoardView.setButtonAction(game);
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
            chessBoardView.setButtonAction(game);
        });
        return btn;
    }



    GridPane chooseButtonGridGeneration(Game game){
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
