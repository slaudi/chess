package chess.pieces;

import chess.game.Colour;
import chess.game.Type;

public class Bishop extends Piece {
    public Type type;

    /**
     * Constructor for a Bishop
     *
     * @param colour The Colour of the Piece
     */
    public Bishop(Colour colour) {
        super(colour);
        this.type = Type.BISHOP;
    }
    public String toString(){
        if (this.colour == Colour.WHITE){
            return "B";
        }
        else{
            return "b";
        }
    }
}
