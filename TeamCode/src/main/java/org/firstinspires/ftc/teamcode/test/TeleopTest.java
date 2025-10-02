package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.GamePad;

@TeleOp(name = "Test TeleOp", group = "Test")
public class TeleopTest extends LinearOpMode {

    public static double FLYWHEEL_POWER = 0.85, FLYWHEEL_DELTA = 0.005;
    public static double INTAKE_POWER = 0.5;

    public static double PIVOT_POS = 0.65, PIVOT_DELTA = 0.005;
    public static double KICK_DOWN_POS = 0.2;
    public static double KICK_UP_POS = 0;
    public static boolean kickerUp, flywheelActive;

    GamePad gp1, gp2;

    public DcMotor flywheelLeft, flywheelRight;
    public DcMotor intakeMotor;


    public Servo kicker;
    public Servo flywheelPivot;
    public Servo pusher;

    @Override
    public void runOpMode() throws InterruptedException {

        flywheelLeft = hardwareMap.get(DcMotor.class, "flyL");
        flywheelRight = hardwareMap.get(DcMotor.class, "flyR");
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");

        kicker = hardwareMap.get(Servo.class, "kicker");
        flywheelPivot = hardwareMap.get(Servo.class, "pivot");
        pusher = hardwareMap.get(Servo.class, "push");

        waitForStart();

        while (opModeIsActive()) {

            if(gp1.leftStickY()>0.3){
                PIVOT_POS+=PIVOT_DELTA;
                PIVOT_POS = Math.min(PIVOT_POS, 1.0);
            } else if(gp1.leftStickY()<-0.3) {
                PIVOT_POS-=PIVOT_DELTA;
                PIVOT_POS = Math.max(PIVOT_POS, 0.0);
            }
            flywheelPivot.setPosition(PIVOT_POS);

            if(-gp1.rightStickY()>0.3){
                FLYWHEEL_POWER+=FLYWHEEL_DELTA;
                FLYWHEEL_POWER = Math.min(FLYWHEEL_POWER, 1.0);
            } else if(-gp1.rightStickY()<-0.3) {
                FLYWHEEL_POWER-=FLYWHEEL_DELTA;
                FLYWHEEL_POWER = Math.max(FLYWHEEL_POWER, 0.0);
            }

            if(gp1.onceX()) flywheelActive = !flywheelActive;
            if(flywheelActive) {
                flywheelLeft.setPower(FLYWHEEL_POWER);
                flywheelRight.setPower(FLYWHEEL_POWER);
            } else {
                flywheelLeft.setPower(0);
                flywheelRight.setPower(0);
            }

            if(gp1.rightTrigger()>0.3) {
                intakeMotor.setPower(INTAKE_POWER);
            } else intakeMotor.setPower(0);

            if(gp1.rightBumper()) {
                pusher.setPosition(1.0);
            } else pusher.setPosition(0.0);

            if(gp1.onceA()) {
                kickerUp = !kickerUp;
            }
            kicker.setPosition(kickerUp? KICK_UP_POS:KICK_DOWN_POS);

        }
    }
}
