import javax.swing.JLabel;

public class GameTimer implements Runnable {
    private JLabel timerLabel;
    private boolean running = true;
    private int seconds = 0;

    public GameTimer(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000); // 1초 대기
                seconds++;
                int minutes = seconds / 60;
                int secs = seconds % 60;
                timerLabel.setText(String.format("Time: %02d:%02d", minutes, secs));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public void stop() {
        running = false;
    }
}