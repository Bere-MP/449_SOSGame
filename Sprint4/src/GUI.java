import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    private int boardSize = 8;
    private char blueLetter = 'S';
    private char redLetter = 'S';
    private GameLogic game;

    private JLabel infoLabel;
    private BoardPanel boardPanel;
    private boolean simpleMode = true;

    private boolean blueIsComputer = false;
    private boolean redIsComputer = false;

    public GUI(){
        setTitle("SOS Game Board");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the game Board
        game = new SimpleGame(boardSize);

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

        // Options for player Blue type: Human / Computer
        playerBluePanel.add(Box.createVerticalStrut(8));
        playerBluePanel.add(new JLabel("Type:"));
        JRadioButton blueHuman = new JRadioButton("Human", true);
        JRadioButton blueComputer = new JRadioButton("Computer");
        ButtonGroup blueTypeGroup = new ButtonGroup();
        blueTypeGroup.add(blueHuman);
        blueTypeGroup.add(blueComputer);
        blueHuman.addActionListener(e -> blueIsComputer = false);
        blueComputer.addActionListener(e -> blueIsComputer = true);
        playerBluePanel.add(blueHuman);
        playerBluePanel.add(blueComputer);

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

        // Options for player Blue type: Human / Computer
        playerRedPanel.add(Box.createVerticalStrut(8));
        playerRedPanel.add(new JLabel("Type:"));
        JRadioButton redHuman = new JRadioButton("Human", true);
        JRadioButton redComputer = new JRadioButton("Computer");
        ButtonGroup redTypeGroup = new ButtonGroup();
        redTypeGroup.add(redHuman);
        redTypeGroup.add(redComputer);
        redHuman.addActionListener(e -> redIsComputer = false);
        redComputer.addActionListener(e -> redIsComputer = true);
        playerRedPanel.add(redHuman);
        playerRedPanel.add(redComputer);

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

        // Default to simple mode
        simpleButton.setSelected(true);

        // Add action to buttons, registering what game mode to play in
        // Action Listener for Simple Game Mode
        simpleButton.addActionListener(e ->{
            simpleMode = true;
            game = new SimpleGame(boardSize);
            boardPanel.repaint();
            updateTurn();
            processComputerTurn();
        });

        // Action Listener for General Game Mode
        generalButton.addActionListener(e ->{
            simpleMode = false;
            game = new GeneralGame(boardSize);
            boardPanel.repaint();
            updateTurn();
            processComputerTurn();
        });

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
                    game = simpleMode ? new SimpleGame(boardSize) : new GeneralGame(boardSize);
                    boardPanel.repaint();
                    updateTurn();
                    processComputerTurn();
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

        // If Player Blue is the Computer, begin turn
        processComputerTurn();

    }

    private class BoardPanel extends JPanel {
        public BoardPanel() {
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    // If the current player is the computer, ignore the mouse clicks
                    if ((game.getCurrentPlayer() == Player.BLUE && blueIsComputer) ||
                            (game.getCurrentPlayer() == Player.RED && redIsComputer)) {
                        return;
                    }

                    int boardSize = game.getGameBoard().getSize();

                    int cellW = getWidth() / boardSize;
                    int cellH = getHeight() / boardSize;

                    //int col = e.getX() / cellW;
                    //int row = e.getY() / cellH;
                    int col = Math.min(boardSize - 1, Math.max(0, e.getX() / cellW));
                    int row = Math.min(boardSize - 1, Math.max(0, e.getY() / cellH));

                    char letter = (game.getCurrentPlayer() == Player.BLUE) ? blueLetter : redLetter;
                    if (game.getGameBoard().placeLetter(row, col, letter)) {
                        // check if an SOS has been found within the board
                        boolean SOSFound = game.checkSOS(row, col, letter);

                        // Within the SOS game on Simple Mode
                        if (game instanceof SimpleGame){
                            SimpleGame simGame = (SimpleGame) game;

                            // Check if the game has ended
                            if (simGame.isGameOver()){
                                Player winner = simGame.getWinner();
                                // Display Message for Winner / Results
                                String message = (winner != null) ? "Game Over! Winner: " + winner : "Game Over! It's a Draw";
                                JOptionPane.showMessageDialog(null, message);
                            }
                            // Continue the game if the game has not ended
                            else if (!SOSFound){
                                game.nextPlayer();
                            }
                        }
                        // Within the SOS game on General Mode
                        else if (game instanceof GeneralGame) {
                            GeneralGame genGame = (GeneralGame) game;

                            // Continue the game if the game has not ended
                            if (!SOSFound){
                                game.nextPlayer();
                            }

                            // Check if the game has ended.
                            if (genGame.isGameOver()){
                                Player winner = genGame.getWinner();
                                // Display Message for Winner / Results
                                String message = (winner != null) ? "Game Over! Winner: " + winner : "Game Over! It's a Draw";
                                JOptionPane.showMessageDialog(null, message + "\n" + genGame.getScore());
                            }
                        }
                        else{
                            if (!SOSFound) game.nextPlayer();
                        }

                        updateTurn();
                        repaint();
                        processComputerTurn();
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

    private void processComputerTurn(){
        // While current player is a computer and the game is still in process
        while (!game.isGameOver() && ((game.getCurrentPlayer() == Player.BLUE && blueIsComputer)
                || (game.getCurrentPlayer() == Player.RED && redIsComputer))) {

            // Have computer choose a move
            Object[] move = ComputerPlayer.chooseMove(game);
            if (move == null) break;  //

            int r = (Integer) move[0];
            int c = (Integer) move[1];
            char letter = (Character) move[2];

            // Place the computer's letter down
            boolean placed = game.getGameBoard().placeLetter(r, c, letter);
            if (!placed) {
                // In the instance that the placement can't be made: break to avoid infinite loop
                break;
            }

            // True or False: Find if an SOS sequence has been found
            boolean sosFound = game.checkSOS(r, c, letter);

            // In the instance of the game
            if (game instanceof SimpleGame){
                SimpleGame sim = (SimpleGame) game;
                // if an SOS sequence has been found,
                // display winner
                if (sim.isGameOver()) {
                    Player winner = sim.getWinner();
                    String message = (winner != null) ? "Game Over! Winner: " + winner : "Game Over! It's a Draw";
                    JOptionPane.showMessageDialog(this, message);
                    updateTurn();
                    repaint();
                    return;
                }
                // If no SOS sequence has been found
                // switch turn
                else if (!sosFound){
                    game.nextPlayer();
                }
            }

            // In the instance of a General Game
            else if (game instanceof GeneralGame) {
                GeneralGame gen = (GeneralGame) game;

                // If no SOS sequence found, switch turn
                if (!sosFound){
                    gen.nextPlayer();
                }

                // if the board is full and the game is over
                // display winner
                if (gen.isGameOver()){
                    Player winner = gen.getWinner();
                    String message = (winner != null) ? "Game Over! Winner: " + winner : "Game Over! It's a Draw";
                    JOptionPane.showMessageDialog(this, message + "\n" + gen.getScore());
                    updateTurn();
                    repaint();
                    return;
                }
            }
            // **Error: Points don't add
            else{
                if (!sosFound) game.nextPlayer();
                // Added line of code to increase counter for
                // the scoring in General Mode
                else game.SOSPlacementFoundCount(r, c, letter);
            }

            updateTurn();
            repaint();
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

// Menu for Simple / General Mode, and Size Input
// Reference:
// - https://www.geeksforgeeks.org/java/java-swing-jmenubar/