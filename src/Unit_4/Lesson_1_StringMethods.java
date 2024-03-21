package Unit_4;

public class Lesson_1_StringMethods {
    public static void main(String[] args) {
        // The following list of methods are the only
        // acceptable methods you're allowed to use
        // for your assignments/assessments

        String school = "Markville SS!";

        // 1) toUpperCase()
        //    This method returns a copy of the string, but with
        //    all lower cases converted to upper cases
        System.out.println(school.toUpperCase());

        // 1) toLowerCase()
        //    This method returns a copy of the string, but with
        //    all lower cases converted to upper cases
        System.out.println(school.toLowerCase());

        // 3) charAt(#)
        //    This method returns the character of a string at position #
        System.out.println(school.charAt(0));
        System.out.println(school.charAt(5));
        System.out.println(school.charAt(11));
        System.out.println(school.charAt(9));
        // Unlike Python, the command:
        // System.out.println(school.charAt(-2));
        // does not work. The index cannot be negative

        // 4) indexOf(s)
        //    This method returns the index of the first left occurrence
        //    of the string/character s in the string
        //    If no such string/character can be found in the string
        //    a value of -1 will be returned
        System.out.println(school.indexOf("v"));
        System.out.println(school.indexOf("T"));
        System.out.println(school.indexOf("ill"));
        System.out.println(school.indexOf("chow"));
        System.out.println(school.indexOf("S"));
    }
}
