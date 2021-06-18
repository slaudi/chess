package chess.savegame;

import chess.game.*;
import chess.pieces.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaveGame {
    //TODO: speichern ob v.a. König/Turm sich schon bewegt haben

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



    private static String boardToStringLine(Game game){
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


    private static String currentPlayerColourToLine(Game game){
        if(game.currentPlayer.getColour() == Colour.WHITE){
            return "W";
        }
        else {
            return "B";
        }
    }


    private static String userColourToLine(Game game){
        if(game.getUserColour() == Colour.WHITE){
            return "W";
        }
        else {
            return "B";
        }
    }


    private static String artificialEnemyToLine(Game game){
        if(game.isArtificialEnemy()){
            return "true";
        }
        else {
            return "false";
        }
    }


    private static String beatenPiecesToLine(Game game){
        StringBuilder beatenPiecesString = new StringBuilder();
        if(!game.beatenPieces.isEmpty()) {
            for (Piece piece : game.beatenPieces) {
                beatenPiecesString.append(piece.toString());
            }
        }
        return beatenPiecesString.toString();
    }


    private static String moveHistoryToLine(Game game){
        StringBuilder moveHistoryString = new StringBuilder();
        List<Move> history = game.moveHistory;
        if(!history.isEmpty()){
            for (Move move : history) {
                moveHistoryString.append(move.getStartSquare().getLabel().toString()).append("-").append(move.getFinalSquare().getLabel().toString()).append(".");
            }
        }
        return moveHistoryString.toString();
    }


    private static String languageToLine(Game game){
        if(game.getLanguage() == Language.English){
            return "e";
        }
        else {
            return "g";
        }
    }

}

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
