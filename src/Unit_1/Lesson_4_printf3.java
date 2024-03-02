package Unit_1;

public class Lesson_4_printf3 {
    public static void main(String[] args){
        // In the previous lesson, we've talked about:
        // 1) Reserved spaces
        // 2) Left/Right alignment

        String firstName = "Hei Nam";
        String lastName = "Chow";

        System.out.printf("%-15s%10s\n", firstName, lastName);

        // In this lesson, we'll cover 4 extra formatting that are NUMBERS specific
        // These formatting cannot apply to Strings (%s)

        // 1) Comma Separator
        //    e.g. 12345678 -> 12,345,678
        //    Comma separators add a comma every 3 digits, starting from the right
        //    Add the , symbol BEFORE the reserved spaces
        int a = 12345678;
        System.out.printf("%15d\n", a);
        System.out.printf("%,15d\n", a);
        System.out.printf("%,d\n", a);
        System.out.printf("%,-15d\n", a);
        System.out.printf("%-,15d\n", a); // same flags

        double b = 12345.6789;
        System.out.printf("%20f\n", b);
        System.out.printf("%,20f\n", b);
        System.out.printf("%,f\n", b);


        // 2) Leading Zeros
        //    Given a number is RIGHT aligned, and there are enough reserved spaces
        //    before the number is printed, the computer will fill those reserved
        //    spaces with zeros
        //    Add the 0 symbol before the reserved spaces
        System.out.printf("%15d\n", a);
        System.out.printf("%015d\n", a);
        System.out.printf("%20f\n", b);
        System.out.printf("%020f\n", b);
        System.out.printf("%0,20f\n", b);
        System.out.printf("%,020f\n", b); // same flags
        // System.out.printf("%-020f\n", b); <- This does NOT work. You cannot have left alignment
        //                                      and leading zeros (conflict).

        // 3) Positive Symbol
        //    Adds a positive symbol (+) in front of a number
        //    if the number is a positive number. No effect if the number is negative
        //    Add the + symbol BEFORE the reserved spaces
        int c = 34;
        double d = -56.78;
        System.out.printf("%+15d\n", c);
        System.out.printf("%0+15f\n", d);

        // Mixed and Match
        // You can mix and match the 3 flags that were listed however you want
        // as long as you don't have the "left alignment and leading zero" conflict
        System.out.printf("%0+,20d\n", a);
        System.out.printf("%,-+20f\n", b);


        // 4) Decimal Places (ONLY APPLIES FOR %f)
        // By default, %f ALWAYS display 6 decimal places
        // You can control the number of decimal places being displayed
        // If you want to display up to n decimal places
        // Add .n AFTER the reserved spaces
        double e = 2.7182818;

        System.out.printf("%20f\n", e);
        System.out.printf("%20.4f\n", e);
        System.out.printf("%20.10f\n", e);
        System.out.printf("%.2f\n", e);

        // The ULTIMATE mix-and-match
        System.out.printf("%+,-20.5f\n", e);

    }
}
