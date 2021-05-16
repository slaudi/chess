package chess.pieces;

import chess.game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The Piece class is the Superclass of the chess pieces on the board of the game.
 */
public abstract class Piece {

    Square square;
    Colour colour;
    boolean notMoved = true;

    /**
     * Constructor for creating a Piece.
     *
     * @param square The location of the Piece on the board.
     * @param colour The Colour associated with the Piece.
     */
    public Piece(Square square, Colour colour) {
        this.square = square;
        this.colour = colour;
    }

    public abstract Square getSquare();

    public abstract void setSquare(Square square);

    public abstract Colour getColour();

    /**
     * Getter for the type of Piece.
     *
     * @return Type Returns the type of the Piece.
     */
    public abstract Type getType();

    /**
     * Getter for the variable 'notMoved' to determine if a Piece has moved yet.
     *
     * @return boolean Returns 'true' if the Piece has not moved yet.
     */
    public abstract boolean hasNotMoved();

    public abstract void setNotMoved(boolean x);

    /**
     * A function to determine if a Piece is printed on the chess board in upper or lower case
     * depending on its colour.
     *
     * @return String Represents the Piece and its colour on the printed chess board.
     */
    @Override
    public abstract String toString();

    /**
     * A function determining if a move is valid based on the type of the Piece.
     *
     * @param finalSquare The square where the Piece should move to.
     * @return boolean Returns 'true' if the move is allowed for the Piece.
     */
    public abstract boolean isPiecesMove(Square finalSquare, Board chessBoard);

    /**
     * A function determining if the direct path from one square to another is empty
     * except for the start and final Square.
     *
     * @param finalSquare   The Square where the Piece should go to.
     * @return boolean Returns 'true' if the path is empty.
     */
    public boolean isPathEmpty (Square finalSquare, Board chessBoard){
        if (isSurroundingSquare(this.getSquare(), finalSquare)) {
            return true;
        }
        List<Square> path = this.generatePath(finalSquare, chessBoard);
        if (path.isEmpty()) {
            return true;
        }
        if (this.getType() == Type.KNIGHT) {
            // Knights can leap
            return true;
        } else {
            for (Square visitedSquare : path) {
                int visitedSquare_x = visitedSquare.getX();
                int visitedSquare_y = visitedSquare.getY();
                if (chessBoard.getBoard()[visitedSquare_x][visitedSquare_y].getOccupiedBy() != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * A function generating a path of the visited Squares in between the first and last Square if Piece
     * moves more than one Square.
     *
     * @param finalSquare The final Square of the move.
     * @return ArrayList<Square> A list of the visited Squares except for the first and last one.
     */
    public List<Square> generatePath(Square finalSquare, Board chessboard) {
        int[][] dir = piecesDirection(finalSquare);
        int dir_x = dir[0][0];
        int dir_y = dir[0][1];
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());
        int squaresVisited;
        if (diff_x == diff_y || diff_y == 0) {
            // Piece moves diagonally or horizontally
            squaresVisited = diff_x;
        } else if (diff_x == 0) {
            // Piece moves vertically
            squaresVisited = diff_y;
        } else {
            // Knight can leap, doesn't need a path
            squaresVisited = 0;
        }

        ArrayList<Square> path = new ArrayList<>();
        if(squaresVisited > 1) {
            // Piece moves more than one square
            for (int i = 1; i < squaresVisited; i++) {
                // stores squares except start and final square
                int x = this.square.getX() + dir_x * i;
                int y = this.square.getY() + dir_y * i;
                //move[0][i] = new Square(Label.values()[x+y], x, y);
                path.add(chessboard.getBoard()[x][y]);
            }
        }
        return path;
    }

    /**
     * A function determining if a Square is directly next to a selected Piece.
     *
     * @param squareOfInterest The Square where the Piece should move to.
     * @return boolean Returns 'true' if the selected Square is only one Square away from the Piece.
     */
    public static boolean isSurroundingSquare(Square piecesSquare, Square squareOfInterest){
        int diffX = piecesSquare.getX() - squareOfInterest.getX();
        int diffY = piecesSquare.getY() - squareOfInterest.getY();
        if (diffX == 0 && diffY == 0) {
            return false;
        }
        return Math.abs(diffX) < 2 && Math.abs(diffY) < 2;
    }

    /**
     * A function determining if a Piece can move to at least one square on the board to help find
     * out if a game is in a draw.
     *
     * @param currentBoard The current board.
     * @return boolean Returns 'true' if the Piece can move somewhere on the board.
     */
    public boolean canPieceMove(Board currentBoard) {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if (currentBoard.getBoard()[x][y].getOccupiedBy() == null) {
                    Square possibleFinalSquare = currentBoard.getBoard()[x][y];
                    if (this.isPiecesMove(possibleFinalSquare, currentBoard)
                            && isPathEmpty(possibleFinalSquare, currentBoard)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    int[][] piecesDirection(Square finalSquare) {
        int[][] dir = new int[1][2];
        if (this instanceof Queen) {
            dir = ((Queen)this).movingDirection(finalSquare);
        } else if (this instanceof Bishop) {
            dir = ((Bishop)this).movingDirection(finalSquare);
        } else if (this instanceof Rook) {
            dir = ((Rook) this).movingDirection(finalSquare);
        } else if (this instanceof Pawn) {
            dir = ((Pawn) this).movingDirection(finalSquare);
        } else {
            // Knight doesn't need a path, can leap
            // King doesn't need a path
            dir[0][0] = 0;
            dir[0][1] = 0;
        }
        return dir;
    }

}
