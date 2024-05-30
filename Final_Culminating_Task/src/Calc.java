import algebra.Vector;
import engine.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

public class Calc implements Runnable {

    public Real_Obj cube = new Real_Obj("Cube");
    public Real_Obj plane = new Real_Obj("Arrow");
    public Flat_Obj bullet = new Flat_Obj("Bullet.png");
    public Label_Obj crosshair = new Label_Obj("crosshair.png");

    public Camera camera = new Camera(0, 0, 100);
    public Camera camera2 = new Camera(0, 100, -3000);
    public Camera plane_origin = new Camera(0, 0, 0);

    public Scene scene;

    public Calc(){
        plane.auto_origin();

        crosshair.scale(0.5);
        bullet.scale(0.5);

        scene = new Scene(800, 450, 5);
        scene.mount_camera(camera2);
    }

    public int fps = 0;

    public int camera_mode = 1;

    public double sensitivity = 0.005;

    public float zoom = 1.1f;
    public float bullet_life = 1e5f;

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

    public LinkedList<Flat_Obj> bullets = new LinkedList<>();
    public LinkedList<Vector> bullets_v = new LinkedList<>();

    public ListIterator<Flat_Obj> bullet_instance_it;
    public ListIterator<Vector> bullet_velocity_it;
    public Flat_Obj bullet_instance;
    public Vector instance_velocity;


    public float adjust(float n, long time){
        // default for n is unit per 50 millisecond
        return n * time / 50f;
    }

    public Vector adjust(Vector v, long time){
        return v.mult(time / 50f);
    }

    public void rotate(Vector axis, float angle){
        plane.rotate(axis, angle);
        plane_origin.rotate(axis, angle);
        camera.rotate(plane_origin.pos, axis, angle);
    }

    public void move(Vector velocity){
        plane.move(velocity);
        camera.move(velocity);
        camera2.move(velocity);
        plane_origin.move(velocity);
        bullet.move(velocity);
    }

    public void shoot(){
        bullets.add(new Flat_Obj(bullet));
        bullets_v.add(velocity.add(z_norm.mult(1000)));
    }

    public void epoch(long time){
        fps = (int)(1000f / time + 0.5);

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
        crosshair.move(z_norm.mult(bullet_life));


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
            // TODO: not working when looking back
            camera.rotate(plane_origin.pos, camera.x_norm.cross(x_norm), (float)Math.acos(camera.x_norm.dot(x_norm)));
            camera.rotate(plane_origin.pos, camera.y_norm.cross(y_norm), (float)Math.acos(camera.y_norm.dot(y_norm)));
            scene.mount_camera(camera);
        }
        if(pressed_keys['2']){
            camera_mode = 1;
            // TODO: not working when looking back
            camera2.rotate(plane_origin.pos, camera2.x_norm.cross(x_norm), (float)Math.acos(camera2.x_norm.dot(x_norm)));
            camera2.rotate(plane_origin.pos, camera2.y_norm.cross(y_norm), (float)Math.acos(camera2.y_norm.dot(y_norm)));
            scene.mount_camera(camera2);
        }
        if(camera_mode == 0){
            camera.rotate(plane_origin.pos, plane_origin.y_norm, (float) (sensitivity * mouse_dx));
            camera.rotate(plane_origin.pos, camera.x_norm, (float) (sensitivity * mouse_dy));
        }
        else if(camera_mode == 1){
            camera2.rotate(plane_origin.pos, camera2.y_norm, (float) (sensitivity * mouse_dx));
            camera2.rotate(plane_origin.pos, camera2.x_norm, (float) (sensitivity * mouse_dy));
        }
        mouse_dx = 0;
        mouse_dy = 0;

        if(pressed_keys[' '] || pressed_keys['k']) shoot();

        if(!bullets.isEmpty()){
            bullet_instance_it = bullets.listIterator();
            bullet_velocity_it = bullets_v.listIterator();
            while(bullet_instance_it.hasNext()){
                bullet_instance = bullet_instance_it.next();
                instance_velocity = bullet_velocity_it.next();
                if(bullet_instance.pos.subtract(plane_origin.pos).mag > bullet_life){
                    bullet_instance_it.remove();
                    bullet_velocity_it.remove();
                }
                bullet_instance.move(adjust(instance_velocity, time));

                scene.rasterize(bullet_instance);
            }
        }

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
                epoch(50);
            }
            else{
                epoch(time);
            }
            end = System.currentTimeMillis();
        }
    }
}
