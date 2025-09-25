package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.common.AutoStates;
import org.firstinspires.ftc.teamcode.util.objectdetector.HSV;
import org.firstinspires.ftc.teamcode.util.objectdetector.ImageProcessor;

public class ArtifactState {

    protected AutoStates autoStates;
    public static double MIN_DIST_WITH_SAMPLE = 0.7;
    private RobotBase robot;

    public ArtifactState(AutoStates autoStates, RobotBase robot) {
        this.autoStates = autoStates;
        this.robot = robot;
    }

    public void update() {
        ArtifactStateColor tempArtifactColor1 = ArtifactStateColor.EMPTY;
        ArtifactStateColor tempArtifactColor2 = ArtifactStateColor.EMPTY;
        ArtifactStateColor tempArtifactColor3 = ArtifactStateColor.EMPTY;

        double dist1 = robot.artifactPos1.getDistance(DistanceUnit.INCH);
        double dist2 = robot.artifactPos2.getDistance(DistanceUnit.INCH);
        double dist3 = robot.artifactPos3.getDistance(DistanceUnit.INCH);
        if (dist1 < MIN_DIST_WITH_SAMPLE) {
            tempArtifactColor1 = toHDColor(robot.artifactPos1.getNormalizedColors());
        }
        if (dist2 < MIN_DIST_WITH_SAMPLE) {
            tempArtifactColor2 = toHDColor(robot.artifactPos2.getNormalizedColors());
        }
        if (dist3 < MIN_DIST_WITH_SAMPLE) {
            tempArtifactColor3 = toHDColor(robot.artifactPos3.getNormalizedColors());
        }
        updateInternal(tempArtifactColor1, tempArtifactColor2, tempArtifactColor3);
    }
    private synchronized void updateInternal(ArtifactStateColor tempArtifactColor1, ArtifactStateColor tempArtifactColor2, ArtifactStateColor tempArtifactColor3) {
        autoStates.updateArtifactPos(tempArtifactColor1, tempArtifactColor2, tempArtifactColor3);
    }

    private ArtifactStateColor toHDColor(NormalizedRGBA color) {
        HSV hsv = ImageProcessor.ColorToHsv(color);
        if (hsv.h>110&&hsv.h<190) {
            return ArtifactStateColor.GREEN;
        }
        if (ImageProcessor.isPurple(hsv)) {
            return ArtifactStateColor.PURPLE;
        }
        return ArtifactStateColor.EMPTY;
    }

    public enum ArtifactStateColor {
        GREEN,
        PURPLE,
        EMPTY
    }
}
