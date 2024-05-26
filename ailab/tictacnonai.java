import java.util.Scanner;

public class tictacnonai {

    public static void main(String[] args) {
        char[][] mat = {
                { '_', '_', '_' },
                { '_', '_', '_' },
                { '_', '_', '_' }
        };
        char player = 'X';
        boolean flag = false;
        Scanner scanner = new Scanner(System.in);

        while (!flag) {
            int row, col;
            System.out.print("Player " + player + "'s turn. Enter row and column (0-2): ");
            row = scanner.nextInt();
            col = scanner.nextInt();

            if (row >= 0 && row < 3 && col >= 0 && col < 3 && mat[row][col] == '_') {
                mat[row][col] = player;
                if (checkWin(mat)) {
                    printBoard(mat);
                    System.out.println("Player " + player + " wins!");
                    break;
                }
                player = (player == 'X') ? 'O' : 'X';
            } else {
                System.out.println("Invalid move. Try again!");
                continue;
            }

            printBoard(mat);

            if (isBoardFull(mat)) {
                System.out.println("It's a draw!");
                break;
            }
        }
        scanner.close();
    }

    private static void printBoard(char[][] mat) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isBoardFull(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '_') {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(char[][] mat) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (mat[i][0] != '_' && mat[i][0] == mat[i][1] && mat[i][1] == mat[i][2]) {
                return true;
            }
            if (mat[0][i] != '_' && mat[0][i] == mat[1][i] && mat[1][i] == mat[2][i]) {
                return true;
            }
        }

        // Check diagonals
        if (mat[0][0] != '_' && mat[0][0] == mat[1][1] && mat[1][1] == mat[2][2]) {
            return true;
        }
        if (mat[0][2] != '_' && mat[0][2] == mat[1][1] && mat[1][1] == mat[2][0]) {
            return true;
        }

        return false;
    }
}
