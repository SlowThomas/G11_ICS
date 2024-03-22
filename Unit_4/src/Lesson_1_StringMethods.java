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

        // 5) equals(s)
        //    This method returns true if the two strings are the same
        System.out.println(school.equals("chow"));
        System.out.println(school.equals(school));
        System.out.println("Hello".equals("hello")); // Casing matters!

        // 6) length()
        //    This method returns the number of characters in a string
        //    The number of characters is also the same as the last
        //    cursor position in a string
        System.out.println(school.length());

        // 7) lastIndexOf(s)
        //    This method returns the index in which the string/character s
        //    is found, from the right side
        //    If the string cannot be found, a value of -1 will be returned
        System.out.println(school.lastIndexOf("l"));
        System.out.println(school.lastIndexOf("ville"));
        System.out.println(school.lastIndexOf("chow"));

        // 8) substring(*)
        //    This returns a copy of the string, but starting from
        //    cursor position #
        System.out.println(school.substring(6));
        System.out.println(school.substring(13)); // empty string
        // System.out.println(school.substring(-3));
        // System.out.println(school.substring(14));

        // 9) substring(#1, #2)
        //    This method returns a copy of a string
        //    from cursor position #1 to #2
        //    Note: The difference between #2 and #1 is the number of
        //          characters in your resulting string
        System.out.println(school.substring(4, 9));
        System.out.println(school.substring(0, 4));
        System.out.println(school.substring(8, 8));
        System.out.println(school.substring(10, 12));
        // System.out.println(school.substring(12, 10));
        // System.out.println(school.substring(-3, 4));
        // System.out.println(school.substring(4, 14));
    }
}
