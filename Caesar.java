import java.util.Scanner;

public class Caesar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text");
        String s = scanner.nextLine();

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();//letters that we change through the key
        String res = "";

        System.out.println("Enter key between 0-26");
        int key = scanner.nextInt();

        System.out.println("1.encrypt or 2.decrypt");//1 to move right - 2 to move left by alphabet
        int ch = scanner.nextInt();
        if (ch == 2) key = -key;

        while(key<26) {
            for (int i = 0; i < s.length(); i++) {//two D loop to compare and record letters of text with array of chars alphabet[]
                for (int j = 0; j < alphabet.length; j++) {
                    if (s.charAt(i) == alphabet[j]) { //to replace lowercase letters
                        res += alphabet[(j + key + 26) % 26];
                        break;
                    } else if (s.charAt(i) == Character.toUpperCase(alphabet[j])) { //to replace uppercase letters
                        res += Character.toUpperCase(alphabet[(j + key + 26) % 26]);
                        break;
                    }
                }

                if (res.length() == i) { //this is a condition for writing non-letters to the result
                    res += s.charAt(i);
                }
            }

            System.out.println(key+res);
            key++;
            res="";
        }
    }
}
