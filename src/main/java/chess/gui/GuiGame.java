package chess.gui;

import chess.game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * HelperClass for Gameclass for Methods only needed when Playing in GUI
 */
public class GuiGame {

    /**
     * Kind of Superclass for GUIGame
     */
    public Game game;

    Square squareStart;
    Square squareFinal;
    boolean isRotatingBoard;
    boolean highlightPossibleMoves;
    boolean allowedToChangeSelectedPiece; //in processingMove
    boolean hintInCheck;
    boolean freshGame;
    boolean draw;

    /**
     * Constructor for GuiGame-Objects
     */
    public GuiGame(){
        this.game = new Game();

        this.squareStart = null;
        this.squareFinal = null;
        this.isRotatingBoard = true;
        this.highlightPossibleMoves = true;
        this.allowedToChangeSelectedPiece = false;
        this.hintInCheck = true;
        this.freshGame = true;
        this.draw = false;
    }

    void setSquareStart(Square square){
        this.squareStart = square;
    }

    void setSquareFinal(Square square){
        this.squareFinal = square;
    }

    void setDraw(boolean bool){
        this.draw = bool;
    }

    Square getSquareStart(){
        return this.squareStart;
    }

    Square getSquareFinal(){
        return this.squareFinal;
    }

    void setBothMovingSquares(Square square){
        if(this.getSquareStart() == null){
            this.setSquareStart(square);
        } else {
            this.setSquareFinal(square);
        }
    }

    List<Square> computePossibleSquares() {
        List<Square> possibleSquares = new ArrayList<>();
        for (int y = 0; y < game.chessBoard.getHeight(); y++) {
            for (int x = 0; x < game.chessBoard.getWidth(); x++) {
                if (game.isMoveAllowed(getSquareStart().getOccupiedBy(), game.chessBoard.getSquareAt(x,y))){
                    if(getSquareStart().getOccupiedBy().getType() != Type.KING){
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
