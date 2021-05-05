package chess.game;


import chess.cli.Cli;
import chess.pieces.Piece;

import java.util.*;

/**
 * Game class which defines current game.
 */

public class Game {
    public Player playerWhite;
    public Player playerBlack;
    public Board board;
    public ArrayList<Piece> beatenPieces;
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
        this.moveHistory = new Stack<Move>();
        this.beatenPieces = new ArrayList<Piece>();

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
            //TODO Abfrage ob Pfad blockiert etc.
        if (selectedPiece.isAllowedPath(to, this.board))
        {
            if (targetPiece != null)
            {
                beatenPieces.add(targetPiece);
            }

            this.board = currentMove.doMove(selectedPiece, this.board);
            this.board.toConsole();
            moveHistory.add(currentMove);
            System.out.println(moveHistory);
            if(checkChess(this.board, selectedPiece.getColour())){
                // TODO kein Spielerwechsel bei Schach

                this.board = currentMove.undoMove(moveHistory, this.board);
                System.out.println(moveHistory);
                //moveHistory.pop();
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

                if (board.blackPieces.get(i).isAllowedPath(board.getSquareOfWhiteKing(),board)
                        && board.blackPieces.get(i).isPathEmpty(board.blackPieces.get(i).getType(), board.blackPieces.get(i).getSquare(),board.getSquareOfWhiteKing(),board)){
                    System.out.println("schach weis true");
                    return true;
                }
            }
            return false;
        }
        else {
            for (int i = 0; i < board.whitePieces.size(); i++){
                if (board.whitePieces.get(i).isAllowedPath(board.getSquareOfBlackKing(),board)
                        && board.whitePieces.get(i).isPathEmpty(board.whitePieces.get(i).getType(), board.whitePieces.get(i).getSquare(),board.getSquareOfBlackKing(),board)){
                    System.out.println("schach black true");
                    return true;
                }
            }
            return false;
        }
    }

}
