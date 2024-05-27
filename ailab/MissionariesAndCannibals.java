import java.util.*;

public class MissionariesAndCannibals {

    static class Pair<K, V> {
        K first;
        V second;

        Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }

    public static boolean isGoal(List<Integer> a) {
        return a.get(2) == 3 && a.get(3) == 3;
    }

    public static boolean notValid(List<Integer> a) {
        return !(a.get(1) > a.get(0) && a.get(0) != 0) || ((a.get(3) > a.get(2)) && a.get(2) != 0);
    }

    public static void main(String[] args) {
        List<Integer> a = Arrays.asList(3, 3, 0, 0, 0);
        int ch = 0;
        Queue<Pair<List<Integer>, List<Pair<Character, Character>>>> q = new LinkedList<>();
        List<Pair<Character, Character>> ans = new ArrayList<>();
        q.offer(new Pair<>(a, ans));

        while (!q.isEmpty()) {
            List<Integer> curr = q.peek().first;
            List<Pair<Character, Character>> ans2 = q.peek().second;
            q.poll();

            if (!notValid(curr)) {
                continue;
            }

            if (isGoal(curr)) {
                for (Pair<Character, Character> c : ans2) {
                    System.out.println(c.first + " " + c.second);
                }
                break;
            }

            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (i + j <= 2 && i + j > 0) {
                        List<Integer> b = new ArrayList<>(curr);
                        if (curr.get(4) == 0 && b.get(0) >= i && b.get(1) >= j) {
                            b.set(0, b.get(0) - i);
                            b.set(1, b.get(1) - j);
                            b.set(2, b.get(2) + i);
                            b.set(3, b.get(3) + j);
                            Pair<Character, Character> boat = new Pair<>('#', '#');
                            if (i == 2) {
                                boat = new Pair<>('M', 'M');
                            } else if (j == 2) {
                                boat = new Pair<>('C', 'C');
                            } else if (i == 1) {
                                boat = new Pair<>('M', '#');
                            } else if (j == 1) {
                                boat = new Pair<>('#', 'C');
                            }
                            b.set(4, 1 - b.get(4));
                            ans2.add(boat);
                            q.offer(new Pair<>(b, new ArrayList<>(ans2)));
                            ans2.remove(ans2.size() - 1);
                        } else if (curr.get(4) == 1 && b.get(2) >= i && b.get(3) >= j) {
                            b.set(0, b.get(0) + i);
                            b.set(1, b.get(1) + j);
                            b.set(2, b.get(2) - i);
                            b.set(3, b.get(3) - j);
                            Pair<Character, Character> boat = new Pair<>('#', '#');
                            if (i == 2) {
                                boat = new Pair<>('M', 'M');
                            } else if (j == 2) {
                                boat = new Pair<>('C', 'C');
                            } else if (i == 1) {
                                boat = new Pair<>('M', '#');
                            } else if (j == 1) {
                                boat = new Pair<>('#', 'C');
                            }
                            b.set(4, 1 - b.get(4));
                            ans2.add(boat);
                            q.offer(new Pair<>(b, new ArrayList<>(ans2)));
                            ans2.remove(ans2.size() - 1);
                        }
                    }
                }
            }
        }
    }
}
