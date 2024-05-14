package engine;
import algebra.*;
import java.util.Scanner;
import java.io.File;

public class Obj {

    private static class Consts{
        public static double epsilon = 1E-10; // another choice: 1E-14
    }

    public int[][] f;
    public Vector[] v;
    public int[] mtl;
    public Mtl material;
    
    public Vector pos = new Vector(0, 0, 0, 1);
    public double zoom = 1;
    // TODO: Implement zoom matrix

    public Matrix T_model = new Matrix(new double[][]{
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    }); // model space transformation
    public Matrix T_world = new Matrix(new double[][]{
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    }); // world space transformation

    public Obj(String object_name){
        material = new Mtl("objects/" + object_name + "/obj.mtl");

        Scanner file;
        try{
            file = new Scanner(new File("objects/" + object_name + "/tinker.obj"));
        } catch(Exception e){
            System.err.printf("Cannot find object: \"%s\"\n", object_name);
            return;
        }

        int v_len = 0, f_len = 0;
        String[] para;

        while(file.hasNextLine()){
            para = (" " + file.nextLine()).split("\\h+");
            if(para[1].equals("v")) v_len++;
            if(para[1].equals("f")) f_len++;
        }
        file.close();
        v = new Vector[v_len];
        f = new int[f_len][3];
        mtl = new int[f_len];
        v_len = 0;
        f_len = 0;

        int mtl_idx = 0;
        while(file.hasNextLine()){
            para = (" " + file.nextLine()).split("\\h+");
            if(para.length < 2 || para[1].startsWith("#")) continue;

            if(para[1].equals("usemtl")){
                mtl_idx = material.find(para[2]);
                if(mtl_idx == -1){
                    System.err.println("Warning: material " + para[2] + " not found.");
                }
            }
            else if(para[1].equals("v")){
                // vertex
                v[v_len++] = new Vector(Double.parseDouble(para[2]), Double.parseDouble(para[3]), Double.parseDouble(para[4]), 1);
            }
            else if(para[1].equals("f")){
                // face
                // Assume a TinkerCAD object with 3 vertecies each face
                for(int i = 2; i < 5; i++){
                    f[f_len][i - 2] = Integer.parseInt(para[i]);
                }
                mtl[f_len] = mtl_idx;

                f_len++;
            }
        }

    }

    // world space transformation
    public void transform(Vector trail, Ray axle){
        // Note: length of the axle's direction vector determines the angle

        // theta - angle in radian counter-clockwise around the axle
        // u - normalized axle of rotation
        // c - position of the axle
        // d - displacement after the rotation
        double theta = axle.dir.mag;

        if(Math.abs(theta) < Consts.epsilon){
            translate(trail);
            return;
        }

        double ux = axle.dir.at(0) / theta;
        double uy = axle.dir.at(1) / theta;
        double uz = axle.dir.at(2) / theta;
        double cx = axle.pos.at(0);
        double cy = axle.pos.at(1);
        double cz = axle.pos.at(2);
        double dx = trail.at(0);
        double dy = trail.at(1);
        double dz = trail.at(2);

        double x11 = Math.cos(theta) + ux*ux*(1 - Math.cos(theta));
        double x12 = ux*uy*(1 - Math.cos(theta)) - uz*Math.sin(theta);
        double x13 = ux*uz*(1 - Math.cos(theta)) + uy*Math.sin(theta);
        double x21 = uy*ux*(1 - Math.cos(theta)) + uz*Math.sin(theta);
        double x22 = Math.cos(theta) + uy*uy*(1 - Math.cos(theta));
        double x23 = uy*uz*(1 - Math.cos(theta)) - ux*Math.sin(theta);
        double x31 = uz*ux*(1 - Math.cos(theta)) - uy*Math.sin(theta);
        double x32 = uz*uy*(1 - Math.cos(theta)) + ux*Math.sin(theta);
        double x33 = Math.cos(theta) + uz*uz*(1 - Math.cos(theta));

        // Translation matrix
        // use Rodrigues’ rotation formula combined with translations
        Matrix T = new Matrix(new double[][]{
                {x11, x12, x13, (1 - Math.cos(theta)) * (ux*uz*cy - uy*uz*cx) + uz*Math.sin(theta)*cy - uy*Math.sin(theta)*cz + cx + dx},
                {x21, x22, x23, (1 - Math.cos(theta)) * (uy*uz*cx - ux*uz*cy) + uz*Math.sin(theta)*cx - ux*Math.sin(theta)*cz + cy + dy},
                {x31, x32, x33, (1 - Math.cos(theta)) * (ux*uy*cz - ux*uz*cy) + uy*Math.sin(theta)*cz - uz*Math.sin(theta)*cx + cz + dz},
                {0, 0, 0, 1}
        });
        Matrix R = new Matrix(new double[][]{
                {x11, x12, x13, 0},
                {x21, x22, x23, 0},
                {x31, x32, x33, 0},
                {0, 0, 0, 1}
        });

        T_world = T.dot(T_world);
        pos = T.dot(pos);
    }

    public void translate(Vector trail){
        Matrix T = new Matrix(new double[][]{
                {1, 0, 0, trail.at(0)},
                {0, 1, 0, trail.at(1)},
                {0, 0, 1, trail.at(2)},
                {0, 0, 0, 1}
        });

        T_world = T.dot(T_world);
        pos = T.dot(pos);
    }

    public void cd(Vector destination){
        translate(destination.subtract(pos));
    }

}
