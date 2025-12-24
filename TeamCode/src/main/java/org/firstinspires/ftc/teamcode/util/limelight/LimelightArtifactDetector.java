package org.firstinspires.ftc.teamcode.util.limelight;

import android.util.Log;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Config
public class LimelightArtifactDetector extends LimelightProcessorBase {

    protected double[] pythonResult = {0};
    protected List<ArtifactCluster> sortedClusters;

    //in degrees
    public static double HORIZONTAL_THRESHOLD_MIN = 10.0, HORIZONTAL_THRESHOLD_MAX = 170.0;
    public static double VERTICAL_THRESHOLD_MIN = 70.0, VERTICAL_THRESHOLD_MAX = 110.0;
    public static double TURRET_DIST_FROM_ROBOT_CENTER = 6.0;
    public static double PIXEL_HEIGHT = 4.9 / 2; // Game manual section 9.9

    public LimelightArtifactDetector(Limelight3A limelight, LimelightConfig LLConfig) {
        super(limelight, LLConfig);
    }

    @Override
    public synchronized void update() {
        result = limelight.getLatestResult();
        if(result!=null) pythonResult = result.getPythonOutput();
        else pythonResult = new double[] {0};
        sortCoordinates();
    }

    public synchronized void sortCoordinates() {
        List<ArtifactCluster> normalizedCoordinates = pythonResultToNormalized(pythonResult);
        List<ArtifactCluster> realWorldCoordinates = normalizedToRealWorld(normalizedCoordinates);
        sortedClusters = sortbyRadius(realWorldCoordinates);
    }

    public synchronized LLResult getLLResult() {
        return result;
    }

    // Use normalized because of different resolutions
    protected Coordinates pixelCoordinatesToNormalizedCoordinates(Coordinates pixelCoordinates) {
//        double nx = 1.0/(LLConfig.resolutionX/2.0) * (pixelCoordinates.x - (LLConfig.resolutionX/2.0));
//        double ny = 1.0/(LLConfig.resolutionY/2.0) * (pixelCoordinates.y - (LLConfig.resolutionY/2.0)) - 1.0;
        double nx = 1.0/(LLConfig.resolutionX/2.0) * (pixelCoordinates.x - (LLConfig.resolutionX/2.0-0.5));
        double ny = 1.0/(LLConfig.resolutionY/2.0) * -(pixelCoordinates.y - (LLConfig.resolutionY/2.0-0.5));
        return new Coordinates(nx, ny, pixelCoordinates.getAngle());
    }

    // Prefer normalized over this
    protected Coordinates pixelCoordinatesToCenteredCoordinates(Coordinates pixelCoordinates) {
        double nx = (pixelCoordinates.x - (LLConfig.resolutionX/2.0-0.5));
        double ny = -(pixelCoordinates.y - (LLConfig.resolutionY/2.0-0.5));
        return new Coordinates(nx, ny);
    }

    //receives normalized pixel coordinates as input
    //returns x, y, z, and angle of object in inches and radians. Right (x), forwards (y), and up (z) are positive.

    protected double toRadians(double angDeg) {
        return Math.toRadians(angDeg);
    }
    private List<ArtifactCluster> sortbyRadius(List<ArtifactCluster> realWorldCoordinates) {
        Collections.sort(realWorldCoordinates, new closestComparator());
        return realWorldCoordinates;
    }
    private class closestComparator implements Comparator<ArtifactCluster> {
        @Override
        public int compare(ArtifactCluster o1, ArtifactCluster o2) {
            double comp = Math.abs(o1.getRadius()) - Math.abs(o2.getRadius());
            if(comp>0) return -1; // if o1 has greater radius, it should go first
            else if(comp<0) return 1;
            else return 0;
        }
    }
    private List<ArtifactCluster> normalizedToRealWorld(List<ArtifactCluster> normalizedCoordinates) {
        List<ArtifactCluster> realWorld = new ArrayList<>();
        for(ArtifactCluster i: normalizedCoordinates) {
            Coordinates temp = normalizedPixelToRealWorldCoordinates(i);
            realWorld.add(new ArtifactCluster(temp, i.getRadius()));
        }
        return realWorld;
    }
    private List<ArtifactCluster> pythonResultToNormalized(double[] pythonResult) {
        List<ArtifactCluster> normalized = new ArrayList<>();
        if(pythonResult.length == 0) return normalized;
        int length = (int)(pythonResult[0]);

        for(int i=1; i<length*3+1; i+=3){
            if(i+2>=pythonResult.length) break;
            Coordinates cur = new Coordinates(pythonResult[i], pythonResult[i+1], 0);
            double radius = pythonResult[i + 2];

            Log.e("cur "+i,""+cur.getY()+" "+cur.getX()+" "+radius);
            normalized.add(new ArtifactCluster(pixelCoordinatesToNormalizedCoordinates(cur), radius));
        }
        return normalized;
    }

    protected Coordinates normalizedPixelToRealWorldCoordinates(ArtifactCluster cluster) {
        Coordinates pixel = cluster.getCenter();
        //Relative to the Limelight:
        // y dist
        double objectAngleWithHorizontal =LLConfig.angleWithHorizontal + pixel.y * (LLConfig.verticalFOV/2.0); //pixel.y should be negative if object is lower than center of camera fov

//        double angularRadius =
//                (cluster.getRadius() / (LLConfig.resolutionY / 2.0))
//                        * (LLConfig.verticalFOV / 2.0);

        double yDistInch = (LLConfig.zOffset-PIXEL_HEIGHT) * Math.tan(toRadians(objectAngleWithHorizontal + 90));

        Log.e("adbug ll normalization to real world", pixel.x + " " + pixel.y + " " + pixel.y * (LLConfig.verticalFOV/2.0) + " " + objectAngleWithHorizontal + " " + Math.tan(toRadians(objectAngleWithHorizontal)) + " " + (LLConfig.zOffset-PIXEL_HEIGHT));

        yDistInch += (yDistInch-12.0) * LLConfig.errorScalerY;

        // x dist
//        double yzPlaneProjectedDist = Math.sqrt(yDistInch*yDistInch + (LLConfig.zOffset-PIXEL_HEIGHT)*(LLConfig.zOffset-PIXEL_HEIGHT)); // projected distance on the yz plane
//        double xAngleWithObject = pixel.x * (LLConfig.horizontalFOV/2.0);
//        double xDistInch = yzPlaneProjectedDist * Math.tan(toRadians(xAngleWithObject));

//        double xDistInch = yDistInch * Math.tan(toRadians(objectAngleWithHorizontal));
        double xAngle = pixel.x * (LLConfig.horizontalFOV / 2.0);
        double xDistInch = yDistInch * Math.tan(toRadians(xAngle));

        xDistInch += (xDistInch) * LLConfig.errorScalerX;


        // account for Limelight offset to center of robot:
        yDistInch += LLConfig.yOffset;
        xDistInch += LLConfig.xOffset;

//        Log.i("pixel x", ""+pixel.x);
//        Log.i("pixel y", ""+pixel.y);
//        Log.i("objectAngleWithHorizontal",""+objectAngleWithHorizontal);
//        Log.i("xAngleWithObject", ""+xAngleWithObject);

        Log.e("adbug ll normalization to real world", pixel.x + " " + pixel.y + " " + pixel.y * (LLConfig.verticalFOV/2.0) + " " + objectAngleWithHorizontal + " " + Math.tan(toRadians(objectAngleWithHorizontal)) + " " + (LLConfig.zOffset-PIXEL_HEIGHT));

        double angleToObject = Math.atan2(xDistInch, yDistInch);

//        Log.e("adbug angles",
//                "pixelY=" + pixel.y +
//                        " center=" + objectAngleWithHorizontal +
//                        " radius=" + angularRadius +
//                        " ground=" + (double)(objectAngleWithHorizontal - angularRadius)
//        );

        return new Coordinates(xDistInch, yDistInch, angleToObject);
    }

    public ArtifactCluster getLargestArtifactCluster(){
        if (sortedClusters == null || sortedClusters.isEmpty()){
            return null;
        }
        ArtifactCluster cluster = sortedClusters.get(0);
        return cluster;
    }
}
