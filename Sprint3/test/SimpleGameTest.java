import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleGameTest {

    private SimpleGame game;

    // set up the game board for a simple game test
    @Before
    public void setUp() {
        // set size to the minimum (3x3)
        game = new SimpleGame(3);
    }

    // AC 4.1 - Player places an “S” or an “O” on an empty cell
    @Test
    public void testLetterPlacement() {
        boolean placed = game.getGameBoard().placeLetter(0, 0, 'S');
        // confirm is the action has gone through
        assertTrue(placed);
        // check if action was performed correctly
        assertEquals('S', game.getGameBoard().getCell(0, 0));
    }

    // AC 4.2 - Player places an “S” or an “O” in a cell that is filled.
    @Test
    public void testPlacementOnFilledCell() {
        // places letter down cell
        game.getGameBoard().placeLetter(0, 0, 'S');
        // tries to place another letter in the same cell
        boolean placedAgain = game.getGameBoard().placeLetter(0, 0, 'O');
        // confirm action has not gone through
        assertFalse(placedAgain);
        // check if action was performed correctly
        assertEquals('S', game.getGameBoard().getCell(0, 0));
    }

    // AC 5.1 - Player wins in Simple mode
    @Test
    public void testDetectSOSAndGameEnds() {
        // place "SOS" across the row
        game.getGameBoard().placeLetter(0, 0, 'S');
        game.getGameBoard().placeLetter(0, 1, 'O');
        game.getGameBoard().placeLetter(0, 2, 'S');

        // check if an "SOS" is found
        boolean found = game.checkSOS(0, 1, 'O');
        assertTrue(found);
        // End the game
        assertTrue(game.isGameOver());
        // Check if action was performed correctly
        assertEquals(Player.BLUE, game.getWinner());
    }

    // AC 5.2 - Board is full in Simple mode
    @Test
    public void testDraw() {
        // place "S" on each cell to fill up the board
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                game.getGameBoard().placeLetter(r,c,'S');
            }
        }

        // Check if the game is over
        assertTrue(game.isGameOver());

        // Check if there is no winner, resulting in a draw
        assertNull(game.getWinner());
    }
}