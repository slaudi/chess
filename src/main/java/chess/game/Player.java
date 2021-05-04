package chess.game;

import chess.pieces.Piece;

import java.util.Vector;

/**
 * Player class which defines each of two Players
 */
public class Player {
    public Colour colour;
    public boolean inCheck = false;
    public boolean isCheckMate = false;
    public boolean isLoser = false;
    public Game currentGame;

    /**
     * Create a new Player instance.
     *
     * @param colour Decides if Colour of Players Chess-Pieces is black or white
     */
    public Player(Colour colour) {
        setColour(colour);
    }

    /**
     * Sets Player-Colour to black or white.
     *
     * @param x Colour: Black or White
     */
    public void setColour(Colour x) {
        this.colour = x;
    }

    /**
     * Sets Player-Check-Status to true or false.
     *
     * @param x boolean
     */
    public void setInCheck(boolean x) {
        this.inCheck = x;
    }

    /**
     * Sets Player-CheckMate-Status to false or true(gameOver).
     *
     * @param x boolean
     */
    public void setCheckMate(boolean x) {
        this.isCheckMate = x;
    }

    /**
     * Sets if Player has lost.
     *
     * @param loose Boolean: true = Player has lost.
     */
    public void setLoser(boolean loose) {
        this.isLoser = loose;
    }

    /**
     * A function determining all the allied Pieces on the current board of a certain Piece
     *
     * @param colour the Colour of the Piece
     * @return a Vector containing all allied Pieces
     */
    public Vector<Piece> getAlliedPieces(Colour colour) {
        Vector<Piece> alliedPieces;
        if(colour == Colour.WHITE) {
            alliedPieces = currentGame.board.whitePieces;
        } else alliedPieces = currentGame.board.blackPieces;
        return alliedPieces;
    }

    /**
     * A function determining all the enemy Pieces on the current board of a certain Piece
     * @param colour the Colour of the Piece
     * @return a Vector containing all enemy Pieces
     */
    public Vector<Piece> getEnemyPieces(Colour colour) {
        Vector<Piece> enemyPieces;
        if(colour == Colour.WHITE) {
            enemyPieces = currentGame.board.blackPieces;
        } else enemyPieces = currentGame.board.whitePieces;
        return enemyPieces;
    }

}
