import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import engine.*;

public class Test_Panel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
    public Robot automation;
    public Cursor blankCursor;

    public Scene scene;

    public Calc calc;


    public Test_Panel(){
        setPreferredSize(new Dimension(800, 450));

        // NOTE: to be repeated before game starts
        calc = new Calc();
        scene = calc.scene;

        Thread calculation = new Thread(calc);
        calculation.start();
        // -----------------------------------------


        try{
            automation = new Robot();
        }
        catch(Exception e){}


        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        this.setCursor(blankCursor);


        // Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        // Add Graphics Thread
        Thread thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(scene.canvas, 0, 0, null);
        g.setColor(new Color(255, 255, 255));
        if(calc.camera_mode == 0){
            g.drawString("Aiming Mode", 50, 100);
        }
        else if(calc.camera_mode == 1){
            g.drawString("Maneuver Mode", 50, 100);
        }
        g.drawString(calc.fps + " FPS", 10, 20);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Test Window");
        Test_Panel panel = new Test_Panel();
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        while(true){
            frame.pack();
        }
    }

    public void run() {
        while(true){
            try { Thread.sleep(50); }
            catch(Exception e){}

            repaint();
        }
    }

    // Input Handling

    public boolean dragging = true;

    public void keyPressed(KeyEvent e) {
        if(calc != null){
            if(e.getKeyChar() <= 'z') calc.pressed_keys[e.getKeyChar()] = true;

            if(e.getKeyCode() == 16){
                this.setCursor(Cursor.getDefaultCursor());
                dragging = false;
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        if(calc != null){
            if(e.getKeyChar() <= 'z') calc.pressed_keys[e.getKeyChar()] = false;

            if(e.getKeyCode() == 16){
                this.setCursor(blankCursor);
                automation.mouseMove(getLocationOnScreen().x + getWidth() / 2, getLocationOnScreen().y + getHeight() / 2);
                dragging = true;
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if(calc != null){
            if(e.getButton() == 1){
                calc.accelerating = true;
            }
            if(e.getButton() == 3){
                calc.decelerating = true;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(calc != null){
            if(e.getButton() == 1){
                calc.accelerating = false;
            }
            if(e.getButton() == 3){
                calc.decelerating = false;
            }
        }
    }



    public void rotate_cam(MouseEvent e){
        if(calc != null && dragging) {
            calc.mouse_dx += e.getX() - getWidth() / 2.0;
            calc.mouse_dy += e.getY() - getHeight() / 2.0;

            automation.mouseMove(getLocationOnScreen().x + getWidth() / 2, getLocationOnScreen().y + getHeight() / 2);
        }
    }

    public void mouseDragged(MouseEvent e) {
        rotate_cam(e);
    }

    public void mouseMoved(MouseEvent e) {
        rotate_cam(e);
    }

    public void keyTyped(KeyEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
