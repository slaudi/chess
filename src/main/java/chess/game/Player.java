package chess.game;

/**
 * Player class which defines each of two Players
 */
public class Player {
    Colour colour;
    boolean inCheck = false;
    boolean loser = false;

    /**
     * Creates a new Player instance.
     *
     * @param colour Decides if Colour of Players Chess-Pieces is black or white
     */
    public Player(Colour colour) {
        setColour(colour);
    }


    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour x) {
        this.colour = x;
    }

    public boolean isInCheck() {
        return this.inCheck;
    }

    public void setInCheck(boolean x) {
        this.inCheck = x;
    }

    /**
     * Getter for the variable 'loser'
     * @return boolean Returns 'true' if the Player lost the game
     */
    public boolean isLoser() {
        return loser;
    }

    public void setLoser(boolean loose) {
        this.loser = loose;
    }



}
