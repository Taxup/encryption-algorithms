import java.util.Scanner;

public class Playfair {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter key");
        String key = scanner.nextLine().toLowerCase();
//        remove all spaces in keyword and replace j with i
        key = key.replaceAll("j", "i");
        key = key.replaceAll(" ", "");

//        remove all duplicate letters except first using regex
        String temp = new StringBuilder(key).reverse().toString();
        temp = temp.replaceAll("(.)(?=.*\\1)", "");
        key = new StringBuilder(temp).reverse().toString();


        String alphabet = "abcdefghiklmnopqrstuvwxyz";//alphabet without j
        for (int i = 0; i < key.length(); i++) {
            alphabet = alphabet.replaceAll(String.valueOf(key.charAt(i)), "");//remove key letters from alphabet
        }
        Character[][] matrix = new Character[5][5];
        String ciphertext = "";

        System.out.println("enter plain text");
        String plaintext = scanner.nextLine().toLowerCase();

        System.out.println("1.encrypt/2.decrypt");
        int ch = scanner.nextInt();

        int h = 0;//index to enter alphabet in matrix
        int f = 0;//index to enter key in matrix
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (f < key.length()) {
                    matrix[i][j] = key.charAt(f);//first we add key letters
                    f++;
                } else {
                    matrix[i][j] = alphabet.charAt(h);//then we add other letters
                    h++;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");//print matrix
            }
            System.out.println();
        }


        String digraphs = "";//changed plain text in future
        if (plaintext.length() % 2 != 0) {
            plaintext += ' ';//added ' ' because java always shows error index out of range
        }
        for (int i = 0; i < plaintext.length(); i++) {
            if (plaintext.charAt(i + 1) == ' ') {
                digraphs += plaintext.charAt(i);//enter last letter then break loop
                break;
            }
            if (plaintext.charAt(i) != plaintext.charAt(i + 1)) {
                digraphs += plaintext.charAt(i);
                digraphs += plaintext.charAt(i + 1);//entering standart two letters
                i++;
            } else {
                digraphs += plaintext.charAt(i);
                digraphs += "x";//enter x between duplicate letters in digraphs
                plaintext += " ";
            }

        }
//        digraphs = digraphs.replaceAll(" ","");
        if (digraphs.length() % 2 != 0) {
            digraphs += 'x';//z to the end
        }
        digraphs = digraphs.replaceAll("j", "i");
        System.out.println(digraphs);


        for (int g = 0; g < digraphs.length(); g += 2)
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        for (int l = 0; l < 5; l++) {
                            if (digraphs.charAt(g + 1) == matrix[k][l] && digraphs.charAt(g) == matrix[i][j]) {
                                if (ch == 1) {
                                    if (i == k) {
                                        ciphertext += matrix[i][(j + 6) % 5];
                                        ciphertext += matrix[k][(l + 6) % 5];
                                    } else if (j == l) {
                                        ciphertext += matrix[(i + 6) % 5][j];
                                        ciphertext += matrix[(k + 6) % 5][l];
                                    } else {
                                        ciphertext += matrix[i][l];
                                        ciphertext += matrix[k][j];
                                    }
                                } else if (ch == 2) {
                                    if (i == k) {
                                        ciphertext += matrix[i][(j + 4) % 5];
                                        ciphertext += matrix[k][(l + 4) % 5];
                                    } else if (j == l) {
                                        ciphertext += matrix[(i + 4) % 5][j];
                                        ciphertext += matrix[(k + 4) % 5][l];
                                    } else {
                                        ciphertext += matrix[i][l];
                                        ciphertext += matrix[k][j];
                                    }
                                }
                            }
                        }
                    }
                }
            }

        System.out.println(ciphertext.toUpperCase());
    }
}