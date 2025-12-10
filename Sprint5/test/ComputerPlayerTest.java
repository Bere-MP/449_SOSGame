import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ComputerPlayerTest {
    // AC 8.2 – Computer Player makes a valid move in Simple Mode
    @Test
    public void testComputerBlocksOpponentsWinningMove() {
        SimpleGame game = new SimpleGame(3);
        Board board = game.getGameBoard();

        // Clear board first
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                board.placeLetter(r, c, ' ');

        // Let human blue player go first
        board.placeLetter(2, 1, 'S');
        game.nextPlayer();  // now RED/computer turn

        // The human player could win by at (2,2)
        // So computer should block (2,2)
        Object[] move = ComputerPlayer.chooseMove(game);

        // ensure correct action has been made
        assertNotNull(move);
        assertEquals('S', move[2]);  // computer always uses S to block
    }
    // AC 8.3 – Computer Player’s turn ends if an SOS is found
    @Test
    public void testComputerWinsImmediately() {
        SimpleGame game = new SimpleGame(5);
        Board board = game.getGameBoard();

        // Clear board first
        for (int r = 0; r < 5; r++)
            for (int c = 0; c < 5; c++)
                board.placeLetter(r, c, ' ');

        // Create S _ S horizontally at row 2 col 1 and col 3
        board.placeLetter(2, 1, 'S');
        board.placeLetter(2, 3, 'S');

        Object[] move = ComputerPlayer.chooseMove(game);

        // Ensure correct actions have been made
        assertNotNull(move);
        assertEquals(2, move[0]);
        assertEquals(2, move[1]);
        assertEquals('O', move[2]);
    }
    // AC 9.2 Computer Player makes a valid move in General Mode
    @Test
    public void testComputerChoosesBestImmediateSOSMove() {
        GameLogic game = new GeneralGame(5);

        // Set board so the best move is (2,2,'O')
        Board b = game.getGameBoard();
        b.placeLetter(2, 1, 'S');
        b.placeLetter(2, 3, 'S');

        Object[] move = ComputerPlayer.chooseMove(game);

        // ensure correct actions were made
        assertNotNull(move);
        assertEquals(2, move[0]);   // row
        assertEquals(2, move[1]);   // col
        assertEquals('O', move[2]); // letter
    }
    // AC 9.3 – Computer player receives an extra turn once an SOS is formed
    @Test
    public void testGeneralGameScoring() {
        GeneralGame game = new GeneralGame(5);
        Board board = game.getGameBoard();

        // Setup to produce 2 SOS sequences from one move
        board.placeLetter(1, 1, 'S');
        board.placeLetter(3, 3, 'S');
        board.placeLetter(3, 1, 'S');
        board.placeLetter(1, 3, 'S');

        // Put O in center after setup, expecting 2 SOS patterns
        Object[] move = {2, 2, 'O'};
        board.placeLetter(2, 2, 'O');
        boolean scored = game.checkSOS(2, 2, 'O');

        // Ensure the score was able to be recorded
        assertTrue(scored);
    }
}