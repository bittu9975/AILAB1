import java.util.Scanner;

public class ticminmax {
    static int[] board = { 2, 2, 2, 2, 2, 2, 2, 2, 2 };
    static int[][] win = {
            { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 },
            { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
            { 0, 4, 8 }, { 2, 4, 6 }
    };

    static void printBoard() {
        System.out.println("Current Board Position: ");
        String ele;
        System.out.println("-------------");
        for (int i = 0; i < 9; i++) {
            if (board[i] == 2)
                ele = "   ";
            else if (board[i] == 3)
                ele = " X ";
            else
                ele = " O ";
            System.out.print("|" + ele);
            if ((i + 1) % 3 == 0)
                System.out.println("|" + '\n' + "-------------");
        }
    }

    static boolean isWin(int x) {
        for (int[] winLine : win) {
            int count = 0;
            for (int pos : winLine) {
                if (board[pos] == x)
                    count++;
            }
            if (count == 3)
                return true;
        }
        return false;
    }

    static void playerMove(Scanner scanner) {
        System.out.println("Enter your move (0-8): ");
        int pos = scanner.nextInt();
        if (pos < 0 || pos > 8 || board[pos] != 2) {
            System.out.println("Invalid position. Try again.");
            playerMove(scanner);
        } else {
            board[pos] = 3;
        }
    }

    static boolean draw() {
        for (int i = 0; i < 9; i++) {
            if (board[i] == 2)
                return false;
        }
        return true;
    }

    static int minimax(boolean isMaximizing) {
        if (isWin(5))
            return 1000;
        if (isWin(3))
            return -1000;
        if (draw())
            return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == 2) {
                    board[i] = 5;
                    int score = minimax(false);
                    bestScore = Math.max(score, bestScore);
                    board[i] = 2;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == 2) {
                    board[i] = 3;
                    int score = minimax(true);
                    bestScore = Math.min(score, bestScore);
                    board[i] = 2;
                }
            }
            return bestScore;
        }
    }

    static void compMove() {
        int bestPos = 0;
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 2) {
                board[i] = 5;
                int score = minimax(false);
                board[i] = 2;
                if (score > bestScore) {
                    bestScore = score;
                    bestPos = i;
                }
            }
        }
        board[bestPos] = 5;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            playerMove(scanner);
            printBoard();
            if (isWin(3)) {
                System.out.println("You win!");
                break;
            }
            if (draw()) {
                System.out.println("It's a draw!");
                break;
            }

            compMove();
            printBoard();
            if (isWin(5)) {
                System.out.println("Computer wins!");
                break;
            }
            if (draw()) {
                System.out.println("It's a draw!");
                break;
            }
        }

        scanner.close();
    }
}
