package chess.gui;

import chess.game.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;




public class ChessBoardView extends BorderPane{

    public ChessBoardView(Game game) {




        HBox heading = new HBox(new Label("CHESS"));
        HBox bottom = new HBox(new Label("Beaten Pieces"));
        VBox right = new VBox(new Label("RIGHT"), new Button("Options"));
        GridPane center = generateButtonGrid(game.chessBoard);

        heading.setAlignment(Pos.CENTER);
        bottom.setAlignment(Pos.CENTER);
        right.setAlignment(Pos.CENTER);

        setCenter(center);
        setRight(right);
        setTop(heading);
        setBottom(bottom);
    }

    public GridPane generateButtonGrid(Board board){
        GridPane grid = new GridPane();
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                Button btn = new Button();
                btn.setGraphic(chooseImage(board.getSquareAt(x, y)));
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

    public ImageView chooseImage(Square square){
        Image BlackPawnOnWhite = new Image(ChessBoardView.class.getResourceAsStream("BlackPawnOnWhite.png"));
        Image BlackPawnOnBlack = new Image(ChessBoardView.class.getResourceAsStream("BlackPawnOnBlack.png"));
        Image WhitePawnOnWhite = new Image(ChessBoardView.class.getResourceAsStream("WhitePawnOnWhite.png"));
        Image WhitePawnOnBlack = new Image(ChessBoardView.class.getResourceAsStream("WhitePawnOnBlack.png"));
        Image BlackRookOnWhite = new Image(ChessBoardView.class.getResourceAsStream("BlackRookOnWhite.png"));
        Image BlackRookOnBlack = new Image(ChessBoardView.class.getResourceAsStream("BlackRookOnBlack.png"));
        Image WhiteRookOnWhite = new Image(ChessBoardView.class.getResourceAsStream("WhiteRookOnWhite.png"));
        Image WhiteRookOnBlack = new Image(ChessBoardView.class.getResourceAsStream("WhiteRookOnBlack.png"));
        Image BlackKnightOnWhite = new Image(ChessBoardView.class.getResourceAsStream("BlackKnightOnWhite.png"));
        Image BlackKnightOnBlack = new Image(ChessBoardView.class.getResourceAsStream("BlackKnightOnBlack.png"));
        Image WhiteKnightOnWhite = new Image(ChessBoardView.class.getResourceAsStream("WhiteKnightOnWhite.png"));
        Image WhiteKnightOnBlack = new Image(ChessBoardView.class.getResourceAsStream("WhiteKnightOnBlack.png"));
        Image BlackBishopOnWhite = new Image(ChessBoardView.class.getResourceAsStream("BlackBishopOnWhite.png"));
        Image BlackBishopOnBlack = new Image(ChessBoardView.class.getResourceAsStream("BlackBishopOnBlack.png"));
        Image WhiteBishopOnWhite = new Image(ChessBoardView.class.getResourceAsStream("WhiteBishopOnWhite.png"));
        Image WhiteBishopOnBlack = new Image(ChessBoardView.class.getResourceAsStream("WhiteBishopOnBlack.png"));
        Image BlackQueenOnWhite = new Image(ChessBoardView.class.getResourceAsStream("BlackQueenOnWhite.png"));
        Image BlackQueenOnBlack = new Image(ChessBoardView.class.getResourceAsStream("BlackQueenOnBlack.png"));
        Image WhiteQueenOnWhite = new Image(ChessBoardView.class.getResourceAsStream("WhiteQueenOnWhite.png"));
        Image WhiteQueenOnBlack = new Image(ChessBoardView.class.getResourceAsStream("WhiteQueenOnBlack.png"));
        Image BlackKingOnWhite = new Image(ChessBoardView.class.getResourceAsStream("BlackKingOnWhite.png"));
        Image BlackKingOnBlack = new Image(ChessBoardView.class.getResourceAsStream("BlackKingOnBlack.png"));
        Image WhiteKingOnWhite = new Image(ChessBoardView.class.getResourceAsStream("WhiteKingOnWhite.png"));
        Image WhiteKingOnBlack = new Image(ChessBoardView.class.getResourceAsStream("WhiteKingOnBlack.png"));
        Image EmptyWhite = new Image(ChessBoardView.class.getResourceAsStream("emptyWhite.png"));
        Image EmptyBlack = new Image(ChessBoardView.class.getResourceAsStream("emptyBlack.png"));

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



}
