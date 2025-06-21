public class GameLogic {
    public enum LetterStatus { CORRECT, PRESENT, ABSENT }

    private String secretWord;
    private int attempts;
    private final int maxAttempts = 6;
    private boolean isGameOver;

    public GameLogic(WordList wordList) {
        this.secretWord = wordList.getRandomWord();
        this.attempts = 0;
        this.isGameOver = false;
        System.out.println("Secret Word: " + secretWord); // For debugging
    }

    public LetterStatus[] checkGuess(String guess) {
        attempts++;
        if (guess.equals(secretWord) || attempts >= maxAttempts) {
            isGameOver = true;
        }

        LetterStatus[] result = new LetterStatus[5];
        char[] secretChars = secretWord.toCharArray();
        char[] guessChars = guess.toCharArray();
        boolean[] secretUsed = new boolean[5];

        // 1. Check for correct letters (Green)
        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == secretChars[i]) {
                result[i] = LetterStatus.CORRECT;
                secretUsed[i] = true;
            }
        }

        // 2. Check for present letters (Yellow)
        for (int i = 0; i < 5; i++) {
            if (result[i] == null) { // If not already marked as correct
                boolean found = false;
                for (int j = 0; j < 5; j++) {
                    if (!secretUsed[j] && guessChars[i] == secretChars[j]) {
                        result[i] = LetterStatus.PRESENT;
                        secretUsed[j] = true;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    result[i] = LetterStatus.ABSENT;
                }
            }
        }
        return result;
    }

    public boolean isWin(String guess) {
        return guess.equals(secretWord);
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getSecretWord() {
        return secretWord;
    }
}