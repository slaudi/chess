package chess.pieces;

import chess.game.Colour;
import chess.game.Player;
import chess.game.Square;
import chess.game.Type;

public class Bishop extends Piece {

    public Type type;

    /**
     * Constructor for a Bishop
     *
     * @param position the location of the Bishop
     * @param player the Player object associated with the Bishop
     */
    public Bishop(int position, Player player) {
        super(position, player);
        type = Type.BISHOP;
    }

    @Override
    public Type getType() {
        return Type.BISHOP;
    }

    @Override
    public Colour getColour() {
        return null;
    }

    /**
     * A function which determines if the Bishop is moving diagonally
     * @param finalPosition the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean allowedMove(int finalPosition) {
        int diffPosition = Math.abs(finalPosition - this.position);
        return false;
    }

    @Override
    public Square drawMove(int firstSquare, int finalSquare) {
        return null;
    }
}
