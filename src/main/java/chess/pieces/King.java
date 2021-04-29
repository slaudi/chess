package chess.pieces;

import chess.game.Colour;
import chess.game.Type;

public class King extends Piece {
    public Type type;

    /**
     * Constructor for a King
     *
     * @param colour The Colour of the Piece
     */
    public King(Colour colour) {
        super(colour);
        this.type = Type.KING;
    }
    public String toString(){
        if (this.colour == Colour.WHITE){
            return "K";
        }
        else{
            return "k";
        }
    }
}
