package chess.engine;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Game;
import chess.pieces.Piece;

/**
 * Evaluates the status of the game on the basis of the Pieces on the board.
 */
public interface Evaluation {

    int highestScore = Integer.MAX_VALUE;

    int lowestScore = Integer.MIN_VALUE;

    int balancedScore = 0;


}
