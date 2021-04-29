package chess.game;

import chess.pieces.*;

import static chess.game.Colour.BLACK;
import static chess.game.Colour.WHITE;


/**
 * Board class representing the Chess-Board of current game.
 */
public class Board {
    public Square[][] board;


    /**
     * Create a new Board instance when starting a new Game.
     */
    public Board() {
        board = new Square[8][8];
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                board[x][y] = new Square(Label.values()[(8*x+y)]);
            }
        }
        startingFormation();
    }
    /**
     * Sets each Chess-Piece on its initial position.
     */
    public void startingFormation() {
        this.board[0][0].occupied = true;
        this.board[0][0].occupiedBy = new Rook(BLACK);
        this.board[0][1].occupied = true;
        this.board[0][1].occupiedBy = new Knight(BLACK);
        this.board[0][2].occupied = true;
        this.board[0][2].occupiedBy = new Bishop(BLACK);
        this.board[0][3].occupied = true;
        this.board[0][3].occupiedBy = new Queen(BLACK);
        this.board[0][4].occupied = true;
        this.board[0][4].occupiedBy = new King(BLACK);
        this.board[0][5].occupied = true;
        this.board[0][5].occupiedBy = new Bishop(BLACK);
        this.board[0][6].occupied = true;
        this.board[0][6].occupiedBy = new Knight(BLACK);
        this.board[0][7].occupied = true;
        this.board[0][7].occupiedBy = new Rook(BLACK);
        this.board[1][0].occupied = true;
        this.board[1][0].occupiedBy = new Pawn(BLACK);
        this.board[1][1].occupied = true;
        this.board[1][1].occupiedBy = new Pawn(BLACK);
        this.board[1][2].occupied = true;
        this.board[1][2].occupiedBy = new Pawn(BLACK);
        this.board[1][3].occupied = true;
        this.board[1][3].occupiedBy = new Pawn(BLACK);
        this.board[1][4].occupied = true;
        this.board[1][4].occupiedBy = new Pawn(BLACK);
        this.board[1][5].occupied = true;
        this.board[1][5].occupiedBy = new Pawn(BLACK);
        this.board[1][6].occupied = true;
        this.board[1][6].occupiedBy = new Pawn(BLACK);
        this.board[1][7].occupied = true;
        this.board[1][7].occupiedBy = new Pawn(BLACK);
        this.board[6][0].occupied = true;
        this.board[6][0].occupiedBy = new Pawn(WHITE);
        this.board[6][1].occupied = true;
        this.board[6][1].occupiedBy = new Pawn(WHITE);
        this.board[6][2].occupied = true;
        this.board[6][2].occupiedBy = new Pawn(WHITE);
        this.board[6][3].occupied = true;
        this.board[6][3].occupiedBy = new Pawn(WHITE);
        this.board[6][4].occupied = true;
        this.board[6][4].occupiedBy = new Pawn(WHITE);
        this.board[6][5].occupied = true;
        this.board[6][5].occupiedBy = new Pawn(WHITE);
        this.board[6][6].occupied = true;
        this.board[6][6].occupiedBy = new Pawn(WHITE);
        this.board[6][7].occupied = true;
        this.board[6][7].occupiedBy = new Pawn(WHITE);
        this.board[7][0].occupied = true;
        this.board[7][0].occupiedBy = new Rook(WHITE);
        this.board[7][1].occupied = true;
        this.board[7][1].occupiedBy = new Knight(WHITE);
        this.board[7][2].occupied = true;
        this.board[7][2].occupiedBy = new Bishop(WHITE);
        this.board[7][3].occupied = true;
        this.board[7][3].occupiedBy = new Queen(WHITE);
        this.board[7][4].occupied = true;
        this.board[7][4].occupiedBy = new King(WHITE);
        this.board[7][5].occupied = true;
        this.board[7][5].occupiedBy = new Bishop(WHITE);
        this.board[7][6].occupied = true;
        this.board[7][6].occupiedBy = new Knight(WHITE);
        this.board[7][7].occupied = true;
        this.board[7][7].occupiedBy = new Rook(WHITE);
    }

    public void toConsole(){
        for (int y = 0; y < 8; y++){
            System.out.print(8-y);
            for (int x = 0; x < 8; x++){
                if (this.board[y][x].occupied){
                    System.out.print(" " + this.board[y][x].occupiedBy.toString());
                }
                else{
                    System.out.print("  ");
                }
            }
            System.out.println(" ");
        }
        System.out.println("  a b c d e f g h");
    }
}
