package sudoku;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;  // Using AWT event classes and listener interfaces
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame{
	private static final long serialVersionUID = 1L;  // to prevent serial warning
	private JButton restart_btn;   // Declare a Button component
	private JButton pauseResumeBtn;
	private String musicFilePath = "music.wav";
    private music musicObject = new music();

	   // private variables
	   GameBoardPanel board = new GameBoardPanel();
	   JButton btnNewGame = new JButton("New Game");

	   // Constructor
	   public SudokuMain() {
	      Container cp = getContentPane();
	      cp.setLayout(new BorderLayout());

	      cp.add(board, BorderLayout.CENTER);

	      // Add a button to the south to re-start the game via board.newGame()
	      restart_btn = new JButton("Restart");
	      cp.add(restart_btn,BorderLayout.SOUTH);
	      BtnCountListener listener = new BtnCountListener();
	      restart_btn.addActionListener(listener);
	      
	      //Add a button for game music
	      //pauseResumeBtn = new JButton("Disable Music");
	      //cp.add(pauseResumeBtn, BorderLayout.NORTH);
	      //pauseResumeBtn.addActionListener(new MusicListener());
	      
	      // Initialize the game board to start the game
	      board.newGame();

	      pack();     // Pack the UI components, instead of using setSize()
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
	      setTitle("Sudoku");
	      setVisible(true);
	      
	      //musicObject.playMusic(musicFilePath);
	   }

	   /** The entry main() entry method */
	   public static void main(String[] args) {
	      // [TODO 1] Check "Swing program template" on how to run
	      //  the constructor of "SudokuMain"
	      new SudokuMain();
	   }
	   
	// Define an inner class to handle the "Count" button-click
	   private class BtnCountListener implements ActionListener {
	      // ActionEvent handler - Called back upon button-click.
	      @Override
	      public void actionPerformed(ActionEvent evt) {
	    	  int res = JOptionPane.showConfirmDialog(null, "You want to restart the game?","Restart Game",JOptionPane.YES_NO_OPTION);
	    	  if(res == JOptionPane.YES_OPTION) {
	    		  //System.out.println("u selected yes");
	    		  dispose();
	    		  new SudokuMain();
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
}
