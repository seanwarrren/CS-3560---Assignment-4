import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3]; // 3x3 grid of buttons for game board
    private boolean xTurn = true; // tracks whose turn it is

    // constructor to initialize the window and game board
    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(340, 360);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3, 5, 5));
        initBoard();
        setVisible(true);
    }

    // initialize the 3x3 board with buttons
    private void initBoard() {
        Font font = new Font("Arial", Font.BOLD, 40);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setFont(font);
                button.addActionListener(this); // add action listener for click events
                buttons[row][col] = button;
                add(button);
            }
        }
    }

    // handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource(); // identify which button was clicked
        if (!clicked.getText().isEmpty()) return;

        // set button text and color based on current player
        clicked.setText(xTurn ? "X" : "O");
        clicked.setForeground(xTurn ? Color.BLUE : Color.RED);

        // check if move ends the game
        if (checkWinner()) {
            JOptionPane.showMessageDialog(this, (xTurn ? "X" : "O") + " wins!");
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetBoard();
        } else {
            xTurn = !xTurn;
        }
    }

    // check if there is a winning line 
    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            // rows and columns
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                    checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) return true;
        }
        // diagonals
        return checkLine(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                checkLine(buttons[0][2], buttons[1][1], buttons[2][0]);
    }

    // check if three buttons have the same text
    private boolean checkLine(JButton b1, JButton b2, JButton b3) {
        String t1 = b1.getText(), t2 = b2.getText(), t3 = b3.getText();
        return !t1.isEmpty() && t1.equals(t2) && t2.equals(t3);
    }

    // check if board is full (draw)
    private boolean isBoardFull() {
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                if (b.getText().isEmpty()) return false;
            }
        }
        return true;
    }

    // reset board for a new game
    private void resetBoard() {
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                b.setText("");
                b.setForeground(Color.BLACK);
            }
        }
        xTurn = true;
    }

    // Entry point to start the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}