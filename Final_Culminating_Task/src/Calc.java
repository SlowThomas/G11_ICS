import algebra.Vector;
import engine.Camera;
import engine.Obj;
import engine.Scene;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Calc{// implements Runnable, MouseListener, KeyListener, MouseMotionListener {
    /*
    public Obj cube = new Obj("3D Object Test");
    public Obj plane = new Obj("Starship");
    public Camera camera = new Camera(0, 100, -3000);
    public Camera camera2 = new Camera(0, 0, 0);
    public Robot automation;

    //public Scene scene = new Scene(new Camera[]{camera, camera2}, new Obj[]{cube, plane});

    public Calc(){
        plane.auto_origin();

        try{
            automation = new Robot();
        }
        catch(Exception e){}
        this.pane = frame;


        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        frame.getContentPane().setCursor(blankCursor);
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
            double acc = 0.1;

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
            if(decelerating){
                velocity = velocity.subtract(z_norm.mult(acc));
            }

            move();
        }
    }

    public double sensitivity = 0.005;
    public int x;
    public int y;
    public boolean dragging = true;
    public boolean accelerating = false;
    public boolean decelerating = false;

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
            frame.getContentPane().setCursor(blankCursor);
            automation.mouseMove(getLocationOnScreen().x + getWidth() / 2, getLocationOnScreen().y + getHeight() / 2);
            dragging = true;
        }
    }

    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            accelerating = true;
        }
        if(e.getButton() == 3){
            decelerating = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1){
            accelerating = false;
        }
        if(e.getButton() == 3){
            decelerating = false;
        }
    }

    public void mouseDragged(MouseEvent e) {
        if(dragging) {
            x = e.getX();
            y = e.getY();
            camera.rotate(plane.pos, camera2.y_norm, sensitivity * (x - getWidth() / 2.0));
            camera.rotate(plane.pos, camera.x_norm, sensitivity * (y - getHeight() / 2.0));
            automation.mouseMove(getLocationOnScreen().x + getWidth() / 2, getLocationOnScreen().y + getHeight() / 2);
        }
    }

    public void mouseMoved(MouseEvent e) {
        if(dragging) {
            x = e.getX();
            y = e.getY();
            camera.rotate(plane.pos, camera2.y_norm, sensitivity * (x - getWidth() / 2.0));
            camera.rotate(plane.pos, camera.x_norm, sensitivity * (y - getHeight() / 2.0));
            automation.mouseMove(getLocationOnScreen().x + getWidth() / 2, getLocationOnScreen().y + getHeight() / 2);
        }
    }

    public void keyTyped(KeyEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

     */
}
