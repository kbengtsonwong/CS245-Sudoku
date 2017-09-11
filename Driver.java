package sudoku;

public class Driver {

	public static void main(String[] args) {
		
		Sudoku sudoku = new Sudoku();
		
		sudoku.fillBoard();
		
		System.out.println();
		System.out.println("Final Game Board");
		sudoku.printBoard();
	}

}
