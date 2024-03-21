public class Lesson_3_printf2 {
    public static void main(String[] args){
        // Reserved Spacing
        String name = "Chow";
        String school = "Markville";

        System.out.printf("%8s%12s\n", name, school);

        // Example 2
        String someWords = "Marks";
        int mark = 98;

        System.out.printf("%10s%5d\n", someWords, mark);

        // Example 3
        String student1 = "Richard";
        String student2 = "Amrita";
        String student3 = "Matthew";
        int grade1 = 11;
        int grade2 = 10;
        int grade3 = 12;

        // By default, reserved spaces are RIGHT ALIGNED
        // If you wish for your data to be LEFT ALIGNED, add a negative
        // in front of the reserved spacing number
        System.out.printf("%-15s%-10s\n", "Student Name", "Grade");
        System.out.printf("%-15s%-10d\n", student1, grade1);
        System.out.printf("%-15s%-10d\n", student2, grade2);
        System.out.printf("%-15s%-10d\n", student3, grade3);

        // Q: What if texts are longer than reserved spaces?
        // A: The reserved spaces will be nullified
        System.out.printf("%4s%5s", "Computer", "Sci");
    }
}
