import java.util.Scanner;

public class Substitution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String result = "";

        String alphabet="abcdefghijklmnopqrstuvwxyz";

        System.out.println("Enter key");
        String key = scanner.nextLine();

        System.out.println("Enter plain text");
        String plaintext = scanner.nextLine();

        for (int i = 0; i < plaintext.length(); i++) {
            if (plaintext.charAt(i) == ' ') {
                result+=' ';
            } else {
                result += key.charAt(alphabet.indexOf(plaintext.charAt(i)));
            }
        }
        System.out.println(result);
    }
}
