package chess.savegame;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Language;
import chess.game.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoadGameTest {
    Game game;
    Game game1;
    Game game2;
    Game game3;
    Game game4;
    Game game5;
    Game game6;
    Game game7;
    Game game8;
    Game game9;
    Game game10;
    Game game11;
    Game game12;
    Game game13;
    Game game14;
    String name;
    File loadingFile;

    @BeforeEach
    public void setUp() {
        game = new Game();
        SaveGame.save(game);
        File f = new File("src/main/resources/saves");
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File("src/main/resources/saves/" + name);
        }
    }

    @Test
    public void load() {
        game1 = LoadGame.loadFile(loadingFile);
        assertEquals(Type.ROOK, game1.chessBoard.getPieceAt(0,0).getType());
    }

    @Test
    public void loadFile() {
        game2 = LoadGame.loadFile(loadingFile);
        assertEquals(Type.ROOK, game2.chessBoard.getPieceAt(0,0).getType());
    }

    @Test
    public void moveHistoryLoading(){
        game3 = new Game();
        game3.processMove(game3.chessBoard.getSquareAt(0, 6), game3.chessBoard.getSquareAt(0, 5), 'Q');
        SaveGame.save(game3);
        File f = new File("src/main/resources/saves");
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File("src/main/resources/saves/" + name);
        }
        game4 = LoadGame.loadFile(loadingFile);
        assertEquals(Type.PAWN, game4.chessBoard.getPieceAt(0,5).getType());
    }

    @Test
    public void LanguageLoading(){
        game5 = new Game();
        game5.setLanguage(Language.German);
        SaveGame.save(game5);
        File f = new File("src/main/resources/saves");
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File("src/main/resources/saves/" + name);
        }
        game6 = LoadGame.loadFile(loadingFile);
        assertEquals(Language.German, game6.getLanguage());
    }

    @Test
    public void CastlingLoading(){
        game7 = new Game();
        game7.chessBoard.clearBoard();
        SaveGame.save(game7);
        File f = new File("src/main/resources/saves");
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File("src/main/resources/saves/" + name);
        }
        game8 = LoadGame.loadFile(loadingFile);
        assertEquals(null, game8.chessBoard.getPieceAt(0, 0));
    }

    @Test
    public void CastlingLoading2(){
        game9 = new Game();
        game9.chessBoard.getBoard()[0][0].getOccupiedBy().setNotMoved(false);
        game9.chessBoard.getBoard()[4][0].getOccupiedBy().setNotMoved(false);
        game9.chessBoard.getBoard()[7][0].getOccupiedBy().setNotMoved(false);
        game9.chessBoard.getBoard()[0][7].getOccupiedBy().setNotMoved(false);
        game9.chessBoard.getBoard()[4][7].getOccupiedBy().setNotMoved(false);
        game9.chessBoard.getBoard()[7][7].getOccupiedBy().setNotMoved(false);
        SaveGame.save(game9);
        File f = new File("src/main/resources/saves");
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File("src/main/resources/saves/" + name);
        }
        game10 = LoadGame.loadFile(loadingFile);
        assertFalse(game10.chessBoard.getPieceAt(0, 0).hasNotMoved());
    }

    @Test
    public void BeatenPiecesLoading(){
        game11 = new Game();
        game11.processMove(game11.chessBoard.getSquareAt(0, 6), game11.chessBoard.getSquareAt(0, 4), 'Q');
        game11.processMove(game11.chessBoard.getSquareAt(1, 1), game11.chessBoard.getSquareAt(1, 3), 'Q');
        game11.processMove(game11.chessBoard.getSquareAt(0, 4), game11.chessBoard.getSquareAt(1, 3), 'Q');
        SaveGame.save(game11);
        File f = new File("src/main/resources/saves");
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File("src/main/resources/saves/" + name);
        }
        game12 = LoadGame.loadFile(loadingFile);
        assertEquals(Type.PAWN, game12.chessBoard.getPieceAt(1,3).getType());
    }

    @Test
    public void UserColourLoading(){
        game13 = new Game();
        game13.setUserColour(Colour.WHITE);
        game13.setArtificialEnemy(true);
        SaveGame.save(game13);
        File f = new File("src/main/resources/saves");
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File("src/main/resources/saves/" + name);
        }
        game14 = LoadGame.loadFile(loadingFile);
        assertEquals(Colour.WHITE, game14.getUserColour());
    }
}