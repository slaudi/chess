package chess.pieces;

import chess.game.Colour;
import chess.game.Type;

public class Queen extends Piece {
    public Type type;

    /**
     * Constructor for a Queen
     *
     * @param colour The Colour of the Piece
     */
    public Queen(Colour colour) {
        super(colour);
        this.type = Type.QUEEN;
    }
    public String toString(){
        if (this.colour == Colour.WHITE){
            return "Q";
        }
        else{
            return "q";
        }
    }
}
