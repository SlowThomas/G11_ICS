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
    public double sensitivity = 0.001;

    public double mouse_dx;
    public double mouse_dy;
    public boolean dragging = true;
    public boolean accelerating;
    public boolean decelerating;
    public boolean[] pressed_keys = new boolean['z' + 1];

    public double t;

    public void run() {
        while(true){
            long start = System.nanoTime();

            try { Thread.sleep(20); }
            catch(Exception e){}


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
            if(dragging){
                camera.rotate(plane.pos, camera2.y_norm, sensitivity * mouse_dx);
                camera.rotate(plane.pos, camera.x_norm, sensitivity * mouse_dy);
            }

            move();

            long end = System.nanoTime();
            t = (end - start) / 1e9;
        }
    }
}
