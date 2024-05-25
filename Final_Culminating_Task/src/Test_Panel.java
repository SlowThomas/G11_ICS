import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

import algebra.Vector;
import engine.*;

public class Test_Panel extends JPanel implements Runnable, KeyListener{

    Obj cube = new Obj("3D Object Test");
    Camera camera = new Camera(0, 0, 0);

    Scene scene = new Scene(new Camera[]{camera}, new Obj[]{cube});

    private final boolean[] pressed_keys = new boolean['z' + 1];

    // Input Handling
    public void keyPressed(KeyEvent e) { if(e.getKeyChar() <= 'z') pressed_keys[e.getKeyChar()] = true; }
    public void keyReleased(KeyEvent e) { if(e.getKeyChar() <= 'z') pressed_keys[e.getKeyChar()] = false; }

    public Test_Panel(){
        setPreferredSize(new Dimension(800, 450));
        // Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
        // Add Thread
        Thread thread = new Thread(this);
        thread.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(scene.render(), 0, 0, null);
        g.setColor(new Color(255));
        g.drawString("" + (int)camera.pos.at(0) + ", " + (int)camera.pos.at(1) + ", " + (int)camera.pos.at(2), 100, 100);
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

    public void run() {
        while(true){
            try { Thread.sleep(20); }
            catch(Exception e){}

            repaint();

            double rot_speed = 0.1;
            double zoom = 1.1;

            if(pressed_keys['l']){
                cube.scale(zoom);
            }
            if(pressed_keys['h']){
                cube.scale(1/zoom);
            }

            if(pressed_keys['w']){
                camera.rotate(camera.x_norm, -rot_speed);
            }
            if(pressed_keys['s']){
                camera.rotate(camera.x_norm, rot_speed);
            }
            if(pressed_keys['a']){
                camera.rotate(camera.z_norm, -rot_speed);
            }
            if(pressed_keys['d']){
                camera.rotate(camera.z_norm, rot_speed);
            }
            if(pressed_keys['q']){
                camera.rotate(camera.y_norm, rot_speed);
            }
            if(pressed_keys['e']){
                camera.rotate(camera.y_norm, -rot_speed);
            }

            if(pressed_keys['j']){
                camera.move(camera.z_norm);
            }
            if(pressed_keys['k']){
                camera.move(camera.z_norm.mult(-1));
            }
        }
    }
}
