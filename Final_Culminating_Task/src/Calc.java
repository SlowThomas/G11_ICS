import algebra.Vector;
import engine.*;

public class Calc implements Runnable {

    public Obj cube = new Obj("Cube");
    public Obj plane = new Obj("Arrow");

    public Flat_Obj bullet = new Flat_Obj("Bullet.png");

    public Label_Obj crosshair = new Label_Obj("crosshair.png");

    public Camera camera = new Camera(0, 100, -3000);
    public Camera camera2 = new Camera(0, 0, 0);

    public Scene scene;

    // mounting points
    public Camera[] cameras;
    public Obj[] objs;
    public Flat_Obj[] flat_objs;
    public Label_Obj[] label_objs;

    public Calc(){
        plane.auto_origin();

        bullet.scale(1.5);

        cameras = new Camera[]{camera, camera2};
        objs = new Obj[]{cube, plane};
        flat_objs = new Flat_Obj[]{bullet};
        label_objs = new Label_Obj[]{crosshair};
        scene = new Scene(800, 450, 5, cameras, objs, flat_objs, label_objs);
    }


    public Vector velocity = new Vector(0, 0, 0);
    public Vector x_norm = camera.x_norm;
    public Vector y_norm = camera.y_norm;
    public Vector z_norm = camera.z_norm;

    public void rotate(Vector axis, float angle){
        plane.rotate(axis, angle);
        if(camera_mode == 0)
            camera.rotate(plane.pos, axis, angle);
        camera2.rotate(axis, angle);
    }

    public void move(){
        plane.move(velocity);
        camera.move(velocity);
        camera2.move(velocity);
    }

    public int frame_counter = 0;
    public double sensitivity = 0.01;

    public double mouse_dx;
    public double mouse_dy;
    public boolean dragging = true;
    public boolean accelerating;
    public boolean decelerating;
    public boolean[] pressed_keys = new boolean['z' + 1];

    public double t;

    float rot_speed_max;
    float rot_speed = 0;
    float rot_acc = 0.005f;
    float zoom = 1.1f;
    float acc = 0.2f;

    public Vector bullet_v;

    int camera_mode = 0;

    boolean shooting = false;

    public void shoot(){
        bullet.show();
        bullet.cd(camera2.pos);
        bullet_v = velocity.add(z_norm.mult(100));

        shooting = true;
    }

    public void epoch(){
        scene.render();

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
            rot_speed += rot_acc;
        }
        if(pressed_keys['s']){
            rotate(x_norm, -rot_speed);
            rot_speed += rot_acc;
        }
        if(pressed_keys['a']){
            rotate(z_norm, rot_speed);
            rot_speed += rot_acc;
        }
        if(pressed_keys['d']){
            rotate(z_norm, -rot_speed);
            rot_speed += rot_acc;
        }
        if(pressed_keys['q']){
            rotate(y_norm, -rot_speed);
            rot_speed += rot_acc;
        }
        if(pressed_keys['e']){
            rotate(y_norm, rot_speed);
            rot_speed += rot_acc;
        }
        if(pressed_keys[' '] || pressed_keys['k']) shoot();

        if(!(pressed_keys['w'] || pressed_keys['a'] || pressed_keys['s'] || pressed_keys['d'] || pressed_keys['e'] || pressed_keys['q'])){
            rot_speed = 0;
        }

        if(camera_mode != 0 && pressed_keys['1']){
            camera.rotate(plane.pos, camera.x_norm.cross(x_norm), (float)Math.acos(camera.x_norm.dot(x_norm)));
            camera.rotate(camera2.pos, camera.y_norm.cross(y_norm), (float)Math.acos(camera.y_norm.dot(y_norm)));
            camera_mode = 0;
        }
        if(pressed_keys['2']){
            camera_mode = 1;
        }

        rot_speed = Math.max(0, Math.min(rot_speed_max, rot_speed));

        if(accelerating || pressed_keys['j']){
            velocity = velocity.add(z_norm.mult(acc));
        }
        if(decelerating){
            velocity = velocity.subtract(z_norm.mult(acc));
        }

        if(shooting){
            bullet.move(bullet_v);
            if(bullet.pos.subtract(camera2.pos).mag > 100000) shooting = false; // 100 meters
        }
        if(!shooting){
            bullet.hide();
            bullet.cd(camera2.pos);
        }

        move();
    }

    public void run() {
        while(true){
            try { Thread.sleep(10); }
            catch(Exception e){}

            epoch();
        }
    }
}
