package engine;

public class Screen {
    // MacBook Air display: 13.3-inch 1440 × 900
    public static int width = 1440;
    public static int height = 900;
    public static double size = 13.3; // in inch
    public static int ppi = (int)(Math.sqrt(width * width + height * height) / size);

    public static double distance = 30; // in inch
    // taken the midpoint in the range of recommended eye-screen distance
}
