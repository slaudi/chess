package chess.savegame;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Language;
import chess.game.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for GameLoading-Tests
 */
public class LoadGameTest {
    Game game;
    Game game4;
    Game game6;
    Game game8;
    Game game10;
    Game game12;
    Game game14;
    String name;
    File loadingFile;
    String directory = "src/main/resources/saves/";

    /**
     * SetUp for each Test
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        SaveGame.save(game);
        File f = new File(directory);
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File(directory + name);
        }
    }

    /**
     * Test for main Loading method
     */
    @Test
    public void load() {
        game = LoadGame.loadFile(loadingFile);
        assertEquals(Type.ROOK, game.chessBoard.getPieceAt(0,0).getType());
    }

    /**
     * test for loading-helper-method
     */
    @Test
    public void loadFile() {
        game = LoadGame.loadFile(loadingFile);
        assertEquals(Type.ROOK, game.chessBoard.getPieceAt(0,0).getType());
    }

    /**
     * test for moveHistory-loading
     */
    @Test
    public void moveHistoryLoading(){
        game = new Game();
        game.processMove(game.chessBoard.getSquareAt(0, 6), game.chessBoard.getSquareAt(0, 5), 'Q');
        SaveGame.save(game);
        File f = new File(directory);
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File(directory + name);
        }
        game4 = LoadGame.loadFile(loadingFile);
        assertEquals(Type.PAWN, game4.chessBoard.getPieceAt(0,5).getType());
    }

    /**
     * test for language-loading
     */
    @Test
    public void LanguageLoading(){
        game = new Game();
        game.setLanguage(Language.German);
        SaveGame.save(game);
        File f = new File(directory);
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File(directory + name);
        }
        game6 = LoadGame.loadFile(loadingFile);
        assertEquals(Language.German, game6.getLanguage());
    }

    /**
     * test for castling-loading
     */
    @Test
    public void CastlingLoading(){
        game = new Game();
        game.chessBoard.clearBoard();
        SaveGame.save(game);
        File f = new File(directory);
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File(directory + name);
        }
        game8 = LoadGame.loadFile(loadingFile);
        assertNull(game8.chessBoard.getPieceAt(0, 0));
    }

    /**
     * another test for castling-loading
     */
    @Test
    public void CastlingLoading2(){
        game = new Game();
        game.chessBoard.getBoard()[0][0].getOccupiedBy().setNotMoved(false);
        game.chessBoard.getBoard()[4][0].getOccupiedBy().setNotMoved(false);
        game.chessBoard.getBoard()[7][0].getOccupiedBy().setNotMoved(false);
        game.chessBoard.getBoard()[0][7].getOccupiedBy().setNotMoved(false);
        game.chessBoard.getBoard()[4][7].getOccupiedBy().setNotMoved(false);
        game.chessBoard.getBoard()[7][7].getOccupiedBy().setNotMoved(false);
        SaveGame.save(game);
        File f = new File(directory);
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File(directory + name);
        }
        game10 = LoadGame.loadFile(loadingFile);
        assertFalse(game10.chessBoard.getPieceAt(0, 0).hasNotMoved());
    }

    /**
     * test for loading beaten pieces list
     */
    @Test
    public void BeatenPiecesLoading(){
        game = new Game();
        game.processMove(game.chessBoard.getSquareAt(0, 6), game.chessBoard.getSquareAt(0, 4), 'Q');
        game.processMove(game.chessBoard.getSquareAt(1, 1), game.chessBoard.getSquareAt(1, 3), 'Q');
        game.processMove(game.chessBoard.getSquareAt(0, 4), game.chessBoard.getSquareAt(1, 3), 'Q');
        SaveGame.save(game);
        File f = new File(directory);
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File(directory + name);
        }
        game12 = LoadGame.loadFile(loadingFile);
        assertEquals(Type.PAWN, game12.chessBoard.getPieceAt(1,3).getType());
    }

    /**
     * test for loading right userColour
     */
    @Test
    public void UserColourLoading(){
        game = new Game();
        game.setUserColour(Colour.WHITE);
        game.setArtificialEnemy(true);
        SaveGame.save(game);
        File f = new File(directory);
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File(directory + name);
        }
        game14 = LoadGame.loadFile(loadingFile);
        assertEquals(Colour.WHITE, game14.getUserColour());
    }
}