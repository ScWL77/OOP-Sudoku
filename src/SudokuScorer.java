package sudoku;

public class SudokuScorer {

    // Calculates the score of a Sudoku game
    public static int calculateScore(int[][] grid) {
        int score = 0;
        int[][][] subGrids = getSubGrids(grid);
        
        // Check rows
        for (int row = 0; row < 9; row++) {
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
        
        return score;
    }
    
    // Returns true if the array is complete (contains no zeros)
    private static boolean isComplete(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                return false;
            }
        }
        return true;
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