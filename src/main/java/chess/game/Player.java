package chess.game;

import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * The Player class defines each of the two Players and their allies and enemies on the board.
 */
public class Player {
    private Colour colour;
    boolean inCheck = false;
    boolean loser = false;

    /**
     * Constructor for creating a new player.
     *
     * @param colour The colour determines if the player is allied with the white or black
     *               chess pieces.
     */
    public Player(Colour colour) {
        this.colour = colour;
    }

    /**
     * Getter of Player-colour
     * @return returns Player-Colour
     */
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

    /**
     * Gets Player Check-Status
     * @return returns Players check-status
     */
    public boolean isInCheck() {
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

    public boolean isLoser() {
        return this.loser;
    }

    public void setLoser(boolean lost) {
        this.loser = lost;
    }

    /**
     * A function gathering all allied Pieces of a player on the current board
     * in a ArrayList by removing the already beaten pieces from the Alliance.
     *
     * @return ArrayList Contains all active allied Pieces
     */
    List<Piece> getAlliedPieces(List<Piece> beatenPieces, Board chessBoard) {
        List<Piece> allies;
        List<Piece> piecesToRemove = new ArrayList<>();
        if(this.getColour() == Colour.WHITE) {
            allies = chessBoard.getWhiteAlliance();
        } else {
            allies = chessBoard.getBlackAlliance();
        }
        for (Piece ally : allies) {
            for (Piece beaten : beatenPieces) {
                if (ally.getColour() == beaten.getColour() && ally.getType() == beaten.getType()
                        && beaten.equals(ally)) {
                    piecesToRemove.add(beaten);
                }
            }
        }
        for (Piece piece : piecesToRemove) {
            allies.remove(piece);
        }
        return allies;
    }

    /**
     * A function gathering all enemy Pieces of a player on the current board
     * in a ArrayList by removing the already beaten pieces from the Alliance.
     *
     * @return ArrayList Contains all active enemy Pieces.
     */
    List<Piece> getEnemyPieces(List<Piece> beatenPieces, Board chessBoard) {
        List<Piece> enemies;
        List<Piece> piecesToRemove = new ArrayList<>();
        if(this.getColour() == Colour.WHITE) {
            enemies = chessBoard.getBlackAlliance();
        } else {
            enemies = chessBoard.getWhiteAlliance();
        }
        for (Piece enemy : enemies) {
            for (Piece beaten : beatenPieces) {
                if (enemy.getColour() == beaten.getColour() && enemy.getType() == beaten.getType()
                        && beaten.equals(enemy)) {
                    piecesToRemove.add(enemy);
                }
            }
        }
        for (Piece piece : piecesToRemove) {
            enemies.remove(piece);
        }
        return enemies;
    }

}
