public class SimpleGame extends GameLogic {
    private boolean gameOver;
    private Player winner;

    // Create a new Simple Game
    public SimpleGame(int size){
        super(size);
        gameOver = false;
        winner = null;
    }

    // Call upon if an SOS has been created
    @Override
    public boolean checkSOS(int row, int col, char letter){
        boolean isFound = SOSPlacementFound(row, col, letter);

        // Declare winner and end game
        if (isFound){
            gameOver = true;
            winner = currentPlayer;
        }
        return isFound;
    }

    // End the game if gameOver = True
    @Override
    public boolean isGameOver(){
        if (gameOver) return true;
        return gameBoard.isFull();
    }

    // Return the winner of the round / game
    public Player getWinner() {
        return winner;
    }

    @Override
    public void resetGame (int newSize){
        super.resetGame(newSize);
        gameOver = false;
        winner = null;
    }

    // Check for SOS within the board
    private boolean SOSPlacementFound(int row, int col, char letter){
        // Check within each direction for SOS
        int[][] directions = {
                {-1, 0}, // Checks the columns
                {0, -1}, // Checks the rows
                {-1, -1}, // Checks in diagonal direction (up - down)
                {-1, 1}, // Checks in diagonal direction (down - up)
        };
        // Reference for SOS Check:
        // - https://www.shecodes.io/athena/12682-how-to-check-winner-in-a-board-game-using-java
        // - https://stackoverflow.com/questions/75215659/determining-a-winner-on-a-grid-of-8x8-tic-tac-toe

        for (int[] direction :  directions){
            //int dr = direction[0];
            //int dc = direction[1];

            // checks the letters around "O"
            if (letter == 'O'){
                // Checks the Cells for any "S" that creates an "SOS"
                if (isSAtCell(row - direction[0], col - direction[1]) && isSAtCell(row + direction[0], col + direction[1])){
                    return true;
                }
            }

            // checks the letters around "S"
            if (letter == 'S'){
                // Checks the Cells for any "O" and "S" that creates an "SOS"
                if (isOAtCell(row + direction[0], col + direction[1]) && isSAtCell(row + 2*direction[0], col + 2*direction[1])){
                    return true;
                }
                if (isOAtCell(row - direction[0], col - direction[1]) && isSAtCell(row - 2*direction[0], col - 2*direction[1])){
                    return true;
                }
            }
        }
        // Return false is no "SOS" is found within the board
        return false;
    }

    @Override
    public int SOSPlacementFoundCount(int row, int col, char letter) {

        int count = 0;
        int [] [] directions = {
                {-1, 0}, {1, 0},
                {0, -1}, {0, 1},
                {-1, -1}, {1, 1},
                {-1, 1}, {1, -1}
        };

        for (int[] direction :  directions){

            // checks the letters around "O"
            if (letter == 'O'){
                // Checks the Cells for any "S" that creates an "SOS"
                if (isSAtCell(row - direction[0], col - direction[1]) && isSAtCell(row + direction[0], col + direction[1])){
                    count++;
                }
            }

            // checks the letters around "S"
            if (letter == 'S'){
                // Checks the Cells for any "O" and "S" that creates an "SOS"
                if (isOAtCell(row + direction[0], col + direction[1]) && isSAtCell(row + 2*direction[0], col + 2*direction[1])){
                    count++;
                }
                if (isOAtCell(row - direction[0], col - direction[1]) && isSAtCell(row - 2*direction[0], col - 2*direction[1])){
                    count++;
                }
            }
        }
        // Return false is no "SOS" is found within the board
        return count;
    }

    // Check if there is an S at a cell at specified coordinates
    private boolean isSAtCell(int row, int col){
        if (row < 0 || row >= gameBoard.getSize() || col < 0 || col >= gameBoard.getSize()) return false;
        return gameBoard.getCell(row, col) == 'S';
    }

    // Check if there is an O at a cell at specified coordinates
    private boolean isOAtCell(int row, int col){
        if (row < 0 || row >= gameBoard.getSize() || col < 0 || col >= gameBoard.getSize()) return false;
        return gameBoard.getCell(row, col) == 'O';
    }
}
// Reference:
// - https://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over
// - https://stackoverflow.com/questions/18491420/sos-game-concept-of-tic-tac-toe