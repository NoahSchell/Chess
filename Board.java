import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board
{
    public static void main(String[] args) {
        JFrame f = new JFrame("Test");
        JButton button = new JButton("TestButton");
        f.add(button);
        f.setSize(200,200);
        f.setVisible(true);
    }
}