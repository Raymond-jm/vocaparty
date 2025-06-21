import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordList {
    private List<String> words;

    public WordList(String filePath) {
        words = new ArrayList<>();
        // 파일을 읽어 단어 목록을 채웁니다.
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 5) { // 5글자 단어만 추가
                    words.add(line.trim().toUpperCase());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading word file: " + e.getMessage());
            // 기본 단어 추가
            words.add("APPLE");
        }
    }

    public String getRandomWord() {
        if (words.isEmpty()) {
            return "ERROR";
        }
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    public boolean contains(String word) {
        return words.contains(word.toUpperCase());
    }
}