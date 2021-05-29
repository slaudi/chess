package chess.engine;

import chess.game.*;
import chess.pieces.Piece;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MinimaxAlgorithm implements Serializable {

    private int searchDepth;
    Evaluation evaluation;
    private List<Square> possibleSquares = new ArrayList<>();
    private List<Piece> alliedPieces;

    /**
     *
     * @param searchDepth
     */
    public void setSearchDepth(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    /**
     *
     * @param possibleSquares
     */
    public void setPossibleSquares(List<Square> possibleSquares) {
        this.possibleSquares = possibleSquares;
    }

    public Move computeBestMove(Game game) {

        Colour currentColour = game.currentPlayer.getColour();
        this.alliedPieces = game.currentPlayer.getAlliedPieces(game.beatenPieces, game.chessBoard);
        int highestValue = Evaluation.lowestScore;
        Move bestMove = null;

        for (Piece ally : alliedPieces) {
            for (Square square : possibleSquares) {
                if(game.isMoveAllowed(ally, square)) {
                    Game newGame = game;
                    newGame.processMove(ally.getSquare(), square, ' ');

                    int value = recursiveBoardEvaluation(newGame, currentColour, searchDepth);

                    if (value > highestValue) {
                        highestValue = value;
                        bestMove = new Move(ally.getSquare(), square);
                    }
                }
            }
        }
        return bestMove;
    }


    int recursiveBoardEvaluation(Game game, Colour currentColour, int currentDepth) {
        if (currentDepth == 0) {
            return evaluation.evaluateBoard(game.chessBoard, currentColour);
        }

        if (currentDepth % 2 == 0) {
            int maxValue = Evaluation.lowestScore;
            this.alliedPieces = game.currentPlayer.getAlliedPieces(game.beatenPieces, game.chessBoard);
            for (Piece ally : alliedPieces) {
                for (Square square : possibleSquares) {
                    if (game.isMoveAllowed(ally, square)) {
                        Game newGame = game;
                        newGame.processMove(ally.getSquare(), square, ' ');

                        int value = recursiveBoardEvaluation(newGame, currentColour, currentDepth - 1);
                        if (value > maxValue) {
                            maxValue = value;
                        }
                    }
                }
            }
        return maxValue;

        } else {
            int minValue = Evaluation.highestScore;
            this.alliedPieces = game.currentPlayer.getAlliedPieces(game.beatenPieces, game.chessBoard);

            for (Piece ally : alliedPieces) {
                for (Square square : possibleSquares) {
                    if (game.isMoveAllowed(ally, square)) {
                        Game newGame = game;
                        newGame.processMove(ally.getSquare(), square, ' ');

                        int value = recursiveBoardEvaluation(newGame, currentColour, currentDepth - 1);
                        if (value < minValue) {
                            minValue = value;
                        }
                    }
                }
            }
            return minValue;
        }
    }

    private Board copyBoard(Board board) {
        Board newBoard = new Board(8,8);
        newBoard = new Square[8][8];
        for(byte c = 0; c < 8; c++)
            for(byte r = 0; r < 8; r++)
                newBoard.getBoard()[c][r] = board.getBoard()[c][r];

        return newBoard;
    }


}
