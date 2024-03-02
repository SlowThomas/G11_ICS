package Unit_1;

import java.util.Scanner;

public class U1A3 {
    static Scanner stdin = new Scanner(System.in);
    public static void main(String[] args){
        final String horizontal_line = "><><><><><><><><><><><><><><><><><><><><><><><><><";
        int apple, orange;
        double lychee, blueberry,
                apple_price, orange_price, lychee_price, blueberry_price,
                sub_total, total;
        System.out.printf("%s\n", horizontal_line);
        System.out.print("\t\t\t  Chow Chow Fruit Centre\n");
        System.out.printf("%s\n\n", horizontal_line);

        System.out.printf("%-35s", "Number of apples purchased:");
        apple = stdin.nextInt();
        System.out.printf("%-35s", "Number of oranges purchased:");
        orange = stdin.nextInt();
        System.out.printf("%-35s", "Amount of lychees purchased:");
        lychee = stdin.nextDouble();
        System.out.printf("%-35s", "Amount of blueberries purchased:");
        blueberry = stdin.nextDouble();
        System.out.println();

        apple_price = apple * 0.83;
        orange_price = orange * 0.75;
        lychee_price = lychee * 2.49;
        blueberry_price = blueberry * 1.42;
        sub_total = apple_price + orange_price + lychee_price + blueberry_price;
        total = sub_total * 1.13;
        System.out.println("----------------------Receipt---------------------\n");
        System.out.printf("%-32s%8s%10s\n\n", "Description", "Quantity", "Price");
        System.out.printf("%-32s%8d%10.2f\n", "Apples @ $0.83/each", apple, apple_price);
        System.out.printf("%-32s%8d%10.2f\n", "Oranges @ $0.75/each", orange, orange_price);
        System.out.printf("%-32s%8.2f%10.2f\n", "Lychees @ $2.49/lbs", lychee, lychee_price);
        System.out.printf("%-32s%8.2f%10.2f\n", "Blueberries @ $1.42/lbs", blueberry, blueberry_price);

        System.out.println();
        System.out.printf("%40s%10.2f\n", "Subtotal", sub_total);
        System.out.printf("%40s%10.2f\n\n", "H.S.T. (13%)", sub_total * 0.13);
        System.out.printf("%40s%10.2f\n\n", "Net Total", total);
        System.out.println("-------THANK YOU FOR SHOPPING WITH US TODAY!------");
    }
}