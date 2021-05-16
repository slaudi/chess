package chess.game;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * The Game class which defines and controls the current game.
 */

public class Game {
    final Player playerWhite;
    final Player playerBlack;
    private final List<Piece> beatenPieces;
    private final Stack<Move> moveHistory;
    public Board chessBoard;
    public Player currentPlayer;

    /**
     * Constructor for creating a new Game.
     */
    public Game() {
        this.playerWhite = new Player(Colour.WHITE);
        this.playerBlack = new Player(Colour.BLACK);
        this.currentPlayer = playerWhite;   // White always begins
        this.chessBoard = new Board();
        this.beatenPieces = new ArrayList<>();
        this.moveHistory = new Stack<>();
    }

    public List<Piece> getBeatenPieces() {
        return this.beatenPieces;
    }

    /**
     * Evaluates the semantically correctness of the console input, determining if the move is
     * allowed for the selected Piece and in the current state of the Game.
     *
     * @param selectedPiece The Piece which the Player wants to move.
     * @param finalSquare   The Square which the Player wants his Piece to move to.
     * @return boolean Returns 'true' if the move is possible.
     */
    public boolean isMoveAllowed(Piece selectedPiece, Square finalSquare) {//NOPMD all if-clauses are needed to cover all special cases
        if (selectedPiece == null || selectedPiece.getColour() != this.currentPlayer.getColour()) {
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
                    return ((Pawn) selectedPiece).canCapture(finalSquare);
                } else {
                    return selectedPiece.isPiecesMove(finalSquare, this.chessBoard) && selectedPiece.isPathEmpty(finalSquare, this.chessBoard);
                }
            } else if (targetPiece.getColour() == selectedPiece.getColour()) {
                return false;
            }
        // final square is empty
        } else if (selectedPiece.getType() == Type.PAWN && this.moveHistory.size() > 1) {
                Move lastEnemyMove = this.moveHistory.peek();
                Square start = lastEnemyMove.getStartSquare();
                Square end = lastEnemyMove.getFinalSquare();
                int diff_enemy = start.getY() - end.getY();
                if (Math.abs(diff_enemy) == 2 && end.getY() == selectedPiece.getSquare().getY()) {
                    // is en passant possible
                    return ((Pawn) selectedPiece).isEnPassant(finalSquare, lastEnemyMove);
                }
        }
        return selectedPiece.isPiecesMove(finalSquare, this.chessBoard) && selectedPiece.isPathEmpty(finalSquare, this.chessBoard);

    }

    /**
     * A function which processes the move: it calls the Move class to execute the move and checks afterwards
     * if the player has put themself in check. If so the function calls the Move class again to undo
     * the move and returns the failure of the move.
     *
     * @param startSquare   The Square where the move starts.
     * @param finalSquare   The Square where the move ends.
     * @param key           The char for a potential promotion.
     * @return boolean Returns 'true' if the move doesn't put the player in check.
     */
    public boolean processMove(Square startSquare, Square finalSquare, char key) {//NOPMD to process a move all if-clauses are needed here
        Move currentMove = new Move(startSquare, finalSquare);
        Piece selectedPiece = startSquare.getOccupiedBy();
        Piece targetPiece = finalSquare.getOccupiedBy();

        if (targetPiece != null && selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK) {
            // move is castling, afterwards never in check -> is covered in canDoCastling()
            currentMove.castlingMove(this.chessBoard);
            targetPiece.setNotMoved(false);
        } else if (selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).isEnPassant(finalSquare, this.moveHistory)) {
            // move is an en passant capture
            Move lastEnemyMove = this.moveHistory.peek(); // get last Move (of the enemy), but don't remove it
            currentMove.enPassantMove(lastEnemyMove, this.chessBoard);
            Piece enemy = lastEnemyMove.getMovingPiece();
            this.beatenPieces.add(enemy);
            if (isInCheck()) {
                // King is in check, undo en passant
                currentMove.undoEnPassant(enemy, lastEnemyMove, this.chessBoard);
                this.beatenPieces.remove(enemy);
                return false;
            }
        } else {
            currentMove.doMove(this.chessBoard);
            if (targetPiece != null) {
                // add a beaten piece to the ArrayList before isInCheck() (don't examine it, it's already beaten)
                beatenPieces.add(targetPiece);
            }
            if (!canMoveStay(targetPiece, currentMove)) {
                // move puts own King in check, undo move
                return false;
            }
            if (selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).promotionPossible(finalSquare)) {
                // move allows promotion
                currentMove.doPromotion(key, this.chessBoard);
            }
        }
        this.moveHistory.add(currentMove);
        selectedPiece.setSquare(finalSquare);
        selectedPiece.setNotMoved(false); // TODO: vorher schon, wegen isInCheck-Test?
        changePlayer(finalSquare);
        return true;
    }

    /**
     * Evaluates if the current state of game is a draw: when the King is not yet in check but every Square
     * the King can move to is under attack and no ally can move either.
     *
     * @return boolean Returns 'true' if the game is a draw.
     */
    public boolean isADraw() {
        if (canKingMove()) {
            // King can move
            return false;
        }
        if (!isInCheck()) {
            // King is not in check but can't move
            List<Piece> allies = this.currentPlayer.getAlliedPieces(this.beatenPieces, this.chessBoard);
            if (allies.isEmpty()) {
                // if no ally exists -> it's a draw
                return true;
            } else {
                // if ally exists
                for (Piece ally : allies) {
                    if (ally.canPieceMove(this.chessBoard)) {
                        // check if they can move somewhere -> it's not a draw
                        return false;
                    }
                }
            }
        }
        // King is in check, but that's not a draw (might be checkmate though)
        return false;
    }

    /**
     * Evaluates if the King is able to move somewhere not putting themself in check.
     *
     * @return boolean Returns 'true' if the King is able to make a safe move.
     */
    public boolean canKingMove() {
        Square kingSquare = this.chessBoard.getSquareOfKing(this.currentPlayer.getColour());
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                // looping through board to check if Square is next to King and is not occupied by ally
                Square tempSquare = this.chessBoard.getChessBoard()[i][j];
                if (Piece.isSurroundingSquare(kingSquare, tempSquare)) {
                    if (tempSquare.getOccupiedBy() != null) {
                        if (tempSquare.getOccupiedBy().getColour() != this.currentPlayer.getColour()) {
                            // if a Square is next to the King is it safe to move there
                            return isSafeSquare(tempSquare);
                        }
                    } else {
                        return isSafeSquare(tempSquare);
                    }
                }
            }
        }
        return false;
    }

    /**
     * A function evaluating if the King is safe from check on a certain Square.
     *
     * @param finalSquare The Square the King should move to.
     * @return boolean Returns 'true' if the King is able to move safely.
     */
    private boolean isSafeSquare(Square finalSquare) {
        List<Piece> enemies = this.currentPlayer.getEnemyPieces(this.beatenPieces, this.chessBoard);
        for (Piece enemyPiece : enemies) {
            if (canKillKing(enemyPiece, finalSquare)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A function to evaluate if an enemy Piece can kill the King in the current state of the game.
     *
     * @param enemyPiece   The enemy piece eventually attacking the King.
     * @param kingSquare   The Square the King is standing or might stand on.
     * @return boolean  Returns 'true' if an enemy can attack the King where it's now standing or
     *                  where the King wants to move to.
     */
    private boolean canKillKing(Piece enemyPiece, Square kingSquare) {
        if (enemyPiece.getType() == Type.BISHOP
                || enemyPiece.getType() == Type.ROOK
                || enemyPiece.getType() == Type.QUEEN) {
            return enemyPiece.isPiecesMove(kingSquare, this.chessBoard)
                    && enemyPiece.isPathEmpty(this.chessBoard.getSquareOfKing(this.currentPlayer.getColour()), this.chessBoard);
        } else if (enemyPiece.getType() == Type.KNIGHT || enemyPiece.getType() == Type.KING) {
            return enemyPiece.isPiecesMove(kingSquare, this.chessBoard);
        } else {
            assert enemyPiece instanceof Pawn;
            return ((Pawn) enemyPiece).canCapture(kingSquare);
            }
    }

    /**
     * A function evaluating if the current Players King is in check, if true it sets
     * the variable in the Player class to true.
     *
     * @return boolean Returns 'true' if the King of the current Player is in check.
     */
    boolean isInCheck(){
        Square squareKing = this.chessBoard.getSquareOfKing(this.currentPlayer.getColour());
        List<Piece> enemies = this.currentPlayer.getEnemyPieces(this.beatenPieces, this.chessBoard);
        for (Piece enemyPiece : enemies) {
            if (canKillKing(enemyPiece, squareKing)) {
                this.currentPlayer.setInCheck(true);
                return true;
            }
        }
        this.currentPlayer.setInCheck(false);
        return false;
    }

    /**
     * Evaluates if current Players King is in check and if they are able to avoid it.If not
     * sets the value of the variable 'loser' in the Player class to true and the current
     * player loses the game.
     *
     * @return boolean Returns 'true' if the current Player is checkmate.
     */
    boolean isCheckMate(Square finalSquare) {
        if (this.currentPlayer.isInCheck()) {
            if (canKingMove() && isSafeSquare(finalSquare)) {
                return false;
            }
            // can ally defend the King if the King can't move
            List<Piece> enemies = this.currentPlayer.getEnemyPieces(this.beatenPieces, this.chessBoard);
            for (Piece enemyPiece : enemies) {
                if (isMoveAllowed(enemyPiece, finalSquare)) {
                    // if they can defend the King it's not checkmate
                    return !canDefendKing(enemyPiece);
                }
            }
            // cannot move and ally can't defend -> checkmate
            this.currentPlayer.setLoser(true);
            return true;
        } else {
            // King is not in check
            return false;
        }
    }

    /**
     * A function evaluating if one of the players Pieces is able to block the attack of an enemy.
     *
     * @param enemyPiece The Piece attacking the King.
     * @return boolean Returns 'true' if Player is able to avoid check by using another Piece.
     */
    private boolean canDefendKing(Piece enemyPiece) {
        List<Piece> allies = this.currentPlayer.getAlliedPieces(this.beatenPieces, this.chessBoard);
        for (Piece alliedPiece : allies) {
            if (isMoveAllowed(alliedPiece, enemyPiece.getSquare())) {
                return true;
            } else {
                List<Square> enemyPath = enemyPiece.generatePath(enemyPiece.getSquare(), this.chessBoard);
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
     * Evaluates if the castling move is possible by checking the selected King and Rook.
     *
     * @param selectedPiece The King of the Player, has been selected first.
     * @param targetPiece   The selected Rook, which can be kingside or queenside.
     * @return boolean Returns 'true' if castling is possible.
     */
    private boolean canDoCastling(Piece selectedPiece, Piece targetPiece) {
        // selectedPiece is King, targetPiece is Rook
        if (selectedPiece.hasNotMoved() && targetPiece.hasNotMoved() // King and Rook didn't move yet
                && selectedPiece.isPathEmpty(targetPiece.getSquare(), this.chessBoard)) {   // no pieces between King and Rook
            List<Piece> enemies = this.currentPlayer.getEnemyPieces(this.beatenPieces, this.chessBoard);
            int diff = Math.abs(targetPiece.getSquare().getX() - selectedPiece.getSquare().getX());
            int king_x;
            int king_y = selectedPiece.getSquare().getY();

            // check if the Kings current Square and/or any Squares the King visits are in check/under attack
            for (Piece enemyPiece : enemies) {
                for (int i = 0; i < diff; i++) {
                    king_x = selectedPiece.getSquare().getX() + i;
                    Square tempSquare = new Square(Label.values()[king_x+king_y], king_x, king_y);
                    if(enemyPiece.isPiecesMove(tempSquare, this.chessBoard) && enemyPiece.isPathEmpty(tempSquare, this.chessBoard)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    void changePlayer(Square finalSquare) {
        // change currentPlayer to next Colour
        this.currentPlayer = this.currentPlayer == this.playerWhite ? this.playerBlack : this.playerWhite;

        if (isCheckMate(finalSquare)) {
            // check if this player is checkmate after the move
            this.currentPlayer.setLoser(true);
        }
    }

    private boolean canMoveStay(Piece targetPiece, Move currentMove) {
        if (isInCheck()) {
            // King is in check, undo move
            if (targetPiece != null) {
                currentMove.undoMove(targetPiece, this.chessBoard);
                this.beatenPieces.remove(targetPiece); // remove the beaten piece from the list again
            } else {
                currentMove.undoMove(this.chessBoard);
            }
            this.moveHistory.remove(currentMove); // move is currently not allowed
            return false;
        }
        // move doesn't put King in check
        return true;
    }
}

