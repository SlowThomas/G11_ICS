package Unit_5;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Bonus_Summarizer {
    public static void main(String[] args) throws IOException {
        Scanner stdin = new Scanner(System.in);

        String year;
        System.out.print("Year to retrieve data from: ");
        year = stdin.nextLine();

        Scanner fin = new Scanner(new File(year + "_sales_summary.txt"));
        PrintWriter fout = new PrintWriter(new File(year + "_ bonus_summary.txt"));


        double[] employee = new double[10000];
        int[] order = new int[10000];
        int num_employee = 0;

        fin.nextLine();fin.nextLine();

        int id;
        double amount;
        while(fin.hasNext()){
            id = Integer.parseInt(fin.next());
            amount = Double.parseDouble(fin.next());
            if(employee[id] == 0) order[num_employee++] = id;
            employee[id] += amount;
        }
        fin.close();

    }
}
