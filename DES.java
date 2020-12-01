import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class DES {
    private static final int[][] S1 = {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };
    private static final int[][] S2 = {
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };
    private static final int[][] S3 = {
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };
    private static final int[][] S4 = {
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };
    private static final int[][] S5 = {
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };
    private static final int[][] S6 = {
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    };
    private static final int[][] S7 = {
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };
    private static final int[][] S8 = {
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String result = "";
        System.out.println("Enter hex text");
//        02468aceeca86420
        String plaintext = scanner.nextLine();
        System.out.println("Enter hex key");
//        0f1571c947d9e859
        String key = scanner.nextLine();

        //        64 to 56 Divide key to C and D PC - 1

        String strk = new BigInteger(key, 16).toString(2);
        String strk2 = String.format("%64s", strk).replace(" ", "0");
        int[][] PC1 = {
                {57, 49, 41, 33, 25, 17, 9},
                {1, 58, 50, 42, 34, 26, 18},
                {10, 2, 59, 51, 43, 35, 27},
                {19, 11, 3, 60, 52, 44, 36},
                {63, 55, 47, 39, 31, 23, 15},
                {7, 62, 54, 46, 38, 30, 22},
                {14, 6, 61, 53, 45, 37, 29},
                {21, 13, 5, 28, 20, 12, 4}
        };
        char[][] matrixkey = new char[8][7];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                matrixkey[i][j] = strk2.charAt(PC1[i][j] - 1);
            }
        }

        String C = "", D = "";
        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                C += matrixkey[i % 4][j];
                D += matrixkey[i][j];
            }
        }


//       1 convert
        String str = new BigInteger(plaintext, 16).toString(2);
        String str2 = String.format("%64s", str).replace(" ", "0");

        //       2 initial perm
        char[][] matrix = new char[8][8];
        int ind = 58;
        for (int i = 0; i < 8; i++) {
            if (ind == 66) ind = 57;
            int h = ind;

            for (int j = 0; j < 8; j++, h -= 8) {
                matrix[i][j] = str2.charAt(h - 1);
                if (h < 9) {
                    ind += 2;
                }
            }
        }


        for (int a = 0; a < 16; a++) {
            int Shift_left_key;
            System.out.println(plaintext);

//       1 convert
            if (a != 0) {
                str = new BigInteger(plaintext, 16).toString(2);
                str2 = String.format("%64s", str).replace(" ", "0");
            }


//      3 divide to left and right
            String L = "", R = "";
            if (a == 0) {
                for (int i = 4; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        L += matrix[i % 4][j];
                        R += matrix[i][j];
                    }
                }
            } else {
                for (int i = 32; i < 64; i++) {
                    L += str2.charAt(i % 32);
                    R += str2.charAt(i);
                }
            }
//        left shift c d
//        HERE code
            String Cnew = "", Dnew = "";
            if (a == 0 || a == 1 || a == 8 || a == 15) Shift_left_key = 1;
            else Shift_left_key = 2;
            for (int i = 0; i < 28; i++) {
                Cnew += C.charAt((i + Shift_left_key + 28) % 28);
                Dnew += D.charAt((i + Shift_left_key + 28) % 28);
            }
            String ShiftLeft = Cnew + Dnew;
            C = Cnew;
            D = Dnew;

//        PErm choice 2

            int[][] PC2 = {{14, 17, 11, 24, 1, 5},
                    {3, 28, 15, 6, 21, 10},
                    {23, 19, 12, 4, 26, 8},
                    {16, 7, 27, 20, 13, 2},
                    {41, 52, 31, 37, 47, 55},
                    {30, 40, 51, 45, 33, 48},
                    {44, 49, 39, 56, 34, 53},
                    {46, 42, 50, 36, 29, 32}};

            char[][] matrixPC2 = new char[8][6];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    matrixPC2[i][j] = ShiftLeft.charAt(PC2[i][j] - 1);
                }
            }

            //        Expansion
            char[][] ExpansionR = new char[8][6];
            int k = 0, inull = 32, ifive = 5;
            for (int i = 0; i < 8; i++) {
                ExpansionR[i][0] = R.charAt(inull - 1);
                inull = (inull + 4 + 32) % 32;
                ExpansionR[i][5] = R.charAt(ifive - 1);
                ifive = (ifive + 4 + 32) % 32;
                for (int j = 1; j < 5; j++, k++) {
                    ExpansionR[i][j] = R.charAt(k);
                }
            }

//        XOR
            String XOR = "";
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    if (ExpansionR[i][j] != matrixPC2[i][j]) {
                        XOR += '1';
                    } else XOR += '0';
                }
            }
//        S-boxes

            String sbox = "";
            ArrayList<int[][]> sBoxes = new ArrayList(Arrays.asList(S1, S2, S3, S4, S5, S6, S7, S8));
            for (int i = 0, j = 0; i < 48 && j < 8; i += 6, j++) {
                int[][] sBox = sBoxes.get(j);

                int row = 2 * Integer.parseInt(String.valueOf(XOR.charAt(i))) + Integer.parseInt(String.valueOf(XOR.charAt(i + 5)));
                int col = 8 * Integer.parseInt(String.valueOf(XOR.charAt(i + 1))) + 4 * Integer.parseInt(String.valueOf(XOR.charAt(i + 2))) + 2 * Integer.parseInt(String.valueOf(XOR.charAt(i + 3))) + Integer.parseInt(String.valueOf(XOR.charAt(i + 4)));


                String strs = new BigInteger(String.valueOf(sBox[row][col]), 10).toString(2);
                String strs2 = String.format("%4s", strs).replace(" ", "0");
                sbox += strs2;
            }
//        permutation
            int[][] P = {
                    {16, 7, 20, 21},
                    {29, 12, 28, 17},
                    {1, 15, 23, 26},
                    {5, 18, 31, 10},
                    {2, 8, 24, 14},
                    {32, 27, 3, 9},
                    {19, 13, 30, 6},
                    {22, 11, 4, 25}
            };
            char[][] matrixPermutations = new char[8][4];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    matrixPermutations[i][j] = sbox.charAt(P[i][j] - 1);
                }
            }

//        xor between L and R
            String newL = "";
            int iL = 0;
            char[][] LxorR = new char[8][4];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++, iL++) {
                    if (L.charAt(iL) != matrixPermutations[i][j]) {
                        LxorR[i][j] = '1';
                    } else LxorR[i][j] = '0';
                    newL += LxorR[i][j];
                }
            }
// 02468aceeca86420 0f1571c947d9e859
            BigInteger bg = new BigInteger(R + newL, 2);
            plaintext = bg.toString(16);
            plaintext = String.format("%16s", plaintext).replace(" ", "0");
        }
        System.out.println(plaintext);

        int[][] IIP = {
                {40, 8, 48, 16, 56, 24, 64, 32},
                {39, 7, 47, 15, 55, 23, 63, 31},
                {38, 6, 46, 14, 54, 22, 62, 30},
                {37, 5, 45, 13, 53, 21, 61, 29},
                {36, 4, 44, 12, 52, 20, 60, 28},
                {35, 3, 43, 11, 51, 19, 59, 27},
                {34, 2, 42, 10, 50, 18, 58, 26},
                {33, 1, 41, 9, 49, 17, 57, 25}
        };

        str = new BigInteger(plaintext, 16).toString(2);
        str2 = String.format("%64s", str).replace(" ", "0");

        String lastR="",lastL="";
        for (int i = 32; i < 64; i++) {
            lastR+=str2.charAt(i);
            lastL+=str2.charAt(i%32);
        }
        plaintext=lastR+lastL;


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                result += plaintext.charAt(IIP[i][j] - 1);
            }
        }
//        System.out.println(result);
        BigInteger res = new BigInteger(result, 2);
        result = res.toString(16);
        result = String.format("%16s", result).replace(" ", "0");
        System.out.println(result);
    }
}