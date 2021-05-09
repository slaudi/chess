package chess.game;

import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Player class which defines each of two Players
 */
public class Player {
    Colour colour;
    boolean inCheck = false;
    boolean isCheckMate = false;
    boolean loser = false;

    /**
     * Create a new Player instance.
     *
     * @param colour Decides if Colour of Players Chess-Pieces is black or white
     */
    public Player(Colour colour) {
        setColour(colour);
    }


    public Colour getColour() {
        return colour;
    }

    /**
     * Sets Player-Colour to black or white.
     *
     * @param x Colour: Black or White
     */
    public void setColour(Colour x) {
        this.colour = x;
    }

    public boolean getInCheck() {
        return this.inCheck;
    }

    /**
     * Sets Player-Check-Status to true or false.
     *
     * @param x boolean
     */
    public void setInCheck(boolean x) {
        this.inCheck = x;
    }

    public boolean getCheckMate() {
        return this.isCheckMate;
    }

    /**
     * Sets Player-CheckMate-Status to false or true(gameOver).
     *
     * @param x boolean
     */
    public void setCheckMate(boolean x) {
        this.isCheckMate = x;
    }

    public boolean getLoser() {
        return loser;
    }

    /**
     * Sets if Player has lost.
     *
     * @param loose Boolean: true = Player has lost.
     */
    public void setLoser(boolean loose) {
        this.loser = loose;
    }



}
