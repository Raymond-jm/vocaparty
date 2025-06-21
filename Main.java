import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Swing GUI는 Event Dispatch Thread에서 생성하는 것이 안전합니다.
        SwingUtilities.invokeLater(() -> new GameFrame());
    }
}