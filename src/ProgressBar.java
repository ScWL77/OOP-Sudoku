package sudoku;

import javax.swing.*;
import java.awt.*;

public class ProgressBar extends JFrame {
    
    private JProgressBar progressBar;
    private int totalCells;
    private int filledCells;

    public ProgressBar(int totalCells, int filledCells) {
        this.totalCells = totalCells;
        this.filledCells = filledCells;
        this.setTitle("Sudoku Progress");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 100);
        this.setLayout(new BorderLayout());
        progressBar = new JProgressBar(0, totalCells);
        progressBar.setValue(filledCells);
        progressBar.setStringPainted(true);
        this.add(progressBar, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    public void updateProgress(int filledCells) {
        this.filledCells = filledCells;
        progressBar.setValue(filledCells);
    }
    
    public static void main(String[] args) {
        int totalCells = 81;
        int filledCells = 20;
        ProgressBar progressBar = new ProgressBar(totalCells, filledCells);
        // update progress bar as cells are filled in
        progressBar.updateProgress(25);
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }
}