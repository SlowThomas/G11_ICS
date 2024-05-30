import algebra.Vector;
import engine.*;

import java.util.LinkedList;
import java.util.ListIterator;

public class Calc implements Runnable {

    public Real_Obj cube = new Real_Obj("Cube");
    public Real_Obj plane = new Real_Obj("Arrow");

    public Flat_Obj bullet = new Flat_Obj("Bullet.png");

    public Label_Obj crosshair = new Label_Obj("crosshair.png");

    public Camera camera = new Camera(0, 100, -3000);
    public Camera camera2 = new Camera(0, 0, 100);
    public Camera plane_origin = new Camera(0, 0, 0);

    public Scene scene;

    public Calc(){
        plane.auto_origin();

        crosshair.scale(0.5);
        bullet.scale(0.5);

        scene = new Scene(800, 450, 5);
        scene.mount_camera(camera2);
    }


    public Vector velocity = new Vector(0, 0, 0);
    public Vector x_norm;
    public Vector y_norm;
    public Vector z_norm;

    public void rotate(Vector axis, float angle){
        plane.rotate(axis, angle);
        plane_origin.rotate(axis, angle);
        camera2.rotate(plane_origin.pos, axis, angle);
    }

    public void move(){
        plane.move(velocity);
        camera.move(velocity);
        camera2.move(velocity);
        plane_origin.move(velocity);
        bullet.move(velocity);
    }

    public int frame_counter = 0;
    public double sensitivity = 0.005;

    public double mouse_dx;
    public double mouse_dy;
    public boolean dragging = true;
    public boolean accelerating;
    public boolean decelerating;
    public boolean[] pressed_keys = new boolean['z' + 1];

    public double t;

    float rot_speed_max;
    float rot_speed_x = 0;
    float rot_speed_y = 0;
    float rot_speed_z = 0;
    float rot_acc = 0.005f;

    float zoom = 1.1f;
    float acc = 0.2f;

    public Vector bullet_v;

    public LinkedList<Flat_Obj> bullets = new LinkedList<>();
    public LinkedList<Vector> bullets_v = new LinkedList<>();

    int camera_mode = 0;

    boolean shooting = false;

    public void shoot(){
        bullets.add(new Flat_Obj(bullet));
        // bullet.cd(plane_origin.pos);
        bullets_v.add(velocity.add(z_norm.mult(1000)));
        // bullet_v = velocity.add(z_norm.mult(1000));

        // shooting = true;
    }

    public void epoch(long time){
        x_norm = plane_origin.x_norm;
        y_norm = plane_origin.y_norm;
        z_norm = plane_origin.z_norm;

        crosshair.cd(plane.pos);
        crosshair.move(z_norm.mult(100000));

        if(camera_mode == 0){
            rot_speed_max = 0.01f;
            rot_acc = 0.001f;
        }
        else if(camera_mode == 1){
            rot_speed_max = 0.1f;
            rot_acc = 0.01f;
        }

        if(pressed_keys['l']){
            cube.scale(zoom);

        }
        if(pressed_keys['h']){
            cube.scale(1/zoom);
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

        rotate(x_norm, rot_speed_x);
        rotate(y_norm, rot_speed_y);
        rotate(z_norm, rot_speed_z);

        if(pressed_keys[' '] || pressed_keys['k']) shoot();

        if(camera_mode != 0 && pressed_keys['1']){
            camera.rotate(plane.pos, camera.x_norm.cross(x_norm), (float)Math.acos(camera.x_norm.dot(x_norm)));
            camera.rotate(plane_origin.pos, camera.y_norm.cross(y_norm), (float)Math.acos(camera.y_norm.dot(y_norm)));
            camera_mode = 0;
            scene.mount_camera(camera2);
        }
        if(camera_mode != 1 && pressed_keys['2']){
            camera_mode = 1;
            scene.mount_camera(camera);
        }

        if(accelerating || pressed_keys['j']){
            velocity = velocity.add(z_norm.mult(acc));
        }
        if(decelerating){
            velocity = velocity.subtract(z_norm.mult(acc));
        }

        /*
        if(shooting){
            scene.rasterize(bullet);
            bullet.move(bullet_v);
            if(bullet.pos.subtract(plane_origin.pos).mag > 100000) shooting = false; // 100 meters
        }
        if(!shooting){
            bullet.cd(plane_origin.pos);
        }*/

        if(!bullets.isEmpty()){
            ListIterator<Flat_Obj> instance_it = bullets.listIterator();
            ListIterator<Vector> velocity_it = bullets_v.listIterator();
            Flat_Obj bullet_instance;
            Vector instance_velocity;
            while(instance_it.hasNext()){
                bullet_instance = instance_it.next();
                instance_velocity = velocity_it.next();
                if(bullet.pos.subtract(plane_origin.pos).mag > 100000){
                    instance_it.remove();
                    velocity_it.remove();
                }
                scene.rasterize(bullet_instance);
                bullet_instance.move(instance_velocity);
            }
        }

        move();

        scene.rasterize(plane);
        scene.rasterize(crosshair);
        scene.rasterize(cube);

        scene.render();
    }

    public void run() {
        long start = System.currentTimeMillis(), end = start, time;

        while(true){
            // Aim for 20 Hz update
            time = end - start;
            start = end;
            if(time <= 50){
                try { Thread.sleep(50 - time); }
                catch(Exception e){}
                System.out.println("normal");
                epoch(50);
            }
            else{
                System.out.println("accelerating");
                epoch(time);
            }
            end = System.currentTimeMillis();
        }
    }
}
