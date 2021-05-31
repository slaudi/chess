package chess.game;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The Game class which defines and controls the current game.
 */
public class Game {
    /**
     * The player associated with the Colour object White in the current game.
     */
    public final Player playerWhite;
    /**
     * The player associated with the Colour object Black in the current game.
     */
    public final Player playerBlack;
    /**
     * The current player making a move.
     */
    public Player currentPlayer;
    /**
     * The current board on which this move is done.
     */
    public Board chessBoard;
    /**
     * The list where all beaten pieces are stored.
     */
    public final List<Piece> beatenPieces;
    public final List<Move> moveHistory;

    Square squareStart;                  // Helper-Attributs for Moving in GUI
    Square squareFinal;
    public boolean enemyIsHuman;
    public Colour userColour;
    public boolean isRotatingBoard;
    public boolean highlightPossibleMoves;
    public boolean allowedToChangeSelectedPiece; //in processingMove
    public boolean hintInCheck;
    public boolean freshGame;



    /**
     * Constructor for creating a new Game.
     */
    public Game() {
        this.playerWhite = new Player(Colour.WHITE);
        this.playerBlack = new Player(Colour.BLACK);
        this.currentPlayer = playerWhite;   // White always begins

        this.chessBoard = new Board(8,8);
        this.beatenPieces = new ArrayList<>();
        this.moveHistory = new ArrayList<>();

        this.squareStart = null;
        this.squareFinal = null;
        this.enemyIsHuman = true;
        this.userColour = Colour.WHITE;
        this.isRotatingBoard = true;
        this.highlightPossibleMoves = true;
        this.allowedToChangeSelectedPiece = false;
        this.hintInCheck = true;
        this.freshGame = true;
    }

    /**
     * Evaluates the semantically correctness of the console input, determining if the move is
     * allowed for the selected Piece and in the current state of the Game.
     *
     * @param selectedPiece The Piece which the Player wants to move.
     * @param finalSquare   The Square which the Player wants his Piece to move to.
     * @return boolean Returns 'true' if the move is possible.
     */
    public boolean isMoveAllowed(Piece selectedPiece, Square finalSquare) {//NOPMD all if-clauses are needed to cover all cases
        if (selectedPiece == null || selectedPiece.getColour() != this.currentPlayer.getColour()) {
            return false;
        }
        Piece targetPiece = finalSquare.getOccupiedBy();
        if (targetPiece != null) {
            // the final Square is occupied by another piece
            if (targetPiece.getColour() != selectedPiece.getColour()) {
                if (selectedPiece.getType() == Type.PAWN) {
                    // if selected Piece is a Pawn see if it is allowed to capture the enemy Piece
                    return ((Pawn) selectedPiece).canCapture(finalSquare);
                } else {
                    return selectedPiece.isPiecesMove(finalSquare, this.chessBoard) && selectedPiece.isPathEmpty(finalSquare, this.chessBoard);
                }
            } else {
                // target piece is ally
                return false;
            }
        // final square is empty
        } else if (selectedPiece.getType() == Type.PAWN && this.moveHistory.size() > 1) {
                Move lastEnemyMove = this.moveHistory.get(this.moveHistory.size() - 1);
                Square start = lastEnemyMove.getStartSquare();
                Square end = lastEnemyMove.getFinalSquare();
                int diff_enemy = start.getY() - end.getY();
                if (Math.abs(diff_enemy) == 2 && end.getY() == selectedPiece.getSquare().getY() && end.getOccupiedBy().getType() == Type.PAWN) {
                    // is en passant possible
                    return ((Pawn)selectedPiece).isEnPassant(finalSquare, lastEnemyMove) || selectedPiece.isPiecesMove(finalSquare, this.chessBoard);
                }
        } else if (selectedPiece.getType() == Type.KING && Math.abs(selectedPiece.getSquare().getX() - finalSquare.getX()) == 2){
            // is castling possible
            List<Piece> enemies = this.currentPlayer.getEnemyPieces(this.beatenPieces, this.chessBoard);
            return ((King)selectedPiece).canDoCastling(finalSquare, enemies, this.chessBoard);
        }
        // all other moves
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
        List<Piece> enemies = this.currentPlayer.getEnemyPieces(this.beatenPieces, this.chessBoard);

        if (selectedPiece.getType() == Type.KING && ((King)selectedPiece).canDoCastling(finalSquare, enemies, this.chessBoard)) {
            // move is castling, afterwards never in check -> is covered in canDoCastling()
            currentMove.castlingMove(this.chessBoard);
        } else if (selectedPiece.getType() == Type.PAWN && ((Pawn)selectedPiece).isEnPassant(finalSquare, this.moveHistory)) {
            // move is an en passant capture
            Move lastEnemyMove = this.moveHistory.get(this.moveHistory.size() - 1); // get last Move (of the enemy)
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
                // add a beaten piece to the ArrayList before isInCheck() (don't test it, it's already beaten)
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
        selectedPiece.setNotMoved(false);
        changePlayer();
        return true;
    }

    /**
     * A function evaluating if the current Players King is in check, if true it sets
     * the variable in the Player class to true.
     *
     * @return boolean Returns 'true' if the King of the current Player is in check.
     */
    public boolean isInCheck(){
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
    public boolean isCheckMate() {
        Square squareKing = this.chessBoard.getSquareOfKing(this.currentPlayer.getColour());
        if (this.currentPlayer.isInCheck()) {
            if (canKingMove()) {
                return false;
            }
            // can ally defend the King against enemies if the King can't move
            List<Piece> enemies = this.currentPlayer.getEnemyPieces(this.beatenPieces, this.chessBoard);
            for (Piece enemyPiece : enemies) {
                if (enemyPiece.getType() == Type.PAWN && ((Pawn)enemyPiece).canCapture(squareKing)
                        || enemyPiece.isPiecesMove(squareKing, this.chessBoard) && enemyPiece.isPathEmpty(squareKing, this.chessBoard)) {
                    if (canDefendKing(enemyPiece)) {
                        // ally can defend against this enemy -> check next enemy
                        continue;
                    }
                    // cannot move and ally can't defend -> checkmate
                    this.currentPlayer.setLoser(true);
                    return true;
                }
            }
        }
        // King is not in check
        return false;
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
    boolean canKingMove() {
        Square kingSquare = this.chessBoard.getSquareOfKing(this.currentPlayer.getColour());
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                // looping through board to check if Square is next to King and is not occupied by ally
                Square tempSquare = this.chessBoard.getBoard()[i][j];
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
    public boolean isSafeSquare(Square finalSquare) {
        List<Piece> enemies = this.currentPlayer.getEnemyPieces(this.beatenPieces, this.chessBoard);
        for (Piece enemyPiece : enemies) {
            if (isMoveAllowed(enemyPiece, finalSquare)) {                                                                 //changed canKillKing to isMoveAllowed
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
    boolean canKillKing(Piece enemyPiece, Square kingSquare) {
        if (enemyPiece.getType() == Type.BISHOP
                || enemyPiece.getType() == Type.ROOK
                || enemyPiece.getType() == Type.QUEEN) {
            if(enemyPiece.isPiecesMove(kingSquare, this.chessBoard)){
                return enemyPiece.isPathEmpty(this.chessBoard.getSquareOfKing(this.currentPlayer.getColour()), this.chessBoard);
            }
            return false;
        } else if (enemyPiece.getType() == Type.KNIGHT || enemyPiece.getType() == Type.KING) {
            return enemyPiece.isPiecesMove(kingSquare, this.chessBoard);
        } else {
            assert enemyPiece instanceof Pawn;
            return ((Pawn) enemyPiece).canCapture(kingSquare);
        }
    }

    /**
     * A function evaluating if one of the players Pieces is able to block the attack of an enemy.
     *
     * @param enemyPiece The Piece attacking the King.
     * @return boolean Returns 'true' if Player is able to avoid check by using another Piece.
     */
    boolean canDefendKing(Piece enemyPiece) {
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
     * Changes the current player to the next Colour and checks if the last move set the next player
     * checkmate.
     */
    public void changePlayer() {
        // change currentPlayer to next Colour
        this.currentPlayer = this.currentPlayer == this.playerWhite ? this.playerBlack : this.playerWhite;

        if (isCheckMate()) {
            // check if next player is checkmate after the last move
            this.squareStart = null;
            this.squareFinal = null;
            if (isCheckMate()) {
                // check if this player is checkmate after the move
                this.currentPlayer.setLoser(true);
            }
        }
    }

    boolean canMoveStay(Piece targetPiece, Move currentMove) {
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

    public void setSquareStart(Square square){
        this.squareStart = square;
    }

    public void setSquareFinal(Square square){
        this.squareFinal = square;
    }

    public Square getSquareStart(){
        return this.squareStart;
    }

    public Square getSquareFinal(){
        return this.squareFinal;
    }

    public void setBothMovingSquares(Square square){
        if(this.getSquareStart() == null){
            this.setSquareStart(square);
        } else {
            this.setSquareFinal(square);
        }
    }

    public List<Square> computePossibleSquares() {
        List<Square> possibleSquares = new ArrayList<>();
        for (int y = 0; y < this.chessBoard.getHeight(); y++) {
            for (int x = 0; x < this.chessBoard.getWidth(); x++) {
                if (isMoveAllowed(getSquareStart().getOccupiedBy(), this.chessBoard.getSquareAt(x,y))){
                    if(getSquareStart().getOccupiedBy().getType() != Type.KING){
                        possibleSquares.add(this.chessBoard.getSquareAt(x,y));
                    } else {
                        if(isSafeSquare(this.chessBoard.getSquareAt(x,y))){
                            possibleSquares.add(this.chessBoard.getSquareAt(x,y));
                        }
                    }
                }
            }
        }
        return possibleSquares;
    }
}

