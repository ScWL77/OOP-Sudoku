package sudoku;

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
	   // All variables have package access
	   // The numbers on the puzzle
	   int[][] numbers = new int[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
	   // The clues - isGiven (no need to guess) or need to guess
	   boolean[][] isGiven = new boolean[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
       private String difficulty;

	   // Constructor
	   public Puzzle(String difficulty) {
	      super();
          this.difficulty = difficulty;
	   }

    public int[][] getNumbers() {
        return numbers;
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
	   //  to control the difficulty level.
	   // This method shall set (or update) the arrays numbers and isGiven
	   public void newPuzzle(String difficulty) {
		   
		   fillDiagonal();
		   fillRemaining(0,3);
		   
		   int setCellsNotGiven = 0;
		   if(difficulty.equals("easy")) {
			   //easy level 
			   setCellsNotGiven = (int) ((Math.random() * (44 - 31)) + 31);
		   }else if (difficulty.equals("intermediate")) {
			   //intermediate level
			   setCellsNotGiven = (int) ((Math.random() * (49 - 45)) + 45);
		   }else if (difficulty.equals("difficult")) {
			   //difficult level
			   setCellsNotGiven = (int) ((Math.random() * (54 - 49)) + 49);
		   }else {
			   //default set to easy if no proper string
			   setCellsNotGiven = (int) ((Math.random() * (44 - 31)) + 31);
		   }
		   removeKDigits(setCellsNotGiven);
	   }

	boolean fillRemaining(int i, int j) {
		if (j>=9 && i<9-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=9 && j>=9)
            return true;
 
        if (i < 3)
        {
            if (j < 3)
                j = 3;
        }
        else if (i < 9-3)
        {
            if (j==(int)(i/3)*3)
                j =  j + 3;
        }
        else
        {
            if (j == 9-3)
            {
                i = i + 1;
                j = 0;
                if (i>=9)
                    return true;
            }
        }
 
        for (int num = 1; num<=9; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                numbers[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;
 
                numbers[i][j] = 0;
            }
        }
        return false;
		
	}

	public void fillBox(int row,int col) {
		int num;
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                do
                {
                    num = randomGenerator(9);
                }
                while (!unUsedInBox(row, col, num));
 
                numbers[row+i][col+j] = num;
            }
        }
		
	}

	private int randomGenerator(int num) {
		// TODO Auto-generated method stub
		return (int) Math.floor((Math.random()*num+1));
	}

	public void fillDiagonal() {
		// TODO Auto-generated method stub
		for (int i = 0; i<9; i=i+3)
			 
            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
	}
	
	boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<3; i++)
            for (int j = 0; j<3; j++)
                if (numbers[rowStart+i][colStart+j]==num)
                    return false;
 
        return true;
    }
	
	boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%3, j-j%3, num));
    }

	private boolean unUsedInCol(int j, int num) {
		// TODO Auto-generated method stub
		 for (int i = 0; i<9; i++)
	            if (numbers[i][j] == num)
	                return false;
	        return true;
	}

	private boolean unUsedInRow(int i, int num) {
		// TODO Auto-generated method stub
		for (int j = 0; j<9; j++)
	           if (numbers[i][j] == num)
	                return false;
	        return true;
	}
	
	// Remove the K no. of digits to
    // complete game
    public void removeKDigits(int cellsNotGiven)
    {
    	for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
	         for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
	            isGiven[row][col] = true;
	         }
	      }
        int count = cellsNotGiven;
        while (count != 0)
        {
            int cellId = randomGenerator(9*9)-1;
 
            // System.out.println(cellId);
            // extract coordinates i  and j
            int i = (cellId/9);
            int j = cellId%9;
            // System.out.println(i+" "+j);
            if (isGiven[i][j] != false)
            {
                count--;
                isGiven[i][j] = false;
            }
        }
    }
	   //(For advanced students) use singleton design pattern for this class
}
