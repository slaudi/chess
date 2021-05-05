package chess.game;


import chess.cli.Cli;
import chess.pieces.Piece;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * Game class which defines current game.
 */

public class Game {
    public Player playerWhite;
    public Player playerBlack;
    public Board board;
    public List<Piece> beatenPieces;
    public Stack<Move> moveHistory;

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
        Move currentMove = new Move(from, to);
        Piece selectedPiece = from.occupiedBy;
        Piece targetPiece = to.occupiedBy;

        if(selectedPiece == null)
        {
            System.out.println("There is no piece under 'from' coordinates");
            return false;
        }

        if(selectedPiece.getColour() != currentPlayer.colour)
        {
            System.out.println("It is not your piece under 'from' coordinates");
            return false;
        }

        if(targetPiece != null && targetPiece.getColour() == currentPlayer.colour)
        {
            System.out.println("Cannot move piece onto cell with your another figure");
            return false;
        }

        if (selectedPiece.isAllowedPath(to, this.board))
        {
            if (targetPiece != null)
            {
                beatenPieces.add(targetPiece);
            }

            board = currentMove.doMove(selectedPiece, board);
            moveHistory.add(currentMove);
            if(checkChess(board, selectedPiece.getColour())){
                currentMove.undoMove(moveHistory);
            }
            System.out.println("Piece " + selectedPiece.getType() + " moved successfully");
        }
        else
        {
            System.out.println("Cannot move " + selectedPiece.getType() + " piece");
            return false;
        }

        return true;
    }

    /*public boolean isInCheck() {
        Vector<Piece> enemies = currentPlayer.getEnemyPieces(currentPlayer.colour);

        for(int i = 0; i < enemies.size(); i++) {
            if(canAttackKing(enemies.elementAt(i), this.square)) {
                currentPlayer.setInCheck(true);
                return true;
            } else {
                currentPlayer.setInCheck(false);
                return false;
            }
        }


        return false;
    }*/

    public void processGame()
    {
        board.toConsole();

        while(!currentPlayer.isLoser)
        {
            System.out.println("Now playing as " + currentPlayer.colour);

            String userInput;

            while (!Move.isValidMove(userInput = Cli.getInput()))
            {
                System.out.println("!InvalidMove");
            }

            Square from = board.getStartSquareFromInput(userInput);
            Square to = board.getFinalSquareFromInput(userInput);

            if (!processMove(from, to))
            {
                // don't switch players or redraw board
                continue;
            }

            currentPlayer = currentPlayer == playerWhite ?
                            playerBlack : playerWhite;


            board.toConsole();

            System.out.println("Beaten pieces:" + beatenPieces);
        }

        System.out.println(currentPlayer.colour + " is Loser!");
    }
    public boolean checkChess (Board board, Colour colour){
        if (colour == Colour.WHITE){
            for (int i = 0; i < board.blackPieces.size(); i++){
                if (isPathEmpty(board.blackPieces.get(i).getType(), board.blackPieces.get(i).getSquare(),board.getSquareOfWhiteKing(),board)){
                    return true;
                }
            }
            return false;
        }
        else {
            for (int i = 0; i < board.whitePieces.size(); i++){
                if (isPathEmpty(board.whitePieces.get(i).getType(), board.whitePieces.get(i).getSquare(),board.getSquareOfBlackKing(),board)){
                    return true;
                }
            }
            return false;
        }
    }

}
