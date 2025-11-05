public enum Player {
    BLUE,
    RED;

    // Determines / Switches Player Turn
    public Player playerTurn() {
        return this == BLUE ? RED : BLUE;
    }
}
