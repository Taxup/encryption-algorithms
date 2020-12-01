import java.util.Scanner;

public class Vigenere {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text");
        String s = scanner.nextLine();
        int[] spaces = new int[s.length()];//array for entering spaces in result
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (s.charAt(i)==' ' && j == 0){
                spaces[i]=i;//writing index for future spaces in result
                j++;
            }
            else if (s.charAt(i)==' '){
                spaces[i-j]=i-j;//writing index for future spaces in result
                j++;
            }
        }
        s = s.replaceAll(" ","");//deleted all spaces from current text

        String alphabet = "abcdefghijklmnopqrstuvwxyz";//letters that we change through the key
        String res = "";//result

        System.out.println("Enter key");
        String skey = scanner.nextLine();//key
        int[] key = new int[skey.length()];
        for (int i = 0; i < skey.length(); i++) {
            key[i]=alphabet.indexOf(skey.charAt(i));//turning key to numbers key
        }


        System.out.println("1.encrypt or 2.decrypt");//1 to move right - 2 to move left by alphabet
        int ch = scanner.nextInt();
        if (ch == 2)
            for (int i = 0; i < key.length; i++)
                key[i] = -key[i];// minus to move left


        int k=0;//index for key
        for (int i = 0; i < s.length(); i++) {//two D loop to compare and record letters of text with array of chars alphabet[]
            if (spaces[i]==i) res+=' ';//add space to result

            for (int j = 0; j < alphabet.length(); j++) {
                if (s.charAt(i) == alphabet.charAt(j)) { //to replace lowercase letters
                    res += alphabet.charAt((j + key[k] + 26) % 26);
                    break;

                }
                else if (s.charAt(i) == Character.toUpperCase(alphabet.charAt(j))) { //to replace uppercase letters
                    res += Character.toUpperCase(alphabet.charAt((j + key[k] + 26) % 26));
                    break;
                }
            }

            k++;//index of key moving
            if (k == key.length) k = 0;
        }

        System.out.println(res);
    }
}
