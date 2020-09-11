package solver;

public class GraphicSolver {

    public static char[][] defaultSudoku = {
            {'.', '.', '8', '.', '.', '2', '.', '1', '.'},
            {'.', '.', '3', '8', '1', '.', '2', '.', '.'},
            {'2', '.', '.', '.', '.', '.', '.', '6', '5'},
            {'.', '2', '.', '.', '.', '1', '.', '.', '.'},
            {'.', '.', '.', '6', '.', '9', '.', '.', '.'},
            {'.', '.', '.', '7', '.', '.', '.', '8', '.'},
            {'5', '8', '.', '.', '.', '.', '.', '.', '9'},
            {'.', '.', '6', '.', '7', '3', '1', '.', '.'},
            {'.', '3', '.', '1', '.', '.', '5', '.', '.'}
    };

    public static void main(String[] args) {
        Sudoku s = new Sudoku(defaultSudoku);
        s.display();
        System.out.println("Valid Sudoku: " + s.isValidSudoku());

        s.solveSudoku();

        System.out.println();
        s.display();
        System.out.println("Valid Sudoku: " + s.isValidSudoku());

    }
}
