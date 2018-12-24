package fhu.machinelearninglearning;
import android.graphics.Paint;
import java.util.LinkedList;
import java.util.Random;

public class pointGenerator {
    public static LinkedList<point> generatePoints(int total, int height, int width){
        Random random = new Random();
        LinkedList<point> points = new LinkedList<>();
        Paint paint = new Paint();
        paint.setARGB(255,255,255,255);
        for(int i = 0; i < total; i++){
            points.add(new point(random.nextInt(width), random.nextInt(height), paint));
        }
        return points;
    }
}
