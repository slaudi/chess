package chess.engine;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Type;
import chess.pieces.Piece;


/**
 * Evaluates the value of the Pieces on the board.
 */
public class EvaluatePieces {

    /**
     *
     * @param game current game
     * @param AIColour colour of AI
     * @return integer-value of current board
     */
    static int evaluateBoard(Game game, Colour AIColour) {
        boolean endgame = evaluateEndgame(game, AIColour);
        int sum = 0;
        for (int y = 0; y < game.chessBoard.getHeight(); y++) {
            for (int x = 0; x < game.chessBoard.getWidth(); x++) {
                Piece piece = game.chessBoard.getPieceAt(x,y);
                if (piece != null) {
                    int value = piecesValue(piece);
                    int controlledSquares = pieceControlsSquares(piece, game);
                    int positionalValue = piece.getPositionalValue(piece.getSquare().getX(), piece.getSquare().getY(), endgame);
                    if (piece.getColour() == AIColour) {
                        sum += value;
                        sum += controlledSquares;
                        sum += positionalValue;
                    } else {
                        sum -= value;
                        sum -= controlledSquares;
                        sum -= positionalValue;
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
    static int piecesValue(Piece piece){
        if(piece != null) {
            switch (piece.getType()) {
                case PAWN:
                    return 100;
                case KNIGHT:
                case BISHOP:
                    return 300;
                case ROOK:
                    return 500;
                case QUEEN:
                    return 900;
                case KING:
                    return 10000;
            }
        }
        return 0;
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

    /**
     * @param game current game
     * @param AIColour current AIColour
     */
    private static boolean evaluateEndgame(Game game, Colour AIColour){
        int endgameSum = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = game.chessBoard.getPieceAt(x, y);
                if (piece != null && piece.getColour() == AIColour && piece.getType() != Type.KING) {
                    endgameSum += piecesValue(piece);
                }
            }
        }
        return endgameSum < 1300;
    }

}
