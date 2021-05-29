package chess.engine;

import chess.game.Game;
import chess.game.Move;

/**
 *
 */
public class Engine {

    private MinimaxAlgorithm minimax;

    /**
     *
     * @param game
     */
    public void nextMove(Game game) {
        Move nextMove = minimax.computeBestMove(game);
        nextMove.doMove(game.chessBoard);
    }


}

