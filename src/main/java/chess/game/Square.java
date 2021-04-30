package chess.game;

/**
 * Square class representing a single square of current Chess-Board.
 */
public class Square {
    public int name;
    public boolean occupied;
    public Piece occupiedBy;

    /**
     * Create a single square instance when starting a new Game.
     *
     * @param name The Number of the square to be initialized.
     */
    public Square(int name) {
        this.name = name;
        this.occupied = false;
        this.occupiedBy ="";
    }
}
