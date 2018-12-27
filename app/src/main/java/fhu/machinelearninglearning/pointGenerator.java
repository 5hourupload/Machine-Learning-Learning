package fhu.machinelearninglearning;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class pointGenerator {
    public static List<point> generatePoints(int total, int height, int width){
        Random random = new Random();
        List<point> points = new LinkedList<>();
        for(int i = 0; i < total; i++){
            points.add(new point(random.nextInt(width), random.nextInt(height)));
        }
        return points;
    }
}
