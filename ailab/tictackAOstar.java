import java.util.*;

class Node {
    List<List<Integer>> state;
    int g;
    int h;
    Node parent;

    Node(List<List<Integer>> state, int g, int h, Node parent) {
        this.state = state;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    int f() {
        return g + h;
    }
}

public class AOStarSearch {

    public static void main(String[] args) {
        List<List<Integer>> start = Arrays.asList(
                Arrays.asList(2, 8, 3),
                Arrays.asList(1, 6, 4),
                Arrays.asList(7, 0, 5)
        );
        List<List<Integer>> goal = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(8, 0, 4),
                Arrays.asList(7, 6, 5)
        );

        System.out.println("Initial State: ");
        printState(start);
        System.out.println("Final State: ");
        printState(goal);

        System.out.println("Solving Puzzle using AO* Search: ");
        aoStarSearch(start, goal);
    }

    static int countMisplaced(List<List<Integer>> curr, List<List<Integer>> goal) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (curr.get(i).get(j) != 0 && !curr.get(i).get(j).equals(goal.get(i).get(j))) {
                    count++;
                }
            }
        }
        return count;
    }

    static int[] findBlank(List<List<Integer>> state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state.get(i).get(j) == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    static void printState(List<List<Integer>> state) {
        for (List<Integer> row : state) {
            for (int el : row) {
                System.out.print(el + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static List<Node> generateChildren(Node node, List<List<Integer>> goal) {
        List<Node> children = new ArrayList<>();
        List<List<Integer>> curr = deepCopy(node.state);
        int[] blank = findBlank(curr);
        int row = blank[0];
        int col = blank[1];

        int[] delrow = {0, 1, 0, -1};
        int[] delcol = {1, 0, -1, 0};

        for (int i = 0; i < 4; i++) {
            int nrow = row + delrow[i];
            int ncol = col + delcol[i];
            if (nrow >= 0 && nrow < 3 && ncol >= 0 && ncol < 3) {
                swap(curr, row, col, nrow, ncol);
                int h = countMisplaced(curr, goal);
                Node child = new Node(deepCopy(curr), node.g + 1, h, node);
                children.add(child);
                swap(curr, row, col, nrow, ncol); // swap back
            }
        }
        return children;
    }

    static void swap(List<List<Integer>> state, int row1, int col1, int row2, int col2) {
        int temp = state.get(row1).get(col1);
        state.get(row1).set(col1, state.get(row2).get(col2));
        state.get(row2).set(col2, temp);
    }

    static List<List<Integer>> deepCopy(List<List<Integer>> original) {
        List<List<Integer>> copy = new ArrayList<>();
        for (List<Integer> row : original) {
            copy.add(new ArrayList<>(row));
        }
        return copy;
    }

    static void reconstructPath(Node node) {
        if (node == null) return;
        reconstructPath(node.parent);
        printState(node.state);
    }

    static void aoStarSearch(List<List<Integer>> start, List<List<Integer>> goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::f));
        Set<List<List<Integer>>> visited = new HashSet<>();

        int h = countMisplaced(start, goal);
        Node root = new Node(start, 0, h, null);
        pq.add(root);
        visited.add(deepCopy(start));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (curr.h == 0) {
                System.out.println("Solution found!");
                reconstructPath(curr);
                return;
            }

            List<Node> children = generateChildren(curr, goal);
            for (Node child : children) {
                if (!containsState(visited, child.state)) {
                    pq.add(child);
                    visited.add(deepCopy(child.state));
                }
            }
        }
        System.out.println("No Solution Found!");
    }

    static boolean containsState(Set<List<List<Integer>>> visited, List<List<Integer>> state) {
        for (List<List<Integer>> s : visited) {
            if (statesAreEqual(s, state)) {
                return true;
            }
        }
        return false;
    }

    static boolean statesAreEqual(List<List<Integer>> state1, List<List<Integer>> state2) {
        for (int i = 0; i < state1.size(); i++) {
            for (int j = 0; j < state1.get(i).size(); j++) {
                if (!state1.get(i).get(j).equals(state2.get(i).get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
