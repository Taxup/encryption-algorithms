import java.util.Arrays;
import java.util.Scanner;

public class Permutation {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String result = "";

        System.out.println("Enter plain text");
        String plaintext = scanner.nextLine();
        plaintext = plaintext.replaceAll(" ","");

        System.out.println("Enter key");
        int key = scanner.nextInt();

        System.out.println("Enter bumber of columns");
        int column = scanner.nextInt();

        char[][] matrix = new char[key][column];
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = ' ';
            }
        }
        int k = 1;
        for (int j = 0, i = 0; j < column && i < key; j++, i += k) {
            matrix[i][j] = plaintext.charAt(j);
            if (i == key - 1) {
                k = -1;
            }
            if (i == 0) {
                k = 1;
            }
        }

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < column; j++) {
                result+=matrix[i][j];
            }
        }

        result = result.replaceAll(" ","");

        System.out.println(result);
    }
}
