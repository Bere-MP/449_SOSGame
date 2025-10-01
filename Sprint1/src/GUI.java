import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private Board gameBoard;
    private int boardSize = 8;
    private boolean p1Turn = true;
    private char currentLetter= 'S';

    private JLabel infoLabel;
    private JRadioButton radioS, radioO;

    public GUI(){
        setTitle("SOS Game Board");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu for Simple / General Mode, and Size Input
        // Reference:
        // - https://www.geeksforgeeks.org/java/java-swing-jmenubar/
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game Mode:");
        JMenuItem simpleMode = new JMenuItem("Simple");
        JMenuItem generalMode = new JMenuItem("General");

        // Add items within their respective folder
        gameMenu.add(simpleMode);
        gameMenu.add(generalMode);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // Create the game Board
        gameBoard = new Board(boardSize);
        BoardPanel boardPanel = new BoardPanel();

    }

    private class BoardPanel extends JPanel {
        public BoardPanel() {
            setPreferredSize(new Dimension(500, 500));

            addMouseListener(new MouseAdapter() {
                @Override

                // How to get position when mouse clicked
                // Reference:
                // - https://stackoverflow.com/questions/17777548/how-to-get-element-position-in-2d-array-when-mouse-clicked/17777739#17777739
                public void mouseClicked(MouseEvent e) {
                    int cellW = getWidth() / boardSize;
                    int cellH = getHeight() / boardSize;

                    int col = e.getX() / cellW;
                    int row = e.getY() / cellH;

                    if (gameBoard.placeLetter(row, col, currentLetter)) {
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

            // Code in function to draw letter to grid
        }
    }

    // Code in Display function to display GUI
    //javax.swing.SwingUtilities.invokeLater(() -> {
    //    GUI gui = new GUI();
    //    gui.setVisible(true);
    //});

}

// Reference:
// - https://stackoverflow.com/questions/51746451/how-do-i-make-a-jpanel-grid
// - https://stackoverflow.com/questions/16192939/how-to-use-mouselistener-to-find-a-specific-cell-in-a-grid
// -

