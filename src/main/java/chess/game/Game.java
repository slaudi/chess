package chess.game;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Game class which defines current game.
 */

public class Game {
    public Player playerWhite;
    public Player playerBlack;
    public Board chessBoard;
    public List<Piece> beatenPieces;
    public Player currentPlayer;
    Stack<Move> moveHistory;

    /**
     * Constructor for a Game
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
     * implementation of movement
     *
     * @param startSquare   Square where movement starts
     * @param finalSquare   Square where movement ends
     * @param key           the potential char for a promotion
     * @return returns opposite of "move is allowed"
     */
    public boolean processMove(Square startSquare, Square finalSquare, char key) {
        Move currentMove = new Move(startSquare, finalSquare);
        this.moveHistory.add(currentMove);
        Piece selectedPiece = startSquare.getOccupiedBy();
        Piece targetPiece = finalSquare.getOccupiedBy();
        Move lastMove = this.moveHistory.peek();
        Move secondLastMove = this.moveHistory.peek();

        if (targetPiece != null && selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK) {
            // move is castling
            currentMove.castlingMove(chessBoard);
            targetPiece.setHasMoved(true);
        }
        if (selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).isEnPassant(finalSquare, this.moveHistory)) {
            // move is an en passant capture
            Piece enemy = currentMove.enPassantMove(lastMove, chessBoard);
            beatenPieces.add(enemy);
            if (isInCheck()) {
                // King is in check, undo move
                currentMove.undoEnPassant(lastMove, secondLastMove, this.chessBoard);
                this.moveHistory.remove(lastMove);
                beatenPieces.remove(enemy);
                return false;
            }
        }
        if (selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).promotionPossible(finalSquare)) {
            // move is a promotion
            Piece enemy = currentMove.doPromotion(lastMove, key, chessBoard);
            beatenPieces.add(enemy);
            if (isInCheck()) {
                // King is in check, undo move
                currentMove.undoPromotion(lastMove, secondLastMove, this.chessBoard);
                moveHistory.remove(lastMove);
                beatenPieces.remove(enemy);
                return false;
            }
        }
        // all other moves
        currentMove.doMove(this.chessBoard);
        if (targetPiece != null) {
            // add a beaten piece to the ArrayList
            beatenPieces.add(targetPiece);
        }
        if (isInCheck()) {
            // King is in check, undo move
            currentMove.undoMove(lastMove, this.chessBoard);
            moveHistory.remove(lastMove);
            return false;
        }
        System.out.println("GAME: " + moveHistory);
        changePlayer(finalSquare);
        return true;
    }

    /**
     * Evaluates semantically correctness of input move
     *
     * @param selectedPiece Piece which Player wants to move
     * @param finalSquare Square which Player wants his Piece to move to
     * @return boolean Returns if input move is possible
     */
    public boolean isMoveAllowed(Piece selectedPiece, Square finalSquare) {
        if (selectedPiece == null) {
            return false;
        }
        if (selectedPiece.getColour() == currentPlayer.getColour()) {
            // player can only choose their own Piece
            if (finalSquare.getOccupiedBy() != null) {
                // the selected second square is not empty
                Piece targetPiece = finalSquare.getOccupiedBy();
                if (selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK
                        && canDoCastling(selectedPiece, targetPiece)) {
                    // if command is castling (selected King and your own Rook), check if it is allowed
                    return true;
                } else if (selectedPiece.getType() == Type.PAWN) {
                    // if selected Piece is a Pawn check if it is allowed to capture the enemy Piece
                    return ((Pawn) selectedPiece).canCapture(finalSquare);
                } else {
                    // check for every one else
                    return selectedPiece.isPiecesMove(finalSquare) && isPathEmpty(selectedPiece, finalSquare);
                }
            } else {
                // the second Square is empty
                if (selectedPiece.getType() == Type.PAWN) {
                    // is it a Pawn move or is en passant possible
                    return selectedPiece.isPiecesMove(finalSquare) || ((Pawn) selectedPiece).isEnPassant(finalSquare, this.moveHistory);
                } else if (selectedPiece.getType() == Type.KING) {
                    // check if it is a King move and it doesn't put the King in check
                    return selectedPiece.isPiecesMove(finalSquare) && isSafeSquare(finalSquare);
                }  else {
                    // check for every one else
                    return selectedPiece.isPiecesMove(finalSquare) && isPathEmpty(selectedPiece, finalSquare);
                }
            }
        }
        return false;
    }

    /**
     * Evaluates if current state of game is draw
     *
     * @return boolean returns if game is draw or not
     */
    public boolean isADraw() {
        if (!isInCheck()) {
            // King is not in check
            if (!getAlliedPieces().isEmpty()) {
                // if ally exists - not a draw
                return false;
            }
            // if King can Move it's not a draw
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
                if (isSurroundingSquare(kingSquare, chessBoard.getChessBoard()[i][j])
                        && chessBoard.getChessBoard()[i][j].getOccupiedBy().getColour() != currentPlayer.getColour()) {
                    // if a Square is next to the King is it safe to move there
                    return isSafeSquare(chessBoard.getChessBoard()[i][j]);
                }
            }
        }
        return false;
    }



    /**
     * Evaluates if direct path from one square to another is empty
     *
     * @param piece Piece which has to move
     * @param finalSquare Square where piece has to go to
     * @return returns if selected path is empty
     */
    private boolean isPathEmpty (Piece piece, Square finalSquare){
        if (isSurroundingSquare(piece.getSquare(), finalSquare)) {
            return finalSquare.getOccupiedBy() == null || finalSquare.getOccupiedBy().getColour() != currentPlayer.getColour();
        }
        List<Square> path = piece.generatePath(finalSquare);
        if (path.isEmpty()) {
            // Knight can leap, Pawn/King don't have a path
            return true;
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
    public boolean isInCheck (){
        Square squareKing = this.chessBoard.getSquareOfKing(currentPlayer.getColour());
        List<Piece> enemies = getEnemyPieces();
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.isPiecesMove(squareKing)
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
    public boolean isCheckMate(Square finalSquare) {
        if (currentPlayer.isInCheck()) {
            if (canKingMove()  && isSafeSquare(finalSquare)) {
                return false;
            }
            // can ally defend the King if the King can't move
            List<Piece> enemies = getEnemyPieces();
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
        List<Piece> allies = getAlliedPieces();
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
     * Evaluates if King is able to safely move to selected square
     * @param finalSquare The Square the King should move to
     * @return boolean Returns 'true' if King is able to move safely
     */
    private boolean isSafeSquare(Square finalSquare) {
        List<Piece> enemies = getEnemyPieces();
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.getType() == Type.BISHOP
                    || enemyPiece.getType() == Type.ROOK
                    || enemyPiece.getType() == Type.QUEEN) {
                if (enemyPiece.isPiecesMove(finalSquare)
                        && isPathEmpty(enemyPiece, chessBoard.getSquareOfKing(currentPlayer.getColour()))) {
                    return false;
                }
            } else if (enemyPiece.getType() == Type.KNIGHT) {
                if (enemyPiece.isPiecesMove(finalSquare)) {
                    return false;
                }
            } else if (enemyPiece.getType() == Type.KING) {
                    if (enemyPiece.isPiecesMove(finalSquare)) {
                        return false;
                    }
            } else {
                if (enemyPiece instanceof Pawn) {
                    if(((Pawn)enemyPiece).canCapture(finalSquare)) {
                        return false;
                    }
                }
            }
        }
        return true;
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
            List<Piece> enemies = getEnemyPieces();
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

    /**
     * Evaluates if a Square is directly next to a selected Piece
     * @param piecesSquare The Piece which wants to move
     * @param squareOfInterest The Square where the Piece wants to go to
     * @return boolean Returns 'true' if selected Square to move to is only one Square away from the Piece
     */
    private boolean isSurroundingSquare(Square piecesSquare, Square squareOfInterest){
        int diffX = piecesSquare.getX() - squareOfInterest.getX();
        int diffY = piecesSquare.getY() - squareOfInterest.getY();
        return diffX < 2 && diffY < 2;
    }

    /**
     * A function putting all the allied Pieces (the same colour) on the current board of a certain Piece
     * in a ArrayList.
     * @return ArrayList Containing all active allied Pieces
     */
    public List<Piece> getAlliedPieces() {
        List<Piece> allies;
        List<Piece> piecesToRemove = new ArrayList<>();
        if(currentPlayer.getColour() == Colour.WHITE) {
            allies = chessBoard.getWhiteAlliance();
        } else {
            allies = chessBoard.getBlackAlliance();
        }
        for (Piece ally : allies) {
            for (Piece beaten : this.beatenPieces) {
                if (ally.getColour() == beaten.getColour() && ally.getType() == beaten.getType()
                    && beaten.equals(ally)) {
                    piecesToRemove.add(beaten);
                }
            }
        }
        for (Piece piece : piecesToRemove) {
            allies.remove(piece);
        }
        return allies;
    }

    /**
     * A function putting all the enemy Pieces (the other colour) on the current board of a certain Piece
     * in a ArrayList.
     * @return ArrayList Containing all active enemy Pieces.
     */
    public List<Piece> getEnemyPieces() {
        List<Piece> enemies;
        List<Piece> piecesToRemove = new ArrayList<>();
        if(currentPlayer.getColour() == Colour.WHITE) {
            enemies = chessBoard.getBlackAlliance();
        } else {
            enemies = chessBoard.getWhiteAlliance();
        }
        for (Piece enemy : enemies) {
            for (Piece beaten : this.beatenPieces) {
                if (enemy.getColour() == beaten.getColour() && enemy.getType() == beaten.getType()
                        && beaten.equals(enemy)) {
                    piecesToRemove.add(enemy);
                }
            }
        }
        for (Piece piece : piecesToRemove) {
            enemies.remove(piece);
        }
        return enemies;
    }

    void changePlayer(Square finalSquare) {
        // change currentPlayer to next Colour
        currentPlayer = currentPlayer == playerWhite ? playerBlack : playerWhite;

        if (isCheckMate(finalSquare)) {
            // check if this player is checkmate after the move
            currentPlayer.setLoser(true);
        }
    }
}

