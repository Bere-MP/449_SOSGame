public class GameLogic {
    private Board gameBoard;
    private Player currentPlayer;
    private String gameMode;

    public GameLogic(int size, String mode){
        gameBoard = new Board(size);
        currentPlayer = Player.BLUE;
        gameMode = mode;
    }

    public Board getGameBoard(){
        return gameBoard;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextPlayer() {
        currentPlayer = currentPlayer.playerTurn();
    }

    public String getGameMode(){
        return gameMode;
    }

    public void setGameMode(String mode){
        this.gameMode = mode;
    }

    public void resetGame (int newSize){
        gameBoard = new Board(newSize);
        currentPlayer = Player.BLUE;
    }
}
