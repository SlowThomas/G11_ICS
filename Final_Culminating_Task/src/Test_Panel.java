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

        cube.scale(10);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(scene.render(), 0, 0, null);
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

            if(pressed_keys['w']){
                camera.rotate(new Ray(camera.pos, new Vector(-0.5, 0, 0)));
            }
            if(pressed_keys['s']){
                camera.rotate(new Ray(camera.pos, new Vector(0.5, 0, 0)));
            }
            if(pressed_keys['a']){
                camera.rotate(new Ray(camera.pos, new Vector(0, 0, -0.5)));
            }
            if(pressed_keys['d']){
                camera.rotate(new Ray(camera.pos, new Vector(0, 0, 0.5)));
            }
            if(pressed_keys['q']){
                camera.rotate(new Ray(camera.pos, new Vector(0, 0.5, 0)));
            }
            if(pressed_keys['e']){
                camera.rotate(new Ray(camera.pos, new Vector(0, -0.5, 0)));
            }

            if(pressed_keys['j']){
                camera.translate(new Vector(0, 0, 0.5));
            }
            if(pressed_keys['k']){
                camera.translate(new Vector(0, 0, -0.5));
            }
        }
    }
}
