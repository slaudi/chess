package chess.engine;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Move;
import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Engine {

    /**
     *
     * @param game
     * @return
     */
    public static Move nextBestMove(Game game) {
        Colour colourAI = Colour.BLACK;
        if(game.userColour == Colour.BLACK){
            colourAI = Colour.WHITE;
        }
        int max = -1000;
        List<Move> moveCollection = new ArrayList<>();
        List<Piece> ownPieceCollection = new ArrayList<>();
        List<Move> maxValueMoves = new ArrayList<>();
        List<Move> dirtyMoveCollection = new ArrayList<>();

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
            Piece temporaryBeatenPiece = game.chessBoard.getPieceAt(move.getFinalSquare().getX(), move.getFinalSquare().getY());
            Game gameAfterOneMove = new Game();
            gameAfterOneMove.chessBoard = game.chessBoard;
            move.doMove(gameAfterOneMove.chessBoard);
            move.boardValueAfterMove = EvaluatePieces.evaluateBoard(gameAfterOneMove, colourAI);

            System.out.println(move.boardValueAfterMove);

            gameAfterOneMove.changePlayer();
            if(gameAfterOneMove.isInCheck()){
                dirtyMoveCollection.add(move);
            }
            gameAfterOneMove.changePlayer();

            if (temporaryBeatenPiece == null){
                move.undoMove(gameAfterOneMove.chessBoard);
            }
            else {
                move.undoMove(temporaryBeatenPiece, gameAfterOneMove.chessBoard);
            }
        }

        for (Move dirty : dirtyMoveCollection){
            moveCollection.remove(dirty);
        }
        if(moveCollection.isEmpty()){
            return null;
        }

        for (Move move : moveCollection){
            if(move.boardValueAfterMove > max){
                max = move.boardValueAfterMove;
            }
        }

        for (Move move : moveCollection) {
            if (move.boardValueAfterMove == max) {
                maxValueMoves.add(move);
            }
        }

        System.out.println("Max: " + max);
        System.out.println("Size: " + maxValueMoves.size());
        System.out.println("maxValueMoves: "+maxValueMoves);

        int r = (int)(Math.random() * maxValueMoves.size());
        return maxValueMoves.get(r);
    }


}

