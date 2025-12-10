public abstract class GameLogic {
    protected Board gameBoard;
    protected Player currentPlayer;

    public GameLogic(int size){
        gameBoard = new Board(size);
        currentPlayer = Player.BLUE;
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

    public void setCurrentPlayer(Player p) {
        this.currentPlayer = p;
    }

    public void resetGame (int newSize){
        gameBoard = new Board(newSize);
        currentPlayer = Player.BLUE;
    }

    // Functions implemented within other classes (SimpleGame and GeneralGame)
    public abstract boolean isGameOver();
    public abstract boolean checkSOS(int row, int col, char letter);

    public abstract int SOSPlacementFoundCount(int row, int col, char letter);
}