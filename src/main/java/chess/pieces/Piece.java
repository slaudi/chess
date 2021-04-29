package chess.pieces;

import chess.game.Colour;

public abstract class Piece {
    public Colour colour;

    /**
     * Constructor for a Piece
     *
     * @param colour The Colour of the Piece
     */
    public Piece(Colour colour) {
        this.colour = colour;
    }
}
