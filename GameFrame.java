import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    private GameBoardPanel gameBoard;
    private KeyboardPanel keyboard;
    private GameLogic gameLogic;
    private WordList wordList;
    private StatisticsManager statsManager;
    private GameStats stats;

    private int currentRow = 0;
    private int currentCol = 0;
    private StringBuilder currentGuess;

    private JLabel timerLabel;
    private GameTimer gameTimer;
    private Thread timerThread;

    public GameFrame() {
        // 기본 프레임 설정
        setTitle("WordParty");
        setSize(800, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        // 컴포넌트 초기화
        wordList = new WordList("words.txt");
        stats = StatisticsManager.loadStats();
        
        // 메뉴바 설정
        setupMenuBar();

        // 상단 패널 (타이틀, 타이머)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        JLabel titleLabel = new JLabel("WordParty", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        timerLabel = new JLabel("Time: 00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(timerLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 게임 컴포넌트
        gameBoard = new GameBoardPanel();
        keyboard = new KeyboardPanel(this); // 'this'를 ActionListener로 전달

        add(gameBoard, BorderLayout.CENTER);
        add(keyboard, BorderLayout.SOUTH);

        startNewGame();
        setVisible(true);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem statsItem = new JMenuItem("Statistics");
        JMenuItem exitItem = new JMenuItem("Exit");

        newGameItem.addActionListener(e -> startNewGame());
        statsItem.addActionListener(e -> showStats());
        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(newGameItem);
        gameMenu.add(statsItem);
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void startNewGame() {
        gameLogic = new GameLogic(wordList);
        currentGuess = new StringBuilder();
        currentRow = 0;
        currentCol = 0;

        gameBoard.reset();
        keyboard.reset();
        
        // 타이머 재시작
        if (timerThread != null && timerThread.isAlive()) {
            gameTimer.stop();
        }
        gameTimer = new GameTimer(timerLabel);
        timerThread = new Thread(gameTimer);
        timerThread.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (gameLogic.isGameOver()) return;

        if (command.equals("ENTER")) {
            handleEnter();
        } else if (command.equals("⌫")) {
            handleBackspace();
        } else if (command.length() == 1) { // 문자 키
            handleLetter(command.charAt(0));
        }
    }

    private void handleLetter(char letter) {
        if (currentCol < 5) {
            currentGuess.append(letter);
            gameBoard.setLetter(currentRow, currentCol, letter);
            currentCol++;
        }
    }

    private void handleBackspace() {
        if (currentCol > 0) {
            currentCol--;
            currentGuess.deleteCharAt(currentCol);
            gameBoard.deleteLetter(currentRow, currentCol);
        }
    }

    private void handleEnter() {
        if (currentCol != 5) {
            JOptionPane.showMessageDialog(this, "5 letter required", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String guess = currentGuess.toString();
        if (!wordList.contains(guess)) {
            JOptionPane.showMessageDialog(this, "Insert available word", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        GameLogic.LetterStatus[] status = gameLogic.checkGuess(guess);
        gameBoard.updateGrid(currentRow, guess, status);
        updateKeyboard(guess, status);

        if (gameLogic.isWin(guess)) {
            gameTimer.stop();
            stats.recordWin(gameLogic.getAttempts());
            StatisticsManager.saveStats(stats);
            JOptionPane.showMessageDialog(this, "Congratulations! You won!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else if (gameLogic.isGameOver()) {
            gameTimer.stop();
            stats.recordLoss();
            StatisticsManager.saveStats(stats);
            JOptionPane.showMessageDialog(this, "Game Over! The word was: " + gameLogic.getSecretWord(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else {
            currentRow++;
            currentCol = 0;
            currentGuess = new StringBuilder();
        }
    }

    private void updateKeyboard(String guess, GameLogic.LetterStatus[] status) {
        for(int i=0; i<guess.length(); i++) {
            char c = guess.charAt(i);
            Color color;
            switch(status[i]) {
                case CORRECT:
                    color = new Color(83, 141, 78); // Green
                    break;
                case PRESENT:
                    color = new Color(181, 159, 59); // Yellow
                    break;
                default:
                    color = Color.DARK_GRAY;
                    break;
            }
            keyboard.updateKeyColor(c, color);
        }
    }
    
    private void showStats() {
        JOptionPane.showMessageDialog(this, stats.toString(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }
}