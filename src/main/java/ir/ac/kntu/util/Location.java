package ir.ac.kntu.util;

import java.awt.*;

public interface Location {
    Point[][] location = new Point[10][10];
    default void setLocation(int x, int y) {
        location[x][y] = new Point(x, y);
    }
}
