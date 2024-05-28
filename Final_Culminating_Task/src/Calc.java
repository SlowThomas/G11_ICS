import algebra.Matrix;
import algebra.Vector;
import engine.Camera;
import engine.Flat_Obj;
import engine.Obj;

public class Calc implements Runnable {

    public Obj cube = new Obj("3D Object Test");
    public Obj plane = new Obj("Starship");
    public Camera camera = new Camera(0, 100, -3000);
    public Camera camera2 = new Camera(0, 0, 0);

    // mounting points
    public Camera[] cameras;
    public Obj[] objs;
    public Flat_Obj[] flat_objs;

    public Calc(){
        plane.auto_origin();

        cameras = new Camera[]{camera, camera2};
        objs = new Obj[]{cube, plane};
        flat_objs = new Flat_Obj[]{};
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
    public double sensitivity = 0.001;

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
    float acc = 0.5f;

    int camera_mode = 0;

    public void epoch(){
        long start = System.currentTimeMillis();

        if(camera_mode == 0){
            rot_speed_max = 0.01f;
            rot_acc = 0.002f;
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

        if(!(pressed_keys['w'] || pressed_keys['a'] || pressed_keys['s'] || pressed_keys['d'] || pressed_keys['e'] || pressed_keys['q'])){
            rot_speed = 0;
        }

        if(camera_mode != 0 && pressed_keys['1']){
            camera.rotate(plane.pos, camera.x_norm.cross(x_norm), (float)Math.acos(camera.x_norm.dot(x_norm)));
            camera.rotate(camera2.pos, camera.y_norm.cross(y_norm), (float)Math.acos(camera.y_norm.dot(y_norm)));
            //camera.rotate(camera2.pos, camera.z_norm.cross(z_norm), (float)Math.acos(camera.z_norm.dot(z_norm)));
            camera_mode = 0;
        }
        if(pressed_keys['2']){
            camera_mode = 1;
        }

        rot_speed = Math.max(0, Math.min(rot_speed_max, rot_speed));

        if(accelerating){
            velocity = velocity.add(z_norm.mult(acc));
        }
        if(decelerating){
            velocity = velocity.subtract(z_norm.mult(acc));
        }
        if(dragging){
            if(camera_mode == 0){
                camera.rotate(plane.pos, camera2.y_norm, (float) (sensitivity * mouse_dx));
                camera.rotate(plane.pos, camera.x_norm, (float) (sensitivity * mouse_dy));
            }
            else if(camera_mode == 1){
                camera.rotate(plane.pos, camera.y_norm, (float) (sensitivity * mouse_dx));
                camera.rotate(plane.pos, camera.x_norm, (float) (sensitivity * mouse_dy));
            }
        }

        move();

        long end = System.currentTimeMillis();
        t = (end - start);
    }

    public void run() {
        while(true){
            long start = System.currentTimeMillis();

            try { Thread.sleep(20); }
            catch(Exception e){}

            epoch();
        }
    }
}
