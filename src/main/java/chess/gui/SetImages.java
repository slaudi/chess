package chess.gui;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;
import javafx.scene.image.ImageView;

/**
 *
 */
public class SetImages {

    static ImageView getBeatenPieces(Type type, Colour colour) {

        int height = 40;

        return setImage(colour, type, height);
    }


    static ImageView chooseImage(Square square) {

        int height = 60;

        if (square.getOccupiedBy() == null) {
            return null;
        } else {
            Type type = square.getOccupiedBy().getType();
            Colour colour = square.getOccupiedBy().getColour();
            return setImage(colour, type, height);
        }
    }

    private static ImageView setImage(Colour colour, Type type, int height) {

        if (colour == Colour.WHITE) {
            if (type == Type.PAWN) {
                return getImage("WhitePawn",height);
            } else if (type == Type.ROOK) {
                return getImage("WhiteRook",height);
            } else if (type == Type.KNIGHT) {
                return getImage("WhiteKnight",height);
            } else if (type == Type.BISHOP) {
                return getImage("WhiteBishop",height);
            } else if (type == Type.QUEEN) {
                return getImage("WhiteQueen",height);
            } else if (type == Type.KING) {
                return getImage("WhiteKing",height);
            }
        } else {
            if (type == Type.PAWN) {
                return getImage("BlackPawn",height);
            } else if (type == Type.ROOK) {
                return getImage("BlackRook",height);
            } else if (type == Type.KNIGHT) {
                return getImage("BlackKnight",height);
            } else if (type == Type.BISHOP) {
                return getImage("BlackBishop",height);
            } else if (type == Type.QUEEN) {
                return getImage("BlackQueen",height);
            } else if (type == Type.KING) {
                return getImage("BlackKing",height);
            }
        }
        return null;
    }

    private static ImageView getImage(String name, int height) {
        ImageView pieceView = new ImageView();
        pieceView.setFitHeight(height);
        pieceView.setFitWidth(height);
        pieceView.setImage(ImageHandler.getInstance().getImage(name));

        return pieceView;
    }



}