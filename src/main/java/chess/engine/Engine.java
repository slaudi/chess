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

    private static int boardValueAfterMove = 0;

    /**
     *
     * @param game current game state
     * @return Move which is estimated best
     */
    public static Move nextBestMove(Game game) {
        Colour colourAI = Colour.BLACK;
        if(game.getUserColour() == Colour.BLACK){
            colourAI = Colour.WHITE;
        }

        int max = -100000;
        List<Move> moveCollection = new ArrayList<>();
        List<Piece> ownPieceCollection = new ArrayList<>();
        List<Move> maxValueMoves = new ArrayList<>();
        List<Move> dirtyMovesToRemove = new ArrayList<>();

        addToCollections(moveCollection, ownPieceCollection, game);

        evaluateEachMove(moveCollection, dirtyMovesToRemove, game, colourAI);

        for (Move dirty : dirtyMovesToRemove){
            moveCollection.remove(dirty);
        }
        if(moveCollection.isEmpty()){
            return null;
        }

        for (Move move : moveCollection){
            if(boardValueAfterMove > max){
                max = boardValueAfterMove;
            }
        }

        for (Move move : moveCollection) {
            if (boardValueAfterMove == max) {
                maxValueMoves.add(move);
            }
        }

        int random = (int)(Math.random() * maxValueMoves.size());
        return maxValueMoves.get(random);
    }

    private static void addToCollections(List<Move> moveCollection, List<Piece> ownPieceCollection, Game game) {
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
    }

    private static void evaluateEachMove(List<Move> moveCollection, List<Move> dirtyMovesToRemove, Game game, Colour colourAI){
        for (Move move : moveCollection){
            Piece temporaryBeatenPiece = game.chessBoard.getPieceAt(move.getFinalSquare().getX(), move.getFinalSquare().getY());
            Game gameAfterOneMove = new Game();
            gameAfterOneMove.chessBoard = game.chessBoard;
            move.doMove(gameAfterOneMove.chessBoard);
            boardValueAfterMove = EvaluatePieces.evaluateBoard(gameAfterOneMove, colourAI);

            gameAfterOneMove.changePlayer();
            if(gameAfterOneMove.isInCheck()){
                dirtyMovesToRemove.add(move);
            }
            gameAfterOneMove.changePlayer();

            if (temporaryBeatenPiece == null){
                move.undoMove(gameAfterOneMove.chessBoard);
            }
            else {
                move.undoMove(temporaryBeatenPiece, gameAfterOneMove.chessBoard);
            }
        }
    }

}

