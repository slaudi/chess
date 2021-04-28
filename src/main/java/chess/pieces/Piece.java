package chess.pieces;

import chess.game.Colour;
import chess.game.Player;
import chess.game.Square;
import chess.game.Type;

public abstract class Piece {

    public int x, y;
    public Player player;

    /**
     * Constructor for a Piece
     * @param x the x location of the Piece
     * @param y the y location of the Piece
     * @param player   the Player object associated with the Piece
     */
    public Piece(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public abstract Type getType();

    public abstract Colour getColour();

    /**
     * Determines if a move is valid based on the type of the Piece
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    public abstract boolean allowedMove(int finalSquare);

    public abstract Square drawMove(int firstSquare, int finalSquare);

}
