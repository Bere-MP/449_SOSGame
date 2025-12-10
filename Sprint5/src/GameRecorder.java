import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameRecorder {
    private final List<String> lines = new ArrayList<>();
    private boolean recording = false;

    // Record game configurations to file
    public void start(GameLogic game, boolean blueIsComputer, boolean redIsComputer) {
        lines.clear();
        recording = true;

        lines.add("MODE=" + (game instanceof SimpleGame ? "SIMPLE" : "GENERAL"));
        lines.add("SIZE=" + game.getGameBoard().getSize());
        lines.add("BLUE=" + (blueIsComputer ? "COMPUTER" : "HUMAN"));
        lines.add("RED=" + (redIsComputer ? "COMPUTER" : "HUMAN"));
        lines.add("START=" + game.getCurrentPlayer());
    }

    // Record each move made from what space, the letter, and the player
    public void recordMove(int row, int col, char letter, Player player) {
        if (!recording) return;
        lines.add("MOVE " + row + " " + col + " " + letter + " " + player);
    }

    // End the recording of the saved file
    public void end() {
        if (!recording) return;
        lines.add("END");
        recording = false;
    }

    // Save the game to a text file
    public void saveToFile(File file) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            for (String s : lines) out.println(s);
        }
    }
}

// References:
// - https://stackoverflow.com/questions/22859453/what-is-the-simplest-way-to-write-a-text-file-in-java
// - https://www.w3schools.com/java/java_files_read.asp