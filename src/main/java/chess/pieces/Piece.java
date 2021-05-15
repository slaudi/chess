package chess.pieces;

import chess.game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Piece class is the Superclass of the chess pieces on the board of the game
 */
public abstract class Piece {

    Square square;
    Colour colour;
    boolean hasMoved;

    /**
     * Constructor for a Piece
     * @param square the location of the Piece
     * @param colour the Colour object associated with the Piece
     */
    public Piece(Square square, Colour colour) {
        this.square = square;
        this.colour = colour;
        this.hasMoved = false;
    }

    /**
     * Getter for the position of the Piece.
     * @return Square The location of the Piece
     */
    public abstract Square getSquare();

    /**
     * Setter for the location of a Piece, used to update the location after a move.
     * @param square The new location of a Piece after a move
     */
    public abstract void setSquare(Square square);

    /**
     * Getter for the colour of a Piece
     * @return Colour The colour of the Piece
     */
    public abstract Colour getColour();

    /**
     * Getter for the type of a Piece
     * @return Type The type of the Piece
     */
    public abstract Type getType();

    /**
     * Getter for the variable 'hasMoved', used to determine if the Piece has moved already for castling or en passant
     * @return boolean A boolean indicating if the Piece has moved yet
     */
    public abstract boolean isHasMoved();

    /**
     * Setter for the variable 'hasMoved', is set to true after the first move of a Piece
     * @param x The boolean 'true' after a Piece has moved
     */
    public abstract void setHasMoved(boolean x);

    /**
     * A function to determine if a Piece is printed on the chess board in upper or lower case
     * depending on the colour of it
     *
     * @return a String representing the Piece on the chess board
     */
    @Override
    public abstract String toString();

    /**
     * Determines if a move is valid based on the type of the Piece
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    public abstract boolean isPiecesMove(Square finalSquare, Board chessBoard);

    /**
     * Evaluates if direct path from one square to another is empty
     *
     * @param piece Piece which has to move
     * @param finalSquare Square where piece has to go to
     * @return returns if selected path is empty
     */
    public boolean isPathEmpty (Piece piece, Square finalSquare, Board chessBoard){
        if (isSurroundingSquare(piece.getSquare(), finalSquare)) {
            return finalSquare.getOccupiedBy() == null || finalSquare.getOccupiedBy().getColour() != this.colour;
        }
        List<Square> path = piece.generatePath(finalSquare);
        if (path.isEmpty()) {
            return true;
        }
        if (piece.getType() == Type.KNIGHT) {
            // Knights can leap
            return true;
        } else {
            for (Square visitedSquare : path) {
                int visitedSquare_x = visitedSquare.getX();
                int visitedSquare_y = visitedSquare.getY();
                if (chessBoard.getChessBoard()[visitedSquare_x][visitedSquare_y].getOccupiedBy() != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Generates Path of the visited Squares in between the first and last Square if Piece
     * moves more than one Square.
     *
     * @param finalSquare The final Square of the move.
     * @return List<Square> A list of the visited Squares except for the first and last one.
     */
    public List<Square> generatePath(Square finalSquare) {
        int[][] dir = piecesDirection(finalSquare);
        int dir_x = dir[0][0];
        int dir_y = dir[0][1];
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());
        int squaresVisited;

        if (diff_x == diff_y || diff_y == 0) {
            // Piece moves diagonally or horizontally
            squaresVisited = diff_x;
        } else if (dir_x == 0 && dir_y == 0) {
            // Knight can leap, doesn't need a path
            squaresVisited = 0;
        } else {
            // Piece moves vertically
            squaresVisited = diff_y;
        }

        Square[][] move = new Square[1][squaresVisited];
        if (squaresVisited > 0) {
            move = new Square[1][squaresVisited - 1];
        }

        if(squaresVisited - 1 >= 1) {
            // Piece moves more than one square
            for (int i = 0; i < squaresVisited -1; i++) {
                // stores squares except start and final square
                int x = this.square.getX() + dir_x * (i + 1);
                int y = this.square.getY() + dir_y * (i + 1);
                move[0][i] = new Square(x, y);
            }
        }
        return new ArrayList<>(Arrays.asList(move[0]).subList(0, move[0].length));
    }

    /**
     * Evaluates if a Square is directly next to a selected Piece
     * @param squareOfInterest The Square where the Piece wants to go to
     * @return boolean Returns 'true' if selected Square to move to is only one Square away from the Piece
     */
    public static boolean isSurroundingSquare(Square piecesSquare, Square squareOfInterest){
        int diffX = piecesSquare.getX() - squareOfInterest.getX();
        int diffY = piecesSquare.getY() - squareOfInterest.getY();
        if (diffX == 0 && diffY == 0) {
            return false;
        }
        return Math.abs(diffX) < 2 && Math.abs(diffY) < 2;
    }

    private int[][] piecesDirection(Square finalSquare) {
        int[][] dir = new int[1][2];
        if (this instanceof Queen) {
            dir = ((Queen)this).movingDirection(finalSquare);
        } else if (this instanceof Bishop) {
            dir = ((Bishop)this).movingDirection(finalSquare);
        } else if (this instanceof Rook) {
            dir = ((Rook) this).movingDirection(finalSquare);
        } else if (this instanceof Pawn) {
            dir = ((Pawn) this).movingDirection(finalSquare);
        } else if (this instanceof King) {
            dir = ((King)this).movingDirection(finalSquare);
        } else {
            // Knight doesn't need a path, can leap
            dir[0][0] = 0;
            dir[0][1] = 0;
        }
        return dir;
    }

}
