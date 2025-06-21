import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class KeyboardPanel extends JPanel {
    private final String[] QWERTY_LAYOUT = {"QWERTYUIOP", "ASDFGHJKL", "ZCVBNM"};
    private Map<Character, JButton> keyButtons;

    public KeyboardPanel(ActionListener listener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        keyButtons = new HashMap<>();

        Font keyFont = new Font("Arial", Font.BOLD, 16);

        for (String row : QWERTY_LAYOUT) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 3));
            rowPanel.setBackground(Color.BLACK);
            for (char c : row.toCharArray()) {
                JButton button = new JButton(String.valueOf(c));
                button.addActionListener(listener);
                button.setFont(keyFont);
                button.setBackground(Color.GRAY);
                button.setForeground(Color.BLACK);
                button.setFocusable(false);
                keyButtons.put(c, button);
                rowPanel.add(button);
            }
            add(rowPanel);
        }

        // Special keys (Enter, Backspace)
        JPanel specialKeyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 3));
        specialKeyPanel.setBackground(Color.BLACK);
        JButton enterButton = new JButton("ENTER");
        enterButton.addActionListener(listener);
        enterButton.setFont(keyFont);
        enterButton.setFocusable(false);
        JButton backspaceButton = new JButton("⌫"); // Backspace
        backspaceButton.addActionListener(listener);
        backspaceButton.setFont(keyFont);
        backspaceButton.setFocusable(false);
        specialKeyPanel.add(enterButton);
        specialKeyPanel.add(backspaceButton);
        add(specialKeyPanel);
    }

    public void updateKeyColor(char c, Color color) {
        JButton key = keyButtons.get(Character.toUpperCase(c));
        if (key != null && key.getBackground() != Color.GREEN) {
             key.setBackground(color);
        }
    }
    
    public void reset() {
        for(JButton button : keyButtons.values()) {
            button.setBackground(Color.GRAY);
        }
    }
}