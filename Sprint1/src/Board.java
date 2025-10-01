
public class Board {
    private char[][] gameBoard;
    private int size;

    // Create Board Grid using user input
    //Reference:
    // - https://stackoverflow.com/questions/19166562/drawing-a-grid-in-java
    public Board(int size){
        this.size = size;
        gameBoard = new char[size][size];

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                gameBoard[i][j] = ' ';
            }
        }
    }

    public int getSize(){
        return size;
    }

    // Get Cell Block from user input
    public char getCell(int row, int col){
        return gameBoard[row][col];
    }

    // Place letter into cell
    public boolean placeLetter(int row, int col, char letter){
        // Check if cell is empty
        char cell = gameBoard[row][col];
        if (cell == ' '){
            gameBoard[row][col] = letter;
            return true;
        }

        return false;
    }

    // Check if all cells within the board is full
    public boolean isFull(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (gameBoard[i][j] == ' '){
                    return false;
                }
            }
        }
        return true;
    }
}

// Reference:
// - https://www.geeksforgeeks.org/java/tic-tac-toe-game-in-java/
