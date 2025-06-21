import java.io.Serializable;
import java.util.Arrays;

public class GameStats implements Serializable {
    private static final long serialVersionUID = 1L; // 직렬화 버전 UID

    private int gamesPlayed;
    private int gamesWon;
    private int currentStreak;
    private int maxStreak;
    private int[] guessDistribution; // 1~6회차 승리 횟수

    public GameStats() {
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.currentStreak = 0;
        this.maxStreak = 0;
        this.guessDistribution = new int[6];
    }

    // 통계 업데이트 메서드들
    public void recordWin(int guessCount) {
        gamesPlayed++;
        gamesWon++;
        currentStreak++;
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }
        if (guessCount >= 1 && guessCount <= 6) {
            guessDistribution[guessCount - 1]++;
        }
    }

    public void recordLoss() {
        gamesPlayed++;
        currentStreak = 0;
    }

    // Getter 메서드들
    public int getGamesPlayed() { return gamesPlayed; }
    public int getGamesWon() { return gamesWon; }
    public int getCurrentStreak() { return currentStreak; }
    public int getMaxStreak() { return maxStreak; }
    public int[] getGuessDistribution() { return guessDistribution; }

    @Override
    public String toString() {
        double winPercentage = (gamesPlayed > 0) ? (double) gamesWon / gamesPlayed * 100 : 0;
        return "<html><h2>Game Statistics</h2>" +
               "<p>Games Played: " + gamesPlayed + "</p>" +
               "<p>Win %: " + String.format("%.2f", winPercentage) + "</p>" +
               "<p>Current Streak: " + currentStreak + "</p>" +
               "<p>Max Streak: " + maxStreak + "</p>" +
               "<h3>Guess Distribution:</h3>" +
               "<p>1: " + guessDistribution[0] + "</p>" +
               "<p>2: " + guessDistribution[1] + "</p>" +
               "<p>3: " + guessDistribution[2] + "</p>" +
               "<p>4: " + guessDistribution[3] + "</p>" +
               "<p>5: " + guessDistribution[4] + "</p>" +
               "<p>6: " + guessDistribution[5] + "</p></html>";
    }
}