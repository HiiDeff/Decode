package org.firstinspires.ftc.teamcode.drive_pp;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.RobotBase;
import org.firstinspires.ftc.teamcode.pedropathing.MecanumDrive;

@Config
public class TestRobot1 extends MecanumDrive {

    public static FollowerConstants FOLLOWER_CONSTANTS
            = new FollowerConstants()
            .mass(10.65)
            .forwardZeroPowerAcceleration(-36.23647244)
            .lateralZeroPowerAcceleration(-60.47752855)
            .useSecondaryDrivePIDF(false)/*true for 2 PIDs*/
            .translationalPIDFCoefficients(new PIDFCoefficients(0.025 ,0.0, 0.035, 0.025))
            .headingPIDFCoefficients(new PIDFCoefficients(1.8 ,0.0, 0.125, 0.025))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.01 ,0.0, 0.0005, 0.6, 0.03))
            .centripetalScaling(0.0007);

    public static MecanumConstants DRIVE_CONSTANTS
            = new MecanumConstants().rightFrontMotorName("rightFront")
            .rightRearMotorName("rightRear")
            .leftRearMotorName("leftRear")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .maxPower(1.0)
            .xVelocity(65.17997008)
            .yVelocity(49.76400684);

    public static PinpointConstants PINPOINT_CONSTANTS
            = new PinpointConstants()
            .forwardPodY(-7.25)
            .strafePodX(3)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);

    // path constraints (pedro)
    public static double T_VALUE_CONSTRAINT = 0.99,
            TIMEOUT_CONSTRAINT = 100,
            BRAKING_STRENGTH = 0.8,
            BRAKING_START = 1;

    public TestRobot1(HardwareMap hardwareMap) {
        super(hardwareMap,
                FOLLOWER_CONSTANTS,
                DRIVE_CONSTANTS,
                new PinpointLocalizer(hardwareMap, PINPOINT_CONSTANTS),
                getPathConstraints()
        );
    }

    public static PathConstraints getPathConstraints() {
        PathConstraints pc = new PathConstraints(T_VALUE_CONSTRAINT, TIMEOUT_CONSTRAINT, BRAKING_STRENGTH, BRAKING_START);
        PathConstraints.setDefaultConstraints(pc);
        return pc;
    }
}
