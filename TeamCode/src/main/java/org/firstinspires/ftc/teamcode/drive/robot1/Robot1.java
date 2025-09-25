package org.firstinspires.ftc.teamcode.drive.robot1;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.RobotBase;
import org.firstinspires.ftc.teamcode.util.pid.PIDCoefficients;

@Config
public class Robot1 extends RobotBase {

    public static PIDCoefficients FLYWHEEL_VELOCITY_PID_COEFFICIENTS = new PIDCoefficients(0, 1.0,  0.00001, 0.0, 0.001,0); // new PIDCoefficients(0.3, 1.0, 0.006, 0.0, 0.0,0.16); //0.07 min ff, 0.37 = max ff, so do

    public static FollowerConstants FOLLOWER_CONSTANTS
            = new FollowerConstants()
            .mass(10.65)
            .forwardZeroPowerAcceleration(-34.85468883462399)
            .lateralZeroPowerAcceleration(-60.980555558410465)
            .useSecondaryDrivePIDF(false)/*true for 2 PIDs*/
            .translationalPIDFCoefficients(new PIDFCoefficients(0.3, 0.0, 0.006, 0.02))
            .headingPIDFCoefficients(new PIDFCoefficients(1.5, 0.0, 0.006, 0.02))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.032,0.0,0.000002,0.6,0.02));

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
            .xVelocity(77.70272226408711)
            .yVelocity(57.90664264348548);

    public static PinpointConstants PINPOINT_CONSTANTS
            = new PinpointConstants()
            .forwardPodY(-7.125)
            .strafePodX(3)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);

    public Robot1(HardwareMap hardwareMap) {
        super(hardwareMap);
    }

    public PIDCoefficients getVelocityPIDCoefficients(){
        return FLYWHEEL_VELOCITY_PID_COEFFICIENTS;
    }
}
