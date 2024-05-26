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
        if (x != newX || y != newY) { // Avoid unnecessary swap
            int temp = this.mat[x][y];
            this.mat[x][y] = this.mat[newX][newY];
            this.mat[newX][newY] = temp;
        }
        this.level = level;
        this.x = newX;
        this.y = newY;
    }
}

public class PuzzleSolverDFS {
    static final int N = 3;
    static int[] row = { -1, 0, 1, 0 };
    static int[] col = { 0, -1, 0, 1 };

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

    static boolean solveDFS(Node node, int[][] finalMat, int depth) {
        if (isEqual(node.mat, finalMat)) {
            printPath(node);
            return true;
        }
        if (depth <= 0) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            int newX = node.x + row[i];
            int newY = node.y + col[i];

            if (isSafe(newX, newY)) {
                Node child = new Node(node.mat, node.x, node.y, newX, newY, node.level + 1, node);
                if (solveDFS(child, finalMat, depth - 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] initial = {
                { 1, 2, 3 },
                { 5, 6, 0 },
                { 7, 8, 4 }
        };
        int[][] finalMat = {
                { 1, 2, 3 },
                { 7, 5, 6 },
                { 8, 4, 0 }
        };
        int x = 1, y = 2;
        int depth = 10;
        Node root = new Node(initial, x, y, x, y, 0, null);

        if (!solveDFS(root, finalMat, depth)) {
            System.out.println("Solution not found within depth limit");
        }
    }
}
