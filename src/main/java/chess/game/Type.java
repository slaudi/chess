package chess.game;

/**
 * enum Type: Type of Chess-Piece, e.g. King, Queen, Bishop, Knight, Rook, Pawn.
 */
enum Type {
    KING,
    QUEEN,
    BISHOP,
    KNIGHT,
    PAWN,
    ROOK;

    /**
     * Returns Type of Chess-Piece
     *
     * @return Type of Chess-Piece as String
     */
    public String toString() {
        return this;
    }
}
