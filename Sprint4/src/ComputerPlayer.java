import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer {
    private static final Random RAND = new Random();

    // Chooses and returns an array [row, col, letter]
    public static Object[] chooseMove(GameLogic game) {
        int n = game.getGameBoard().getSize();

        // In the instance of a General Game, the Computer pick the move that
        // yields the immediate SOS
        if (game instanceof GeneralGame) {
            int bestScore = -1;
            List<Object[]> bestMoves = new ArrayList<>();
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (game.getGameBoard().getCell(r, c) != ' ') continue;
                    for (char letter : new char[]{'S', 'O'}) {
                        // This counts the placement of the S/O to see if it's the best possible move to make
                        int count = game.SOSPlacementFoundCount(r, c, letter);

                        // if the move is better than previous, update score
                        if (count > bestScore) {
                            bestScore = count;
                            bestMoves.clear();
                            bestMoves.add(new Object[]{r, c, letter});
                        }
                        else if (count == bestScore) {
                            bestMoves.add(new Object[]{r, c, letter});
                        }
                    }
                }
            }
            if (!bestMoves.isEmpty()) {
                return bestMoves.get(RAND.nextInt(bestMoves.size()));
            }
        }

        // In the instance of a Simple Game
        // The goal is to try to immediately win,
        // else block opponent or make a random move
        if (game instanceof SimpleGame) {
            // The instance of a win
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (game.getGameBoard().getCell(r, c) != ' ') continue;
                    for (char letter : new char[]{'S', 'O'}) {
                        if (game.SOSPlacementFoundCount(r, c, letter) > 0) {
                            return new Object[]{r, c, letter};
                        }
                    }
                }
            }
            // Block the opponent player from making an SOS sequence
            Player opponent = (game.getCurrentPlayer() == Player.BLUE) ? Player.RED : Player.BLUE;
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (game.getGameBoard().getCell(r, c) != ' ') continue;
                    // if opponent has a winning move, block it
                    for (char oppLetter : new char[]{'S', 'O'}) {
                        if (game.SOSPlacementFoundCount(r, c, oppLetter) > 0) {
                            // choose a letter to place as the computer ('S' by default)
                            return new Object[]{r, c, 'S'};
                        }
                    }
                }
            }
        }

        // If nothing found, make a random move
        List<int[]> empties = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (game.getGameBoard().getCell(r, c) == ' ') empties.add(new int[]{r, c});
            }
        }
        if (!empties.isEmpty()) {
            int[] cell = empties.get(RAND.nextInt(empties.size()));
            char letter = 'S';
            return new Object[]{cell[0], cell[1], letter};
        }
        return null; // no moves made
    }
}

// References:
// - https://www.youtube.com/watch?v=rYhfDkfpEEk&list=PLYwSrMCScKQvYK6IlNqeCcc7BHTSlL9je&index=36
// - https://stackoverflow.com/questions/34254661/player-vs-computer-tic-tac-toe-game-code
// - https://realpython.com/tic-tac-toe-ai-python/
// - https://codepal.ai/code-generator/query/4UGKq8c1/sos-game-in-python-100-squares