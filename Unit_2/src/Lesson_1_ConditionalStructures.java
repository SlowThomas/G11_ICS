public class Lesson_1_ConditionalStructures {
    public static void main(String[] args){
        // Without running the program
        // Determine what will be displayed in the console

        // Question 1
        System.out.println("--- Question 1 ---");

        int a = 3;
        int b = 4;

        // Question: How many conditional structures are there in question 1?
        // Answer:   TWO

        if(a >= b){ // false
            System.out.println("A");
        }
        else if(a + b != b + a){ // false
            System.out.println("B");
        }
        else{
            System.out.println("C"); // <- displayed
        }
        if(Math.pow(a,2) >= Math.pow(b,3)){ // false
            System.out.println("D");
        }
        else{
            System.out.println("E"); // <- displayed
        }

        // Question 2
        System.out.println("\n--- Question 2 ---");

        double c = 5.3;
        int d = 9;
        int e = 5;


        // Question: How many conditional structures are there in this question?
        // Answer:   FOUR
        if(d % e < c % e){ // false
            System.out.println("A");
        }
        if(c % 2 >= c % 3){ // false
            System.out.println("B");
        }
        else if(d%2 != e%2){ // false
            System.out.println("C");
        }
        if(c%2 != c%3){ // true
            System.out.println("D"); // <- displayed
        }
        if(d-c > d / e){ // true
            System.out.println("E"); // <- displayed
        }
        else{
            System.out.println("F");
        }
    }
}
