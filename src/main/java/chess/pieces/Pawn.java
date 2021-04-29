package chess.pieces;

import chess.game.Colour;
import chess.game.Type;

public class Pawn extends Piece {
    public Type type;

    /**
     * Constructor for a Pawn
     *
     * @param colour The Colour of the Piece
     */
    public Pawn(Colour colour) {
        super(colour);
        this.type = Type.PAWN;
    }
    public String toString(){
        if (this.colour == Colour.WHITE){
            return "P";
        }
        else{
            return "p";
        }
    }
}
