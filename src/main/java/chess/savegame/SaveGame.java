package chess.savegame;

import chess.game.*;
import chess.pieces.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaveGame {

    public static void save(Game game) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            String date = dtf.format(LocalDateTime.now());
            String filename = "src/main/resources/saves/SaveGame" + date + ".txt";
            FileWriter myWriter;
            myWriter = new FileWriter(filename);
            myWriter.write(boardToStringLine(game));
            myWriter.write(System.lineSeparator());
            myWriter.write(currentPlayerColourToLine(game));
            myWriter.write(System.lineSeparator());
            myWriter.write(userColourToLine(game));
            myWriter.write(System.lineSeparator());
            myWriter.write(artificialEnemyToLine(game));
            myWriter.write(System.lineSeparator());
            myWriter.write(beatenPiecesToLine(game));
            myWriter.write(System.lineSeparator());
            myWriter.write(moveHistoryToLine(game));
            myWriter.write(System.lineSeparator());
            myWriter.write(languageToLine(game));
            myWriter.write(System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred during Saving.");
            e.printStackTrace();
        }
    }

    public static Game load(ArrayList<String> loadString){
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

    public static String boardToStringLine(Game game){
        StringBuilder boardString = new StringBuilder();
        for (int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                if (game.chessBoard.getBoard()[x][y].getOccupiedBy() != null){
                    boardString.append(game.chessBoard.getBoard()[x][y].getOccupiedBy().toString());
                }
                else{
                    boardString.append("X");
                }
            }
        }
        return boardString.toString();
    }

    public static Board stringToBoard(String boardString){
        Board board = new Board(8, 8);
        for (int i = 0; i < 64; i++){
            if(boardString.charAt(i) != 'X'){
                int x = i % 8;
                int y = (int) i / 8;
                board.setPieceAt(x, y, charToPiece(boardString.charAt(i), board.getSquareAt(x, y)));
            }
        }
        return board;
    }

    public static Piece charToPiece(char c, Square square){
        switch (c) {
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

    public static String currentPlayerColourToLine(Game game){
        if(game.currentPlayer.getColour() == Colour.WHITE){
            return "W";
        }
        else return "B";
    }

    public static Colour stringToCurrentPlayerColour(String string){
        if(string.charAt(0) == 'W'){
            return Colour.WHITE;
        }
        else return Colour.BLACK;
    }

    public static String userColourToLine(Game game){
        if(game.getUserColour() == Colour.WHITE){
            return "W";
        }
        else return "B";
    }

    public static Colour stringToUserColour(String string){
        if(string.charAt(0) == 'W'){
            return Colour.WHITE;
        }
        else return Colour.BLACK;
    }

    public static String artificialEnemyToLine(Game game){
        if(game.isArtificialEnemy()){
            return "true";
        }
        else return "false";
    }

    public static boolean stringToArtificialEnemy(String string){
        return string.equals("true");
    }

    public static String beatenPiecesToLine(Game game){
        StringBuilder beatenPiecesString = new StringBuilder();
        if(!game.beatenPieces.isEmpty()) {
            for (Piece piece : game.beatenPieces) {
                beatenPiecesString.append(piece.toString());
            }
        }
        return beatenPiecesString.toString();
    }

    public static ArrayList<Piece> stringToBeatenPieces(String string){
        ArrayList<Piece> beatenPieces = new ArrayList<>();
        if(string.length() > 0){
            for (int i = 0; i < string.length(); i++){
                beatenPieces.add(charToPiece(string.charAt(i), new Square(Label.a8, 0, 0)));
            }
        }
        return beatenPieces;
    }

    public static String moveHistoryToLine(Game game){
        StringBuilder moveHistoryString = new StringBuilder();
        List<Move> history = game.moveHistory;
        if(!history.isEmpty()){
            for (Move move : history) {
                moveHistoryString.append(move.getStartSquare().getLabel().toString()).append("-").append(move.getFinalSquare().getLabel().toString()).append(".");
            }
        }
        return moveHistoryString.toString();
    }

    public static ArrayList<Move> stringToMoveHistory(String string, Game game){
        ArrayList<Move> moveHistory = new ArrayList<>();
        if(!string.isEmpty()){
            String[] slicedString = string.split(".");
            for (String s : slicedString) {
                Square startSquare = game.chessBoard.getStartSquareFromInput(s);
                Square finalSquare = game.chessBoard.getFinalSquareFromInput(s);
                moveHistory.add(new Move(startSquare, finalSquare));
            }
        }
        return moveHistory;
    }

    public  static String languageToLine(Game game){
        if(game.getLanguage() == Language.English){
            return "e";
        }
        else return "g";
    }

    public static Language stringToLanguage(String string){
        if(string.charAt(0) == 'e'){
            return Language.English;
        }
        else return Language.German;
    }
}
//TODO: speichern ob v.a. König/Turm sich schon bewegt haben
/*
Format of Savegame: Line-wise
Board from a8 to h1: X for empty-Square, Letters like in CLI for Non-Empty-Square
Current Player-Colour: B / W
User-Colour: B / W
artificialEnemy: true / false
Beaten-Pieces
Move-History: like in Cli, seperated by "."
language: g / e
 */
