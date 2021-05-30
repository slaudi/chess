package chess.gui;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class SetImages {

    static Image BlackPawn = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackPawn.png")));
    static Image WhitePawn = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhitePawn.png")));
    static Image BlackRook = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackRook.png")));
    static Image WhiteRook = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteRook.png")));
    static Image BlackKnight = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKnight.png")));
    static Image WhiteKnight = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKnight.png")));
    static Image BlackBishop = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackBishop.png")));
    static Image WhiteBishop = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteBishop.png")));
    static Image BlackQueen = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackQueen.png")));
    static Image WhiteQueen = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteQueen.png")));
    static Image BlackKing = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("BlackKing.png")));
    static Image WhiteKing = new Image(Objects.requireNonNull(ChessBoardView.class.getResourceAsStream("WhiteKing.png")));


    static ImageView chooseImage(Type type, Colour colour) {

        ImageView blackPawnViewMini = new ImageView();
        blackPawnViewMini.setFitHeight(40);
        blackPawnViewMini.setFitWidth(40);
        blackPawnViewMini.setImage(BlackPawn);

        ImageView whitePawnViewMini = new ImageView();
        whitePawnViewMini.setFitHeight(40);
        whitePawnViewMini.setFitWidth(40);
        whitePawnViewMini.setImage(WhitePawn);

        ImageView blackRookViewMini = new ImageView();
        blackRookViewMini.setFitHeight(40);
        blackRookViewMini.setFitWidth(40);
        blackRookViewMini.setImage(BlackRook);

        ImageView whiteRookViewMini = new ImageView();
        whiteRookViewMini.setFitHeight(40);
        whiteRookViewMini.setFitWidth(40);
        whiteRookViewMini.setImage(WhiteRook);

        ImageView blackKnightViewMini = new ImageView();
        blackKnightViewMini.setFitHeight(40);
        blackKnightViewMini.setFitWidth(40);
        blackKnightViewMini.setImage(BlackKnight);

        ImageView whiteKnightViewMini = new ImageView();
        whiteKnightViewMini.setFitHeight(40);
        whiteKnightViewMini.setFitWidth(40);
        whiteKnightViewMini.setImage(WhiteKnight);

        ImageView blackBishopViewMini = new ImageView();
        blackBishopViewMini.setFitHeight(40);
        blackBishopViewMini.setFitWidth(40);
        blackBishopViewMini.setImage(BlackBishop);

        ImageView whiteBishopViewMini = new ImageView();
        whiteBishopViewMini.setFitHeight(40);
        whiteBishopViewMini.setFitWidth(40);
        whiteBishopViewMini.setImage(WhiteBishop);

        ImageView blackQueenViewMini = new ImageView();
        blackQueenViewMini.setFitHeight(40);
        blackQueenViewMini.setFitWidth(40);
        blackQueenViewMini.setImage(BlackQueen);

        ImageView whiteQueenViewMini = new ImageView();
        whiteQueenViewMini.setFitHeight(40);
        whiteQueenViewMini.setFitWidth(40);
        whiteQueenViewMini.setImage(WhiteQueen);

        ImageView blackKingViewMini = new ImageView();
        blackKingViewMini.setFitHeight(40);
        blackKingViewMini.setFitWidth(40);
        blackKingViewMini.setImage(BlackKing);

        ImageView whiteKingViewMini = new ImageView();
        whiteKingViewMini.setFitHeight(40);
        whiteKingViewMini.setFitWidth(40);
        whiteKingViewMini.setImage(WhiteKing);

        if (colour == Colour.WHITE) {
            if (type == Type.PAWN) {
                return whitePawnViewMini;
            } else if (type == Type.ROOK) {
                return whiteRookViewMini;
            } else if (type == Type.KNIGHT) {
                return whiteKnightViewMini;
            } else if (type == Type.BISHOP) {
                return whiteBishopViewMini;
            } else if (type == Type.QUEEN) {
                return whiteQueenViewMini;
            } else if (type == Type.KING) {
                return whiteKingViewMini;
            }
        } else {
            if (type == Type.PAWN) {
                return blackPawnViewMini;
            } else if (type == Type.ROOK) {
                return blackRookViewMini;
            } else if (type == Type.KNIGHT) {
                return blackKnightViewMini;
            } else if (type == Type.BISHOP) {
                return blackBishopViewMini;
            } else if (type == Type.QUEEN) {
                return blackQueenViewMini;
            } else if (type == Type.KING) {
                return blackKingViewMini;
            }
        }
        return null;
    }

    static ImageView chooseImage(Square square) {

        int imageHeight = 60;
        int imageWidth = 60;

        ImageView blackPawnView = new ImageView();
        blackPawnView.setFitHeight(imageHeight);
        blackPawnView.setFitWidth(imageWidth);
        blackPawnView.setImage(BlackPawn);

        ImageView whitePawnView = new ImageView();
        whitePawnView.setFitHeight(imageHeight);
        whitePawnView.setFitWidth(imageWidth);
        whitePawnView.setImage(WhitePawn);

        ImageView blackRookView = new ImageView();
        blackRookView.setFitHeight(imageHeight);
        blackRookView.setFitWidth(imageWidth);
        blackRookView.setImage(BlackRook);

        ImageView whiteRookView = new ImageView();
        whiteRookView.setFitHeight(imageHeight);
        whiteRookView.setFitWidth(imageWidth);
        whiteRookView.setImage(WhiteRook);

        ImageView blackKnightView = new ImageView();
        blackKnightView.setFitHeight(imageHeight);
        blackKnightView.setFitWidth(imageWidth);
        blackKnightView.setImage(BlackKnight);

        ImageView whiteKnightView = new ImageView();
        whiteKnightView.setFitHeight(imageHeight);
        whiteKnightView.setFitWidth(imageWidth);
        whiteKnightView.setImage(WhiteKnight);

        ImageView blackBishopView = new ImageView();
        blackBishopView.setFitHeight(imageHeight);
        blackBishopView.setFitWidth(imageWidth);
        blackBishopView.setImage(BlackBishop);

        ImageView whiteBishopView = new ImageView();
        whiteBishopView.setFitHeight(imageHeight);
        whiteBishopView.setFitWidth(imageWidth);
        whiteBishopView.setImage(WhiteBishop);

        ImageView blackQueenView = new ImageView();
        blackQueenView.setFitHeight(imageHeight);
        blackQueenView.setFitWidth(imageWidth);
        blackQueenView.setImage(BlackQueen);

        ImageView whiteQueenView = new ImageView();
        whiteQueenView.setFitHeight(imageHeight);
        whiteQueenView.setFitWidth(imageWidth);
        whiteQueenView.setImage(WhiteQueen);

        ImageView blackKingView = new ImageView();
        blackKingView.setFitHeight(imageHeight);
        blackKingView.setFitWidth(imageWidth);
        blackKingView.setImage(BlackKing);

        ImageView whiteKingView = new ImageView();
        whiteKingView.setFitHeight(imageHeight);
        whiteKingView.setFitWidth(imageWidth);
        whiteKingView.setImage(WhiteKing);

        if (square.getOccupiedBy() == null) {
            return null;
        }
        if (square.getOccupiedBy().getColour() == Colour.WHITE) {
            if (square.getOccupiedBy().getType() == Type.PAWN) {
                return whitePawnView;
            } else if (square.getOccupiedBy().getType() == Type.ROOK) {
                return whiteRookView;
            } else if (square.getOccupiedBy().getType() == Type.KNIGHT) {
                return whiteKnightView;
            } else if (square.getOccupiedBy().getType() == Type.BISHOP) {
                return whiteBishopView;
            } else if (square.getOccupiedBy().getType() == Type.QUEEN) {
                return whiteQueenView;
            } else if (square.getOccupiedBy().getType() == Type.KING) {
                return whiteKingView;
            }
        } else {
            if (square.getOccupiedBy().getType() == Type.PAWN) {
                return blackPawnView;
            } else if (square.getOccupiedBy().getType() == Type.ROOK) {
                return blackRookView;
            } else if (square.getOccupiedBy().getType() == Type.KNIGHT) {
                return blackKnightView;
            } else if (square.getOccupiedBy().getType() == Type.BISHOP) {
                return blackBishopView;
            } else if (square.getOccupiedBy().getType() == Type.QUEEN) {
                return blackQueenView;
            } else if (square.getOccupiedBy().getType() == Type.KING) {
                return blackKingView;
            }
        }
        return null;
    }

    static void setImage(int width, int height) {

    }

}