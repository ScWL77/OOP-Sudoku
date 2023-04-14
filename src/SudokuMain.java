package sudoku;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import java.util.Timer;
import java.util.TimerTask;

/**
 * The main Sudoku program
 */
//LINE 30-55 IS MODIFIED AND LINE 106-113 IS ADDED
public class SudokuMain extends JFrame{
    private static final long serialVersionUID = 1L;  // to prevent serial warning
    private JButton restart_btn;   // Declare a Button component
    private JButton pauseResumeBtn;
    private JButton hint_btn;
    private String musicFilePath = "music.wav";
    private music musicObject = new music();
    private JLabel time;
    private String difficulty;
    private Container cp;
    private Cell[][] grid;
    private ProgressBar progressBar;
    private int totalCells;
    private int filledCells;
    private JProgressBar jprogressBar;
    private SudokuScorer sudokuScorer;
    private JLabel scoreText;


    // private variables
    GameBoardPanel board;
    JButton btnNewGame = new JButton("New Game");

    // Constructor
    public SudokuMain(String difficulty) {
        this.difficulty = difficulty;
        this.board  = new GameBoardPanel(difficulty);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);


        // Add a button to the south to re-start the game via board.newGame()
        restart_btn = new JButton("Restart");
        restart_btn.setForeground(Color.black);
        restart_btn.setBackground(new Color(240, 240, 240));
        //cp.add(restart_btn,BorderLayout.PAGE_END);

        //Add a button for game music
        pauseResumeBtn = new JButton("Disable Music");
        pauseResumeBtn.setForeground(Color.black);
        pauseResumeBtn.setBackground(new Color(240, 240, 240));
        //cp.add(pauseResumeBtn, BorderLayout.PAGE_END);

        //Add a hint button for getting cheats and hint
        hint_btn = new JButton("Get Hints");
        hint_btn.setForeground(Color.black);
        hint_btn.setBackground(new Color(240, 240, 240));

        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(restart_btn);
        southPanel.add(pauseResumeBtn);
        southPanel.add(hint_btn);
        cp.add(southPanel,BorderLayout.SOUTH);

        BtnCountListener listener = new BtnCountListener();
        restart_btn.addActionListener(listener);
        pauseResumeBtn.addActionListener(new MusicListener());
        hint_btn.addActionListener(new HintListener());

        // Initialize the game board to start the game
        board.newGame();
        new Clock();


        grid = board.getCells();
        totalCells = countEmptyCells() + countFilledCells();
        filledCells = countFilledCells();
        progressBar = new ProgressBar(totalCells, filledCells);
        cp.add(progressBar.getProgressBar(), BorderLayout.NORTH);

        sudokuScorer = new SudokuScorer();
        scoreText = new JLabel("   Score =    " + sudokuScorer.calculateScore() + "      ");

        pack();     // Pack the UI components, instead of using setSize()
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);

        musicObject.playMusic(musicFilePath);
    }

    // Method to count the number of empty cells in the grid
    public int countEmptyCells() {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getStatus() == CellStatus.TO_GUESS || grid[i][j].getStatus() == CellStatus.WRONG_GUESS) { // If cell is empty
                    count++;
                }
            }
        }
        //Testing for number empty
        //System.out.println(count + " this is the empty");
        return count;
    }

    // Method to count the number of filled cells in the grid
    public int countFilledCells() {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getStatus() == CellStatus.GIVEN || grid[i][j].getStatus() == CellStatus.HINT || grid[i][j].getStatus() == CellStatus.CORRECT_GUESS) { // If cell is filled
                    count++;
                }
            }
        }
        //testing for number filled
        //System.out.println(count + " this is the filled");
        return count;
    }

    /** The entry main() entry method */

    /*public static void main(String[] args) {
        // [TODO 1] Check "Swing program template" on how to run
        //  the constructor of "SudokuMain"
        new SudokuMain();
    }
     */

    // Define an inner class to handle the "Count" button-click
    private class BtnCountListener implements ActionListener {
        // ActionEvent handler - Called back upon button-click.
        @Override
        public void actionPerformed(ActionEvent evt) {
            int res = JOptionPane.showConfirmDialog(null, "You want to restart the game?","Restart Game",JOptionPane.YES_NO_OPTION);
            if(res == JOptionPane.YES_OPTION) {
                //System.out.println("u selected yes");
                dispose();
                new SudokuMain(difficulty);
            }else if(res == JOptionPane.NO_OPTION) {
                //System.out.println("u selected no");
            }
        }
    }

    // Define an inner class to handle the "Pause/Resume Music" button-click
    private class MusicListener implements ActionListener {
        // ActionEvent handler - Called back upon button-click.
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (pauseResumeBtn.getText().equals("Disable Music")) {
                musicObject.pauseMusic();
                pauseResumeBtn.setText("Enable Music");
            } else {
                musicObject.resumeMusic();
                pauseResumeBtn.setText("Disable Music");
            }
        }
    }

    private class HintListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            board.getHint();
        }
    }

    private class Clock {
        java.util.Timer timer = new Timer();

        private Clock(){
            time = new JLabel("00:00:00");
            time.setFont(new Font("Arial", Font.PLAIN, 20));
            TimerTask task = new TimerTask() {
                int secondsPassed = 0;

                @Override
                public void run() {
                    int hours = secondsPassed / 3600;
                    int minutes = secondsPassed / 60 - hours * 60;
                    int seconds = secondsPassed - hours * 3600 - minutes * 60;

                    String timeElapsed = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                    //Testing for time
                    //System.out.println(timeElapsed);

                    time.setText(timeElapsed);
                    secondsPassed++;

                    progressBar.updateProgress(countFilledCells());
                    scoreText.setText("   Score =    " + sudokuScorer.calculateScore() + "      ");
                    cp.add(new Panel().add(scoreText, 0), BorderLayout.WEST);

                    cp.add(new Panel().add(time, 0), BorderLayout.EAST);
                }
            };
            timer.schedule(task, 1000, 1000); // starts the task after 1 second and repeats every 1 second
        }

    }

    public class ProgressBar extends JFrame {

        private JProgressBar progressBar;
        private int totalCells;
        private int filledCells;

        public ProgressBar(int totalCells, int filledCells) {
            this.totalCells = totalCells;
            this.filledCells = filledCells;
            this.setTitle("Sudoku Progress");
            //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //this.setSize(300, 100);
            //this.setLayout(new BorderLayout());
            progressBar = new JProgressBar(0, totalCells);
            progressBar.setValue(filledCells);
            progressBar.setStringPainted(true);
            //this.add(progressBar, BorderLayout.CENTER);
            //this.setVisible(true);
        }

        public void updateProgress(int filledCells) {
            this.filledCells = filledCells;
            progressBar.setValue(filledCells);
        }


        public JProgressBar getProgressBar() {
            return progressBar;
        }
    }
    public class SudokuScorer {

        // Calculates the score of a Sudoku game
        public int calculateScore() {
            int score = 0;
            for (int i = 0; i < grid.length; i++) {

                boolean x = true;
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j].getStatus() == CellStatus.TO_GUESS || grid[i][j].getStatus() == CellStatus.WRONG_GUESS) { // If cell is empty
                        x = false;
                    }
                }
                if (x == true){
                    score += 10;
                }
            }
            for (int j = 0; j < grid.length; j++) {

                boolean x = true;
                //System.out.println("grid length is " + grid[i].length);
                for (int i = 0; i < grid[j].length; i++) {
                    System.out.println("i = " + i);
                    if (grid[i][j].getStatus() == CellStatus.TO_GUESS || grid[i][j].getStatus() == CellStatus.WRONG_GUESS) { // If cell is empty
                        x = false;
                    }
                }
                if (x == true){
                    score += 10;
                }
            }

            // Check rows
          /*  for (int row = 0; row < 9; row++) {
                if (isComplete(grid[row])) {
                    score += 10;
                }
            }



            // Check columns
            for (int col = 0; col < 9; col++) {
                int[] column = getColumn(grid, col);
                if (isComplete(column)) {
                    score += 10;
                }
            }

            // Check sub-grids
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int[] subGrid = subGrids[row][col];
                    if (isComplete(subGrid)) {
                        score += 20;
                    }
                }
            }
            */
            return score;
        }

        // Returns true if the array is complete (contains no zeros)
        // private static boolean isComplete(Cell[] array) {
        //  for (int i = 0; i < array.length; i++) {
        //      if (array[i] == 0) {
        //          return false;
        //      }
        //   }
        //return true;
    }

    // Returns the column at the specified index as a 1D array
    private static int[] getColumn(int[][] grid, int colIndex) {
        int[] column = new int[9];
        for (int row = 0; row < 9; row++) {
            column[row] = grid[row][colIndex];
        }
        return column;
    }

    // Returns a 3D array containing the 3x3 sub-grids
    private static int[][][] getSubGrids(int[][] grid) {
        int[][][] subGrids = new int[3][3][9];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int[] subGrid = new int[9];
                int index = 0;
                for (int i = row * 3; i < row * 3 + 3; i++) {
                    for (int j = col * 3; j < col * 3 + 3; j++) {
                        subGrid[index++] = grid[i][j];
                    }
                }
                subGrids[row][col] = subGrid;
            }
        }
        return subGrids;
    }
}