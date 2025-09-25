package be.ecam.basics.exercises;

public class ImageScaler {
    public static int[] scale(int width, int height, int num, int den) {
        if (width < 0 || height < 0)
            throw new IllegalArgumentException();
        if (den == 0)
            throw new IllegalArgumentException();
        int w = (int) Math.round((double) (width * num) / den);
        int h = (int) Math.round((double) (height * num) / den);
        if (w < 0)
            w = 0;
        if (h < 0)
            h = 0;
        return new int[] { w, h };
    }
}
