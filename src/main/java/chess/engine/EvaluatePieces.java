package chess.engine;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Game;
import chess.game.Move;
import chess.pieces.Piece;

import java.util.ArrayList;

/**
 * Evaluates the value of the Pieces on the board.
 */
public class EvaluatePieces implements Evaluation{



    public static int evaluateBoard(Game game, Colour playerColour) {
        int sum = 0;
        for (int y = 0; y < game.chessBoard.getHeight(); y++) {
            for (int x = 0; x < game.chessBoard.getWidth(); x++) {
                Piece piece = game.chessBoard.getPieceAt(x,y);
                if (piece != null) {
                    int value = piecesValue(piece);
                    int controledSquares = pieceControlsSquares(piece, game);
                    if (piece.getColour() == playerColour) {
                        sum += value;
                        sum += controledSquares;
                    } else {
                        sum -= value;
                        sum -= controledSquares;
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

    public static int pieceControlsSquares(Piece piece, Game game){
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

    public static Move nextBestMove(Game game) {
        int max = 0;
        ArrayList<Move> moveCollection = new ArrayList<>();
        ArrayList<Piece> ownPieceCollection = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (game.chessBoard.getSquareAt(x, y).getOccupiedBy() != null && game.chessBoard.getSquareAt(x, y).getOccupiedBy().getColour() == game.currentPlayer.getColour()) {
                    ownPieceCollection.add(game.chessBoard.getSquareAt(x, y).getOccupiedBy());
                }
            }
        }
        for (Piece piece : ownPieceCollection) {
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    if(game.isMoveAllowed(piece, game.chessBoard.getSquareAt(x, y))){
                        moveCollection.add(new Move(piece.getSquare(), game.chessBoard.getSquareAt(x, y)));
                    }
                }
            }
        }
        for (Move move : moveCollection){
            final Game gameAfterOneMove = game;
            move.doMove(gameAfterOneMove.chessBoard);
            move.boardValueAfterMove = evaluateBoard(gameAfterOneMove, gameAfterOneMove.currentPlayer.getColour());
        }
        for (Move move : moveCollection){
            if(move.boardValueAfterMove > max){
                max = move.boardValueAfterMove;
            }
        }
        for (int i = 0; i < moveCollection.size(); i++){
            if(moveCollection.get(i).boardValueAfterMove == max){
                return moveCollection.get(i);
            }
        }
        return moveCollection.get(0);
    }
}
