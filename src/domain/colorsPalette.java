package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class colorsPalette implements Serializable{
    private static final long serialVersionUID = 1L; 
    public static final Color PASTEL_PINK = new Color(255, 182, 193);
    public static final Color PASTEL_BLUE = new Color(173, 216, 230);
    public static final Color PASTEL_GREEN = new Color(152, 251, 152);
    public static final Color PASTEL_YELLOW = new Color(255, 255, 204);
    public static final Color PASTEL_PURPLE = new Color(221, 160, 221);

    public static final Color VIBRANT_RED = new Color(255, 69, 0);
    public static final Color VIBRANT_BLUE = new Color(0, 0, 255);
    public static final Color VIBRANT_GREEN = new Color(0, 255, 0);
    public static final Color VIBRANT_YELLOW = new Color(255, 255, 0);
    public static final Color VIBRANT_PURPLE = new Color(128, 0, 128);

    public static List<Color> getPastelColors() {
        return Arrays.asList(PASTEL_PINK, PASTEL_BLUE, PASTEL_GREEN, PASTEL_YELLOW, PASTEL_PURPLE);
    }

    public static List<Color> getVibrantColors() {
        return Arrays.asList(VIBRANT_RED, VIBRANT_BLUE, VIBRANT_GREEN, VIBRANT_YELLOW, VIBRANT_PURPLE);
    }
}

