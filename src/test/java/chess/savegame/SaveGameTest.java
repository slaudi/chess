package chess.savegame;

import chess.game.Game;
import chess.game.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test-Class for Game-Saving
 */
public class SaveGameTest {
    Game game;
    Game game2;
    String name;
    File loadingFile;

    /**
     * Setup for each Test
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    /**
     * Test for main saving method
     */
    @Test
    public void save() {
        SaveGame.save(game);
        File f = new File("src/main/resources/saves");
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            name = fileArray[fileArray.length - 1];
            loadingFile = new File("src/main/resources/saves/" + name);
        }
        game2 = LoadGame.loadFile(loadingFile);
        assertEquals(Type.ROOK, game2.chessBoard.getPieceAt(0,0).getType());
    }
}