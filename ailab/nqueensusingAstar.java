import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class nqueensusingAstar {

    static int cnt = 0;
    static int n;
    static List<List<Integer>> board;

    static void show() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board.get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }

    static boolean isValid(int row, int col) {
        // Check for horizontal positions
        for (int i = col; i >= 0; i--) {
            if (board.get(row).get(i) == 1) {
                return false;
            }
        }

        int i = row, j = col;

        // Check upper left diagonal
        while (i >= 0 && j >= 0) {
            if (board.get(i).get(j) == 1) {
                return false;
            }
            i--;
            j--;
        }

        i = row;
        j = col;

        // Check lower left diagonal
        while (i < n && j >= 0) {
            if (board.get(i).get(j) == 1) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    static void nQueens(int cur_col) {
        if (cur_col >= n) {
            return;
        }

        for (int i = 0; i < n; i++) {
            if (isValid(i, cur_col)) {
                board.get(i).set(cur_col, 1);

                if (cur_col == n - 1) {
                    show();
                    cnt++;
                }

                nQueens(cur_col + 1);
                board.get(i).set(cur_col, 0);
            }
        }
    }

    static void solve() {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        board = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                row.add(0);
            }
            board.add(row);
        }
        nQueens(0);
        System.out.println(cnt);
    }

    public static void main(String[] args) {
        solve();
    }
}
