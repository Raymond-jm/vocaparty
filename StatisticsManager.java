import java.io.*;

public class StatisticsManager {
    private static final String STATS_FILE = "stats.dat";

    public static void saveStats(GameStats stats) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STATS_FILE))) {
            oos.writeObject(stats);
        } catch (IOException e) {
            System.err.println("Error saving stats: " + e.getMessage());
        }
    }

    public static GameStats loadStats() {
        File file = new File(STATS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STATS_FILE))) {
                return (GameStats) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading stats, creating new ones: " + e.getMessage());
                return new GameStats();
            }
        }
        return new GameStats();
    }
}