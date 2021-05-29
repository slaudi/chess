package chess.engine;

import chess.game.*;
import chess.pieces.Piece;

import java.util.List;

/**
 *
 */
public class MinimaxAlgorithm {

    private int searchDepth;
    private List<Square> possibleSquares;
    private List<Piece> alliedPieces;
    Game tempGame;

    /**
     *
     * @param searchDepth
     */
    public void setSearchDepth(int searchDepth) {
        this.searchDepth = searchDepth;
    }


    /**
     *
      * @param game
     * @return
     */
    public Move computeBestMove(Game game) {

        Colour currentColour = game.currentPlayer.getColour();
        this.alliedPieces = game.currentPlayer.getAlliedPieces(game.beatenPieces, game.chessBoard);
        int highestValue = Evaluation.lowestScore;
        Move bestMove = null;

        for (Piece ally : alliedPieces) {
            possibleSquares = game.computePossibleSquares();
            for (Square square : possibleSquares) {
                tempGame = game;
                tempGame.processMove(ally.getSquare(), square, ' ');

                int value = recursiveBoardEvaluation(tempGame, currentColour, this.searchDepth);
                if (value > highestValue) {
                    highestValue = value;
                    bestMove = new Move(ally.getSquare(), square);
                }
                }
            }
        return bestMove;
    }


    /**
     *
     * @param game
     * @param currentColour
     * @param currentDepth
     * @return
     */
    int recursiveBoardEvaluation(Game game, Colour currentColour, int currentDepth) {
        if (currentDepth == 0) {
            return EvaluatePieces.evaluateBoard(game, currentColour);
        }

        Game newGame;
        if (currentDepth % 2 == 0) {
            int maxValue = Evaluation.lowestScore;
            this.alliedPieces = game.currentPlayer.getAlliedPieces(game.beatenPieces, game.chessBoard);
            for (Piece ally : alliedPieces) {
                possibleSquares = game.computePossibleSquares();
                for (Square square : possibleSquares) {
                        newGame = game;
                        newGame.processMove(ally.getSquare(), square, ' ');

                        int value = recursiveBoardEvaluation(newGame, currentColour, currentDepth - 1);
                        if (value > maxValue) {
                            maxValue = value;
                        }
                    }
                }
            return maxValue;

        } else {
            int minValue = Evaluation.highestScore;
            this.alliedPieces = game.currentPlayer.getAlliedPieces(game.beatenPieces, game.chessBoard);

            for (Piece ally : alliedPieces) {
                possibleSquares = game.computePossibleSquares();
                for (Square square : possibleSquares) {
                        newGame = game;
                        newGame.processMove(ally.getSquare(), square, ' ');

                        int value = recursiveBoardEvaluation(newGame, currentColour, currentDepth - 1);
                        if (value < minValue) {
                            minValue = value;
                        }
                    }
                }
            return minValue;
        }
    }

}
