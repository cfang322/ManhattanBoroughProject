package Backend;

import java.awt.*;
import javax.swing.*;

public class Website {
    
    public JFrame frame;
    public Map map;


    public Website(){
        frame = new JFrame("The Manhanton Map and Review");
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Website();
    }
}
