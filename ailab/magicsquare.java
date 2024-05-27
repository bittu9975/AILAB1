import java.util.Arrays;

public class magicsquare {

    // A function to generate odd sized magic squares
    static void generateSquare(int n) {
        if (n % 2 == 0) {
            System.out.println("Magic square can only be generated for odd values of n.");
            return;
        }

        int[][] magicSquare = new int[n][n];

        // set all slots as 0
        for (int[] row : magicSquare) {
            Arrays.fill(row, 0);
        }

        // Initialize position for 1
        int i = n / 2;
        int j = n - 1;

        // One by one put all values in magic square
        for (int num = 1; num <= n * n;) {
            if (i == -1 && j == n) // 3rd condition
            {
                j = n - 2;
                i = 0;
            } else {
                // 1st condition helper if next number
                // goes to out of square's right side
                if (j == n)
                    j = 0;

                // 1st condition helper if next number
                // is goes to out of square's upper side
                if (i < 0)
                    i = n - 1;
            }
            if (magicSquare[i][j] != 0) // 2nd condition
            {
                j -= 2;
                i++;
                continue;
            } else
                magicSquare[i][j] = num++; // set number

            j++;
            i--; // 1st condition
        }

        // Print magic square
        System.out.println("The Magic Square for n=" + n
                + ": Sum of each row or column " + n * (n * n + 1) / 2 + ":\n");
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)

                // Print each element with proper formatting
                System.out.printf("%4d ", magicSquare[i][j]);
            System.out.println();
        }
    }

    // Driver code
    public static void main(String[] args) {
        // Works only when n is odd
        int n = 7;
        generateSquare(n);
    }
}
