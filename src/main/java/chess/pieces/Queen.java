package chess.pieces;

import chess.game.Colour;
import chess.game.Player;
import chess.game.Square;
import chess.game.Type;

public class Queen extends Piece {

    public Type type;

    /**
     * Constructor for a Queen
     * @param position the Queen's position
     * @param player the Player object associated with the King
     */
    public Queen(int position, Player player) {
        super(position, player);
        type = Type.QUEEN;
    }

    @Override
    public Type getType() {
        return Type.QUEEN;
    }

    @Override
    public Colour getColour() {
        return null;
    }

    /**
     * A function which determines if the Queen is moving...
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
