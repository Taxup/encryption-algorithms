import javax.swing.*;
import java.awt.*;

public class Kura extends JFrame {

    public static void main(String[] args) {
        JFrame j = new JFrame();

        j.getContentPane().setLayout(new FlowLayout());
        JTextField t1 = new JTextField("",10);
        JTextField t2 = new JTextField("",10);
        JButton b1 = new JButton("Run a");
        JButton b2 = new JButton("Run b");
        JButton b3 = new JButton("Run c");
        j.add(t1);
        j.add(t2);
        j.add(b1);
        j.add(b2);
        j.add(b3);

        b1.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, euclid(t1.getText(),t2.getText()), "Result", JOptionPane.PLAIN_MESSAGE);
        });
        b2.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, igf(t1.getText(),t2.getText()), "Result", JOptionPane.PLAIN_MESSAGE);
        });
        b3.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, mod(t1.getText(),t2.getText()), "Result", JOptionPane.PLAIN_MESSAGE);
        });

        j.setLocation(500, 250);
        j.setSize(300, 150);
        j.setVisible(true);
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //    INVERSE GF
     static int igf(String m, String n){
        int q[] = new int[10],a1[] = new int[10],a2[] = new int[10],a3[]= new int[10],b1[] = new int[10],b2[]= new int[10],b3[]= new int[10];
        q[0] = 0;
        a1[0] = 1;
        a2[0] = 0;
        b1[0] = 0;
        b2[0] = 1;
        a3[0]=Integer.parseInt(m);
        b3[0]=Integer.parseInt(n);
        int i = 1;
        int j;
        do {
            j = i - 1;
            q[i] = a3[j] / b3[j];
            a1[i] = b1[j];
            a2[i] = b2[j];
            a3[i] = b3[j];
            b1[i] = a1[j] - b1[j] * q[i];
            b2[i] = a2[j] - b2[j] * q[i];
            b3[i] = a3[j] % b3[j];
            i++;
            if (b3[j+1] == 1)
                return b2[j];
        } while (b3[j+1] != 0);
        return 0;
    }

    //    EUCLID
    private static String euclid(String m, String n){

        int a=Integer.parseInt(m), b=Integer.parseInt(n);
        while (a % b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return "Result is "+b;
    }

    //    m mod n

    private static String mod(String m, String n){
        return "Result is "+(Integer.parseInt(m)%Integer.parseInt(n)+Integer.parseInt(n))+" mod "+n;
    }

}
