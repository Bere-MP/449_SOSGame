import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GeneralGameTest {

    private GeneralGame game;

    // set up the game board for a simple game test
    @Before
    public void setUp() {
        // set size to the minimum (3x3)
        game = new GeneralGame(3);
    }

    // AC 6.1 - Player places an “S” or an “O” on an empty cell
    @Test
    public void testLetterPlacement() {
        boolean placed = game.getGameBoard().placeLetter(0, 0, 'S');
        // confirm is the action has gone through
        assertTrue(placed);
        // check if action was performed correctly
        assertEquals('S', game.getGameBoard().getCell(0, 0));
    }

    // AC 6.2 - Player places an “S” or an “O” in a cell that is filled.
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

    // AC 6.3 - Scoring occurs when SOS is made
    @Test
    public void testDetectSOSAndIncreaseScore() {
        // place "SOS" across the row
        game.getGameBoard().placeLetter(0,0,'S');
        game.getGameBoard().placeLetter(0,1,'O');
        game.getGameBoard().placeLetter(0,2,'S');

        // check if an "SOS" is found
        assertTrue(game.checkSOS(0,1,'O'));
        // check if Blue earned a score
        assertTrue(game.getScore().contains("Blue: 1"));
    }

    // AC 7.1 - Game ends when board is full
    @Test
    public void testGameOver() {
        // place "S" on each cell to fill up the board
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                game.getGameBoard().placeLetter(r,c,'S');
            }
        }
        // Check if the game is over
        assertTrue(game.isGameOver());

    }

    // AC 7.2 - Final scores are added and displayed.
    @Test
    public void testFinalWinner() {
        // Make Player Blue win
        game.getGameBoard().placeLetter(0,0,'S');
        game.getGameBoard().placeLetter(0,1,'O');
        game.getGameBoard().placeLetter(0,2,'S');
        game.checkSOS(0,1,'O');

        // Fill rest of the empty cell with "S"
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                if(game.getGameBoard().getCell(r,c) == ' ')
                    game.getGameBoard().placeLetter(r,c,'S');
            }
        }

        // Check if the game is over
        assertTrue(game.isGameOver());

        // Check if Player Blue is the winner
        assertEquals(Player.BLUE, game.getWinner());
    }
}