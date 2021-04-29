package chess.pieces;

import chess.game.Colour;
import chess.game.Type;

public class Knight extends Piece {
    public Type type;

    /**
     * Constructor for a Knight
     *
     * @param colour The Colour of the Piece
     */
    public Knight(Colour colour) {
        super(colour);
        this.type = Type.KNIGHT;
    }
    public String toString(){
        if (this.colour == Colour.WHITE){
            return "N";
        }
        else{
            return "n";
        }
    }
}
