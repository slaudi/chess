package chess.pieces;

import chess.game.Colour;
import chess.game.Type;

public class Rook extends Piece {
    public Type type;

    /**
     * Constructor for a Rook
     *
     * @param colour The Colour of the Piece
     */
    public Rook(Colour colour) {
        super(colour);
        this.type = Type.ROOK;
    }
    public String toString(){
        if (this.colour == Colour.WHITE){
            return "R";
        }
        else{
            return "r";
        }
    }
}
