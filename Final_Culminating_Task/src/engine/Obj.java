package engine;
import algebra.*;
import java.util.Scanner;
import java.io.File;

public class Obj {

    private static class Consts{
        public static double epsilon = 1E-10; // another choice: 1E-14
    }

    // Consider:
    // rgb as fractions
    // https://www.youtube.com/watch?v=C8YtdC8mxTU

    public String mtl;
    public int[][] f;
    public Vector[] v;
    public Vector[] vt;
    public Vector[] vn;
    // public Vector[] vp;
    public Vector[] colo;
    public Vector pos = new Vector(0, 0, 0, 1);

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

    public Obj(String filename){
        Scanner file;
        try{
            file = new Scanner(new File(filename));
        } catch(Exception e){
            System.err.printf("Cannot find object file: \"%s\"\n", filename);
            return;
        }

        int v_len = 0, vt_len = 0, vn_len = 0, vp_len = 0, f_len = 0;
        String[] para;

        while(file.hasNextLine()){
            para = (" " + file.nextLine()).split("\\h+");
            if(para[1].equals("v")) v_len++;
            if(para[1].equals("vt")) vt_len++;
            if(para[1].equals("vn")) vn_len++;
            if(para[1].equals("vp")) vp_len++;
            if(para[1].equals("f")) f_len++;
        }
        file.close();
        v = new Vector[v_len];
        vt = new Vector[vt_len];
        vn = new Vector[vn_len];
        // vp = new Vector[vp_len];
        f = new int[f_len][];
        v_len = 0; vt_len = 0; vn_len = 0; vp_len = 0; f_len = 0;

        String material;
        // NOTE: weight parameter is not considered yet
        while(file.hasNextLine()){
            para = (" " + file.nextLine()).split("\\h+");
            if(para.length < 2 || para[1].startsWith("#")) continue;

            if(para[1].equals("usemtl")){
                material = para[1];
            }
            else if(para[1].equals("v")){
                // vertex
                v[v_len++] = new Vector(Double.parseDouble(para[2]), Double.parseDouble(para[3]), Double.parseDouble(para[4]), 1);
            }
            else if(para[1].equals("vt")){
                // texture vertex
                if(para.length == 2){
                    vt[vt_len++] = new Vector(Double.parseDouble(para[2]), 1);
                }
                else{
                    vt[vt_len++] = new Vector(Double.parseDouble(para[2]), Double.parseDouble(para[3]), 1);
                }
            }
            else if(para[1].equals("vn")){
                // normal
                vn[vn_len++] = new Vector(Double.parseDouble(para[2]), Double.parseDouble(para[3]), Double.parseDouble(para[4]), 1);
            }
            else if(para[1].equals("vp")){
                // Free-form geometry statement
            }
            else if(para[1].equals("f")){
                // face
                for(int i = 2; i < para.length; i++){
                    String[] features = para[i].split("/");
                    f[f_len] = new int[features.length];
                    for(int j = 0; j < features.length; j++){
                        if(features[j].isEmpty()){
                            continue;
                        }
                        f[f_len][j] = Integer.parseInt(features[j]) - 1;
                    }
                }

                //mtl[f_len] = material;

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
