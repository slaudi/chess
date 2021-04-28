package chess.pieces;

import chess.game.Colour;
import chess.game.Player;
import chess.game.Square;
import chess.game.Type;

public class Knight extends Piece {

    public Type type;

    /**
     * Constructor for a Knight
     *
     * @param position the location of the Knight
     * @param player the Player object associated with the Knight
     */
    public Knight(int position, Player player) {
        super(position, player);
        type = Type.KNIGHT;
    }

    @Override
    public Type getType() {
        return Type.KNIGHT;
    }

    @Override
    public Colour getColour() {
        return null;
    }

    /**
     * A function which determines if the Knight is moving...
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
