package chess.game;

/**
 * Player class which defines each of two Players
 */
public class Player {
    public Colour colour;
    public boolean inCheck;
    public boolean isCheckMate;
    public boolean isLoser;

    /**
     * Create a new Player instance.
     *
     * @param colour Decides if Colour of Players Chess-Pieces is black or white
     */
    public Player(Colour colour) {
        setColour(colour);
        setInCheck(false);
        setCheckMate(false);
        setLoser(false);

    }

    /**
     * Sets Player-Colour two black or white.
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


}
