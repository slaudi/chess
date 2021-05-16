package chess.game;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chess.game.Colour.BLACK;
import static chess.game.Colour.WHITE;

/**
 * Board class representing the chess board of the current game.
 */
public class Board {
    public final Square[][] chessBoard; // Board can access class Square
    private final List<Piece> whitePieces = new ArrayList<>(16);
    private final List<Piece> blackPieces = new ArrayList<>(16);

    /**
     * Constructor for creating a new chess board when starting a new Game.
     */
    public Board() {
        this.chessBoard = new Square[8][8];
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                this.chessBoard[x][y] = new Square(Label.values()[8*x+y], x, y);
            }
        }
        startingFormation();                //sets Pieces on Board to start game
        setAlliance();                      //generates piece-groups of same colour
    }

    /**
     * Getter of a deep copy of the current board.
     *
     * @return Square[][] A copy of the current board.
     */
    public Square[][] getChessBoard() {
        Square[][] deepCopy = new Square[this.chessBoard.length][];
        for (int i = 0; i < this.chessBoard.length; i++) {
            deepCopy[i] = Arrays.copyOf(this.chessBoard[i], this.chessBoard[i].length);
        }
        return deepCopy;
    }

    /**
     * Getter for Board.
     *
     * @return Returns board
     */
    public Square[][] getBoard(){
        return this.chessBoard;
    }


    /**
     * Sets each Chess-Piece on its initial position when starting a new Game.
     */
    void startingFormation() {
        // Black pieces
        this.chessBoard[0][0].setOccupiedBy(new Rook(this.chessBoard[0][0],BLACK));
        this.chessBoard[1][0].setOccupiedBy(new Knight(this.chessBoard[1][0], BLACK));
        this.chessBoard[2][0].setOccupiedBy(new Bishop(this.chessBoard[2][0], BLACK));
        this.chessBoard[3][0].setOccupiedBy(new Queen(this.chessBoard[3][0], BLACK));
        this.chessBoard[4][0].setOccupiedBy(new King(this.chessBoard[4][0], BLACK));
        this.chessBoard[5][0].setOccupiedBy(new Bishop(this.chessBoard[5][0], BLACK));
        this.chessBoard[6][0].setOccupiedBy(new Knight(this.chessBoard[6][0], BLACK));
        this.chessBoard[7][0].setOccupiedBy(new Rook(this.chessBoard[7][0], BLACK));
        for(int i = 0; i < 8; i++) {
            this.chessBoard[i][1].setOccupiedBy(new Pawn(this.chessBoard[i][1], BLACK));
        }

        // White pieces
        this.chessBoard[0][7].setOccupiedBy(new Rook(this.chessBoard[0][7], WHITE));
        this.chessBoard[1][7].setOccupiedBy(new Knight(this.chessBoard[1][7], WHITE));
        this.chessBoard[2][7].setOccupiedBy(new Bishop(this.chessBoard[2][7], WHITE));
        this.chessBoard[3][7].setOccupiedBy(new Queen(this.chessBoard[3][7], WHITE));
        this.chessBoard[4][7].setOccupiedBy(new King(this.chessBoard[4][7], WHITE));
        this.chessBoard[5][7].setOccupiedBy(new Bishop(this.chessBoard[5][7], WHITE));
        this.chessBoard[6][7].setOccupiedBy(new Knight(this.chessBoard[6][7], WHITE));
        this.chessBoard[7][7].setOccupiedBy(new Rook(this.chessBoard[7][7], WHITE));
        for(int i = 0; i < 8; i++) {
            this.chessBoard[i][6].setOccupiedBy(new Pawn(this.chessBoard[i][6], WHITE));
        }
    }

    private void setAlliance() {
        for(int i = 0; i < 8; i++) {
            this.whitePieces.add(this.chessBoard[i][7].getOccupiedBy());
            this.whitePieces.add(this.chessBoard[i][6].getOccupiedBy());
            this.blackPieces.add(this.chessBoard[i][0].getOccupiedBy());
            this.blackPieces.add(this.chessBoard[i][1].getOccupiedBy());
        }
    }

    /**
     * Getter for the white alliance.
     * @return ArrayList All white pieces.
     */
    List<Piece> getWhiteAlliance() {
        return this.whitePieces;
    }

    /**
     * Getter for the black alliance.
     * @return ArrayList All black pieces.
     */
    List<Piece> getBlackAlliance() {
        return this.blackPieces;
    }



    /**
     * Prints current state of game to console.
     */
    public void toConsole(){
        for (int y = 0; y < 8; y++){
            System.out.print(8-y);
            for (int x = 0; x < 8; x++){
                if (this.chessBoard[x][y].getOccupiedBy() != null){
                    System.out.print(" " + this.chessBoard[x][y].getOccupiedBy().toString());
                }
                else{
                    System.out.print("  ");
                }
            }
            System.out.println(" ");
        }
        System.out.println("  a b c d e f g h");
    }

    /**
     * Determines the Piece standing on the selected Square.
     *
     * @param input The console input as a String.
     * @return Piece The Piece which was selected by the player.
     */
    public Piece getMovingPieceFromInput(String input){
        String start = input.substring(0, 2);
        int startX = Square.getXFromString(start);
        int startY = Square.getYFromString(start);
        return this.chessBoard[startX][startY].getOccupiedBy();
    }

    /**
     * Determines from the Console-Input the Square where the move should start.
     *
     * @param input The console input as a String.
     * @return Square The square where the move is going to start.
     */
    public Square getStartSquareFromInput(String input){
        String start = input.substring(0, 2);
        int startX = Square.getXFromString(start);
        int startY = Square.getYFromString(start);
        return this.chessBoard[startX][startY];
    }

    /**
     * Determines from the Console-Input the Square where the move should end.
     *
     * @param input The console input as a String.
     * @return Square The square where the move is going to end.
     */
    public Square getFinalSquareFromInput(String input){
        String end = input.substring(3, 5);
        int finalX = Square.getXFromString(end);
        int finalY = Square.getYFromString(end);
        return this.chessBoard[finalX][finalY];
    }

    /**
     * When a promotion is possible, the function extracts the letter from the input to determine the
     * Piece to which the player wants to promote the Pawn.
     *
     * @param input The console input as a String.
     * @return char A letter representing the requested promotion; Q = Queen, B = Bishop, N = knight,
     *              R = Rook.
     */
    public char getPromotionKey(String input) {
        if (input.length() == 6) {
            return input.charAt(5);
        } else {
            return ' ';
        }
    }

    /**
     * Finds the Square of the current players King.
     *
     * @param colour The colour of the player which needs the position of their King.
     * @return Square The Square the King currently stands on
     */
    protected Square getSquareOfKing(Colour colour){
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(this.chessBoard[i][j].getOccupiedBy() != null && this.chessBoard[i][j].getOccupiedBy().getType() == Type.KING
                            && this.chessBoard[i][j].getOccupiedBy().getColour() == colour) {
                        return this.chessBoard[i][j];
                    }
                }
            }
        return null;
    }

    /**
     * Returns Piece at given Square
     * @param x X-Coordinate of Square
     * @param y Y-Coordinate of Square
     * @return Piece at given Coordinates
     */
    public Piece getPieceAt( int x, int y){
        return this.chessBoard[x][y].getOccupiedBy();
    }

    /**
     * Sets Piece on given Square
     * @param x X-Coordinate of Square
     * @param y Y-Coordinate of Square
     * @param piece Piece to set on Square
     */
    public void setPieceAt(int x, int y, Piece piece){
        this.chessBoard[x][y].setOccupiedBy(piece);
    }

    /**
     * Returns Square at given Coordinates
     * @param x X-Coordinate of Square
     * @param y Y-Coordinate of Square
     * @return Square at given Coordinates
     */
    public Square getSquareAt( int x, int y){
        return this.chessBoard[x][y];
    }

    /**
     * clears Board from Pieces
     */
    public void clearBoard(){
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                this.chessBoard[x][y].setOccupiedBy(null);
            }
        }
    }

    /**
     * clears White Ally-List
     */
    public void clearWhiteAlliance(){
        this.whitePieces.clear();
    }

    /**
     * clears Black Ally-List
     */
    public void clearBlackAlliance(){
        this.blackPieces.clear();
    }

    /**
     * adds given Piece to List
     * @param piece Piece to add to List
     */
    public void addWhiteAlliance(Piece piece){
        this.whitePieces.add(piece);
    }

    /**
     * adds given Piece to List
     * @param piece Piece to add to List
     */
    public void addBlackAlliance(Piece piece){
        this.blackPieces.add(piece);
    }

}
