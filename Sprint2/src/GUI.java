import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    private int boardSize = 8;
    private char blueLetter = 'S';
    private char redLetter = 'S';
    private GameLogic game;

    private JLabel infoLabel;
    private BoardPanel boardPanel;

    public GUI(){
        setTitle("SOS Game Board");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the game Board
        game = new GameLogic(boardSize, "Simple");

        // Player Blue
        JPanel playerBluePanel = new JPanel();
        playerBluePanel.setLayout(new BoxLayout(playerBluePanel, BoxLayout.Y_AXIS));
        playerBluePanel.add(new JLabel("Blue Player:"));

        // Radio Buttons Specifically for Player Blue
        JRadioButton blueS = new JRadioButton("S", true);
        JRadioButton blueO = new JRadioButton("O" );
        ButtonGroup blueGroup = new ButtonGroup();
        blueGroup.add(blueS);
        blueGroup.add(blueO);

        // Keeps track of who (player blue) choose what ("s" or "o")
        blueS.addActionListener(e -> blueLetter = 'S');
        blueO.addActionListener(e -> blueLetter = 'O');

        playerBluePanel.add(blueS);
        playerBluePanel.add(blueO);

        // Options for player blue set to the left
        add(playerBluePanel, BorderLayout.WEST);

        // Player Red
        JPanel playerRedPanel = new JPanel();
        playerRedPanel.setLayout(new BoxLayout(playerRedPanel, BoxLayout.Y_AXIS));
        playerRedPanel.add(new JLabel("Red Player:"));

        // Radio Buttons Specifically for Player Red
        JRadioButton redS = new JRadioButton("S", true);
        JRadioButton redO = new JRadioButton("O" );
        ButtonGroup redGroup = new ButtonGroup();
        redGroup.add(redS);
        redGroup.add(redO);

        // Keeps track of who (player red) choose what ("s" or "o")
        redS.addActionListener(e -> redLetter = 'S');
        redO.addActionListener(e -> redLetter = 'O');

        playerRedPanel.add(redS);
        playerRedPanel.add(redO);

        // Options for player red set to the right
        add(playerRedPanel, BorderLayout.EAST);

        // Options for Game Mode, Board Size, and the Game Title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Display Game Title
        JLabel gameTitle = new JLabel("SOS");
        gameTitle.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(gameTitle);

        // Display Game Mode Options
        JRadioButton simpleButton = new JRadioButton("Simple Game");
        JRadioButton generalButton = new JRadioButton("General Game");
        ButtonGroup modesGroup = new ButtonGroup();
        modesGroup.add(simpleButton);
        modesGroup.add(generalButton);
        simpleButton.setSelected(true);

        // Default the game mode settings to simple
        game.setGameMode("Simple");

        // Add action to buttons, registering what game mode to play in
        simpleButton.addActionListener(e ->{
            game.setGameMode("Simple");
            updateTurn();
        });

        generalButton.addActionListener(e ->{
            game.setGameMode("General");
            updateTurn();
        });

        // default to simple
        simpleButton.setSelected(true);

        topPanel.add(simpleButton);
        topPanel.add(generalButton);

        // Board Size Options
        JLabel sizeLabel = new JLabel("Board Size: ");
        topPanel.add(sizeLabel);
        JTextField boardSizeField = new JTextField(String.valueOf(boardSize), 3);
        topPanel.add(boardSizeField);

        // New Game
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            // create a try and error for new game
            try {
                int newSize = Integer.parseInt(boardSizeField.getText());
                // make sure the size does not go below 3 or exceed 10
                if (newSize >= 3 && newSize <= 10) {
                    boardSize = newSize;
                    game.resetGame(boardSize);
                    boardPanel.repaint();
                    updateTurn();
                }

                // Game ensures size is valid, error invalid size if not
                else{
                    JOptionPane.showMessageDialog(this, "Board size must be between 3 - 10");
                }

            }

            // ensures the input is an integer, error invalid input if not
            catch (NumberFormatException sizeError){
                JOptionPane.showMessageDialog(this, "Invalid Input");
            }
        });

        topPanel.add(newGameButton);

        add(topPanel, BorderLayout.NORTH);

        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        // Current Player Info
        infoLabel = new JLabel ("Current Turn: " + game.getCurrentPlayer());
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(infoLabel, BorderLayout.SOUTH);

    }

    private class BoardPanel extends JPanel {
        public BoardPanel() {
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    int boardSize = game.getGameBoard().getSize();

                    int cellW = getWidth() / boardSize;
                    int cellH = getHeight() / boardSize;

                    int col = e.getX() / cellW;
                    int row = e.getY() / cellH;

                    char letter = (game.getCurrentPlayer() == Player.BLUE) ? blueLetter : redLetter;
                    if (game.getGameBoard().placeLetter(row, col, letter)) {
                        game.nextPlayer();
                        updateTurn();
                        repaint();
                    }
                }
            });
        }

        // Panel
        // Reference:
        // - https://stackoverflow.com/questions/14068472/java-mouselistener-action-event-in-paintcomponent

        @Override
        protected void paintComponent(Graphics g) {

            // How to display grid
            // Reference:
            // - https://stackoverflow.com/questions/40746687/drawing-a-game-board-in-java
            super.paintComponent(g);
            Board gameBoard = game.getGameBoard();

            int width = getWidth();
            int height = getHeight();

            int rows = gameBoard.getSize();
            int cols = gameBoard.getSize();

            for (int i = 1; i < cols; i++) {
                g.drawLine(i * width / cols, 0, i * width / cols, height);
            }

            for (int i = 1; i < cols; i++) {
                g.drawLine(0, i * height / rows, width, i * height / rows);
            }

            // Draw the Letters (S or O) within the grid

            g.setFont(new Font("Arial", Font.BOLD, 24));
            for (int i = 0; i < rows; i++ ){
                for (int j = 0; j < cols; j++){
                    char letter = gameBoard.getCell(i, j);
                    if (letter != ' '){
                        g.drawString(String.valueOf(letter),
                                j * width / cols + width / cols / 2 - 5,
                                i * height / rows + height / rows / 2 + 5);
                    }
                }
            }
        }
    }
    // Player Turn
    private void updateTurn() {
        infoLabel.setText("Current Turn: " + game.getCurrentPlayer());
    }
}

// Reference:
// - https://stackoverflow.com/questions/51746451/how-do-i-make-a-jpanel-grid
// - https://stackoverflow.com/questions/16192939/how-to-use-mouselistener-to-find-a-specific-cell-in-a-grid
// -

// Menu for Simple / General Mode, and Size Input
// Reference:
// - https://www.geeksforgeeks.org/java/java-swing-jmenubar/

// Code in Display function to display GUI
//javax.swing.SwingUtilities.invokeLater(() -> {
//    GUI gui = new GUI();
//    gui.setVisible(true);
//});