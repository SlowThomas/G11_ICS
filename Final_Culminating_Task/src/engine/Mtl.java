package engine;

import java.util.Scanner;
import java.io.File;

public class Mtl {
    private String[] lib;
    private int[] lib_map;
    private double[][] Ka, Kd;

    public Mtl(String filename){
        Scanner file;
        try{
            file = new Scanner(new File(filename));
        }
        catch(Exception e){
            System.err.printf("Cannot find material file: \"%s\"\n", filename);
            return;
        }

        String[] para;
        int lib_len = 0;
        while(file.hasNextLine()){
            para = (" " + file.nextLine()).split("\\h+");
            if(para[1].equals("newmtl")) lib_len++;
        }
        file.close();
        lib = new String[lib_len];
        lib_map = new int[lib_len];
        Ka = new double[lib_len][];
        Kd = new double[lib_len][];

        lib_len = 0;
        while(file.hasNextLine()){
            para = (" " + file.nextLine()).split("\\h+");
            if(para.length < 2 || para[1].startsWith("#")) continue;

            if(para[1].equals("newmtl")){
                lib[lib_len] = para[2];
                lib_map[lib_len] = lib_len;
                lib_len++;
            }
            // properties
            else if(para[1].equals("Ka")){
                Ka[lib_len] = new double[]{
                        Double.parseDouble(para[2]),
                        Double.parseDouble(para[3]),
                        Double.parseDouble(para[4])};
            }
            else if(para[1].equals("Kd")){
                Kd[lib_len] = new double[]{
                        Double.parseDouble(para[2]),
                        Double.parseDouble(para[3]),
                        Double.parseDouble(para[4])};
            }
        }
    }

    public double[] get_Ka(int idx){
        return Ka[idx];
    }

    public double[] get_Ka(String material_name){
        try {
            return Ka[find(material_name)];
        }
        catch (Exception e){
            System.err.printf("Material not found: %s\n", material_name);
            return new double[]{0, 0, 0};
        }
    }

    public double[] get_Kd(int idx){
        return Ka[idx];
    }

    public double[] get_Kd(String material_name){
        try {
            return Kd[find(material_name)];
        }
        catch (Exception e){
            System.err.printf("Material not found: %s\n", material_name);
            return new double[]{0, 0, 0};
        }
    }

    public int find(String material_name){
        for(int i = 0; i < lib.length; i++){
            if(lib[i].equals(material_name)) return i;
        }
        return -1;
    }
}
