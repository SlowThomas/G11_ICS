public class Lesson_2_printf1 {
    public static void main(String[] args){
        // Q: What does the 'f' in printf stands for?
        // A: 'f' stands for 'format'

        // Suppose you choose NOT to apply any formatting
        // printf and print are essentially the same

        // Note: Both print/printf do NOT skip line
        System.out.print("Hello\n");
        System.out.printf("Hello\n");

        // In this lesson we'll cover the first type of
        // formatting - PLACEHOLDER

        // 1st Example
        String name = "Chow";
        int grade = 11;
        String school = "Markville";
        System.out.println("My name is " + name + ", I'm a grade " + grade + " student at " + school + ".");
        System.out.printf("My name is %s, I'm a grade %d student at %s.\n", name, grade, school);

        // 2nd Example
        int a = 9;
        int b = 17;
        System.out.println(a + " x " + b + " = " + a*b);
        System.out.printf("%d x %d = %d\n", a, b, a*b);

        // 3rd Example
        String university = "U of T";
        double mark = 72.5;
        // I'm ___ at ___.
        // I want to go to ___
        // with a ___%
        System.out.println("I'm " + name + " at " + school + ". I want to go to " + university + " with a " + mark + "%.");
        System.out.printf("I'm %s at %s. I want to go to %s with a %f%%.\n", name, school, university, mark);
    }
}
