import java.util.LinkedList;
import java.util.Queue;

class Node {
    Node parent;
    int[][] mat;
    int x, y;
    int level;

    Node(int[][] mat, int x, int y, int newX, int newY, int level, Node parent) {
        this.parent = parent;
        this.mat = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            System.arraycopy(mat[i], 0, this.mat[i], 0, mat[i].length);
        }
        // Swap the values
        int temp = this.mat[x][y];
        this.mat[x][y] = this.mat[newX][newY];
        this.mat[newX][newY] = temp;
        this.level = level;
        this.x = newX;
        this.y = newY;
    }
}

public class PuzzleSolver {
    static final int N = 3;
    static int[] row = {1, 0, -1, 0};
    static int[] col = {0, -1, 0, 1};

    static void printMatrix(int[][] mat) {
        for (int[] row : mat) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }

    static boolean isSafe(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N);
    }

    static boolean isEqual(int[][] mat1, int[][] mat2) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (mat1[i][j] != mat2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    static void printPath(Node root) {
        if (root == null) {
            return;
        }
        printPath(root.parent);
        printMatrix(root.mat);
        System.out.println();
    }

    static void solve(int[][] initial, int x, int y, int[][] finalMat) {
        Queue<Node> q = new LinkedList<>();
        Node root = new Node(initial, x, y, x, y, 0, null);
        q.add(root);

        while (!q.isEmpty()) {
            Node curr = q.poll();

            if (isEqual(curr.mat, finalMat)) {
                printPath(curr);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int newX = curr.x + row[i];
                int newY = curr.y + col[i];
                if (isSafe(newX, newY)) {
                    Node child = new Node(curr.mat, curr.x, curr.y, newX, newY, curr.level + 1, curr);
                    q.add(child);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] initial = {
                {1, 2, 3},
                {5, 6, 0},
                {7, 8, 4}
        };
        int[][] finalMat = {
                {1, 2, 3},
                {7, 5, 6},
                {8, 4, 0}
        };
        int x = 1, y = 2;
        solve(initial, x, y, finalMat);
    }
}
