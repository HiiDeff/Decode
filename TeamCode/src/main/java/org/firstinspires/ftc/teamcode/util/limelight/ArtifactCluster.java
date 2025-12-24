package org.firstinspires.ftc.teamcode.util.limelight;

public class ArtifactCluster {

    public Coordinates center;
    public double radius;
    public ArtifactCluster(Coordinates center, double radius){
        this.center = center;
        this.radius = radius;
    }

    public String toString() {
        return "("+center.getX()+" "+center.getY()+" "+center.getZ()+" "+radius+")";
    }

    public Coordinates getCenter(){
        return center;
    }

    public double getX(){
        return center.getX();
    }

    public double getY(){
        return center.getY();
    }

    public double getZ(){
        return center.getZ();
    }

    public double getRadius() {
        return radius;
    }

}
