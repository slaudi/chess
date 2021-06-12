package chess.game;

import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * The Player class defines each of the two Players and their allies and enemies on the board.
 */
public class Player {
    private final Colour colour;
    private boolean inCheck = false;
    private boolean loser = false;

    /**
     * Constructor for creating a new player.
     *
     * @param colour The colour determines if the player is allied with the white or black
     *               chess pieces.
     */
    public Player(Colour colour) {
        this.colour = colour;
    }


    public Colour getColour() {
        return colour;
    }

    public boolean isInCheck() {
        return inCheck;
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }

    public boolean isLoser() {
        return loser;
    }

    public void setLoser(boolean loser) {
        this.loser = loser;
    }


    /**
     * A function gathering all allied Pieces of a player on the current board
     * in a ArrayList by removing the already beaten pieces from the Alliance.
     * @param beatenPieces BeatenPieces-List of current Game
     * @param chessBoard Current-Chessboard
     * @return ArrayList Contains all active allied Pieces
     */
    public List<Piece> getAlliedPieces(List<Piece> beatenPieces, Board chessBoard) {
        List<Piece> allies;
        List<Piece> piecesToRemove = new ArrayList<>();
        if(this.colour == Colour.WHITE) {
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
     * @param beatenPieces BeatenPieces-List of current Game
     * @param chessBoard Current-Chessboard
     * @return ArrayList Contains all active enemy Pieces.
     */
    public List<Piece> getEnemyPieces(List<Piece> beatenPieces, Board chessBoard) {
        List<Piece> enemies;
        List<Piece> piecesToRemove = new ArrayList<>();
        if(this.colour == Colour.WHITE) {
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
