package sudoku;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

public class HomeMain extends JFrame{
    private JButton easy;
    private JButton intermediate;
    private JButton difficult;
    private String difficulty;
    private JPanel difficultyPanel;

    public HomeMain(){
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        java.net.URL imgURL = getClass().getResource("sudoku.images/screenshot.jpg");
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            icon.setImage(img);
            JLabel image = new JLabel(icon);

            Panel imagePanel = new Panel();
            //imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
            imagePanel.add(image);

            Label title = new Label("Sudoku");
            title.setFont(new Font("Monospace", Font.PLAIN, 40));
            Panel titlePanel = new Panel();
            titlePanel.add(title, 0);
            //imagePanel.add(title);
            cp.add(imagePanel, BorderLayout.CENTER);
            cp.add(titlePanel, BorderLayout.NORTH);
        } else {
            System.err.println("Couldn't find file: sudoku.images/Sudoku screenshot.jpeg");
        }

        difficultyPanel = new JPanel();

        easy = new JButton("Easy");
        //cp.add(easy, BorderLayout.SOUTH);
        easy.addActionListener(new difficultyEasy());
        easy.setPreferredSize(new Dimension(200, 50));
        easy.setFont(new Font("Arial", Font.PLAIN, 40));
        difficultyPanel.add(easy, 0);

        intermediate = new JButton("Medium");
        //cp.add(intermediate, BorderLayout.SOUTH);
        intermediate.addActionListener(new difficultyIntermediate());
        intermediate.setPreferredSize(new Dimension(200, 50));
        intermediate.setFont(new Font("Arial", Font.PLAIN, 40));
        difficultyPanel.add(intermediate, 1);

        difficult = new JButton("Hard");
        //cp.add(difficult, BorderLayout.SOUTH);
        difficult.addActionListener(new difficultyDifficult());
        difficult.setPreferredSize(new Dimension(200, 50));
        difficult.setFont(new Font("Arial", Font.PLAIN, 40));
        difficultyPanel.add(difficult, 2);

        cp.add(difficultyPanel, BorderLayout.SOUTH);



        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Home Screen");
        setVisible(true);
    }

    public static void main(String[] args) {
        // [TODO 1] Check "Swing program template" on how to run
        //  the constructor of "SudokuMain"
        new HomeMain();
    }

    private class difficultyEasy implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt){
            difficulty = "easy";
            new SudokuMain(difficulty);
        }
    }

    private class difficultyIntermediate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt){
            difficulty = "intermediate";
            new SudokuMain(difficulty);
        }
    }

    private class difficultyDifficult implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt){
            difficulty = "difficult";
            new SudokuMain(difficulty);
        }
    }
}
