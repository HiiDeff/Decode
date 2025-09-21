package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.localization.Localizer;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.AutoStates;
import org.firstinspires.ftc.teamcode.pedropathing.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.limelight.LimelightAprilTagDetector;

public abstract class RobotBase extends MecanumDrive {

    protected final HardwareMap hardwareMap;

    protected final AutoStates autoStates;


    //VISION
    public final Limelight3A limelight;
    private final LimelightAprilTagDetector aprilTagDetector;

    //SENSORS
    public final ArtifactState artifactState;

    public final RevColorSensorV3 artifactPos1;
    public final RevColorSensorV3 artifactPos2;
    public final RevColorSensorV3 artifactPos3;

    public RobotBase(HardwareMap hardwareMap, FollowerConstants followerConstants, MecanumConstants driveConstants, Localizer localizer, AutoStates autoStates) {
        super(hardwareMap,
                followerConstants,
                driveConstants,
                localizer
        );
        this.hardwareMap = hardwareMap;
        this.autoStates = autoStates;


        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        //get hardware


        //SENSORS
        artifactPos1 = hardwareMap.get(RevColorSensorV3.class, "pos1Color");
        artifactPos2 = hardwareMap.get(RevColorSensorV3.class, "pos2Color");
        artifactPos3 = hardwareMap.get(RevColorSensorV3.class, "pos3Color");
        artifactState = new ArtifactState(autoStates, this);

        //VISION
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        aprilTagDetector = new LimelightAprilTagDetector(limelight);
    }

    public void updateArtifactState() {
        artifactState.update();
    }

    public void setLimelightAprilTagPipeline(){
        limelight.pipelineSwitch(9);
    }

    public int getMotif(){
        return aprilTagDetector.checkAprilTags();
    }

    public void updateEverything() {
        update();
        aprilTagDetector.updateLimelight();
    }
}