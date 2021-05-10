package chess.game;

import chess.pieces.Pawn;
import chess.pieces.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static chess.game.Label.e2;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Square[][] board;
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void getBoard() {
        Assertions.assertEquals(board, game.board.getBoard());
    }

    @Test
    void startingFormation() {
    }

    @Test
    void toConsole() {
    }

    @Test
    void getMovingPieceFromInput() {
        String input = "e2-e3";
        Assertions.assertEquals(board[4][6].getOccupiedBy(),game.board.getMovingPieceFromInput(input));
    }

    @Test
    void getStartSquareFromInput() {
        String input = "e2-e3";
        Assertions.assertEquals(board[4][6].getOccupiedBy().getSquare(),game.board.getStartSquareFromInput(input));
    }

    @Test
    void getFinalSquareFromInput() {
        String input = "e2-e3";
        Assertions.assertEquals(board[4][6].getOccupiedBy().getSquare(),game.board.getFinalSquareFromInput(input));
    }

    @Test
    void getSquareOfKing() {

    }
}