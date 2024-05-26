import java.util.ArrayList;
import java.util.List;

public class NQueensCSP {

    private static final int EMPTY = -1;

    public static void main(String[] args) {
        int n = 8; // Change the board size here if desired

        List<Integer> board = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            board.add(EMPTY); // Initialize board with no queens placed
        }

        List<List<Integer>> solutions = new ArrayList<>();
        solve(board, n, 0, solutions);

        // Print solutions
        for (List<Integer> solution : solutions) {
            for (int row : solution) {
                for (int col = 0; col < n; col++) {
                    if (col == row) {
                        System.out.print("Q ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static void solve(List<Integer> board, int n, int col, List<List<Integer>> solutions) {
        if (col == n) {
            solutions.add(new ArrayList<>(board)); // Found a solution, add it to the list of solutions
            return;
        }

        for (int row = 0; row < n; row++) {
            if (isSafe(board, col, row)) {
                board.set(col, row); // Place queen at (row, col)
                solve(board, n, col + 1, solutions); // Recur to next column
                board.set(col, EMPTY); // Backtrack by removing the queen from (row, col)
            }
        }
    }

    private static boolean isSafe(List<Integer> board, int col, int row) {
        for (int prevCol = 0; prevCol < col; prevCol++) {
            int prevRow = board.get(prevCol);
            // Check if there is a queen in the same row or diagonal
            if (row == prevRow || Math.abs(row - prevRow) == Math.abs(col - prevCol)) {
                return false;
            }
        }
        return true;
    }
}
