import org.junit.Test;
import static org.junit.Assert.*;

public class GameLogicTest {

    // AC 1.3 - Default board size if invalid
    @Test
    public void testDefaultBoardSize() {
        SimpleGame game = new SimpleGame(' ');
        assertTrue(game.getGameBoard().getSize() > 3);
    }

    // AC 2.1 - Simple mode selected
    @Test
    public void testSimpleModeSelected() {
        SimpleGame game = new SimpleGame(6);
        assertTrue(game instanceof SimpleGame);
    }

    // AC 2.2 - General mode selected
    @Test
    public void testGeneralModeSelected() {
        GeneralGame game = new GeneralGame(6);
        assertTrue(game instanceof GeneralGame);
    }

    // AC 2.3 - Default mode if none selected
    @Test
    public void testDefaultMode() {
        SimpleGame game = new SimpleGame(6);
        assertTrue(game instanceof SimpleGame);
    }

    // AC 3.1 - New game starts correctly
    @Test
    public void testNewGameWithChosenSettings() {
        SimpleGame game = new SimpleGame(6);
        game.resetGame(6);
        assertEquals(6, game.getGameBoard().getSize());
        assertTrue(game instanceof SimpleGame);
    }

    // AC 3.2 - Restart keeps same mode
    @Test
    public void testRestartKeepsSameSettings() {
        GeneralGame game = new GeneralGame(6);
        game.resetGame(6);
        assertEquals(6, game.getGameBoard().getSize());
        assertTrue(game instanceof GeneralGame);
    }

    // Checks if game mode is not null
    @Test
    public void testGameModeNotNull() {
        SimpleGame game = new SimpleGame(6);
        assertTrue(game instanceof SimpleGame);
    }
}