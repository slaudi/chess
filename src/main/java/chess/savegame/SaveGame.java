package chess.savegame;

import chess.game.*;
import chess.pieces.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * class in which game-loading is kept
 */
public class SaveGame {

    /**
     * main method when it comes to game-saving
     * @param game Game which should be saved and converted to txt-File
     */
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
            myWriter.write(movementToLine(game));
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

    private static String movementToLine(Game game) {//NOPMD dividing this method in submethods would make code harder to read
        StringBuilder movementString = new StringBuilder();
        if (game.chessBoard.getBoard()[0][0].getOccupiedBy() != null){
            if (game.chessBoard.getBoard()[0][0].getOccupiedBy().hasNotMoved()) {
                movementString.append("n");
            }
            else {
                movementString.append("m");
            }
        }
        else {
            movementString.append("x");
        }
        if (game.chessBoard.getBoard()[4][0].getOccupiedBy() != null){
            if (game.chessBoard.getBoard()[4][0].getOccupiedBy().hasNotMoved()) {
                movementString.append("n");
            }
            else {
                movementString.append("m");
            }
        }
        else {
            movementString.append("x");
        }
        if (game.chessBoard.getBoard()[7][0].getOccupiedBy() != null){
            if (game.chessBoard.getBoard()[7][0].getOccupiedBy().hasNotMoved()) {
                movementString.append("n");
            }
            else {
                movementString.append("m");
            }
        }
        else {
            movementString.append("x");
        }
        if (game.chessBoard.getBoard()[0][7].getOccupiedBy() != null){
            if (game.chessBoard.getBoard()[0][7].getOccupiedBy().hasNotMoved()) {
                movementString.append("n");
            }
            else {
                movementString.append("m");
            }
        }
        else {
            movementString.append("x");
        }
        if (game.chessBoard.getBoard()[4][7].getOccupiedBy() != null){
            if (game.chessBoard.getBoard()[4][7].getOccupiedBy().hasNotMoved()) {
                movementString.append("n");
            }
            else {
                movementString.append("m");
            }
        }
        else {
            movementString.append("x");
        }
        if (game.chessBoard.getBoard()[7][7].getOccupiedBy() != null){
            if (game.chessBoard.getBoard()[7][7].getOccupiedBy().hasNotMoved()) {
                movementString.append("n");
            }
            else {
                movementString.append("m");
            }
        }
        else {
            movementString.append("x");
        }
        return movementString.toString();
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
Rook/King-Movement: n for not-moved, m for moved, x for empty (a8, e8, h8, a1, e1, h1)
 */
