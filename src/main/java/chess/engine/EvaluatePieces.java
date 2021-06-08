package chess.engine;

import chess.game.Colour;
import chess.game.Game;
import chess.pieces.Piece;


/**
 * Evaluates the value of the Pieces on the board.
 */
public class EvaluatePieces {

    /**
     *
     * @param game
     * @param playerColour
     * @return
     */
    public static int evaluateBoard(Game game, Colour playerColour) {
        int sum = 0;
        for (int y = 0; y < game.chessBoard.getHeight(); y++) {
            for (int x = 0; x < game.chessBoard.getWidth(); x++) {
                Piece piece = game.chessBoard.getPieceAt(x,y);
                if (piece != null) {
                    int value = piecesValue(piece);
                    int controlledSquares = pieceControlsSquares(piece, game);
                    int positionalValue = piece.getPositionalValue(piece.getSquare().getX(), piece.getSquare().getY());
                    if (piece.getColour() == playerColour) {
                        sum += value;
                        sum += controlledSquares;
                        sum += positionalValue;
                    } else {
                        sum -= value;
                        sum -= controlledSquares;
                    }
                }
            }
        }
        if(game.isInCheck()){
            sum += 100;
        }
        return sum;
    }

    /**
     * Value of the different Pieces.
     *
     * @param piece The Piece being evaluated.
     * @return int The value of the Piece.
     */
    private static int piecesValue(Piece piece){
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

    private static int pieceControlsSquares(Piece piece, Game game){
        int squareCount = 0;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                if(game.isMoveAllowed(piece, game.chessBoard.getSquareAt(x, y))){
                    squareCount++;
                }
            }
        }
        return squareCount;
    }

}
