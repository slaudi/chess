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
    public Board board;
    public List<Piece> beatenPieces;
    public Stack<Move> moveHistory;
    public Player currentPlayer;

    /**
     * Constructor for a Game
     */
    public Game() {
        this.playerWhite = new Player(Colour.WHITE);
        this.playerBlack = new Player(Colour.BLACK);
        this.currentPlayer = playerWhite;   // White always begins
        this.board = new Board();
        this.moveHistory = new Stack<>();
        this.beatenPieces = new ArrayList<>();
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

        if (targetPiece != null &&
                selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK) {
            // if the move is castling and it is allowed to do the move
            currentMove.castlingMove(board);
            moveHistory.add(currentMove);
            selectedPiece.setHasMoved(true);
            targetPiece.setHasMoved(true);

            // change currentPlayer to next Colour
            currentPlayer = currentPlayer == playerWhite ? playerBlack : playerWhite;

            if (isCheckMate()) {
                currentPlayer.setLoser(true);
            }
            return true;
        }

        if (selectedPiece.getType() == Type.PAWN) {
            if(((Pawn)selectedPiece).isEnPassant(finalSquare, moveHistory)) {
                // if the move is an en passant capture
                Piece enemyPiece = currentMove.enPassantMove(moveHistory, board);
                beatenPieces.add(enemyPiece);
                moveHistory.add(currentMove);
                // change currentPlayer to next Colour
                currentPlayer = currentPlayer == playerWhite ? playerBlack : playerWhite;

                if (isCheckMate()) {
                    currentPlayer.setLoser(true);
                }
                return true;
            } else if(((Pawn)selectedPiece).promotionPossible(finalSquare)) {
                // if the move is a promotion
                currentMove.promotion(key, board);
                if (finalSquare.getOccupiedBy() != null) {
                    beatenPieces.add(finalSquare.getOccupiedBy());
                }
                moveHistory.add(currentMove);
                // change currentPlayer to next Colour
                currentPlayer = currentPlayer == playerWhite ? playerBlack : playerWhite;

                if (isCheckMate()) {
                    currentPlayer.setLoser(true);
                }
                return true;
            }
        }

        if (targetPiece != null) {
            // add a beaten piece to the ArrayList
            beatenPieces.add(targetPiece);
        }

        // all other moves
        currentMove.doMove(this.board);
        moveHistory.add(currentMove);
        if (isInCheck()) {
            // if after the move the King is in check, undo the move
            currentMove.undoMove(this.moveHistory, this.board);
            return false;   // current player can try again
        }

        selectedPiece.setHasMoved(true); // set only after checking if the King is in check

        // change currentPlayer to next Colour
        currentPlayer = currentPlayer == playerWhite ? playerBlack : playerWhite;

        if (isCheckMate()) {
            currentPlayer.setLoser(true);
        }
        return true; // the move was allowed
    }

    /**
     * Evaluates semantically correctness of input/move
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

            if (finalSquare.getOccupiedBy() != null) {
                Piece targetPiece = finalSquare.getOccupiedBy();

                // if command is castling, check if it is allowed
                if (selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK
                        && canDoCastling(selectedPiece, targetPiece)) {
                    return true;
                } else if (selectedPiece.getType() == Type.PAWN) {
                    return ((Pawn) selectedPiece).canCapture(finalSquare);
                } else {
                    return selectedPiece.isPiecesMove(finalSquare) && isPathEmpty(selectedPiece, finalSquare)
                            && !isInCheck();
                }
            } else {
                if (selectedPiece.getType() == Type.PAWN) {
                    return selectedPiece.isPiecesMove(finalSquare) || ((Pawn) selectedPiece).isEnPassant(finalSquare, moveHistory);
                } else if (selectedPiece.getType() == Type.KING) {
                    return selectedPiece.isPiecesMove(finalSquare) && isSafeSquare(finalSquare);
                }  else {
                    return selectedPiece.isPiecesMove(finalSquare) && isPathEmpty(selectedPiece, finalSquare)
                            && !isInCheck();
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
                // if ally exists that can move - not a draw
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
        Square kingSquare = this.board.getSquareOfKing(currentPlayer.getColour());
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                // looping through board to check if Square is next to King and is not occupied by ally
                if (isSurroundingSquare(kingSquare, board.getChessBoard()[i][j])
                        && board.getChessBoard()[i][j].getOccupiedBy().getColour() != currentPlayer.getColour()) {
                    // if a Square is next to the King is it safe to move there
                    return isSafeSquare(board.getChessBoard()[i][j]);
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
        Type type = piece.getType();
        List<Square> path = generatePath(type, piece.getSquare(), finalSquare);
        if (path.isEmpty() && type == Type.KNIGHT || type == Type.PAWN) {
            // Knight can leap, Pawns don't have a path
            return true;
        } else if (path.isEmpty()) {
            return false;
        }  else {
            for (Square visitedSquare : path) {
                if (visitedSquare.getOccupiedBy() != null) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Evaluates if current Players King is in check and sets Value
     *
     * @return returns checkStatus
     */
    private boolean isInCheck (){
        Square squareKing = this.board.getSquareOfKing(currentPlayer.getColour());
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
    public boolean isCheckMate() {
        if (currentPlayer.isInCheck()) {
            if (canKingMove()) {
                return false;
            }
            // can ally defend the King if the King can't move
            List<Piece> enemies = getEnemyPieces();
            for (Piece enemyPiece : enemies) {
                if (isMoveAllowed(enemyPiece, board.getSquareOfKing(currentPlayer.getColour()))) {
                    return !canDefendKing(enemyPiece);
                }
            }
            // cannot move and ally can't defend -> checkmate
            currentPlayer.setCheckMate(true);
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
                ArrayList<Square> enemyPath = generatePath(enemyPiece.getType(),
                        enemyPiece.getSquare(), board.getSquareOfKing(currentPlayer.getColour()));
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
        System.out.println(enemies);
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.getType() == Type.BISHOP
                    || enemyPiece.getType() == Type.ROOK
                    || enemyPiece.getType() == Type.QUEEN) {
                if (enemyPiece.isPiecesMove(finalSquare) &&
                        isPathEmpty(enemyPiece, board.getSquareOfKing(currentPlayer.getColour()))) {
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
                    if (((Pawn)enemyPiece).canCapture(finalSquare)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Generates Path if Piece moves more than one Square
     * @param type Type of Piece
     * @param startSquare Start-Square of Movement
     * @param finalSquare End-Square of Movement
     * @return Path of moving Piece
     */
    private ArrayList<Square> generatePath(Type type, Square startSquare, Square finalSquare) {
        List<Square> path = new ArrayList<>();
        if (type == Type.QUEEN) {
            if (startSquare.getX() - finalSquare.getX() == 0) {                                 //vertical move
                if (Math.abs(startSquare.getY() - finalSquare.getY()) > 1) {                    //Queen moves more than one Square
                    int from = Math.min(startSquare.getY(), finalSquare.getY());
                    for (int i = 1; i < Math.abs(startSquare.getY() - finalSquare.getY()); i++) {
                        path.add(board.getChessBoard()[startSquare.getX()][from + i]);
                    }
                }
            } else if (startSquare.getY() - finalSquare.getY() == 0){                           //horizontal move
                if (Math.abs(startSquare.getX() - finalSquare.getX()) > 1) {                    //Queen moves more than one Square
                    int from = Math.min(startSquare.getX(), finalSquare.getX());
                    for (int i = 1; i < Math.abs(startSquare.getX() - finalSquare.getX()); i++) {
                        path.add(board.getChessBoard()[from + i][startSquare.getY()]);
                    }
                }
            }
            else {
                int diffX = finalSquare.getX() - startSquare.getX();                                // if positive: Queen moves from up to down
                int diffY = finalSquare.getY() - startSquare.getY();                                // if positive: Queen moves from left to right
                int dirX = diffX / Math.abs(diffX);                         // if positive: Queen moves from up to down
                int dirY = diffY / Math.abs(diffY);                         // if positive: Queen moves from left to right
                if (Math.abs(diffX) > 1){                                   // Queen moves more than one Square
                    for (int i = 1; i < Math.abs(diffX); i++){
                        path.add(board.getChessBoard()[startSquare.getX() + i * dirX][startSquare.getY() + i * dirY]);
                    }
                }
            }

        } else if (type == Type.BISHOP) {
            int diffX = finalSquare.getX() - startSquare.getX();                                // if positive: Bishop moves from up to down
            int diffY = finalSquare.getY() - startSquare.getY();                                // if positive: Bishop moves from left to right
            int dirX = diffX / Math.abs(diffX);                         // if positive: Bishop moves from up to down
            int dirY = diffY / Math.abs(diffY);                         // if positive: Bishop moves from left to right
            if (Math.abs(diffX) > 1){                                   // Bishop moves more than one Square
                for (int i = 1; i < Math.abs(diffX); i++){
                    path.add(board.getChessBoard()[startSquare.getX() + i * dirX][startSquare.getY() + i * dirY]);
                }
            }

        } else if (type == Type.ROOK) {
            if (startSquare.getX() - finalSquare.getX() == 0) {                                 //vertical move
                if (Math.abs(startSquare.getY() - finalSquare.getY()) > 1) {                    //Rook moves more than one Square
                    int from = Math.min(startSquare.getY(), finalSquare.getY());
                    for (int i = 1; i < Math.abs(startSquare.getY() - finalSquare.getY()); i++) {
                        path.add(board.getChessBoard()[startSquare.getX()][from + i]);
                    }
                }
            } else {                                                    //horizontal move
                if (Math.abs(startSquare.getX() - finalSquare.getX()) > 1) {                    //Rook moves more than one Square
                    int from = Math.min(startSquare.getX(), finalSquare.getX());
                    for (int i = 1; i < Math.abs(startSquare.getX() - finalSquare.getX()); i++) {
                        path.add(board.getChessBoard()[from + i][startSquare.getY()]);
                    }
                }
            }
        }
        return new ArrayList<>(path);
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
    public ArrayList<Piece> getAlliedPieces() {
        List<Piece> allies;
        if(currentPlayer.getColour() == Colour.WHITE) {
            allies = board.getWhiteAlliance();
        } else {
            allies = board.getBlackAlliance();
        }
        for (Piece ally : allies) {
            for (Piece beaten : beatenPieces) {
                if (ally.getColour() == beaten.getColour() && ally.getType() == beaten.getType()
                    && beaten.equals(ally)) {
                    allies.remove(ally);
                }
            }
        }
        return new ArrayList<>(allies);
    }

    /**
     * A function putting all the enemy Pieces (the other colour) on the current board of a certain Piece
     * in a ArrayList.
     * @return ArrayList Containing all active enemy Pieces.
     */
    public ArrayList<Piece> getEnemyPieces() {
        List<Piece> enemies;
        if(currentPlayer.getColour() == Colour.WHITE) {
            enemies = board.getBlackAlliance();
        } else {
            enemies = board.getWhiteAlliance();
        }
        for (Piece enemy : enemies) {
            for (Piece beaten : beatenPieces) {
                if (enemy.getColour() == beaten.getColour() && enemy.getType() == beaten.getType()
                    && beaten.equals(enemy)) {
                    enemies.remove(enemy);
                }
            }
        }
        return new ArrayList<>(enemies);
    }
}

