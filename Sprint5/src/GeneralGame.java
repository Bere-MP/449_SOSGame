public class GeneralGame extends GameLogic {

    private int blueScore;
    private int redScore;

    // Create a new General Game
    public GeneralGame(int size) {
        super(size);
        blueScore = 0;
        redScore = 0;
    }

    // Call upon if an SOS has been created
    @Override
    public boolean checkSOS(int row, int col, char letter) {
        // Find if there is an SOS within the board.
        int count = SOSPlacementFoundCount(row, col, letter);
        boolean scored = count > 0;
        if (scored) {
            if (currentPlayer == Player.BLUE) {
                blueScore += count;
            }
            else {
                redScore += count;
            }
        }
        return scored;
    }

    // End the game if gameOver = True
    @Override
    public boolean isGameOver() {
        return gameBoard.isFull();
    }

    // Message at the end of the game
    // Return score between both players
    public String getScore() {
        return "Blue: " + blueScore + " | Red: " + redScore;
    }

    // Return the winner of the round / game
    public Player getWinner() {
        // Return blue as winner if blue score > red score
        if (blueScore > redScore){
            return Player.BLUE;
        }
        // Return red as winner if red score > blue score
        else if (redScore > blueScore){
            return Player.RED;
        }
        // if no winner, is found due to draw in score, return null
        else {
            return null;
        }
    }

    @Override
    public int SOSPlacementFoundCount(int row, int col, char letter) {

        int count = 0;

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
            // checks the letters around "O"
            if (letter == 'O'){
                // Checks the Cells for any "S" that creates an "SOS"
                if (isSAtCell(row - direction[0], col - direction[1]) && isSAtCell(row + direction[0], col + direction[1])){
                    // if SOS formed, add to score
                    count++;
                }
            }

            // checks the letters around "S"
            if (letter == 'S'){
                // Checks the Cells for any "O" and "S" that creates an "SOS"
                if (isOAtCell(row + direction[0], col + direction[1]) && isSAtCell(row + 2*direction[0], col + 2*direction[1])){
                    // if SOS formed, add to score
                    count++;
                }
            }
        }
        // Return current count is no "SOS" is found within the board
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