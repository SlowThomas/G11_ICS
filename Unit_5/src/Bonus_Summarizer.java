// TODO: Write Header Comments
// import necessary modules
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Bonus_Summarizer {
    public static void main(String[] args) throws IOException {
        // Standard input stream
        Scanner stdin = new Scanner(System.in);


        // Retrieve the target year
        String year;
        System.out.print("Year to retrieve data from: ");
        year = stdin.nextLine();


        // In/out stream for the input file and the output file
        Scanner fin;
        try { fin = new Scanner(new File(year + "_sales_summary.txt")); }
        catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
        PrintWriter fout = new PrintWriter(new FileWriter(year + "_bonus_summary.txt"));


        // Arrays for storing the data

        // employee[i] -> the total sales amount of the employee with id i.
        // order[i] -> the id of the i-th employee that appeared on the original list.
        double[] employee = new double[10000];
        int[] order = new int[10000];


        // Variables for storing key quantities

        // num_employee -> total number of employees
        // sales_total -> total sales amount by all employees
        // bonus_total -> total bonus awarded to all employees

        // id -> temporary variable, stores the id of the current entity
        // amount -> temporary variable, stores the sales amount of the current entity
        // bonus -> temporary variable, stores the bonus awarded for the current employee
        int     id, num_employee = 0;
        double  amount, bonus, sales_total = 0, bonus_total;


        // dump the first two lines of the input file
        fin.nextLine();fin.nextLine();


        // Go through each entity of the input file's list
        while(fin.hasNext()){
            // record the id
            id = Integer.parseInt(fin.next());
            // record the sales amount
            amount = Double.parseDouble(fin.next());
            // if the employee is not recorded before, add it to the list of employees
            if(employee[id] == 0) order[num_employee++] = id;
            // update the sales amount recorded for the employee
            employee[id] += amount;
        }
        fin.close();
        // Finish going through the input file


        // Print the header for the output file
        fout.printf("%-11s%15s%14s\n", "Employee ID", "Sales Total", "Bonus Total");
        fout.println("****************************************");
        // Go through each employee recorded
        for(int i = 0; i < num_employee; i++){
            // Record the information of the employee
            id = order[i];  amount = employee[id];  bonus = 0.05 * amount;
            // Print out the information to the output file
            fout.printf("%-11d%15.2f%14.2f\n", id, amount, bonus);

            // update the sales total
            sales_total += amount;
        }
        // calculate the total bonus rewarded
        bonus_total = sales_total * 0.05;
        // Fill the footer with information
        fout.println("****************************************");
        fout.printf("Number of Employees: %d\n", num_employee);
        fout.printf("Sales Total: %.2f\n", sales_total);
        fout.printf("Bonus Total: %.2f\n", bonus_total);

        // save the changes to the output file
        fout.close();


        // notify the user for the program's completion
        System.out.println("\nChanges have been applied");
    }
}
