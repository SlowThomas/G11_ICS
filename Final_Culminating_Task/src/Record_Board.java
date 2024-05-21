import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class Record_Board {
    public int[] scores = new int[3];
    public boolean[] survived = new boolean[3];

    public Record_Board() throws IOException {
        Scanner fin = new Scanner(new File("data/records.txt"));
        for(int i = 0; i < 3; i++){
            if(!fin.hasNextInt()) {
                scores[i] = -1;
                continue;
            }
            scores[i] = fin.nextInt();
            survived[i] = fin.nextBoolean();
        }
        fin.close();
    }

    public String[] get_scores(){
        String[] out = new String[3];
        for(int i = 0; i < 3; i++){
            if(scores[i] == -1) {
                out[i] = "-";
                continue;
            }
            out[i] = "" + scores[i];
        }
        return out;
    }

    public boolean record(int score, boolean survived){ // returns whether new record is achieved
        for(int i = 0; i < 3; i++){
            if(scores[i] < score || scores[i] == score && !this.survived[i] && survived){
                for(int j = 2; j > i; j--){
                    scores[j] = scores[j - 1];
                    this.survived[j] = this.survived[j - 1];
                }
                scores[i] = score;
                this.survived[i] = survived;
                return i == 0;
            }
        }
        return false;
    }

    public void save() throws IOException{
        PrintWriter fout = new PrintWriter(new FileWriter("data/record.txt"));
        for(int i = 0; i < 3 && scores[i] != -1; i++){
            fout.println(scores[i] + " " + survived[i]);
        }
        fout.close();
    }
}
