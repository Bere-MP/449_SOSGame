import java.io.*;
import java.util.*;

public class GameReplayer {
    public String mode;
    public int size;
    public boolean blueComputer;
    public boolean redComputer;
    public Player startPlayer;
    public List<Move> moves = new ArrayList<>();

    // Create class for move to gather information
    public static class Move {
        public int row, col;
        public char letter;
        public Player player;

        // Each move contains information from each move within the game
        public Move(int r, int c, char l, Player p) {
            row = r; col = c; letter = l; player = p;
        }
    }

    // Load file from a saved game and gather the data
    public void load(File file) throws IOException {
        moves.clear();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        // configure new game to make the saved game
        while ((line = br.readLine()) != null) {
            if (line.startsWith("MODE=")) mode = line.substring(5);
            else if (line.startsWith("SIZE=")) size = Integer.parseInt(line.substring(5));
            else if (line.startsWith("BLUE=")) blueComputer = line.contains("COMPUTER");
            else if (line.startsWith("RED=")) redComputer = line.contains("COMPUTER");
            else if (line.startsWith("START=")) startPlayer = Player.valueOf(line.substring(6));

            // gather the information written in the file
            else if (line.startsWith("MOVE")) {
                String[] t = line.split(" ");
                int r = Integer.parseInt(t[1]);
                int c = Integer.parseInt(t[2]);
                char letter = t[3].charAt(0);
                Player p = Player.valueOf(t[4]);
                moves.add(new Move(r, c, letter, p));
            }
        }
        br.close();
    }

    // apply the move from the file to the game to replay
    public void applyMove(GameLogic game, Move m) {
        // Make game match the recorded player
        game.setCurrentPlayer(m.player);

        // Place moves down
        game.getGameBoard().placeLetter(m.row, m.col, m.letter);
        game.checkSOS(m.row, m.col, m.letter);
    }
}

// Reference:
// - https://stackoverflow.com/questions/30038712/read-data-to-load-game-from-text-file
// - https://www.w3schools.com/java/java_files_read.asp
