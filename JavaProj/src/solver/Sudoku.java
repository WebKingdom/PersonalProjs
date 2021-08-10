package solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Sudoku {

    /**
     * Board of the Sudoku
     */
    private char board[][];

    /**
     * A random generator
     */
    private Random r;


    /**
     * Constructs an empty 9x9 Sudoku
     */
    public Sudoku() {
        r = new Random(13);
        board = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = '.';
            }
        }
    }

    /**
     * Constructs a Sudoku with the given board layout/numbers
     *
     * @param b board for the Sudoku
     */
    public Sudoku(char[][] b) {
        r = new Random(13);
        board = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = b[i][j];
            }
        }
    }

    /**
     * Creates a random solvable sudoku board
     *
     * @param percentRand percentage of board that should be empty (higher percent -> harder)
     */
    public void createRandomSudoku(int percentRand) {
        ArrayList<Character> numList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numList.add(Character.forDigit(i, 10));
        }
        Collections.shuffle(numList);
        createSudoku(numList);

        if (!isValidSudoku()) {
            System.out.println("ERROR: Generated sudoku is not solvable.");
        }

        // take out a random percent of numbers
        int numToHide = (int) ((percentRand / 100.0) * 81.0);
        int numHidden = 0;
        while (numHidden < numToHide) {
            int curRow = r.nextInt(9);
            int curCol = r.nextInt(9);
            board[curRow][curCol] = '.';
            numHidden++;
        }
    }

    /**
     * Helper for creating a random sudoku board.
     *
     * @param al list of numbers (as chars) for randomly generating sudoku
     * @return true if successfully created sudoku, false otherwise
     */
    public boolean createSudoku(ArrayList<Character> al) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    // loop through random number list to find solvable sudoku
                    for (int k = 0; k < 9; k++) {
                        char numToAdd = al.get(k);
                        if (validNum(i, j, numToAdd)) {
                            board[i][j] = numToAdd;
                            Collections.shuffle(al);

                            // backtrack recursively
                            if (createSudoku(al)) {
                                return true;
                            } else {
                                board[i][j] = '.';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a random character either 1-9 or '.'
     *
     * @return a valid random character
     */
    private char randChar() {
        if (r.nextInt(3) == 0) {
            return '.';
        }
        char ret = (char) (r.nextInt(9) + 49);
        return ret;
    }

    /**
     * Returns a random number as a character from 1-9
     *
     * @return a valid random number as char
     */
    private char randNum() {
        return (char) (r.nextInt(9) + 49);
    }

    /**
     * Displays the Sudoku
     */
    public void display() {
        for (int i = 0; i < board.length; i++) {
            if (i % 3 == 0) {
                System.out.println("-------------------------");
            }
            for (int j = 0; j < board[i].length; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(board[i][j]);
                if (j != 8) {
                    System.out.print(" ");
                }
                if (j == 8) {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }

    /**
     * Solves the Sudoku and identifies if it could not be solved
     */
    public void solveSudoku() {
        if (!solve()) {
            System.out.println("Could not solve.");
        }
    }

    /**
     * Logic for solving the Sudoku
     *
     * @return true if solved, false otherwise
     */
    private boolean solve() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    //char numbers 1-9
                    for (char num = 49; num <= 57; num++) {
                        if (validNum(i, j, num)) {
                            board[i][j] = num;
                            //start backtracking recursively
                            if (solve()) {
                                return true;
                            } else {
                                board[i][j] = '.';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines if a number is valid in the Sudoku at the specified location
     *
     * @param r
     * @param c
     * @param item
     * @return true if number is valid at this location in Sudoku, flase otherwise
     */
    private boolean validNum(int r, int c, char item) {
        //i represents rows
        for (int i = 0; i < board.length; i++) {
            if (board[i][c] == item) {
                return false;
            }
        }
        //i represents columns
        for (int i = 0; i < board[r].length; i++) {
            if (board[r][i] == item) {
                return false;
            }
        }
        //determine if item is valid in box
        //find relative position
        int boxR = r - (r % 3);
        int boxC = c - (c % 3);
        //now loop through from top left corner of box
        for (int i = boxR; i < boxR + 3; i++) {
            for (int j = boxC; j < boxC + 3; j++) {
                if (board[i][j] == item) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines if the Sudoku is valid (solvable or validly solved)
     *
     * @return true if valid/correct, false otherwise
     */
    public boolean isValidSudoku() {
        ArrayList<Character> al = new ArrayList<>();
        ArrayList<Character> alCol = new ArrayList<>();

        //check all rows and columns
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char cur = board[i][j];     //check rows
                if (cur != '.') {
                    if (al.contains(cur)) {
                        return false;
                    }
                    int num = Character.getNumericValue(cur);
                    if (num < 1 || num > 9) {
                        return false;
                    }
                    al.add(cur);
                }
                char curCol = board[j][i];  //check columns
                if (curCol != '.') {
                    if (alCol.contains(curCol)) {
                        return false;
                    }
                    int num = Character.getNumericValue(curCol);
                    if (num < 1 || num > 9) {
                        return false;
                    }
                    alCol.add(curCol);
                }
                //check 3x3 boxes
                if (i % 3 == 0 && j % 3 == 0) {
                    if (!check3x3(i, j, board)) {
                        return false;
                    }
                }
            }
            al.clear();
            alCol.clear();
        }
        return true;
    }

    /**
     * Checks if Sudoku is valid in the 3x3 box
     *
     * @param r
     * @param c
     * @param arr
     * @return true if valid, false otherwise
     */
    private boolean check3x3(int r, int c, char[][] arr) {
        ArrayList<Character> al = new ArrayList<>();
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                char cur = arr[i][j];
                if (cur != '.') {
                    if (al.contains(cur)) {
                        return false;
                    }
                    int num = Character.getNumericValue(cur);
                    if (num < 1 || num > 9) {
                        return false;
                    }
                    al.add(cur);
                }
            }
        }
        return true;
    }
}
