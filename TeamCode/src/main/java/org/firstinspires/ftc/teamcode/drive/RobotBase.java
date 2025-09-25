package org.firstinspires.ftc.teamcode.drive;

import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.localization.Localizer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedropathing.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.Utils;
import org.firstinspires.ftc.teamcode.util.pid.PIDCoefficients;

public abstract class RobotBase {

    protected final HardwareMap hardwareMap;

    public final DcMotorEx flywheelMotor;

    public final VelocityPID velocityPID;

    public RobotBase(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        //get hardware
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "flywheel");

        velocityPID = new VelocityPID(this, getVelocityPIDCoefficients());
    }

    public void updateEverything() {
        updatePIDs();
    }

    public void updatePIDs() {
        velocityPID.updatePID(getVelocityPIDCoefficients());
        setFlywheelPowerForPID(getVelocityPIDCoefficients().feedForward + velocityPID.getPower());
    }

    public abstract PIDCoefficients getVelocityPIDCoefficients();

    ///////////////////* FLYWHEEL UTILS *///////////////////


    public void setFlywheelPowerForPID(double power){
        flywheelMotor.setPower(Utils.clamp(power, -1, 1));
    }

    public void setFlywheelVelocity(double velocityTicksPerSecond){
        velocityPID.setTargetVelocity(velocityTicksPerSecond);
    }

    public double getFlywheelVelocity(){
        return (double)(flywheelMotor.getVelocity());
    }
}