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
    public Colour colour;
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

    // TODO: checkMate()

    /**
     * Checks if Console Input is a syntactical correct Move.
     *
     * @param consoleInput Input of active Player as a String.
     *
     * @return a boolean if the syntax of the input is correct
     */
    public boolean isValidMove(String consoleInput){
        if(consoleInput.length() > 4) {
            if (consoleInput.charAt(2) == '-') {
                return Label.contains(consoleInput.substring(0, 2)) && Label.contains(consoleInput.substring(3, 5));
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // TODO: isMoveAllowed() vervollständigen
    public boolean isMoveAllowed(Piece selectedPiece, Square finalSquare) {
        if (finalSquare.occupiedBy != null) {
            Piece targetPiece = finalSquare.occupiedBy;
            // if command is castling, check if it is allowed
            if (selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK
                    && canDoCastling(selectedPiece, targetPiece)) {
                return true;
            }
        } else {
            return (selectedPiece.isPiecesMove(finalSquare)
                    && isPathEmpty(selectedPiece, finalSquare)
                    && !isInCheck(selectedPiece.getColour()));
        }
        return false;
    }


    public boolean processMove(Square startSquare, Square finalSquare) {
        Move currentMove = new Move(startSquare, finalSquare);
        Piece selectedPiece = startSquare.occupiedBy;
        Piece targetPiece = finalSquare.occupiedBy;

        // check if the move is allowed for this piece
        if (isMoveAllowed(selectedPiece, finalSquare)) {
            if (selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK) {
                // if the move is castling and it is allowed to do the move
                currentMove.castlingMove(board);
                selectedPiece.setHasMoved(true);
                targetPiece.setHasMoved(true);
                return true;
            }
            if (targetPiece != null) {
                // add a beaten piece to the ArrayList
                beatenPieces.add(targetPiece);
            }
            // if the move is not castling and is allowed do the move
            currentMove.doMove(selectedPiece, this.board);
            moveHistory.add(currentMove);
            if (isInCheck(selectedPiece.getColour())) {
                // if after the move King is in check, undo the move
                currentMove.undoMove(this.moveHistory, this.board);
                return false;
            }
            selectedPiece.setHasMoved(true); // only after checking if the King is in check
        }
       return true; // the move was not allowed
    }
        // TODO: draw() -> pattstellung + tote Stellung


    private boolean isPathEmpty (Piece piece, Square end){
        ArrayList<Square> path = generatePath(piece.getType(), piece.getSquare(), end);
        if (path.isEmpty() && piece.getType() == Type.KNIGHT) {
            // Knight can leap
            return true;
        } else if (path.isEmpty()) {
            return false;
        }  else {
            for (Square visitedSquare : path) {
                if (visitedSquare.occupiedBy != null) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     *
     * @param colour
     * @return
     */
    private boolean isInCheck (Colour colour){
        Square squareKing = board.getSquareOfKing(colour);
        ArrayList<Piece> enemies = board.getEnemyPieces(colour);
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.isPiecesMove(squareKing)
                    && isPathEmpty(enemyPiece, squareKing)) {
                System.out.println("schach" + colour + "true");
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param finalSquare
     * @param colour
     * @return
     */
    private boolean checkSafeSquare (Square finalSquare, Colour colour) {
        ArrayList<Piece> enemies = board.getEnemyPieces(colour);
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.getType() == Type.BISHOP
                    || enemyPiece.getType() == Type.ROOK
                    || enemyPiece.getType() == Type.QUEEN) {
                if (isPathEmpty(enemyPiece, board.getSquareOfKing(colour))) {
                    return false;
                }
            } else if (enemyPiece.getType() == Type.KNIGHT) {
                if (enemyPiece.isPiecesMove(finalSquare)) {
                    return false;
                }
            } else if (enemyPiece.getType() == Type.KING) {
                if (enemyPiece instanceof King) {
                    if (enemyPiece.isPiecesMove(finalSquare)) {
                        return false;
                    }
                }
            } else {
                if (enemyPiece instanceof Pawn) {
                    if (((Pawn)enemyPiece).pawnCanCapture(finalSquare)) {
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
     * @param start Start-Square of Movement
     * @param end End-Square of Movement
     * @return Path of Moving Piece
     */
    private ArrayList<Square> generatePath(Type type, Square start, Square end) {
        ArrayList<Square> path = new ArrayList<>();
        if (type == Type.QUEEN) {
            if (start.x - end.x == 0) {                                 //vertical move
                if (Math.abs(start.y - end.y) > 1) {                    //Queen moves more than one Square
                    int from = Math.min(start.y, end.y);
                    for (int i = 1; i < Math.abs(start.y - end.y); i++) {
                        path.add(board.board[start.x][from + i]);
                    }
                }
            } else if (start.y - end.y == 0){                           //horizontal move
                if (Math.abs(start.x - end.x) > 1) {                    //Queen moves more than one Square
                    int from = Math.min(start.x, end.x);
                    for (int i = 1; i < Math.abs(start.x - end.x); i++) {
                        path.add(board.board[from + i][start.y]);
                    }
                }
            }
            else {
                int diffX = end.x - start.x;                                // if positive: Queen moves from up to down
                int diffY = end.y - start.y;                                // if positive: Queen moves from left to right
                int dirX = diffX / Math.abs(diffX);                         // if positive: Queen moves from up to down
                int dirY = diffY / Math.abs(diffY);                         // if positive: Queen moves from left to right
                if (Math.abs(diffX) > 1){                                   // Queen moves more than one Square
                    for (int i = 1; i < Math.abs(diffX); i++){
                        path.add(board.board[start.x + i * dirX][start.y + i * dirY]);
                    }
                }
            }


        } else if (type == Type.BISHOP) {
            int diffX = end.x - start.x;                                // if positive: Bishop moves from up to down
            int diffY = end.y - start.y;                                // if positive: Bishop moves from left to right
            int dirX = diffX / Math.abs(diffX);                         // if positive: Bishop moves from up to down
            int dirY = diffY / Math.abs(diffY);                         // if positive: Bishop moves from left to right
            if (Math.abs(diffX) > 1){                                   // Bishop moves more than one Square
                for (int i = 1; i < Math.abs(diffX); i++){
                    path.add(board.board[start.x + i * dirX][start.y + i * dirY]);
                }
            }
        } else if (type == Type.ROOK) {
            if (start.x - end.x == 0) {                                 //vertical move
                if (Math.abs(start.y - end.y) > 1) {                    //Rook moves more than one Square
                    int from = Math.min(start.y, end.y);
                    for (int i = 1; i < Math.abs(start.y - end.y); i++) {
                        path.add(board.board[start.x][from + i]);
                    }
                }
            } else {                                                    //horizontal move
                if (Math.abs(start.x - end.x) > 1) {                    //Rook moves more than one Square
                    int from = Math.min(start.x, end.x);
                    for (int i = 1; i < Math.abs(start.x - end.x); i++) {
                        path.add(board.board[from + i][start.y]);
                    }
                }
            }
        }
        return path;
    }


    /**
     *
     * @param selectedPiece
     * @param targetPiece
     * @return
     */
    private boolean canDoCastling(Piece selectedPiece, Piece targetPiece) {
        Colour kingColour = selectedPiece.getColour();

        if (!selectedPiece.getHasMoved() && !targetPiece.getHasMoved() // King and Rook didn't move yet
                && isPathEmpty(selectedPiece, targetPiece.getSquare())) {   // no pieces between King and Rook

            ArrayList<Piece> enemies = board.getEnemyPieces(kingColour);
            int diff = Math.abs(targetPiece.getSquare().x - selectedPiece.getSquare().x);
            int king_x;
            int king_y = selectedPiece.getSquare().y;

            // check if the Kings current Square or any Squares the King visits are in check/under attack
            for (Piece enemyPiece : enemies) {
                for (int i = 0; i < diff; i++) {
                    king_x = selectedPiece.getSquare().x + i;
                    Square tempSquare = new Square(Label.values()[king_x + king_y], king_x, king_y);
                    return enemyPiece.isPiecesMove(tempSquare) && isPathEmpty(enemyPiece, tempSquare);
                }
            }
        }
        return false;
    }

}

