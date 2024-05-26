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
    Camera camera = new Camera(0, 0, 0);

    Scene scene = new Scene(new Camera[]{camera}, new Obj[]{cube, plane});

    private final boolean[] pressed_keys = new boolean['z' + 1];

    // Input Handling
    public void keyPressed(KeyEvent e) { if(e.getKeyChar() <= 'z') pressed_keys[e.getKeyChar()] = true; }
    public void keyReleased(KeyEvent e) { if(e.getKeyChar() <= 'z') pressed_keys[e.getKeyChar()] = false; }

    public Test_Panel(){
        setPreferredSize(new Dimension(800, 450));
        // Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        // Add Thread
        Thread thread = new Thread(this);
        thread.start();

        plane.scale(0.1);
        plane.auto_origin();
        camera.move(0, 10, -100);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(scene.render(), 0, 0, null);
        g.setColor(new Color(255));
        g.drawString((int)camera.pos.at(0) + ", " + (int)camera.pos.at(1) + ", " + (int)camera.pos.at(2), 100, 100);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Test Window");
        Test_Panel panel = new Test_Panel();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void keyTyped(KeyEvent e) {
    }

    public Vector velocity = new Vector(0, 0, 0);
    public Vector x_norm = camera.x_norm;
    public Vector y_norm = camera.y_norm;
    public Vector z_norm = camera.z_norm;

    public void rotate(Vector axis, double angle){
        plane.rotate(axis, angle);
        //camera.rotate(plane.pos, axis, angle);
    }

    public void move(){
        plane.move(velocity);
        camera.move(velocity);
    }

    public int frame_counter = 0;

    public void run() {
        while(true){
            try { Thread.sleep(20); }
            catch(Exception e){}

            repaint();



            double rot_speed = 0.02;
            double zoom = 1.1;
            double acc = 0.01;

            x_norm = camera.x_norm;
            y_norm = camera.y_norm;
            z_norm = camera.z_norm;

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

            if(pressed_keys['j']){
                velocity = velocity.add(z_norm.mult(acc));
            }
            if(pressed_keys['k']){
                velocity = velocity.subtract(z_norm.mult(acc));
            }

            move();
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public double sensitivity = 0.005;
    public int last_x = -1;
    public int last_y = -1;
    public int x = -1;
    public int y = -1;
    public boolean dragging = false;

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        dragging = false;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        last_x = x;
        last_y = y;
        x = e.getX();
        y = e.getY();
        if(dragging) {
            camera.rotate(plane.pos, y_norm, sensitivity * (x - last_x));
            camera.rotate(plane.pos, x_norm, sensitivity * (y - last_y));
        }
        else {
            dragging = true;
        }
    }

    public void mouseMoved(MouseEvent e) {

    }
}
