package chess.pieces;

import chess.game.Colour;
import chess.game.Player;
import chess.game.Square;
import chess.game.Type;

public class Pawn extends Piece {

    public Type type;

    /**
     * Constructor for a Pawn
     *
     * @param x the x location of the Piece
     * @param y the y location of the Piece
     * @param player the Player object associated with the Pawn
     */
    public Pawn(int x, int y, Player player) {
        super(x, y, player);
        type = Type.PAWN;
    }

    @Override
    public Type getType() {
       return Type.PAWN;
    }

    @Override
    public Colour getColour() {
        return null;
    }

    /**
     * A function which determines if the Pawn is moving one square up or down
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean allowedMove(int finalSquare) {
        return false;
    }

    @Override
    public Square drawMove(int firstSquare, int finalSquare) {
        return null;
    }

    @Override
    public int[][] drawMove(int firstSquare_x, int firstSquare_y, int finalSquare_x, int finalSquare_y) {
        return null;
    }
}
