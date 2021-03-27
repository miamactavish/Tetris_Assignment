import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static int num_rows;
    static int num_cols;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        num_rows = sc.nextInt();
        num_cols = sc.nextInt();

        int[][] map = new int[num_rows][num_cols];
        
        for (int i = 0; i < num_rows; i++) {

            String row = sc.next();

            for (int j = 0; j < num_cols; j++) {
                char letter = row.charAt(j);

                int val = Character.getNumericValue(letter);
                map[i][j] = val;
            }
        }

        Solver s = new Solver(map);

        int num_iterations = sc.nextInt();

        for (int i = 0; i < num_iterations; i++) {
            
            // Coordinates for starting cell
            int r1 = sc.nextInt();
            int c1 = sc.nextInt();

            // Coordinates for our goal cell
            int r2 = sc.nextInt();
            int c2 = sc.nextInt();

            s.solve(r1, c1, r2, c2);
        }
    }
}