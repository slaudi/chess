package chess.engine;

import chess.game.Board;
import chess.game.Colour;
import chess.pieces.Piece;

/**
 * Evaluates the value of the Pieces on the board.
 */
public class EvaluatePieces implements Evaluation{


    @Override
    public int evaluateBoard(Board currentBoard, Colour playerColour) {
        int sum = 0;
        for (int y = 0; y < currentBoard.getHeight(); y++) {
            for (int x = 0; x < currentBoard.getWidth(); x++) {
                Piece piece = currentBoard.getPieceAt(x,y);
                if (piece != null) {
                    int value = piecesValue(piece);
                    if (piece.getColour() == playerColour) {
                        sum += value;
                    } else {
                        sum -= value;
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Value of the different Pieces.
     *
     * @param piece The Piece being evaluated.
     * @return int The value of the Piece.
     */
    private int piecesValue(Piece piece){
        switch (piece.getType()) {
            case PAWN:
                return 1;
            case KNIGHT:
            case BISHOP:
                return 3;
            case ROOK:
                return 5;
            case QUEEN:
                return 9;
            default:
                return 0;
        }
    }

}
