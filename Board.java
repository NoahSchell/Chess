import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JFrame {
    public Board() {
        super("Chess");
        Container win = getContentPane();
        win.setLayout(new BorderLayout());

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(8,8));

        win.add(p1,BorderLayout.WEST);
        win.add(p2,BorderLayout.CENTER);

        setSize(500,500);
        setVisible(true);
    }


    public static void main(String[] args) {
        new Board();
        Space [][] grid = new Space[8][8];

    }


}