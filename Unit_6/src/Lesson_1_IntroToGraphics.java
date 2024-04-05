import java.awt.*;
import javax.swing.*;

// extends JPanel means that we'll complete the JPanel class,
// which was left incomplete by the Java developers

// The moment you "extends JPanel", this Java class (in this case,
// Lesson_1_IntroToGraphics) BECOMES a panel.
public class Lesson_1_IntroToGraphics extends JPanel{
    // Setting Up the JPanel
    public Lesson_1_IntroToGraphics(){
        setPreferredSize(new Dimension(600, 480));
    }

    public void paintComponent(Graphics g){
        g.setColor(new Color(200, 174, 80));
        g.drawLine(50, 75, 400, 240);

        g.setColor(new Color(240, 20, 20));
        g.drawRect(30, 300, 200, 100);

        g.setColor(new Color(0, 255, 0));
        g.fillRect(500, 400, 50, 50);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Lesson 1");
        Lesson_1_IntroToGraphics panel = new Lesson_1_IntroToGraphics();
        frame.add(panel);
        frame.setVisible(true);
        // frame.setSize(new Dimension(600, 480));
        frame.pack();
    }
}
