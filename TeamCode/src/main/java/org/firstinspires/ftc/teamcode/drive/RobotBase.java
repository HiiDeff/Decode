package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.localization.Localizer;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedropathing.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.limelight.LimelightAprilTagDetector;

public abstract class RobotBase extends MecanumDrive {

    protected final HardwareMap hardwareMap;

    public final Limelight3A limelight;
    private final LimelightAprilTagDetector aprilTagDetector;

    public RobotBase(HardwareMap hardwareMap, FollowerConstants followerConstants, MecanumConstants driveConstants, Localizer localizer, LimelightAprilTagDetector aprilTagDetector) {
        super(hardwareMap,
                followerConstants,
                driveConstants,
                localizer
        );
        this.hardwareMap = hardwareMap;
        this.aprilTagDetector = aprilTagDetector;

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        //get hardware
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
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