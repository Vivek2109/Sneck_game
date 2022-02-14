import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class App {

    public static void main(String[] args) {
        JFrame j = new JFrame();
        j.setBounds(10, 10, 905, 700);
        j.setTitle("Sneck Game");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Myframe frame = new Myframe();
        frame.setBackground(Color.DARK_GRAY);
        j.add(frame);
        j.setVisible(true);

    }

}
