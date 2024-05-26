import java.util.*;

class WaterJugSolver {
    static class Node {
        int jug1, jug2, depth;
        Node parent;
        String action;

        Node(int jug1, int jug2, int depth, Node parent, String action) {
            this.jug1 = jug1;
            this.jug2 = jug2;
            this.depth = depth;
            this.parent = parent;
            this.action = action;
        }
    }

    private static Node newNode(int jug1, int jug2, int depth, Node parent, String action) {
        return new Node(jug1, jug2, depth, parent, action);
    }

    private static void printPath(Node node) {
        if (node == null)
            return;
        printPath(node.parent);
        if (!node.action.isEmpty()) {
            System.out.println(node.action);
        }
        System.out.println("Jug1: " + node.jug1 + ", Jug2: " + node.jug2);
    }

    private static boolean isGoalState(Node node, int target) {
        return node.jug1 == target || node.jug2 == target;
    }

    public static void solve(int jug1Capacity, int jug2Capacity, int target) {
        Stack<Node> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        Node root = newNode(0, 0, 0, null, "");
        stack.push(root);
        visited.add("0,0");

        while (!stack.isEmpty()) {
            Node current = stack.pop();

            if (isGoalState(current, target)) {
                System.out.println("Goal state reached");
                printPath(current);
                System.out.println("Depth: " + current.depth);
                return;
            }

            List<Node> nextStates = Arrays.asList(
                    newNode(jug1Capacity, current.jug2, current.depth + 1, current, "Fill Jug1"),
                    newNode(current.jug1, jug2Capacity, current.depth + 1, current, "Fill Jug2"),
                    newNode(0, current.jug2, current.depth + 1, current, "Empty Jug1"),
                    newNode(current.jug1, 0, current.depth + 1, current, "Empty Jug2"),
                    newNode(Math.max(0, current.jug1 - (jug2Capacity - current.jug2)),
                            Math.min(jug2Capacity, current.jug2 + current.jug1), current.depth + 1, current,
                            "Pour Jug1 -> Jug2"),
                    newNode(Math.min(jug1Capacity, current.jug1 + current.jug2),
                            Math.max(0, current.jug2 - (jug1Capacity - current.jug1)), current.depth + 1, current,
                            "Pour Jug2 -> Jug1"));

            for (Node next : nextStates) {
                String stateKey = next.jug1 + "," + next.jug2;
                if (!visited.contains(stateKey)) {
                    stack.push(next);
                    visited.add(stateKey);
                }
            }
        }

        System.out.println("Goal state not reached");
    }

    public static void main(String[] args) {
        int jug1Capacity = 3;
        int jug2Capacity = 4;
        int target = 2;

        solve(jug1Capacity, jug2Capacity, target);
    }
}
