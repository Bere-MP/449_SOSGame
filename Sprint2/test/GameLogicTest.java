import org.junit.Test;
import static org.junit.Assert.*;

public class GameLogicTest {

    // AC 1.3 - Default board size if invalid
    @Test
    public void testDefaultBoardSize() {
        GameLogic game = new GameLogic(' ', "Simple");
        assertTrue(game.getGameBoard().getSize() > 3);
    }

    // AC 2.1 - Simple mode selected
    @Test
    public void testSimpleModeSelected() {
        GameLogic game = new GameLogic(6, "Simple");
        assertEquals("Simple", game.getGameMode());
    }

    // AC 2.2 - General mode selected
    @Test
    public void testGeneralModeSelected() {
        GameLogic game = new GameLogic(6, "General");
        assertEquals("General", game.getGameMode());
    }

    // AC 2.3 - Default mode if none selected
    @Test
    public void testDefaultMode() {
        GameLogic game = new GameLogic(6, "");
        assertNotNull(game.getGameMode());
    }

    // AC 3.1 - New game starts correctly
    @Test
    public void testNewGameWithChosenSettings() {
        GameLogic game = new GameLogic(6, "Simple");
        game.resetGame(6);
        assertEquals(6, game.getGameBoard().getSize());
        assertEquals("Simple", game.getGameMode());
    }

    // AC 3.2 - Restart keeps same mode
    @Test
    public void testRestartKeepsSameSettings() {
        GameLogic game = new GameLogic(6, "General");
        game.resetGame(6);
        assertEquals(6, game.getGameBoard().getSize());
        assertEquals("General", game.getGameMode());
    }

    // Checks if game mode is not null
    @Test
    public void testGameModeNotNull() {
        GameLogic game = new GameLogic(6, "Simple");
        assertNotNull(game.getGameMode());
    }

}

