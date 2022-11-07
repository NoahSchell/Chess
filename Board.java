import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JFrame
{
    public Board() {
        super("Chess");
        Container win = getContentPane();

    }


    public static void main(String[] args) {
        new Board();
    }
}