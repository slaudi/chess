package chess.game;

import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class which defines each of two Players
 */
public class Player {
    private Colour colour;
    private boolean inCheck = false;
    private boolean loser = false;

    /**
     * Creates a new Player instance.
     *
     * @param colour Decides if Colour of Players Chess-Pieces is black or white
     */
    public Player(Colour colour) {
        this.colour = colour;
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

    public boolean isLoser() {
        return this.loser;
    }

    public void setLoser(boolean lost) {
        this.loser = lost;
    }

    /**
     * A function putting all the allied Pieces (the same colour) on the current board of a certain Piece
     * in a ArrayList.
     * @return ArrayList Containing all active allied Pieces
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
     * A function putting all the enemy Pieces (the other colour) on the current board of a certain Piece
     * in a ArrayList.
     * @return ArrayList Containing all active enemy Pieces.
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
