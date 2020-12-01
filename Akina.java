public class Akina {

    // This is equation we have to solve
    private static double[][] equation = {
            { 2, 4, 1, 6 }, // 2x + 4y + 1z = 6
            { 3, 2, -1, 5 }, // 3x + 2y - 1z = 5
            { 2, -2, 3, 0 } // 2x - 2y + 3z = 0
    };

    public static void solve(double[][] c, int row1) {
        int rows = c.length;
        int cols = rows + 1;
//c[row1][row1] = 1
        double factor = c[row1][row1];
        for (int col=0; col<cols; col++)
            c[row1][col] /= factor;

// c[row][row2] = 0
        for (int row2=0; row2<rows; row2++)
            if (row2 != row1) {
                factor = -c[row2][row1];
                for (int col=0; col<cols; col++)
                    c[row2][col] += factor * c[row1][col];
            }
    }

    public static void solve(double[][] c) {
        int rows = c.length;
        for (int row1=0; row1<rows; row1++)
            solve(c,row1);
    }

    public static void print(double[][] c) {
        int rows = c.length;
        int cols = rows + 1;
        for (int row1=0; row1<rows; row1++) {
            for (int col=0; col<cols; col++)
                System.out.printf(String.valueOf(c[row1][col])+" ");
            System.out.println();
        }
        System.out.println();
    }

    public static void printSolution(double[][] c) {
        int rows = c.length, cols = rows + 1;
        char variable = (char)((rows > 3) ? ('z' - (rows-1)) : 'x');
        System.out.println("Solution:");
        for (int row1=0; row1<rows; row1++)
            System.out.printf(String.valueOf((char)variable++),c[row1][cols-1]);
        System.out.println();
    }

    public static void doProblem(double[][] problem, String description) {
        System.out.printf(description);
        System.out.println("Equations:");
        print(problem);
        solve(problem);
        System.out.println("Solved:");
        print(problem);
        printSolution(problem);
    }

    public static void main(String[] args) {
        doProblem(equation,"Problem 1 ");

    }
}