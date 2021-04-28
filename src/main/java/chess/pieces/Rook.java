package chess.pieces;

import chess.game.Colour;
import chess.game.Player;
import chess.game.Square;
import chess.game.Type;

public class Rook extends Piece {

    public Type type;

    /**
     * Constructor for a Rook
     * @param position the location of the Rook
     * @param player the Player object associated with the Rook
     */
    public Rook(int position, Player player) {
        super(position, player);
        type = Type.ROOK;
    }

    @Override
    public Type getType() {
        return Type.ROOK;
    }

    @Override
    public Colour getColour() {
        return null;
    }

    /**
     * A function which determines if the Rook is moving...
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
}
