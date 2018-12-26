package fhu.machinelearninglearning;
import android.graphics.Paint;

public class point {
    private int x,y;
    private Paint paint;
    point(int x, int y, Paint paint){
        this.x = x;
        this.y = y;
        this.paint = paint;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Paint getPaint() {
        return paint;
    }
}
