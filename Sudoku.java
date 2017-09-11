package sudoku;

import java.util.Random;
import java.util.ArrayList;


public class Sudoku {

	// 2D Array "gameboard" to store integer values.
	private int[][] gameboard;
	
	// Counter variable to clear the entire board if a given solution hits a dead-end.
	private int resetCounter = 0;
	
	// 9 sub-grids representing 3 x 3 sub-grids to check the safeToPlace condition
	ArrayList<Integer> subgrid1 = new ArrayList<Integer>();
	ArrayList<Integer> subgrid2 = new ArrayList<Integer>();
	ArrayList<Integer> subgrid3 = new ArrayList<Integer>();
	ArrayList<Integer> subgrid4 = new ArrayList<Integer>();
	ArrayList<Integer> subgrid5 = new ArrayList<Integer>();
	ArrayList<Integer> subgrid6 = new ArrayList<Integer>();
	ArrayList<Integer> subgrid7 = new ArrayList<Integer>();
	ArrayList<Integer> subgrid8 = new ArrayList<Integer>();
	ArrayList<Integer> subgrid9 = new ArrayList<Integer>();

	
	// Constructor
	public Sudoku() {
		gameboard = new int[9][9];
	}
	
	
	// Operative function to generate a randomized, valid Sudoku puzzle.
	public void fillBoard() {
		
		
		// First Column of Sub-grids
		// Iterate left to right, then down
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				// Move position down after each 3 numbers are generated
				if(j == 3) {
					i++;
					j = 0;
				}
				// Call the placeGuess() function which modifies the gameboard and returns the appropriate indices
				System.out.println("(" + i + ", " + j + ")");
				int[] indices = placeGuess(i, j, 0, resetCounter);
				i = indices[0];
				j = indices[1];
				
				// Exit this double-for loop to move onto the next column of sub-grids
				if(i == 8 && j == 2) 
					break;
			}
		}	
		
		// Generating the second column of sub-grids
		for(int i = 0; i < 9; i++) {
			for(int j = 3; j < 9; j++) {
				if(j == 6) {
					i++;
					j = 3;
				}
				// Call the placeGuess() function
				System.out.println("(" + i + ", " + j + ")");
				int[] indices = placeGuess(i, j, 0, resetCounter);
				i = indices[0];
				j = indices[1];
				
				// If a recursive solution cannot be reached indicated by these i, j values, start over.
				if(i == 0 && j == -1) {
					fillBoard();
					i = 8;
					j = 8;
					break;
				}
				
				// If a recursive solution is reached for the second block, move on to the third.
				if(i == 8 && j == 5) 
					break;
			}
		}
		
		// Third column of sub-grids
		for(int i = 0; i < 9; i++) {
			for(int j = 6; j <= 9; j++) {
				if(j == 9) {
					i++;
					j = 6;
				}
				// Call the placeGuess function
				System.out.println("(" + i + ", " + j + ")");
				int[] indices = placeGuess(i, j, 0, resetCounter);
				i = indices[0];
				j = indices[1];
				
				// Again, provide mechanism to reset the grid and try again if a recursive solution cannot be reached.
				if(i == 0 && j == -1) {
					fillBoard();
					i = 8;
					j = 8;
					break;
				}
				
				// Exit the loop when the board is successfully generated.
				if(i == 8 && j == 8)
					break;
			}
		}
	}
	
	// Private method called repeatedly within fillBoard() and recursively by itself to generate valid solutions.
	private int[] placeGuess(int i, int j, int counter, int counter2) {
		
		// Generate a random number 1-9.
		Random random = new Random();
		int attempt = random.nextInt(9) + 1;
		
		// Track the indices of interest.
		int[] indices = {i, j};
		
		// Retrieve the current sub-grid based on index.
		ArrayList<Integer> currentSubgrid = findCurrentSubgrid(i, j);
		
		// BASE CASE 1 (Optimal)
		// If the safeToPlace function passes, put the valid attempt/guess in the spot.
		if(safeToPlace(attempt, i, j, currentSubgrid)) {
			gameboard[i][j] = attempt;
			System.out.println(attempt + " placed");
			
			// Add the placed integer to its sub-grid arraylist tracker to ensure following integers in the sub-grid aren't repeated.
			currentSubgrid.add(attempt);
			printBoard();
			
			// Return the current indices to fillBoard() so it can continue iterating in order.
			return indices;
		}
		
		// If not safeToPlace() fails...
		else {
			// RECURSIVE CASE
			// If there are fewer than 100 guesses of integers, guess again.
			if(counter < 100) {
				counter++;
				return placeGuess(i, j, counter, resetCounter);
			}
			
			// BASE CASE 2 (Least Optimal)
			// If the sub-grid has been cleared 25 times, there must be no solution with the current arrangement.
			// Reset the gameboard and subgrids and try a new solution.
			else if(counter2 > 25) {
				System.out.println("TOTAL RESET!!!");
				gameboard = new int[9][9];
				subgrid1.clear();
				subgrid2.clear();
				subgrid3.clear();
				subgrid4.clear();
				subgrid5.clear();
				subgrid6.clear();
				subgrid7.clear();
				subgrid8.clear();
				subgrid9.clear();
				indices[0] = 0;
				indices[1] = -1;
				resetCounter = 0;
				
			}
			
			// BASE CASE 3 (Backtracks to the starting state of current sub-grid)
			// Clear the current sub-grid, and return the appropriate starting indices of the sub-grid to the fillBoard() function.
			else {
				// Increment the big reset counter by one each time the sub-grid is completely cleared.
				// When it gets to 25, a total reset is implemented. 
				resetCounter = resetCounter + 1;

				// Clear the values in the currentSubgrid reference.
				currentSubgrid.clear();
				
				// Set the indices of i, j to the top-left position of the current sub-grid.
				if(currentSubgrid == subgrid1) {
					indices[0] = 0;
					indices[1] = 0;
				} else if(currentSubgrid == subgrid2) {
					indices[0] = 0;
					indices[1] = 3;
				} else if(currentSubgrid == subgrid3) {
					indices[0] = 0;
					indices[1] = 6;
				} else if(currentSubgrid == subgrid4) {
					indices[0] = 3;
					indices[1] = 0;
				} else if(currentSubgrid == subgrid5) {
					indices[0] = 3;
					indices[1] = 3;
				} else if(currentSubgrid == subgrid6) {
					indices[0] = 3;
					indices[1] = 6;
				} else if(currentSubgrid == subgrid7) {
					indices[0] = 6;
					indices[1] = 0;
				} else if(currentSubgrid == subgrid8) {
					indices[0] = 6;
					indices[1] = 3;
				} else {
					indices[0] = 6;
					indices[1] = 6;
				}
				
				// Clear the "gameboard" values back to 0 for the current sub-grid.
				for(int y = indices[0]; y < indices[0] + 3; y++ ) {
					for(int x = indices[1]; x < indices[1] + 3; x++ ) {
						gameboard[y][x] = 0;
					}
				} 
				
				// Since j will be incremented by 1 before placeGuess is called again,
				// temporarily decrease by 1 to start at the proper position.
				indices[1] = indices[1] - 1; 
					
			}
			// Returns {i, j} to fillBoard(), which will indicate the next position to be filled after being incremented. 
			return indices;
		}
	}
	

	
	// Returns the sub-grid arraylist corresponding to the {i, j} position. 
	private ArrayList<Integer> findCurrentSubgrid(int i, int j) {
		if(i < 3) {
			if(j < 3)
				return subgrid1;
			else if(j < 6)
				return subgrid2;
			else if(j < 9) 
				return subgrid3;
		}
		else if(i < 6) {
			if(j < 3)
				return subgrid4;
			else if(j < 6)
				return subgrid5;
			else if(j < 9) 
				return subgrid6;
		}
		else {
			if(j < 3)
				return subgrid7;
			else if(j < 6)
				return subgrid8;
		}
		return subgrid9;	
	}
	
	
	// Returns false if there is a column, row, or sub-grid conflict of the given attempt/guess; true otherwise. 
	private boolean safeToPlace(int attempt, int row, int col, ArrayList<Integer> subgrid) {
		
		// Checks if the attempt is in the current column or row.
		for(int i = 0; i < 9; i++) {
			if(gameboard[i][col] == attempt) 
				return false;
			else if(gameboard[row][i] == attempt)
				return false;		
		}
		
		// Checks for conflicts in the current sub-grid.
		if(subgrid.contains(attempt)) {
			return false;
		}
		
		return true;
	}
	
	
	// Prints the formatted state of the "gameboard" to the display.
	public void printBoard(){
		String separator = (" ----------------------- ");
		
		System.out.println(separator);
		
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++) {
				if((i == 3 || i == 6) && j == 0) 
					System.out.println(separator);
				if(j % 3 == 0)
					System.out.print("| ");
				System.out.print(gameboard[i][j] + " ");
			} 
			System.out.print("|");
			System.out.println();
		} 
		
		System.out.println(separator);
	}	
}
