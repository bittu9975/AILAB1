import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Arrays;

class Node {
    Node parent;
    int[][] mat;
    int x, y;
    int depth;
    int cost;
    String action;

    Node(int[][] mat, int x, int y, int newX, int newY, int depth, Node parent, String action) {
        this.parent = parent;
        this.mat = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            System.arraycopy(mat[i], 0, this.mat[i], 0, mat[i].length);
        }
        if (x != newX || y != newY) {
            int temp = this.mat[x][y];
            this.mat[x][y] = this.mat[newX][newY];
            this.mat[newX][newY] = temp;
        }
        this.x = newX;
        this.y = newY;
        this.depth = depth;
        this.action = action;
        this.cost = 0;
    }
}

public class PuzzleSolverAStar {
    static final int N = 3;

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

    static int heuristics(int[][] mat, int[][] goal) {
        int h = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (mat[i][j] != 0 && mat[i][j] != goal[i][j]) {
                    h++;
                }
            }
        }
        return h;
    }

    static void printMatrix(int[][] mat) {
        for (int[] row : mat) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printPath(Node root) {
        if (root == null) {
            return;
        }
        printPath(root.parent);
        if (!root.action.isEmpty()) {
            System.out.println("Move: " + root.action);
        }
        printMatrix(root.mat);
    }

    static Node newNode(int[][] mat, int x, int y, int newX, int newY, int depth, Node parent, String action) {
        return new Node(mat, x, y, newX, newY, depth, parent, action);
    }

    static class CompareCost implements Comparator<Node> {
        @Override
        public int compare(Node a, Node b) {
            return Integer.compare(a.cost, b.cost);
        }
    }

    static void solve(int[][] initial, int x, int y, int[][] goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new CompareCost());
        Node root = newNode(initial, x, y, x, y, 0, null, "");
        root.cost = heuristics(initial, goal);
        pq.add(root);

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (isEqual(curr.mat, goal)) {
                System.out.println("Goal state reached");
                printPath(curr);
                return;
            }

            int[] row = { -1, 0, 1, 0 };
            int[] col = { 0, -1, 0, 1 };
            String[] directions = { "up", "left", "down", "right" };

            for (int i = 0; i < 4; i++) {
                int newX = curr.x + row[i];
                int newY = curr.y + col[i];

                if (newX >= 0 && newX < N && newY >= 0 && newY < N) {
                    Node child = newNode(curr.mat, curr.x, curr.y, newX, newY, curr.depth + 1, curr, directions[i]);
                    child.cost = child.depth + heuristics(child.mat, goal);
                    pq.add(child);
                }
            }
        }
        System.out.println("Goal state not reached");
    }

    public static void main(String[] args) {
        int[][] initial = {
                { 1, 2, 3 },
                { 0, 4, 6 },
                { 7, 5, 8 }
        };

        int[][] goal = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };

        int x = 1, y = 0; // Initial blank tile coordinates
        solve(initial, x, y, goal);
    }
}
