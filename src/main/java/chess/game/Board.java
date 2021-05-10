package chess.game;

import chess.pieces.*;

import java.util.ArrayList;

import static chess.game.Colour.BLACK;
import static chess.game.Colour.WHITE;

/**
 * Board class representing the Chess-Board of current game.
 */
public class Board {
    private final Square[][] board; // Board can access class Square
    ArrayList<Piece> whitePieces = new ArrayList<>(16);
    ArrayList<Piece> blackPieces = new ArrayList<>(16);


    /**
     * Create a new Board instance when starting a new Game.
     */
    public Board() {
        board = new Square[8][8];
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                board[x][y] = new Square(Label.values()[8*x+y], x, y);
            }
        }
        startingFormation();                //sets Pieces on Board to start game
        setAlliance();                      //generates piece-groups of same colour
    }

    /**
     * Getter of Board
     * @return Square[][] two-dimensional Sqaure-Array representing the current state of game
     */
    public Square[][] getBoard() {
        return this.board;
    }

    /**
     * Sets each Chess-Piece on its initial position.
     */
    void startingFormation() {
        // Black pieces
        this.board[0][0].setOccupiedBy(new Rook(this.board[0][0],BLACK));
        this.board[1][0].setOccupiedBy(new Knight(this.board[1][0], BLACK));
        this.board[2][0].setOccupiedBy(new Bishop(this.board[2][0], BLACK));;
        this.board[3][0].setOccupiedBy(new Queen(this.board[3][0], BLACK));
        this.board[4][0].setOccupiedBy(new King(this.board[4][0], BLACK));
        this.board[5][0].setOccupiedBy(new Bishop(this.board[5][0], BLACK));
        this.board[6][0].setOccupiedBy(new Knight(this.board[6][0], BLACK));
        this.board[7][0].setOccupiedBy(new Rook(this.board[7][0], BLACK));
        for(int i = 0; i < 8; i++) {
            this.board[i][1].setOccupiedBy(new Pawn(this.board[i][1], BLACK));
        }

        // White pieces
        this.board[0][7].setOccupiedBy(new Rook(this.board[0][7], WHITE));
        this.board[1][7].setOccupiedBy(new Knight(this.board[1][7], WHITE));
        this.board[2][7].setOccupiedBy(new Bishop(this.board[2][7], WHITE));
        this.board[3][7].setOccupiedBy(new Queen(this.board[3][7], WHITE));
        this.board[4][7].setOccupiedBy(new King(this.board[4][7], WHITE));
        this.board[5][7].setOccupiedBy(new Bishop(this.board[5][7], WHITE));
        this.board[6][7].setOccupiedBy(new Knight(this.board[6][7], WHITE));
        this.board[7][7].setOccupiedBy(new Rook(this.board[7][7], WHITE));
        for(int i = 0; i < 8; i++) {
            this.board[i][6].setOccupiedBy(new Pawn(this.board[i][6], WHITE));
        }
    }

    /**
     * Add all pieces on the board to a ArrayList whitePieces and blackPieces according to their colour
     */
    void setAlliance() {
        for(int i = 0; i < 8; i++) {
            whitePieces.add(this.board[i][7].getOccupiedBy());
            whitePieces.add(this.board[i][6].getOccupiedBy());
            blackPieces.add(this.board[i][0].getOccupiedBy());
            blackPieces.add(this.board[i][1].getOccupiedBy());
        }
    }



    /**
     * Prints current state of game to console.
     */
    public void toConsole(){
        for (int y = 0; y < 8; y++){
            System.out.print(8-y);
            for (int x = 0; x < 8; x++){
                if (this.board[x][y].getOccupiedBy() != null){
                    System.out.print(" " + this.board[x][y].getOccupiedBy().toString());
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
        return this.board[startX][startY].getOccupiedBy();
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
        return this.board[startX][startY];
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
        return this.board[finalX][finalY];
    }

    /**
     * Finds Square of King
     * @param colour Colour of King searched for
     * @return Square Square King currently stands on
     */
    protected Square getSquareOfKing(Colour colour){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j].getOccupiedBy() != null) {
                    if (board[i][j].getOccupiedBy().getType() == Type.KING
                            && board[i][j].getOccupiedBy().getColour() == colour) {
                        return board[i][j];
                    }
                }
            }
        }
        return null;
    }

}
