import java.util.*;

class Node {
    char letter;
    int value;
}

public class CSP {

    static boolean[] used = new boolean[10]; // tracks used digits

    public static void main(String[] args) {
        String s1 = "TO";
        String s2 = "GO";
        String s3 = "OUT";

        if (!solvePuzzle(s1, s2, s3)) {
            System.out.println("No solution");
        }
    }

    public static boolean solvePuzzle(String s1, String s2, String s3) {
        int uniqueChar = 0;
        int[] freq = new int[26]; // to count frequency of each character

        countFrequency(s1, freq);
        countFrequency(s2, freq);
        countFrequency(s3, freq);

        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                uniqueChar++;
            }
        }

        if (uniqueChar > 10) {
            System.out.println("Invalid strings");
            return false;
        }

        Node[] nodeList = new Node[uniqueChar];
        for (int i = 0, j = 0; i < 26; i++) {
            if (freq[i] > 0) {
                nodeList[j] = new Node();
                nodeList[j].letter = (char) (i + 'A');
                j++;
            }
        }

        return permutation(uniqueChar, nodeList, 0, s1, s2, s3);
    }

    public static void countFrequency(String s, int[] freq) {
        for (char ch : s.toCharArray()) {
            freq[ch - 'A']++;
        }
    }

    public static boolean permutation(int count, Node[] nodeList, int n, String s1, String s2, String s3) {
        if (n == count - 1) {
            for (int i = 0; i < 10; i++) {
                if (!used[i]) {
                    nodeList[n].value = i;
                    if (isValid(nodeList, count, s1, s2, s3)) {
                        System.out.println("Solution found: ");
                        for (Node node : nodeList) {
                            System.out.println(node.letter + " = " + node.value);
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        for (int i = 0; i < 10; i++) {
            if (!used[i]) {
                nodeList[n].value = i;
                used[i] = true;
                if (permutation(count, nodeList, n + 1, s1, s2, s3)) {
                    return true;
                }
                used[i] = false;
            }
        }
        return false;
    }

    public static boolean isValid(Node[] nodeList, int count, String s1, String s2, String s3) {
        int val1 = getValue(s1, nodeList, count);
        int val2 = getValue(s2, nodeList, count);
        int val3 = getValue(s3, nodeList, count);

        return val3 == (val1 + val2);
    }

    public static int getValue(String s, Node[] nodeList, int count) {
        int value = 0;
        int m = 1;

        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            for (int j = 0; j < count; j++) {
                if (nodeList[j].letter == ch) {
                    value += m * nodeList[j].value;
                    break;
                }
            }
            m *= 10;
        }

        return value;
    }
}
