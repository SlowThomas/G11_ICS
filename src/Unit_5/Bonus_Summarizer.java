package Unit_5;
// TODO: Write comments
import java.io.FileWriter;
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

        Scanner fin;
        try { fin = new Scanner(new File(year + "_sales_summary.txt")); }
        catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
        PrintWriter fout = new PrintWriter(new FileWriter(year + "_bonus_summary.txt"));


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

        double bonus, sales_total = 0, bonus_total;
        fout.printf("%-11s%15s%14s\n", "Employee ID", "Sales Total", "Bonus Total");
        fout.println("****************************************");
        for(int i = 0; i < num_employee; i++){
            id = order[i];  amount = employee[id];  bonus = 0.05 * amount;
            fout.printf("%-11d%15.2f%14.2f\n", id, amount, bonus);

            sales_total += amount;
        }
        bonus_total = sales_total * 0.05;
        fout.println("****************************************");
        fout.printf("Number of Employees: %d\n", num_employee);
        fout.printf("Sales Total: %.2f\n", sales_total);
        fout.printf("Bonus Total: %.2f\n", bonus_total);

        fout.close();

        System.out.println("\nChanges have been applied");
    }
}
