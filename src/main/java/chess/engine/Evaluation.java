package chess.engine;


/**
 * Evaluates the status of the game on the basis of the Pieces on the board.
 */
public interface Evaluation {

    int highestScore = Integer.MAX_VALUE;

    int lowestScore = Integer.MIN_VALUE;

    int balancedScore = 0;

}
