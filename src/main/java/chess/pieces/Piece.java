package chess.pieces;

import chess.game.Colour;
import chess.game.Player;
import chess.game.Square;
import chess.game.Type;

public abstract class Piece {

    public int x, y;
    public Colour colour;

    /**
     * Constructor for a Piece
     *
     * @param x the x location of the Piece
     * @param y the y location of the Piece
     * @param colour the Colour object associated with the Piece
     */
    public Piece(int x, int y, Colour colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    public abstract Type getType();

    public abstract Colour getColour();

    /**
     * Determines if a move is valid based on the type of the Piece
     *
     * @param last_x the final x location
     * @param last_y the final y location
     * @return a boolean indicating if the move is allowed
     */
    public abstract boolean isAllowedMove(int last_x, int last_y);
    // kein Piece darf ziehen, wenn der eigene King im Angriff steht oder dadurch einem Angriff ausgesetzt wird

    /**
     * Draws a path of a move based on the type of the Piece and stores it
     * to later determine if another piece is in it's path
     *
     * @param first_x   the first x position
     * @param first_y   the first y position
     * @param last_x   the final x position
     * @param last_y   the final y position
     * @return a Square array of the path
     */
    public abstract int[][] drawMove(int first_x, int first_y, int last_x, int last_y);

}
