package engine;
import java.util.ArrayList;

public class Scene {
    // Consider:
    // Light intensity
    // Z buffer as a pair (adjusted color, depth)
    // rotation by re-shifting rotation axis to a primary axis

    // TODO: raw rendering - just use the rendering technique from Dash
    // TODO: partition into different sections



    private int[] size = new int[3];
    private int section_size;
    private ArrayList<Object>[][][] sections;
    public Scene(int width, int length, int height, int view_distance, Object[] obj_list){
        section_size = 2 * view_distance; // use midline to decide the next section to load
        // sections may cover more space than the whole scene
        sections = new ArrayList[width / section_size + 1][length / section_size + 1][height / section_size + 1];
        for(Object obj : obj_list){
            int i = (int)(obj.pos.at(0) - width / 2) / section_size;
            int j = (int)(obj.pos.at(1) - length / 2) / section_size;
            int k = (int)(obj.pos.at(2) - height / 2) / section_size;
            sections[i][j][k].add(obj);
        }

    }

    // Surface normal: point out or point in decides color and visibility
}
