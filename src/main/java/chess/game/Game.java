package chess.game;


import chess.cli.Cli;
import chess.pieces.Piece;

import java.util.List;

/**
 * Game class which defines current game.
 */

public class Game {
    public Player playerWhite;
    public Player playerBlack;
    public Board board;
    public List<Piece> beatenPieces;

    private Player currentPlayer;

    /**
     * Constructor for a Game
     */
    public Game() {
        this.playerWhite = new Player(Colour.WHITE);
        this.playerBlack = new Player(Colour.BLACK);

        this.currentPlayer = playerWhite;
        this.board = new Board();
    }

    private boolean processMove(Square from, Square to)
    {
        Piece selectedPiece = from.occupiedBy;
        Piece targetPiece = to.occupiedBy;

        if(selectedPiece == null)
        {
            System.out.println("There is no piece under 'from' coordinates");
            return false;
        }

        if(selectedPiece.colour != currentPlayer.colour)
        {
            System.out.println("It is not your piece under 'from' coordinates");
            return false;
        }

        if(targetPiece != null && targetPiece.colour == currentPlayer.colour)
        {
            System.out.println("Cannot move piece onto cell with your another figure");
            return false;
        }

        if (selectedPiece.isAllowedMove(to))
        {
            if (targetPiece != null)
            {
                beatenPieces.add(targetPiece);
            }

            to.occupiedBy = from.occupiedBy;
            to.occupied = true;

            from.occupiedBy = null;
            from.occupied = false;

            System.out.println("Piece " + selectedPiece.getType() + " moved successfully");
        }
        else
        {
            System.out.println("Cannot move " + selectedPiece.getType() + " piece");
            return false;
        }

        return true;
    }

    public void processGame()
    {
        board.toConsole();

        while(true)
        {
            System.out.println("Now playing as " + currentPlayer.colour);

            String userInput;

            while (!Move.validMove(userInput = Cli.getInput()))
            {
                System.out.println("Invalid syntax");
            }

            Square from = board.getStartSquareFromInput(userInput);
            Square to = board.getFinalSquareFromInput(userInput);

            if (processMove(from, to) == false)
            {
                // don't switch players or redraw board
                continue;
            }

            currentPlayer = currentPlayer == playerWhite ?
                            playerBlack : playerWhite;


            board.toConsole();

            System.out.println("Beaten pieces:" + beatenPieces);
        }
    }

}
