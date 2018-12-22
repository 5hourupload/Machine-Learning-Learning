package fhu.machinelearninglearning;

import android.graphics.Color;

public class point {
    private int x,y;
    private Color color;
    point(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
