package chess.game;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Game class which defines and controls the current game.
 */

public class Game {
    public Player playerWhite;
    public Player playerBlack;
    public Board chessBoard;
    public List<Piece> beatenPieces;
    public Player currentPlayer;
    Stack<Move> moveHistory;

    /**
     * Constructor for a Game.
     */
    public Game() {
        this.playerWhite = new Player(Colour.WHITE);
        this.playerBlack = new Player(Colour.BLACK);
        this.currentPlayer = playerWhite;   // White always begins
        this.chessBoard = new Board();
        this.beatenPieces = new ArrayList<>();
        this.moveHistory = new Stack<>();
    }

    /**
     * Evaluates semantically correctness of input move to return if the move is allowed in the current state
     * of the game.
     *
     * @param selectedPiece Piece which Player wants to move.
     * @param finalSquare Square which Player wants his Piece to move to.
     * @return boolean Returns if input move is possible.
     */
    public boolean isMoveAllowed(Piece selectedPiece, Square finalSquare) {//NOPMD all if-clauses are needed to cover all special cases
        if (selectedPiece == null || selectedPiece.getColour() != currentPlayer.getColour()) {
            return false;
        }
        Piece targetPiece = finalSquare.getOccupiedBy();
        if (targetPiece != null) {
            // the final Square is occupied by another piece
            if (selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK
                    && canDoCastling(selectedPiece, targetPiece)) {
                // if move is castling (select your own King and Rook)
                return true;
            } else if (targetPiece.getColour() != selectedPiece.getColour()) {
                // you can't attack your own Pieces
                if (selectedPiece.getType() == Type.PAWN) {
                    // if selected Piece is a Pawn see if it is allowed to capture the enemy Piece
                    return ((Pawn) selectedPiece).canCapture(this.moveHistory);
                } else {
                    return selectedPiece.isPiecesMove(finalSquare) && isPathEmpty(selectedPiece, finalSquare);
                }
            }
        // final square is empty
        } else if (selectedPiece.getType() == Type.PAWN && selectedPiece.isHasMoved()) {
            // is it a Pawn move / is en passant possible
            return ((Pawn) selectedPiece).isEnPassant(finalSquare, this.moveHistory);
        } else {
            return selectedPiece.isPiecesMove(finalSquare) && isPathEmpty(selectedPiece, finalSquare);
        }
        return false;
    }

    /**
     * implementation of movement
     *
     * @param startSquare   Square where movement starts
     * @param finalSquare   Square where movement ends
     * @param key           the potential char for a promotion
     * @return returns opposite of "move is allowed"
     */
    public boolean processMove(Square startSquare, Square finalSquare, char key) {
        Move currentMove = new Move(startSquare, finalSquare);
        Piece selectedPiece = startSquare.getOccupiedBy();
        Piece targetPiece = finalSquare.getOccupiedBy();
        Move lastEnemyMove = this.moveHistory.peek(); // get last Move (of the enemy), but don't remove it

        if (targetPiece != null && selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK) {
            // move is castling, afterwards never in check -> is covered in canDoCastling()
            currentMove.castlingMove(chessBoard);
            targetPiece.setHasMoved(true);
        } else if (selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).isEnPassant(finalSquare, this.moveHistory)) {
            // move is an en passant capture
            currentMove.enPassantMove(lastEnemyMove, chessBoard);
            Piece enemy = lastEnemyMove.movingPiece;
            if (isInCheck()) {
                // King is in check, undo en passant
                currentMove.undoEnPassant(enemy, lastEnemyMove, this.chessBoard);
                return false;
            }
            beatenPieces.add(enemy);
        } else {
            currentMove.doMove(this.chessBoard);
            if (!canMoveStay(targetPiece, currentMove)) {
                // move puts own King in check, undo move
                return false;
            }
            if (selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).promotionPossible(finalSquare)) {
                // move allows promotion
                currentMove.doPromotion(key, chessBoard);
            }
            if (targetPiece != null) {
                // add a beaten piece to the ArrayList
                beatenPieces.add(targetPiece);
            }
        }
        this.moveHistory.add(currentMove);
        selectedPiece.setSquare(finalSquare);
        selectedPiece.setHasMoved(true);
        changePlayer(finalSquare);
        return true;
    }

    /**
     * Evaluates if current state of game is draw
     *
     * @return boolean returns if game is draw or not
     */
    public boolean isADraw() {
        if (!isInCheck()) {
            // King is not in check
            if (!currentPlayer.getAlliedPieces(beatenPieces, chessBoard).isEmpty()) {
                // if ally exists - not a draw
                return false;
            }
            // if King can move it's not a draw
            return !canKingMove();
        }
        // King is in check, but that's not a draw
        return false;
    }

    /**
     * Evaluates if King is able to move safely
     *
     * @return boolean Returns if King is able to make a safe move
     */
    private boolean canKingMove() {
        Square kingSquare = this.chessBoard.getSquareOfKing(currentPlayer.getColour());
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                // looping through board to check if Square is next to King and is not occupied by ally
                if (Piece.isSurroundingSquare(kingSquare, chessBoard.getChessBoard()[i][j])
                        && chessBoard.getChessBoard()[i][j].getOccupiedBy().getColour() != currentPlayer.getColour()) {
                    // if a Square is next to the King is it safe to move there
                    return isSafeSquare(chessBoard.getChessBoard()[i][j]);
                }
            }
        }
        return false;
    }

    /**
     * Evaluates if King is able to safely move to selected square
     * @param finalSquare The Square the King should move to
     * @return boolean Returns 'true' if King is able to move safely
     */
    private boolean isSafeSquare(Square finalSquare) {
        List<Piece> enemies = currentPlayer.getEnemyPieces(beatenPieces, chessBoard);
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.getType() == Type.BISHOP
                    || enemyPiece.getType() == Type.ROOK
                    || enemyPiece.getType() == Type.QUEEN) {
                if (enemyPiece.isPiecesMove(finalSquare)
                        && isPathEmpty(enemyPiece, chessBoard.getSquareOfKing(currentPlayer.getColour()))) {
                    return false;
                }
            } else if (enemyPiece.getType() == Type.KNIGHT || enemyPiece.getType() == Type.KING) {
                if (enemyPiece.isPiecesMove(finalSquare)) {
                    return false;
                }
            } else {
                if (enemyPiece.getType() == Type.PAWN) {
                    if (((Pawn) enemyPiece).canCapture(this.moveHistory)) {//NOPMD return of 'true' would break the for-loop
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Evaluates if direct path from one square to another is empty
     *
     * @param piece Piece which has to move
     * @param finalSquare Square where piece has to go to
     * @return returns if selected path is empty
     */
    private boolean isPathEmpty (Piece piece, Square finalSquare){
        if (Piece.isSurroundingSquare(piece.getSquare(), finalSquare)) {
            return finalSquare.getOccupiedBy() == null || finalSquare.getOccupiedBy().getColour() != currentPlayer.getColour();
        }
        List<Square> path = piece.generatePath(finalSquare);
        if (piece.getType() == Type.BISHOP && path.isEmpty()) {
            // Knight can leap
            return true;
        } else if (piece.getType() == Type.KING || piece.getType() == Type.PAWN) {
            // King and Pawn don't have a path
            return false;
        } else {
            for (Square visitedSquare : path) {
                if (visitedSquare.getOccupiedBy() != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Evaluates if current Players King is in check and sets Value
     *
     * @return returns checkStatus
     */
    private boolean isInCheck(){
        // TODO: ein Piece einem enemy in den Weg stellen funktioniert noch nicht bei check!?
        Square squareKing = this.chessBoard.getSquareOfKing(currentPlayer.getColour());
        List<Piece> enemies = currentPlayer.getEnemyPieces(beatenPieces, chessBoard);
        for (Piece enemyPiece : enemies) {
            if (enemyPiece instanceof Pawn) {
                return ((Pawn)enemyPiece).canCapture(this.moveHistory);
            } else if (enemyPiece.isPiecesMove(squareKing)
                    && isPathEmpty(enemyPiece, squareKing)) {
                currentPlayer.setInCheck(true);
                return true;
            }
        }
        currentPlayer.setInCheck(false);
        return false;
    }

    /**
     * Evaluates if current Players King is in check and if he is able to avoid it and sets Value
     *
     * @return boolean returns if Player is checkMate or not
     */
    private boolean isCheckMate(Square finalSquare) {
        if (currentPlayer.isInCheck()) {
            if (canKingMove()  && isSafeSquare(finalSquare)) {
                return false;
            }
            // can ally defend the King if the King can't move
            List<Piece> enemies = currentPlayer.getEnemyPieces(beatenPieces, chessBoard);
            for (Piece enemyPiece : enemies) {
                if (isMoveAllowed(enemyPiece, chessBoard.getSquareOfKing(currentPlayer.getColour()))) {
                    return !canDefendKing(enemyPiece);
                }
            }
            // cannot move and ally can't defend -> checkmate
            currentPlayer.setLoser(true);
            return true;
        } else {
            // King is not in check
            return false;
        }
    }

    /**
     * Evaluates if one of the players Pieces is able to block the attack of an enemy
     * @param enemyPiece The Piece attacking the King
     * @return boolean Returns 'true' if Player is able to avoid check by using another Piece
     */
    private boolean canDefendKing(Piece enemyPiece) {
        List<Piece> allies = currentPlayer.getAlliedPieces(beatenPieces, chessBoard);
        for (Piece alliedPiece : allies) {
            if (isMoveAllowed(alliedPiece, enemyPiece.getSquare())) {
                return true;
            } else {
                List<Square> enemyPath = enemyPiece.generatePath(enemyPiece.getSquare());
                if (enemyPath.isEmpty()) {
                    return false;
                }
                for (Square end : enemyPath) {
                    if (isMoveAllowed(alliedPiece, end)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Evaluates if the input castling move is possible by checking the selected King and Rook
     * @param selectedPiece The King of the Player, selected first
     * @param targetPiece The selected Rook, which can be kingside or queenside
     * @return boolean Returns 'true' if castling is possible
     */
    private boolean canDoCastling(Piece selectedPiece, Piece targetPiece) {
        // selectedPiece is King, targetPiece is Rook
        if (!selectedPiece.isHasMoved() && !targetPiece.isHasMoved() // King and Rook didn't move yet
                && isPathEmpty(selectedPiece, targetPiece.getSquare())) {   // no pieces between King and Rook
            List<Piece> enemies = currentPlayer.getEnemyPieces(beatenPieces, chessBoard);
            int diff = Math.abs(targetPiece.getSquare().getX() - selectedPiece.getSquare().getX());
            int king_x;
            int king_y = selectedPiece.getSquare().getY();

            // check if the Kings current Square and/or any Squares the King visits are in check/under attack
            for (Piece enemyPiece : enemies) {
                for (int i = 0; i < diff; i++) {
                    king_x = selectedPiece.getSquare().getX() + i;
                    Square tempSquare = new Square(Label.values()[king_x + king_y], king_x, king_y);
                    if(enemyPiece.isPiecesMove(tempSquare) && isPathEmpty(enemyPiece, tempSquare)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void changePlayer(Square finalSquare) {
        // change currentPlayer to next Colour
        currentPlayer = currentPlayer == playerWhite ? playerBlack : playerWhite;

        if (isCheckMate(finalSquare)) {
            // check if this player is checkmate after the move
            currentPlayer.setLoser(true);
        }
    }

    private boolean canMoveStay(Piece targetPiece, Move currentMove) {
        if (isInCheck()) {
            // King is in check, undo move
            if (targetPiece != null) {
                currentMove.undoMove(targetPiece, this.chessBoard);
            } else {
                currentMove.undoMove(this.chessBoard);
            }
            return false;
        }
        return true;
    }
}

