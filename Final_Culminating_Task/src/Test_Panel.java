import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

import algebra.Vector;
import engine.*;

public class Test_Panel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

    Obj cube = new Obj("3D Object Test");
    Obj plane = new Obj("Starship");
    Camera camera = new Camera(0, 100, -3000);
    Camera camera2 = new Camera(0, 0, 0);
    Robot automation;
    JFrame frame;
    Cursor blankCursor;

    Scene scene = new Scene(new Camera[]{camera, camera2}, new Obj[]{cube, plane});


    public Test_Panel(JFrame frame){
        setPreferredSize(new Dimension(800, 450));
        // Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        // Add Thread
        Thread thread = new Thread(this);
        thread.start();

        try{
            automation = new Robot();
        }
        catch(Exception e){}
        this.frame = frame;


        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        frame.getContentPane().setCursor(blankCursor);

        plane.auto_origin();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(scene.render(), 0, 0, null);
        g.setColor(new Color(255));
        g.drawString((int)camera2.pos.at(0) / 100 + ", " + (int)camera2.pos.at(1) / 100 + ", " + (int)camera2.pos.at(2) / 100, 100, 100);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Test Window");
        Test_Panel panel = new Test_Panel(frame);
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        while(true){
            frame.pack();
        }
    }


    public Vector velocity = new Vector(0, 0, 0);
    public Vector x_norm = camera.x_norm;
    public Vector y_norm = camera.y_norm;
    public Vector z_norm = camera.z_norm;

    public void rotate(Vector axis, double angle){
        plane.rotate(axis, angle);
        camera.rotate(plane.pos, axis, angle);
        camera2.rotate(axis, angle);
    }

    public void move(){
        plane.move(velocity);
        camera.move(velocity);
        camera2.move(velocity);
    }

    public int frame_counter = 0;

    public void run() {
        while(true){
            try { Thread.sleep(20); }
            catch(Exception e){}

            repaint();

            double rot_speed = 0.03;
            double zoom = 1.1;
            double acc = 0.05;

            x_norm = camera2.x_norm;
            y_norm = camera2.y_norm;
            z_norm = camera2.z_norm;

            if(pressed_keys['l']){
                cube.scale(zoom);

            }
            if(pressed_keys['h']){
                cube.scale(1/zoom);
            }

            if(pressed_keys['w']){
                rotate(x_norm, rot_speed);
            }
            if(pressed_keys['s']){
                rotate(x_norm, -rot_speed);
            }
            if(pressed_keys['a']){
                rotate(z_norm, rot_speed);
            }
            if(pressed_keys['d']){
                rotate(z_norm, -rot_speed);
            }
            if(pressed_keys['q']){
                rotate(y_norm, -rot_speed);
            }
            if(pressed_keys['e']){
                rotate(y_norm, rot_speed);
            }

            if(accelerating){
                velocity = velocity.add(z_norm.mult(acc));
            }

            move();
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public double sensitivity = 0.005;
    public int x;
    public int y;
    public boolean dragging = true;
    public boolean accelerating = false;

    private final boolean[] pressed_keys = new boolean['z' + 1];

    // Input Handling
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() <= 'z') pressed_keys[e.getKeyChar()] = true;

        if(e.getKeyCode() == 16){
            dragging = false;
            frame.getContentPane().setCursor(Cursor.getDefaultCursor());
        }
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() <= 'z') pressed_keys[e.getKeyChar()] = false;

        if(e.getKeyCode() == 16){
            dragging = true;
            frame.getContentPane().setCursor(blankCursor);
            automation.mouseMove(getLocationOnScreen().x + getWidth() / 2, getLocationOnScreen().y + getHeight() / 2);
        }
    }
    public void keyTyped(KeyEvent e) {}

    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            accelerating = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1){
            accelerating = false;
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        if(dragging) {
            camera.rotate(plane.pos, camera2.y_norm, sensitivity * (x - getWidth() / 2));
            camera.rotate(plane.pos, camera.x_norm, sensitivity * (y - getHeight() / 2));
            automation.mouseMove(getLocationOnScreen().x + getWidth() / 2, getLocationOnScreen().y + getHeight() / 2);
        }
    }

    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        if(dragging) {
            camera.rotate(plane.pos, camera2.y_norm, sensitivity * (x - getWidth() / 2));
            camera.rotate(plane.pos, camera.x_norm, sensitivity * (y - getHeight() / 2));
            automation.mouseMove(getLocationOnScreen().x + getWidth() / 2, getLocationOnScreen().y + getHeight() / 2);
        }
    }
}
