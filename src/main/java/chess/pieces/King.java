package chess.pieces;
import chess.game.*;

public class King extends Piece {

    public Type type;

    /**
     * Constructor for a King
     *
     * @param position the King's position
     * @param player the Player object associated with the King
     */
    public King(int position, Player player){
        super(position, player);
        type = Type.KING;
    }

    @Override
    public Type getType() {
        return Type.KING;
    }

    @Override
    public Colour getColour() {
        return null;
    }

    /**
     * A function which determines if the King is moving only one square
     *
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

    public boolean isChecked() {
        return false;
    }

    public boolean isCheckMate() {
        return false;
    }

    public boolean hasMoved() {
        return false;
    }

    

}
