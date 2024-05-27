import java.util.Arrays;
import java.util.Random;

public class HillClimbing {

    public static final int N = 8;

    public static void main(String[] args) {
        Random rand = new Random();
        boolean success = false;
        int cnt = 1;
        int[] board = new int[N];

        while (true) {
            for (int i = 0; i < N; ++i) {
                board[i] = rand.nextInt(N); // Generate random numbers between 0 and N-1
            }

            System.out.println("======================== Iteration : " + cnt);
            System.out.println("Initial Board");
            printBoard(board);
            success = hillClimbing(board);
            ++cnt;

            if (success) {
                break;
            }
        }
    }

    public static int calcEvaluationFunction(int[] board) {
        int cnt = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = i + 1; j < N; ++j) {
                if (board[i] == board[j]) {
                    ++cnt;
                } else if (Math.abs(i - j) == Math.abs(board[i] - board[j])) {
                    ++cnt;
                }
            }
        }
        return 28 - cnt;
    }

    public static void printBoard(int[] board) {
        int[][] grid = new int[N][N];
        for (int i = 0; i < N; ++i) {
            Arrays.fill(grid[i], 0);
        }
        for (int i = 0; i < N; ++i) {
            grid[board[i]][i] = 1;
        }
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void copyBoard(int[] board1, int[] board2) {
        System.arraycopy(board2, 0, board1, 0, N);
    }

    public static boolean hillClimbing(int[] board) {
        int[] bestBoard = new int[N];
        int bestValue, boardValue;
        copyBoard(bestBoard, board);

        int cnt = 0;
        while (true) {
            int[] currBoard = new int[N];
            int currValue;
            copyBoard(currBoard, board);
            boardValue = bestValue = calcEvaluationFunction(board);

            if (bestValue == 28) {
                System.out.println("Reached Global Maxima after " + cnt + " moves");
                printBoard(bestBoard);
                return true;
            }

            ++cnt;
            for (int i = 0; i < N; ++i) {
                int temp = currBoard[i];
                for (int j = 0; j < N; ++j) {
                    if (j == board[i]) {
                        continue;
                    }
                    currBoard[i] = j;
                    currValue = calcEvaluationFunction(currBoard);
                    if (currValue > bestValue) {
                        bestValue = currValue;
                        copyBoard(bestBoard, currBoard);
                    }
                }
                currBoard[i] = temp;
            }
            if (bestValue <= boardValue) {
                System.out.println("Stuck in Local Maxima after " + cnt + " moves");
                printBoard(bestBoard);
                return false;
            }
            copyBoard(board, bestBoard);
        }
    }
}
