import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameBoardPanel extends JPanel {
    private JLabel[][] cells;

    public GameBoardPanel() {
        setLayout(new GridLayout(6, 5, 5, 5));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cells = new JLabel[6][5];
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                cells[row][col] = new JLabel("", SwingConstants.CENTER);
                cells[row][col].setOpaque(true);
                cells[row][col].setBackground(Color.BLACK);
                cells[row][col].setForeground(Color.WHITE);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 36));
                cells[row][col].setBorder(border);
                add(cells[row][col]);
            }
        }
    }

    public void updateGrid(int row, String guess, GameLogic.LetterStatus[] status) {
        for (int i = 0; i < 5; i++) {
            cells[row][i].setText(String.valueOf(guess.charAt(i)));
            switch (status[i]) {
                case CORRECT:
                    cells[row][i].setBackground(new Color(83, 141, 78)); // Green
                    break;
                case PRESENT:
                    cells[row][i].setBackground(new Color(181, 159, 59)); // Yellow
                    break;
                case ABSENT:
                    cells[row][i].setBackground(Color.DARK_GRAY);
                    break;
            }
        }
    }

    public void setLetter(int row, int col, char letter) {
        if(col < 5) cells[row][col].setText(String.valueOf(letter));
    }

    public void deleteLetter(int row, int col) {
        if(col >= 0) cells[row][col].setText("");
    }

    public void reset() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                cells[row][col].setText("");
                cells[row][col].setBackground(Color.BLACK);
            }
        }
    }
}