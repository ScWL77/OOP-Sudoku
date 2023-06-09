package sudoku;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

//LINE 94-122 IS ADDED GET HINT FUNCTION
public class GameBoardPanel extends JPanel{
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for the game board properties
    public static final int GRID_SIZE = 9;    // Size of the board
    public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * GRID_SIZE + 100;
    public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /** The game board composes of 9x9 Cells (customized JTextFields) */
    private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    /** It also contains a Puzzle with array numbers and isGiven */
    private Puzzle puzzle;
    private String difficulty;

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public Cell[][] getCells() {
        return cells;
    }

    /** Constructor */
    public GameBoardPanel(String difficulty) {
        this.difficulty = difficulty;
        this.puzzle  = new Puzzle(difficulty);
        super.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                if ((row ==2 || row ==5 || row==GRID_SIZE-1)&&(col ==2 || col == 5 || col ==GRID_SIZE-1)) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
                } else if (row ==2 || row ==5 || row==GRID_SIZE-1 ) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK));
                } else if (col ==2 || col == 5 || col ==GRID_SIZE-1) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK));
                } else {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                }
                super.add(cells[row][col]);   // JPanel
            }
        }

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)
        CellInputListener listener = new CellInputListener();
        // [TODO 4] Adds this common listener to all editable cells
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);   // For all editable rows and cols
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /**
     * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame() {
        // Generate a new puzzle with intermediate level
        puzzle.newPuzzle(difficulty);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    public void getHint() {
        // Count how many hints have already been given
        int numHintsGiven = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (cells[i][j].status == CellStatus.HINT) {
                    numHintsGiven++;
                }
            }
        }

        // If all cells have been hinted, don't do anything
        if (numHintsGiven == 5) {
            JOptionPane.showMessageDialog(null,"You have already reach the maximum limit for hints");
        }else {
            Random rand = new Random();
            int row, col;
            do {
                row = rand.nextInt(9);
                col = rand.nextInt(9);
            } while (cells[row][col].status != CellStatus.TO_GUESS); // Keep generating random indices until an empty cell is found

            // Highlight the randomly selected cell
            //puzzle.isGiven[row][col] = true;
            //cells[row][col].setEditable(false);
            cells[row][col].status = CellStatus.HINT;
            cells[row][col].paint();
        }
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell)e.getSource();

            // Retrieve the int entered
            int numberIn = Integer.parseInt(sourceCell.getText());
            // For debugging
            System.out.println("You entered " + numberIn);

            /*
             * [TODO 5]
             * Check the numberIn against sourceCell.number.
             * Update the cell status sourceCell.status,
             * and re-paint the cell via sourceCell.paint().
             */
            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
                sourceCell.setEditable(false);
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
            }
            sourceCell.paint();   // re-paint this cell based on its status

            /*
             * [TODO 6] Check if the player has solved the puzzle after this move,
             *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
             */
            if(isSolved()==true) {
                JOptionPane.showMessageDialog(null,"Congratulations");
            }
        }
    }

}
