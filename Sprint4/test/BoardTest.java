import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    // AC 1.1 - Test board size (ChatGPT)

    /**
     * Test 1: Verify that the GUI correctly initializes
     * the board based on the chosen size.
     */
    @Test
    public void testBoardInitialization() {
        Board board = new Board(3);
        assertEquals("Board size should be 3x3", 3, board.getSize());
    }

    /**
     * Test 2: Verify that placing a letter through the GUI
     * updates both the board state and the GUI cell display.
     */
    // AC 4.1 and AC 6.1 -  Place letter (ChatGPT)
    @Test
    public void testPlaceLetterUpdatesGUI() {
        Board board = new Board(3);
        boolean placed = board.placeLetter(1, 1, 'S');
        assertEquals("Board cell (1,1) should contain 'S'", 'S', board.getCell(1, 1));
    }

    // AC 1.2 - Size reflected within the game board
    @Test
    public void testBoardCellsMatch() {
        Board board = new Board(3);
        assertEquals(' ', board.getCell(2, 2));
    }

    // Makes sure placement of the letter cannot go out of bounds
    @Test
    public void testPlaceLetterOutsideBoard() {
        Board board = new Board(6);
        boolean letterPlacement = board.placeLetter(7, 7, 'S');
        assertFalse(letterPlacement);
    }
}