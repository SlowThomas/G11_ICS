import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.ListIterator;

import algebra.Matrix;
import algebra.Vector;
import engine.*;

public class Test_Panel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

    public static class Calc implements Runnable {

        public Real_Obj cube = new Real_Obj("Cube");
        public Real_Obj plane = new Real_Obj("Ship");
        public Real_Obj plane_acc = new Real_Obj("Ship_Accelerating");
        public Label_Obj crosshair = new Label_Obj("crosshair.png");

        public Camera camera = new Camera(0, 0, -530);
        public Camera camera2 = new Camera(0, 100, -3000);
        public Camera plane_origin = new Camera(0, 0, 0);

        public Scene scene;

        public Calc(){
            plane.scale(3);
            plane_acc.scale(3);

            crosshair.scale(0.8);

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
        public boolean mouse_left_down = false;
        public boolean mouse_right_down = false;

        public float rot_speed_max;
        public float rot_acc;
        public float rot_speed_x = 0;
        public float rot_speed_y = 0;
        public float rot_speed_z = 0;

        public float acc = 2f;

        public boolean accelerating;
        public boolean decelerating;

        public Vector velocity = new Vector(0, 0, 0);
        public Vector x_norm;
        public Vector y_norm;
        public Vector z_norm;

        public static class Bullet{
            public Flat_Obj obj = new Flat_Obj("Bullet.png");
            public Vector pos;
            public Vector velocity;
            public long time = 5000; // bullet life

            public Bullet(Vector pos, Vector velocity){
                obj.scale(0.5);
                obj.cd(pos);
                this.pos = pos;
                this.velocity = velocity;
            }

            public void update(long delta){
                time -= delta;
                obj.move(velocity.mult(delta / 50f));
                pos = obj.getPos();
            }
        }

        public static class Enemy_Bullet{
            public Flat_Obj obj = new Flat_Obj("purple_lightning.png");
            public Vector pos;
            public Vector velocity;
            public long time = 10000; // bullet life

            public Enemy_Bullet(Vector pos, Vector velocity){
                obj.scale(2);
                obj.cd(pos);
                this.pos = pos;
                this.velocity = velocity;
            }

            public void update(long delta){
                time -= delta;
                obj.move(velocity.mult(delta / 50f));
                pos = obj.getPos();
            }
        }

        public static class Enemy{
            public Flat_Obj obj = new Flat_Obj("ghost_red.png");
            public Vector pos;
            public long fire_time_delta = 7000;
            public long fire_countdown = fire_time_delta;

            public Enemy(Vector pos){
                obj.cd(pos);
                this.pos = obj.getPos();
            }

            public void update(long delta){
                fire_countdown -= delta;
            }
        }

        public LinkedList<Bullet> bullets = new LinkedList<>();
        public ListIterator<Bullet> bullet_it;
        public Bullet bullet;

        public LinkedList<Enemy> enemies = new LinkedList<>();
        public ListIterator<Enemy> enemy_it;
        public Enemy enemy;

        public LinkedList<Enemy_Bullet> enemy_bullets = new LinkedList<>();
        public ListIterator<Enemy_Bullet> enemy_bullet_it;
        public Enemy_Bullet enemy_bullet;

        public int enemy_timer = 0;

        public int invincible_timer = 0;
        public int invincible_time = 5000;

        public int score = 0;
        public int life = 3;


        public float adjust(float n, long time){
            // default for n is unit per 50 millisecond
            return n * time / 50f;
            // return n;
        }

        public Vector adjust(Vector v, long time){
            // default for n is unit per 50 millisecond
            return v.mult(time / 50f);
            // return v;
        }

        public void rotate(Vector axis, float angle){
            plane.rotate(axis, angle);
            plane_acc.rotate(axis, angle);
            plane_origin.rotate(axis, angle);
            camera.rotate(plane_origin.getPos(), axis, angle);
        }

        public void move(Vector velocity){
            plane.move(velocity);
            plane_acc.move(velocity);
            camera.move(velocity);
            camera2.move(velocity);
            plane_origin.move(velocity);
        }

        public void shoot(){
            bullets.add(new Bullet(plane_origin.getPos(), velocity.add(z_norm.mult(1000))));
        }

        public void epoch(long time){
            fps = (int)(1000f / time + 0.5);

            // Generate enemy
            if(enemy_timer <= 0 && enemies.size() < 3){
                enemy_timer = 2000 + 10000 / (score / 10 + 1);

                // TODO: one-liner randomization
                Vector dir = new Vector(0, 0, 1);
                float rx = (float) (2 * Math.PI * Math.random());
                float ry = (float) (2 * Math.PI * Math.random());
                float a = (float) Math.cos(rx);
                float b = (float) Math.sin(rx);
                float c = (float) Math.cos(ry);
                float d = (float) Math.sin(ry);
                /*dir = new Matrix(new float[][]{
                        {0, 0, 0},
                        {-b*d, 0, -b*c},
                        {a*d, 0, a*c}
                }).dot(dir);*/

                dir = dir.mult((float)(Math.random() * 1e2 + 1e4));
                enemies.add(new Enemy(plane_origin.getPos().add(dir)));
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
            crosshair.cd(plane.getPos());
            crosshair.move(z_norm.mult(10000f)); // 100 meters


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

            accelerating = mouse_left_down || pressed_keys['j'];
            decelerating = mouse_right_down;

            if(accelerating){
                velocity = velocity.add(z_norm.mult(adjust(acc, time)));
            }
            if(decelerating){
                velocity = velocity.subtract(z_norm.mult(adjust(acc, time)));
            }
            move(adjust(velocity, time));


            if(pressed_keys['1']){
                camera_mode = 0;
            }
            if(pressed_keys['2']){
                camera_mode = 1;
            }

            if(camera_mode == 0){
                scene.mount_camera(camera);
            }
            else if(camera_mode == 1){
                scene.mount_camera(camera2);
                camera2.rotate(plane_origin.getPos(), camera2.y_norm, (float) (sensitivity * mouse_dx));
                camera2.rotate(plane_origin.getPos(), camera2.x_norm, (float) (sensitivity * mouse_dy));
            }
            mouse_dx = 0;
            mouse_dy = 0;

            if(pressed_keys[' '] || pressed_keys['k']) shoot();


            if(camera_mode != 0){
                if(accelerating)
                    scene.rasterize(plane_acc);
                else
                    scene.rasterize(plane);
            }
            scene.rasterize(crosshair);
            // scene.rasterize(cube);

            if(!bullets.isEmpty()){
                bullet_it = bullets.listIterator();
                while(bullet_it.hasNext()){
                    bullet = bullet_it.next();
                    if(bullet.time <= 0) {
                        bullet_it.remove();
                        continue;
                    }
                    bullet.update(time);
                    scene.rasterize(bullet.obj);
                }
            }

            if(!enemies.isEmpty()){
                enemy_it = enemies.listIterator();
                while(enemy_it.hasNext()){
                    enemy = enemy_it.next();
                    if(enemy.fire_countdown <= 0){
                        enemy.fire_countdown = enemy.fire_time_delta;
                        Vector enemy_bullet_v = plane_origin.getPos().subtract(enemy.pos);
                        for(int i = 0; i < 3; i++){
                            Vector this_bullet_v = enemy_bullet_v.add(velocity.mult((float)Math.random() * 1000));
                            if(this_bullet_v.mag < 1e-11) enemy_bullets.add(new Enemy_Bullet(enemy.pos, this_bullet_v));
                            else enemy_bullets.add(new Enemy_Bullet(enemy.pos, this_bullet_v.mult(100 / this_bullet_v.mag)));
                        }
                    }
                    enemy.update(time);
                    scene.rasterize(enemy.obj);
                    if(!bullets.isEmpty()){
                        bullet_it = bullets.listIterator();
                        while(bullet_it.hasNext()) {
                            bullet = bullet_it.next();
                            double distance = 140;
                            if(
                                    bullet.pos.subtract(enemy.pos).cross(bullet.velocity).mag <= bullet.velocity.mag * distance &&
                                    ((bullet.pos.subtract(enemy.pos).dot(bullet.velocity) > 0) != (bullet.pos.subtract(bullet.velocity).subtract(enemy.pos).dot(bullet.velocity) > 0))
                              ){
                                    enemy_it.remove();
                                    score++;
                                    break;
                              }
                        }
                    }
                }
            }

            if(!enemy_bullets.isEmpty()){
                enemy_bullet_it = enemy_bullets.listIterator();
                while(enemy_bullet_it.hasNext()){
                    enemy_bullet = enemy_bullet_it.next();
                    double distance = 140;
                    if(
                            invincible_timer == 0 &&
                            enemy_bullet.pos.subtract(plane_origin.getPos()).cross(enemy_bullet.velocity).mag <= enemy_bullet.velocity.mag * distance &&
                                    ((enemy_bullet.pos.subtract(plane_origin.getPos()).dot(enemy_bullet.velocity) > 0) != (enemy_bullet.pos.subtract(enemy_bullet.velocity).subtract(plane_origin.getPos()).dot(enemy_bullet.velocity) > 0))
                    ){
                        life--;
                        invincible_timer = invincible_time;
                    }
                    invincible_timer -= time;
                    invincible_timer = Math.max(0, invincible_timer);

                    if(enemy_bullet.time <= 0){
                        enemy_bullet_it.remove();
                        continue;
                    }
                    enemy_bullet.update(time);
                    scene.rasterize(enemy_bullet.obj);
                }
            }

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

    public int last_score = 0;
    public long notification_countdown = 0;

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
        g.drawString("score: " + calc.score, 10, 50);
        g.drawString("life: " + calc.life, 10, 70);

        if(calc.score != last_score) notification_countdown = 60;
        if(notification_countdown > 0){
            g.setColor(new Color(255, 0, 0));
            g.drawString("Eliminated", 350, 70);
            notification_countdown--;
        }
        last_score = calc.score;
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Test Window");
        Test_Panel panel = new Test_Panel();
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
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
                calc.mouse_left_down = true;
            }
            if(e.getButton() == 3){
                calc.mouse_right_down = true;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(calc != null){
            if(e.getButton() == 1){
                calc.mouse_left_down = false;
            }
            if(e.getButton() == 3){
                calc.mouse_right_down = false;
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
