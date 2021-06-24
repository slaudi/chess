package chess.savegame;

import chess.game.*;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class LoadGame {

    public static Game load(List<String> loadString){
        Game loadedGame = new Game();
        loadedGame.chessBoard.clearBoard();
        System.out.println(loadString);
        loadedGame.chessBoard = stringToBoard(loadString.get(0));
        if(stringToCurrentPlayerColour(loadString.get(1)) == Colour.BLACK){
            loadedGame.changePlayer();
        }
        loadedGame.setUserColour(stringToUserColour(loadString.get(2)));
        loadedGame.setArtificialEnemy(stringToArtificialEnemy(loadString.get(3)));
        loadedGame.beatenPieces = stringToBeatenPieces(loadString.get(4));
        loadedGame.moveHistory = stringToMoveHistory(loadString.get(5), loadedGame);
        loadedGame.setLanguage(stringToLanguage(loadString.get(6)));
        return loadedGame;
    }


    private static Board stringToBoard(String boardString){
        Board board = new Board(8, 8);
        board.clearBoard();
        for (int i = 0; i < 64; i++){
            if(boardString.charAt(i) != 'X'){
                int x = i % 8;
                int y = (int)(i / 8);
                board.setPieceAt(x, y, charToPiece(boardString.charAt(i), board.getSquareAt(x, y)));
            }
        }
        return board;
    }


    private static Colour stringToCurrentPlayerColour(String string){
        if(string.charAt(0) == 'W'){
            return Colour.WHITE;
        }
        else {
            return Colour.BLACK;
        }
    }


    private static Colour stringToUserColour(String string){
        if(string.charAt(0) == 'W'){
            return Colour.WHITE;
        }
        else {
            return Colour.BLACK;
        }
    }


    private static boolean stringToArtificialEnemy(String string){
        return string.equals("true");
    }


    private static List<Piece> stringToBeatenPieces(String string){
        ArrayList<Piece> beatenPieces = new ArrayList<>();
        if(string.length() > 0){
            for (int i = 0; i < string.length(); i++){
                beatenPieces.add(charToPiece(string.charAt(i), new Square(Label.a8, 0, 0)));
            }
        }
        return beatenPieces;
    }


    private static List<Move> stringToMoveHistory(String string, Game game){
        ArrayList<Move> moveHistory = new ArrayList<>();
        if(string.length() > 0){
            String[] slicedString = string.split("\\.");
            for (String s : slicedString) {
                Square startSquare = game.chessBoard.getStartSquareFromInput(s);
                Square finalSquare = game.chessBoard.getFinalSquareFromInput(s);
                moveHistory.add(new Move(startSquare, finalSquare));
            }
            moveHistory.get(moveHistory.size() - 1).setMovingPieceToPieceOnFinalSquare();
        }
        return moveHistory;
    }


    private static Language stringToLanguage(String string){
        if(string.charAt(0) == 'e'){
            return Language.English;
        }
        else {
            return Language.German;
        }
    }


    private static Piece charToPiece(char c, Square square){
        switch (c) {
            case 'p':
                return new Pawn(square, Colour.BLACK);
            case 'P':
                return new Pawn(square, Colour.WHITE);
            case 'r':
                return new Rook(square, Colour.BLACK);
            case 'n':
                return new Knight(square, Colour.BLACK);
            case 'b':
                return new Bishop(square, Colour.BLACK);
            case 'q':
                return new Queen(square, Colour.BLACK);
            case 'k':
                return new King(square, Colour.BLACK);
            case 'R':
                return new Rook(square, Colour.WHITE);
            case 'N':
                return new Knight(square, Colour.WHITE);
            case 'B':
                return new Bishop(square, Colour.WHITE);
            case 'Q':
                return new Queen(square, Colour.WHITE);
            case 'K':
                return new King(square, Colour.WHITE);
        }
        return null;
    }


}
