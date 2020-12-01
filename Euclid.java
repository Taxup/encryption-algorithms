import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Euclid {
    static String GF(int a, int b, int c) {

        ArrayList<Integer> q = new ArrayList<Integer>();
        ArrayList<Integer> a1 = new ArrayList<Integer>();
        ArrayList<Integer> a2 = new ArrayList<Integer>();
        ArrayList<Integer> a3 = new ArrayList<Integer>();
        ArrayList<Integer> b1 = new ArrayList<Integer>();
        ArrayList<Integer> b2 = new ArrayList<Integer>();
        ArrayList<Integer> b3 = new ArrayList<Integer>();

        q.add(0, 0);
        a1.add(0, 1);
        a2.add(0, 0);
        a3.add(0, a);
        b1.add(0, 0);
        b2.add(0, 1);
        if (c!=0){
            b3.add(0, (int) Math.pow(2, c));
        }else{
            b3.add(0, b);
        }

        for (int i = 1; b3.get(i - 1) != 0; i++) {
            q.add(a3.get(i - 1) / b3.get(i - 1));
            a1.add(b1.get(i - 1));
            a2.add(b2.get(i - 1));
            a3.add(b3.get(i - 1));
            b1.add(a1.get(i - 1) - b1.get(i - 1) * q.get(i));
            b2.add(a2.get(i - 1) - b2.get(i - 1) * q.get(i));
            b3.add(a3.get(i - 1)  % b3.get(i - 1));
            if(b3.get(i)==1) {
                if (b2.get(i) < 0)return "" + (a3.get(0) + b2.get(i));
                else return "" + (b2.get(i));
            }
        }
        return "0";
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

    static String mod(int a, int b) {
        while (a <= 0) {a += b;}return a + " mod " + b;
    }

    private static JTextField textfield1, textfield2, textfield3, textfield4, textfield5, textfield6, textfield7, textfield8;

    public static void main(String[] args) {

        JFrame f = new JFrame("");
        f.getContentPane().setLayout(new FlowLayout());
        textfield1 = new JTextField("", 10);
        textfield2 = new JTextField("", 10);

        textfield3 = new JTextField("", 8);
        textfield4 = new JTextField("", 8);
        textfield5 = new JTextField("0", 8);

        textfield6 = new JTextField("", 8);
        textfield7 = new JTextField("", 8);
        textfield8 = new JTextField("", 8);

        JButton button = new JButton("a");
        JButton button2 = new JButton("b");
        JButton button3 = new JButton("c");

        f.add(textfield1);
        f.add(textfield2);
        f.add(button);
        f.add(textfield3);
        f.add(textfield4);
        f.add(textfield5);
        f.add(button2);
        f.add(textfield6);
        f.add(textfield7);
        f.add(textfield8);
        f.add(button3);

        button.addActionListener(e -> {
            try{
                int message = GCD(Integer.parseInt(textfield1.getText()), Integer.parseInt(textfield2.getText()));
                JOptionPane.showMessageDialog(null, message, "result", JOptionPane.PLAIN_MESSAGE);

            }catch (Exception t){JOptionPane.showMessageDialog(null, "ERROR", "result", JOptionPane.PLAIN_MESSAGE);
            }
        });

        button2.addActionListener(e -> {
            String a = String.valueOf(Integer.parseInt(textfield3.getText()));
            String b = String.valueOf(Integer.parseInt(textfield4.getText()));
            String c = String.valueOf(Integer.parseInt(textfield5.getText()));

            if (!a.equals(textfield3.getText()) || !b.equals(textfield4.getText()) || !c.equals(textfield5.getText())) {
                JOptionPane.showMessageDialog(null, "ERROR", "result", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, GF(Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c)), "result", JOptionPane.PLAIN_MESSAGE);

            }
        });

        button3.addActionListener(e -> {
            String a = String.valueOf(Integer.parseInt(textfield6.getText()));
            String b = String.valueOf(Integer.parseInt(textfield8.getText()));
            if (!textfield7.getText().equals("mod") || !a.equals(textfield6.getText()) || !b.equals(textfield8.getText())) {
                JOptionPane.showMessageDialog(null, "ERROR", "result", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, mod(Integer.parseInt(a), Integer.parseInt(b)), "result", JOptionPane.PLAIN_MESSAGE);
            }
        });

        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLocation(500, 250);
        f.setSize(350, 150);
    }
}
