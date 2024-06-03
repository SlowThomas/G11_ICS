import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.ListIterator;

import algebra.Vector;
import engine.*;

public class Test_Panel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

    public static class Calc implements Runnable {

        public Real_Obj cube = new Real_Obj("Cube");
        public Real_Obj plane = new Real_Obj("Ship");
        public Real_Obj plane_acc = new Real_Obj("Ship_Accelerating");
        public Flat_Obj bullet = new Flat_Obj("Bullet.png");
        public Label_Obj crosshair = new Label_Obj("crosshair.png");

        public Flat_Obj enemy = new Flat_Obj("ufo.png");

        public Camera camera = new Camera(0, 0, -530);
        public Camera camera2 = new Camera(0, 100, -3000);
        public Camera plane_origin = new Camera(0, 0, 0);

        public Scene scene;

        public Calc(){
            // plane.auto_origin();
            plane.scale(3);
            plane_acc.scale(3);

            crosshair.scale(0.8);
            bullet.scale(0.5);
            enemy.scale(1);

            scene = new Scene(800, 450, 5);
            scene.mount_camera(camera2);
        }

        public int fps = 0;

        public int camera_mode = 0;

        public double sensitivity = 0.005;

        public float zoom = 1.1f;

        public double mouse_dx;
        public double mouse_dy;
        public boolean[] pressed_keys = new boolean['z' + 1];

        public float rot_speed_max;
        public float rot_acc;
        public float rot_speed_x = 0;
        public float rot_speed_y = 0;
        public float rot_speed_z = 0;

        public float acc = 0.2f;

        public boolean accelerating;
        public boolean decelerating;

        public Vector velocity = new Vector(0, 0, 0);
        public Vector x_norm;
        public Vector y_norm;
        public Vector z_norm;

        public float bullet_life = 5000;
        public LinkedList<Flat_Obj> bullets = new LinkedList<>();
        public LinkedList<Vector> bullets_v = new LinkedList<>();
        public LinkedList<Float> bullet_time = new LinkedList<>();
        public ListIterator<Flat_Obj> bullet_instance_it;
        public ListIterator<Vector> bullet_velocity_it;
        public ListIterator<Float> bullet_time_it;
        public Flat_Obj bullet_instance;
        public Vector instance_velocity;
        public Float instance_time;

        public LinkedList<Flat_Obj> enemies = new LinkedList<>();
        public ListIterator<Flat_Obj> enemy_instance_it;
        public Flat_Obj enemy_instance;
        public int enemy_count = 0;

        public int enemy_timer = 0;

        public int score = 0;


        public float adjust(float n, long time){
            // default for n is unit per 50 millisecond
            // return n * time / 50f;
            return n;
        }

        public Vector adjust(Vector v, long time){
            // default for n is unit per 50 millisecond
            // return v.mult(time / 50f);
            return v;
        }

        public void rotate(Vector axis, float angle){
            plane.rotate(axis, angle);
            plane_acc.rotate(axis, angle);
            plane_origin.rotate(axis, angle);
            camera.rotate(plane_origin.pos, axis, angle);
        }

        public void move(Vector velocity){
            plane.move(velocity);
            plane_acc.move(velocity);
            camera.move(velocity);
            camera2.move(velocity);
            plane_origin.move(velocity);
            bullet.move(velocity);
        }

        public void shoot(){
            bullets.add(new Flat_Obj(bullet));
            bullets_v.add(velocity.add(z_norm.mult(1000)));
            bullet_time.add(bullet_life);
        }

        public void epoch(long time){
            fps = (int)(1000f / time + 0.5);

            // Generate enemy
            if(enemy_timer <= 0 && enemy_count < 10){
                enemy_timer = 2000 + 10000 / (score / 10 + 1);

                enemies.add(new Flat_Obj(enemy));
                enemies.getLast().cd(plane_origin.pos);
                Vector dir = new Vector(0, 0, 1);
                // TODO: add x and y rotation matrix

                enemies.getLast().move(dir.mult((float)(Math.random() * 1e5 + 1e4)));
                enemy_count++;
            }
            if(enemy_timer > 0){ enemy_timer -= (int) time;}

            if(pressed_keys['l']){
                cube.scale(zoom);

            }
            if(pressed_keys['h']){
                cube.scale(1/zoom);
            }


            x_norm = plane_origin.x_norm;
            y_norm = plane_origin.y_norm;
            z_norm = plane_origin.z_norm;
            crosshair.cd(plane.pos);
            crosshair.move(z_norm.mult(50000f)); // 500 meters


            if(camera_mode == 0){
                rot_speed_max = 0.01f;
                rot_acc = adjust(0.001f, time);
            }
            else if(camera_mode == 1){
                rot_speed_max = 0.1f;
                rot_acc = adjust(0.01f, time);
            }
            if(pressed_keys['w']){
                if(rot_speed_x < 0) rot_speed_x = 0;
                rot_speed_x += rot_acc;
            }
            if(pressed_keys['s']){
                if(rot_speed_x > 0) rot_speed_x = 0;
                rot_speed_x -= rot_acc;
            }
            if(pressed_keys['a']){
                if(rot_speed_z < 0) rot_speed_z = 0;
                rot_speed_z += rot_acc;
            }
            if(pressed_keys['d']){
                if(rot_speed_z > 0) rot_speed_z = 0;
                rot_speed_z -= rot_acc;
            }
            if(pressed_keys['q']){
                if(rot_speed_y > 0) rot_speed_y = 0;
                rot_speed_y -= rot_acc;
            }
            if(pressed_keys['e']){
                if(rot_speed_y < 0) rot_speed_y = 0;
                rot_speed_y += rot_acc;
            }
            if(!pressed_keys['w'] && !pressed_keys['s']) rot_speed_x = 0;
            if(!pressed_keys['a'] && !pressed_keys['d']) rot_speed_z = 0;
            if(!pressed_keys['q'] && !pressed_keys['e']) rot_speed_y = 0;
            rot_speed_x = Math.max(Math.min(rot_speed_x, rot_speed_max), -rot_speed_max);
            rot_speed_y = Math.max(Math.min(rot_speed_y, rot_speed_max), -rot_speed_max);
            rot_speed_z = Math.max(Math.min(rot_speed_z, rot_speed_max), -rot_speed_max);
            rotate(x_norm, adjust(rot_speed_x, time));
            rotate(y_norm, adjust(rot_speed_y, time));
            rotate(z_norm, adjust(rot_speed_z, time));


            if(accelerating || pressed_keys['j']){
                velocity = velocity.add(z_norm.mult(adjust(acc, time)));
            }
            if(decelerating){
                velocity = velocity.subtract(z_norm.mult(adjust(acc, time)));
            }
            move(adjust(velocity, time));


            if(pressed_keys['1']){
                camera_mode = 0;
                scene.mount_camera(camera);
            }
            if(pressed_keys['2']){
                camera_mode = 1;
                scene.mount_camera(camera2);
            }

            if(camera_mode == 1){
                camera2.rotate(plane_origin.pos, camera2.y_norm, (float) (sensitivity * mouse_dx));
                camera2.rotate(plane_origin.pos, camera2.x_norm, (float) (sensitivity * mouse_dy));
            }
            mouse_dx = 0;
            mouse_dy = 0;

            if(pressed_keys[' '] || pressed_keys['k']) shoot();

            if(!bullets.isEmpty()){
                bullet_instance_it = bullets.listIterator();
                bullet_velocity_it = bullets_v.listIterator();
                bullet_time_it = bullet_time.listIterator();
                while(bullet_instance_it.hasNext()){
                    bullet_instance = bullet_instance_it.next();
                    instance_velocity = bullet_velocity_it.next();
                    instance_time = bullet_time_it.next();
                    scene.rasterize(bullet_instance);
                    bullet_instance.move(adjust(instance_velocity, time));
                    bullet_time_it.set(instance_time - time);
                    if(instance_time <= 0){
                        bullet_instance_it.remove();
                        bullet_velocity_it.remove();
                        bullet_time_it.remove();
                    }
                }
            }

            if(!enemies.isEmpty()){
                enemy_instance_it = enemies.listIterator();
                while(enemy_instance_it.hasNext()){
                    enemy_instance = enemy_instance_it.next();
                    scene.rasterize(enemy_instance);
                    if(!bullets.isEmpty()){
                        bullet_instance_it = bullets.listIterator();
                        bullet_velocity_it = bullets_v.listIterator();
                        while(bullet_instance_it.hasNext()) {
                            bullet_instance = bullet_instance_it.next();
                            instance_velocity = bullet_velocity_it.next();

                            // point-line distance detection
                        }
                    }
                }
            }

            if(camera_mode != 0){
                if(accelerating)
                    scene.rasterize(plane_acc);
                else
                    scene.rasterize(plane);
            }
            scene.rasterize(crosshair);
            scene.rasterize(cube);

            scene.render();
        }

        public int update_time = 50;

        public void run() {
            long start = System.currentTimeMillis(), end = start, time;

            while(true){
                // Aim for 20 Hz update
                time = end - start;
                start = end;
                if(time <= update_time){
                    try { Thread.sleep(update_time - time); }
                    catch(Exception e){}
                    epoch(update_time);
                }
                else{
                    epoch(time);
                }
                end = System.currentTimeMillis();
            }
        }
    }
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
