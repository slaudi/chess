package chess.pieces;

import chess.game.Square;

/**
 * The MovingDirection Interface is implemented by Queen, Rook and Bishop to help generate their
 * paths on the board and by King to check the possibility of castling and by Pawn the possibility
 * of moving 2 Square the first time moving.
 */
public interface MovingDirection {

    /**
     * The function is a helper function for 'generatePath()' and computes the direction of the piece,
     * for example up-right: x-direction = 1, y-direction = -1.
     *
     * @param finalSquare The Square the piece wants to move to
     * @return int[][] An 2D-Array with the x-direction first and the y-direction second
     */
    int[][] movingDirection(Square finalSquare);

}
