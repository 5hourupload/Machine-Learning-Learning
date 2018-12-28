package fhu.machinelearninglearning;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class pointGenerator {
    private static Random random = new Random();
    private static List<point> points = new LinkedList<>();
    public static List<point> generateRandomPoints(int total, int length){
        points.clear();
        for(int i = 0; i < total; i++){
            points.add(new point(random.nextInt(length), random.nextInt(length)));
        }
        return points;
    }

    public static List<point> generateClusteredPoints(int total, int length){
        points.clear();
        //Pick three random cluster points.
        int maxClusterRadius = (int)(0.2 * length);
        int clusterUpperBound = length - maxClusterRadius;
        //Select X coordinates.
        //The maximum cluster radius is a constant calculated based on the img view size.
        //That constant serves as the lower bound for all the random centroid calculations.
        int A_ClusterX = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
        int B_ClusterX = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
        int C_ClusterX = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
        //Select Y coordinates.
        int A_ClusterY = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
        int B_ClusterY = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
        int C_ClusterY = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;

        int clusterSize = total/3;
        //Cluster point calculations
        for(int i = 0; i < clusterSize; i++){
            //A cluster
            int AX_max = A_ClusterX + maxClusterRadius;
            int AX_min = A_ClusterX - maxClusterRadius;

            int AY_max = A_ClusterY + maxClusterRadius;
            int AY_min = A_ClusterY - maxClusterRadius;

            int AX = random.nextInt(AX_max - AX_min) + AX_min;
            int AY = random.nextInt(AY_max - AY_min) + AY_min;
            points.add(new point(AX,AY));

            //B cluster
            int BX_max = B_ClusterX + maxClusterRadius;
            int BX_min = B_ClusterX - maxClusterRadius;

            int BY_max = B_ClusterY + maxClusterRadius;
            int BY_min = B_ClusterY - maxClusterRadius;

            int BX = random.nextInt(BX_max - BX_min) + BX_min;
            int BY = random.nextInt(BY_max - BY_min) + BY_min;
            points.add(new point(BX,BY));

            //C cluster
            int CX_max = C_ClusterX + maxClusterRadius;
            int CX_min = C_ClusterX - maxClusterRadius;

            int CY_max = C_ClusterY + maxClusterRadius;
            int CY_min = C_ClusterY - maxClusterRadius;

            int CX = random.nextInt(CX_max - CX_min) + CX_min;
            int CY = random.nextInt(CY_max - CY_min) + CY_min;
            points.add(new point(CX,CY));
        }
        return points;
    }

    public static List<point> generateSmileyFace(int total, int length){
        
        return points;
    }
}
