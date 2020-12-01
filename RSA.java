import java.math.BigInteger;
import java.util.Scanner;

public class RSA {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter two prime numbers");
        int p = scanner.nextInt(), q = scanner.nextInt();

        int n = p * q;
        System.out.println("n = " + n);

        int fiN = (p - 1) * (q - 1);
        System.out.println("Fi(n) = " + fiN);

        System.out.println("Enter e");
        int e = scanner.nextInt();
        while (GCD(e, fiN) != 1) {
            e = scanner.nextInt();
        }
        System.out.println("e = " + e);

//        d = (k*Fi(n)+1)/e formula | k is a number where d is integer
        double d = (fiN + 1) / (double) (e);
        for (int k = 1; k < fiN; k++) {
            d = (k * fiN + 1) / (double) (e);
            if (d % 2 == 0 || d % 2 == 1) break;
        }
        System.out.println("d = " + d);
//
//        BigInteger b_e = new BigInteger(String.valueOf(e));
//        BigInteger b_fiN = new BigInteger(String.valueOf(fiN));
//        BigInteger d = b_e.modInverse(b_fiN);
//        System.out.println(d);

        int[] arr = {3185, 2038, 2460, 2550};
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 4; i++) {


//            System.out.println("Enter number for encryption m<n");
//            int m = arr[i];
            BigInteger c = new BigInteger(String.valueOf(arr[i]));
            System.out.println("Encrypted Bob's text = " + c);


            System.out.println("Decrypted text " + decrypt(d,n,c));
            res.append(decrypt(d,n,c)).append(" ");

        }
        System.out.println(res);
    }

    static BigInteger encrypt(int n, int e, int m) {
        BigInteger bn = new BigInteger(String.valueOf(n));
        BigInteger bm = new BigInteger(String.valueOf(m));
//        c = m^e%n
        return (bm.pow(e).mod(bn));
    }

    static BigInteger decrypt(double d, int n, BigInteger c){
        BigInteger BigNPowD = new BigInteger(String.valueOf(c));
        BigNPowD = BigNPowD.pow((int) d);
        BigInteger BigN = new BigInteger(String.valueOf(n));
        return BigNPowD.mod(BigN);

    }

    private static int GCD(int a, int b) {
        int c = 1;
        while (c != 0) {
            c = a % b;
            a = b;
            b = c;
        }
        return a;
    }
}

//        1)
//        Enter two prime numbers
//        3 11
//        n = 33
//        Fi(n) = 20
//        Enter e
//        7
//        e = 7
//        d = 3.0
//        Enter number for encryption m<n
//        5
//        Encrypted Bob's text = 14
//        Decrypted text 5

//        2)
//        n=35 is simple so we can find p=5 and q=7
//        Enter two prime numbers
//        5 7
//        n = 35
//        Fi(n) = 24
//        Enter e
//        5
//        e = 5
//        d = 5.0
//        Encrypted Bob's text = 10
//        Decrypted text 5


//      3)
//       using this algorith I found prime numbers
//          public static void main(String[] args) {
//              for (int i = 1; i < 3599; i++) {
//                  if (3599%i==0&&isPrimeNumber(i)){
//                      for (int j = 1; j < 3599; j++) {
//                          if (i*j==3599&&isPrimeNumber(j)){
//                              System.out.println(i+"  "+j);
//                            }
//                       }
//                  }
//             }
//        }
//          public static boolean isPrimeNumber(int i) {
//              int factors = 0;
//              int j = 1;
//              while(j <= i){
//                  if(i % j == 0){
//                      factors++;
//                  }
//                  j++;
//              }
//              return (factors == 2);
//          }
//        p=59 q=61

//    Enter two prime numbers
//        59 61
//        n = 3599
//        Fi(n) = 3480
//        Enter e
//        31
//        e = 31
//        d = 3031.0

//        private key = {d=3031, e=31}


//4)
//public static void main(String[] args) {
//        int p = 1817, q = 1, e = 29;
//        int fiN =  (p - 1) * (q - 1);
//        while (true){
//        q++;
//        if(isPrimeNumber(q)) {
//        fiN = (p - 1) * (q - 1);
//        int n = p * q;
//        double d = (fiN + 1) / (double) (e);
//        for (int k = 1; k < fiN; k++) {
//        d = (k * fiN + 1) / (double) (e);
//        if (d % 2 == 0 || d % 2 == 1) break;
//        }
//        if (encrypt(n, e, decrypt(d, n, BigInteger.valueOf(711))).equals(BigInteger.valueOf(711))) {
//        System.out.println(q);
//        break;
//        }
//        }
//        }
//        System.out.println(q);
//        }
//
//public static boolean isPrimeNumber(int i) {
//        int factors = 0;
//        int j = 1;
//
//        while(j <= i)
//        {
//        if(i % j == 0)
//        {
//        factors++;
//        }
//        j++;
//        }
//        return (factors == 2);
//        }

//    Decrypted text 5609
//    private key = {4133, 12719}


//5)
//The scheme will work correctly because we can send an encrypted message with a private key, the recipient will first decrypt the message with the private key, then he must decrypt again using his private key. Even if someone intercepts the message, he will not be able to encrypt it without the recipient's private key.