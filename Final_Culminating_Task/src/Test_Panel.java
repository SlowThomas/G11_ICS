import javax.swing.*;
import java.awt.*;

public class Test_Panel extends JPanel{

    public Test_Panel(){
        setPreferredSize(new Dimension(800, 450));
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Test Window");
        Test_Panel panel = new Test_Panel();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
