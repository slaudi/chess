package chess.game;

import chess.pieces.Piece;

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
     * @return boolean
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
    public boolean isMoveAllowed(Piece piece, Square finalSquare) {
        return (piece.isAllowedPath(finalSquare) && isPathEmpty(piece, finalSquare, this.board)
                && !isInCheck(this.board, piece.getColour()));
    }


    public boolean processMove(Square startSquare, Square finalSquare) {
        Move currentMove = new Move(startSquare, finalSquare);
        Piece selectedPiece = startSquare.occupiedBy;
        Piece targetPiece = finalSquare.occupiedBy;

        // check if the move is allowed for this piece
        if (selectedPiece.isAllowedPath(finalSquare)/*isMoveAllowed(selectedPiece, finalSquare*/) {
            //
            if (targetPiece != null) {
                beatenPieces.add(targetPiece);
            }
            if (selectedPiece.getType() == Type.KING) {
                if (checkSafeSquare(finalSquare, this.board, selectedPiece.getColour())) {
                    currentMove.doMove(selectedPiece, this.board);
                }
            } else {
                currentMove.doMove(selectedPiece, this.board);
                moveHistory.add(currentMove);
                if (isInCheck(this.board, selectedPiece.getColour())) {
                    currentMove.undoMove(this.moveHistory, this.board);
                    return false;
                }
            }
        }
       return true;
    }
        // TODO: draw() -> pattstellung, tote Stellung

    /**
     *
     * @param piece
     * @param end
     * @param board
     * @return
     */
    private boolean isPathEmpty (Piece piece, Square end, Board board){
        ArrayList<Square> path = generatePath(piece.getType(), piece.getSquare(), end, board);
        if (path.isEmpty()) {
            return false;
        } else {
            for (Square visitedSquare : path) {
                if (visitedSquare.occupiedBy != null) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean isInCheck (Board board, Colour colour){
        // TODO: Methode in Player, um auf enemy pieces zuzugreifen (momentan NullPointerExecption)
        // TODO: dadurch nur eine Methode für beide Fälle
        Square squareKing = board.getSquareOfKing(colour);
        ArrayList<Piece> enemies = board.getEnemyPieces(colour);
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.isAllowedPath(squareKing)
                    && isPathEmpty(enemyPiece, squareKing, board)) {
                System.out.println("schach" + colour + "true");
                return true;
            }
        }
        return false;
    }

    private boolean checkSafeSquare (Square end, Board board, Colour colour) {
        // TODO: Methode in Player, um auf enemy pieces zuzugreifen (momentan NullPointerExecption)
        // TODO: dadurch nur eine Methode für beide Fälle
        ArrayList<Piece> enemies = board.getEnemyPieces(colour);
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.getType() == Type.BISHOP
                    || enemyPiece.getType() == Type.ROOK
                    || enemyPiece.getType() == Type.QUEEN) {
                if (isPathEmpty(enemyPiece, board.getSquareOfKing(colour), board)) {
                    return false;
                }
            } else if (enemyPiece.getType() == Type.KNIGHT) {
                if (enemyPiece.isAllowedPath(end)) {
                    return false;
                }
            } else if (enemyPiece.getType() == Type.KING) {
                if (enemyPiece.isSurroundingSquare(end)) {
                    return false;
                }
            } else {
                if (enemyPiece.pawnCanCapture(end)) {
                    return false;
                }
            }
        /*} else {
            for (int i = 0; i < board.whitePieces.size(); i++) {
                Piece enemyPiece = board.whitePieces.get(i);
                if (enemyPiece.getType() == Type.BISHOP
                        || enemyPiece.getType() == Type.ROOK
                        || enemyPiece.getType() == Type.QUEEN) {
                    if (isPathEmpty(enemyPiece, board.getSquareOfKing(colour), board)) {
                        return false;
                    }
                } else if (enemyPiece.getType() == Type.KNIGHT) {
                    if (enemyPiece.isAllowedPath(end)) {
                        return false;
                    }
                } else if (enemyPiece.getType() == Type.KING) {
                    if (enemyPiece.isSurroundingSquare(end)) {
                        return false;
                    }
                } else {
                    if (enemyPiece.pawnCanCapture(end)) {
                        return false;
                    }
                }
            }
        }*/
        }
        return true;
    }

    /**
     * Generates Path if Piece moves more than one Square
     * @param type Type of Piece
     * @param start Start-Square of Movement
     * @param end End-Square of Movement
     * @param board Board of current game
     * @return Path of Moving Piece
     */
    private ArrayList<Square> generatePath(Type type, Square start, Square end, Board board) {
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
}

