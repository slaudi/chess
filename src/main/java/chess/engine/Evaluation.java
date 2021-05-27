package chess.engine;

import chess.game.Board;
import chess.game.Colour;

/**
 * Evaluates the status of the game on the basis of the Pieces on the board.
 */
public interface Evaluation {

    int bestScore = Integer.MAX_VALUE;

    int worstScore = Integer.MIN_VALUE;

    int balancedScore = 0;

    /**
     * Evaluates the current board based on the Pieces on it from the point of view
     * of the passed players colour.
     *
      * @param currentBoard The current Board.
     * @param playerColour  The colour of the Player who wants to evaluate the Board.
     * @return int  Returns an int representing the value of the current board. The higher the
     *              better for the player of the passed colour.
     */
    int evaluateBoard(Board currentBoard, Colour playerColour);
}
