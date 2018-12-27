package fhu.machinelearninglearning;
import android.graphics.Paint;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class pointGenerator {
    public static List<point> generatePoints(int total, int height, int width){
        Random random = new Random();
        List<point> points = new LinkedList<>();
        Paint paint = new Paint();
        paint.setARGB(255,0,255,0);
        for(int i = 0; i < total; i++){
            points.add(new point(random.nextInt(width), random.nextInt(height), paint));
        }
        return points;
    }
}
