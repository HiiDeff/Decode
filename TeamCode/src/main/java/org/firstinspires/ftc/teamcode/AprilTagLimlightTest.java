package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.AutoStates;
import org.firstinspires.ftc.teamcode.drive.RobotBase;
import org.firstinspires.ftc.teamcode.drive.RobotFactory;
import org.firstinspires.ftc.teamcode.util.objectdetector.HSV;
import org.firstinspires.ftc.teamcode.util.objectdetector.ImageProcessor;

@TeleOp(name="AprilTagTest", group = "test")
public class AprilTagLimlightTest extends LinearOpMode {

    private RobotBase robot;

    AutoStates autoStates = new AutoStates();

    @Override
    public void runOpMode() throws InterruptedException {
        robot = (RobotBase) RobotFactory.createRobot(hardwareMap, autoStates);
        robot.setLimelightAprilTagPipeline();
        waitForStart();
        robot.limelight.start();
        while(opModeIsActive()){
            autoStates.setMotifNumber(robot.getMotif());
            telemetry.addData("motif: ", autoStates.getMotifNumber());
            HSV hsv = ImageProcessor.ColorToHsv(robot.artifactPos1.getNormalizedColors());
            telemetry.addData("pos1Color: ", autoStates.inventory[0]);
            telemetry.update();
            robot.updateEverything();
        }
    }
}
