import java.math.BigInteger;
import java.util.Arrays;

public class Test extends Euclid{

    public static void main(String[] args) {
        System.out.println(GCD(250,1877));
        System.out.println(GF(1877, 250, 0));
//        String[][] arr = new String[5][5];
//        int k=2;
//        for(int i = 0; i<5;i++) {
//            for (int j = 0; j < 5; j++, k++) {
//                int o = 0;
//                for (int l = 1; l <= k; l++) {
//                    if (k % l == 0) o++;
//                }
//                if (o == 2) arr[i][j] = String.valueOf(k);
//                else arr[i][j] = "x";
//            }
//        }
//        System.out.println(Arrays.deepToString(arr));
//        int p = 1817, q = 1, e = 29;
//        int fiN =  (p - 1) * (q - 1);
//        while (true){
//            q++;
//            if(isPrimeNumber(q)) {
//                fiN = (p - 1) * (q - 1);
//                int n = p * q;
//                double d = (fiN + 1) / (double) (e);
//                for (int k = 1; k < fiN; k++) {
//                    d = (k * fiN + 1) / (double) (e);
//                    if (d % 2 == 0 || d % 2 == 1) break;
//                }
////                if (encrypt(n, e, decrypt(d, n, BigInteger.valueOf(711))).equals(BigInteger.valueOf(711))) {
////                    System.out.println(q);
////                    break;
////                }
//            }
//        }
////        System.out.println(q);
    }
     static int GCD(int a, int b) {
        int c = 1;
        while (c != 0) {
            c = a % b;
            a = b;
            b = c;
        }
        return a;
    }
    public static boolean isPrimeNumber(int i) {
        int factors = 0;
        int j = 1;

        while(j <= i)
        {
            if(i % j == 0)
            {
                factors++;
            }
            j++;
        }
        return (factors == 2);
    }


    }













//        for (int i = 1; i < 3599; i++) {
//            if (3599%i==0&&isPrimeNumber(i)){
//                for (int j = 1; j < 3599; j++) {
//                    if (i*j==3599&&isPrimeNumber(j)){
//                        System.out.println(i+"  "+j);
//                    }
//                }
//            }
//        }
//
//        for (int i = 1; i < 2000; i++) {
//            if (Integer.parseInt(GF(1817,i,0))==29) System.out.println(i);
//
//        }

//    public static boolean isPrimeNumber(int i) {
//        int factors = 0;
//        int j = 1;
//
//        while(j <= i)
//        {
//            if(i % j == 0)
//            {
//                factors++;
//            }
//            j++;
//        }
//        return (factors == 2);
//    }
