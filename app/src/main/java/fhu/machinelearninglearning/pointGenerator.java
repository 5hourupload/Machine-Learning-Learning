package fhu.machinelearninglearning;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class pointGenerator {
    private static Random random = new Random();
    private static List<point> points = new LinkedList<>();
    public static List<point> generateRandomPoints(int total, int height, int width){
        for(int i = 0; i < total; i++){
            points.add(new point(random.nextInt(width), random.nextInt(height)));
        }
        return points;
    }

    public static List<point> generateClusters(int total, int height, int width){
        //Pick three random cluster points.
        int maxClusterRadius = (int)(0.2 * width);
        int clusterUpperBound = width - maxClusterRadius;
        //Select X coordinates.
        int A_ClusterX = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
        int B_ClusterX = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
        int C_ClusterX = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
        //Select Y coordinates.
        int A_ClusterY, B_ClusterY, C_ClusterY;

        int clusterSize = total/3;
        for(int i = 0; i < clusterSize; i++){
            points.add(new point(random.nextInt(width), random.nextInt(height)));
        }
        return points;
    }
}
