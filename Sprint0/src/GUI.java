import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private JCheckBox timeBox;
    private JRadioButton radioS, radioO;
    private JLabel infoLabel;

    public GUI() {
        setTitle("SOS Game Board");

        // setting size of board
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // text
        JLabel title = new JLabel("SOS Game Board", JLabel.CENTER);
        add(title, BorderLayout.NORTH);

        // Creating the Grid Board
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = getWidth();
                int h = getHeight();
                int rows = 3, cols = 3;

                // creates vertical lines
                for (int i = 1; i < cols; i++) {
                    g.drawLine(i * w / cols, 0, i * w / cols, h);
                }
                // creates horizontal lines
                for (int i = 1; i < rows; i++) {
                    g.drawLine(0, i * h / rows, w, i * h / rows);
                }
            }
        };

        // Setting the panel
        boardPanel.setPreferredSize(new Dimension(300, 300));
        add(boardPanel, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(0, 1));

        // checkbox
        timeBox = new JCheckBox("Timed");
        controls.add(timeBox);

        // radio buttons
        ButtonGroup group = new ButtonGroup();
        radioS = new JRadioButton("S");
        radioO = new JRadioButton("O");
        radioS.setSelected(true);
        group.add(radioS);
        group.add(radioO);

        controls.add(radioS);
        controls.add(radioO);

        infoLabel = new JLabel("Select a letter.");
        controls.add(infoLabel);

        add(controls, BorderLayout.SOUTH);

        timeBox.addActionListener(e -> {
            if (timeBox.isSelected()) {
                infoLabel.setText("Timing enabled.");
            } else {
                infoLabel.setText("Timing disabled.");
            }
        });

        radioS.addActionListener(e -> infoLabel.setText("You chose S"));
        radioO.addActionListener(e -> infoLabel.setText("You chose O"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}

// References:
//      Geeks for Geeks: https://www.geeksforgeeks.org/java/introduction-to-java-swing/
//      Stack Overflow: https://stackoverflow.com/questions/53593173/gui-manipulations-for-board-game

