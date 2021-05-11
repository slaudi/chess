package chess.game;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

import static chess.game.Colour.BLACK;
import static chess.game.Colour.WHITE;

/**
 * Board class representing the Chess-Board of current game.
 */
public class Board {
    private final Square[][] chessBoard; // Board can access class Square
    List<Piece> whitePieces = new ArrayList<>(16);
    List<Piece> blackPieces = new ArrayList<>(16);


    /**
     * Create a new Board instance when starting a new Game.
     */
    public Board() {
        chessBoard = new Square[8][8];
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                chessBoard[x][y] = new Square(Label.values()[8*x+y], x, y);
            }
        }
        startingFormation();                //sets Pieces on Board to start game
        setAlliance();                      //generates piece-groups of same colour
    }

    /**
     * Getter of Board
     * @return Square[][] two-dimensional Sqaure-Array representing the current state of game
     */
    public Square[][] getChessBoard() {
        return this.chessBoard;
    }

    /**
     * Sets each Chess-Piece on its initial position.
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

    /**
     * Add all pieces on the board to a ArrayList whitePieces and blackPieces according to their colour
     */
    void setAlliance() {
        for(int i = 0; i < 8; i++) {
            whitePieces.add(this.chessBoard[i][7].getOccupiedBy());
            whitePieces.add(this.chessBoard[i][6].getOccupiedBy());
            blackPieces.add(this.chessBoard[i][0].getOccupiedBy());
            blackPieces.add(this.chessBoard[i][1].getOccupiedBy());
        }
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
     * Returns the Piece standing on selected Square.
     * @param input Console-Input as String
     * @return Piece which is on selected Square.
     */
    public Piece getMovingPieceFromInput(String input){
        String start = input.substring(0, 2);
        int startX = Square.getXFromString(start);
        int startY = Square.getYFromString(start);
        return this.chessBoard[startX][startY].getOccupiedBy();
    }

    /**
     * Computes Square where Piece-Movement should start from console input.
     * @param input Console-Input as String.
     * @return Square where Piece-Movement is going to start.
     */
    public Square getStartSquareFromInput(String input){
        String start = input.substring(0, 2);
        int startX = Square.getXFromString(start);
        int startY = Square.getYFromString(start);
        return this.chessBoard[startX][startY];
    }

    /**
     * Computes Square where Piece-Movement should end from console input.
     * @param input Console-Input as String
     * @return Square where Piece-Movement is going to end.
     */
    public Square getFinalSquareFromInput(String input){
        String end = input.substring(3, 5);
        int finalX = Square.getXFromString(end);
        int finalY = Square.getYFromString(end);
        return this.chessBoard[finalX][finalY];
    }

    /**
     * When a promotion is possible, the function extracts the letter from the input to determine the
     * Piece to which the player wants to promote the Pawn
     *
     * @param input a String of the input
     * @return a char representing the requested promotion
     */
    public char getPromotionKey(String input) {
        if (input.length() == 6) {
            return input.charAt(5);
        } else {
            return ' ';
        }
    }

    /**
     * Finds Square of King
     * @param colour Colour of King searched for
     * @return Square Square King currently stands on
     */
    protected Square getSquareOfKing(Colour colour){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(chessBoard[i][j].getOccupiedBy() != null) {
                    if (chessBoard[i][j].getOccupiedBy().getType() == Type.KING
                            && chessBoard[i][j].getOccupiedBy().getColour() == colour) {
                        return chessBoard[i][j];
                    }
                }
            }
        }
        return null;
    }

}
