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
//        int A_ClusterX = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
//        int B_ClusterX = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
//        int C_ClusterX = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
        //Select Y coordinates.
//        int A_ClusterY = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
//        int B_ClusterY = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;
//        int C_ClusterY = random.nextInt(clusterUpperBound - maxClusterRadius) + maxClusterRadius;

        int A_ClusterX = (int) (length * .25);
        int A_ClusterY = (int) (length * .25);

        int B_ClusterX = (int) (length * .75);
        int B_ClusterY = (int) (length * .25);


        int C_ClusterX = (int) (length * .5);
        int C_ClusterY = (int) (length * .75);

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
        points.clear();
        //Generate border points
        //Find center
        int centerX = length/2;
        int centerY = length/2;
        int radius = (length/2) - 50;
//        double angleDifferential = 360/(total/2);
        for(int i = 0; i < 360; i++){
            int borderWidth= random.nextInt(50);
            int borderX = centerX + (int)((radius + borderWidth) * Math.cos(Math.toRadians(i)));
            int borderY = centerY + (int)((radius + borderWidth) * Math.sin(Math.toRadians(i)));
            points.add(new point(borderX,borderY));
        }
        //Generate smile points
        int minAngle = 205-180;
        int maxAngle = 335-180;
        for(int i = 0; i < 100; i++){
            int angle = random.nextInt(maxAngle - minAngle) + minAngle;
            int smileWidth = random.nextInt(20);
            int smileX = centerX + (int)((length/3 + smileWidth) * Math.cos(Math.toRadians(angle)));
            int smileY = centerY + (int)((length/3 + smileWidth) * Math.sin(Math.toRadians(angle)));
            points.add(new point(smileX, smileY));
        }
        //Generate eye points
        int leftEyeCenterX = length/3;
        int leftEyeCenterY = length/3;
        for (int i = 0; i <10; i ++)
        {
            points.add(new point(leftEyeCenterX + (int)(Math.random() * 30) - 15,
                    leftEyeCenterY + (int)(Math.random() * 30) - 15));
        }
        int rightEyeCenterX = length - length/3;
        int rightEyeCenterY = length/3;
        for (int i = 0; i <10; i ++)
        {
            points.add(new point(rightEyeCenterX + (int)(Math.random() * 30) - 15,
                    rightEyeCenterY + (int)(Math.random() * 30) - 15));
        }


        return points;
    }
}
