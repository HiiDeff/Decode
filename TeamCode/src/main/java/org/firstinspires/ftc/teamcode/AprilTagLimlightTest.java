package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.RobotBase;
import org.firstinspires.ftc.teamcode.drive.RobotFactory;

@TeleOp(name="AprilTagTest", group = "test")
public class AprilTagLimlightTest extends LinearOpMode {

    private RobotBase robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = (RobotBase) RobotFactory.createRobot(hardwareMap);
        robot.setLimelightAprilTagPipeline();
        waitForStart();
        robot.limelight.start();
        while(opModeIsActive()){
            telemetry.addData("motif: ", robot.getMotif());
            telemetry.update();
        }
    }
}
