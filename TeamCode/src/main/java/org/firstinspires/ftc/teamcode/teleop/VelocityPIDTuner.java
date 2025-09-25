package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.RobotBase;
import org.firstinspires.ftc.teamcode.drive.RobotFactory;

@Config
@TeleOp(name="Velocity PID Tuner")
public class VelocityPIDTuner extends LinearOpMode {
    public static double TARGET_VELOCITY = 0;

    private RobotBase robot;

    private MultipleTelemetry multipleTelemetry;

    @Override
    public void runOpMode() throws InterruptedException{

        multipleTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        robot = (RobotBase) RobotFactory.createRobot(hardwareMap);

        waitForStart();

        while (opModeIsActive()){
            multipleTelemetry.addData("Velocity", robot.getFlywheelVelocity());
            multipleTelemetry.addData("Target", TARGET_VELOCITY);

            multipleTelemetry.update();
            update();
        }
    }

    private void update(){
        robot.setFlywheelVelocity(TARGET_VELOCITY);
        robot.updateEverything();
    }
}
