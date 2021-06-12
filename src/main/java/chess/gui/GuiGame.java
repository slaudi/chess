package chess.gui;

import chess.game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * HelperClass for Game class for Methods only needed when Playing in GUI.
 */
public class GuiGame {

    /**
     * The GuiGame class can access the actual Game class to make moves.
     */
    public Game game;

    Square squareStart = null;
    Square squareFinal = null;

    boolean isRotatingBoard = true;
    boolean highlightPossibleMoves = true;
    boolean allowedToChangeSelectedPiece = false;
    boolean hintInCheck = true;
    boolean turnAI = false;

    /**
     * Constructor for GuiGame-Objects
     */
    public GuiGame(){
        this.game = new Game();
    }

    Square getSquareStart(){
        return this.squareStart;
    }

    void setSquareStart(Square square){
        this.squareStart = square;
    }

    Square getSquareFinal(){
        return this.squareFinal;
    }

    void setSquareFinal(Square square){
        this.squareFinal = square;
    }


    void setBothSquares(Square square){
        if(this.getSquareStart() == null){
            this.setSquareStart(square);
        } else {
            this.setSquareFinal(square);
        }
    }


    // computes a list a possible Squares to move to for a Piece to highlight in GUI
    List<Square> computePossibleSquares() {
        List<Square> possibleSquares = new ArrayList<>();
        for (int y = 0; y < game.chessBoard.getHeight(); y++) {
            for (int x = 0; x < game.chessBoard.getWidth(); x++) {
                if (game.isMoveAllowed(getSquareStart().getOccupiedBy(), game.chessBoard.getSquareAt(x,y))){
                    if(this.squareStart.getOccupiedBy().getType() != Type.KING){
                        possibleSquares.add(game.chessBoard.getSquareAt(x,y));
                    } else {
                        if(game.isSafeSquare(game.chessBoard.getSquareAt(x,y))){
                            possibleSquares.add(game.chessBoard.getSquareAt(x,y));
                        }
                    }
                }
            }
        }
        return possibleSquares;
    }

}
