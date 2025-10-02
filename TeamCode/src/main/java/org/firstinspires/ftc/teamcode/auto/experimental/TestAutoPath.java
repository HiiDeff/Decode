package org.firstinspires.ftc.teamcode.auto.experimental;//package org.firstinspires.ftc.teamcode.auto.experimental;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.auto.AutoBase;
import org.firstinspires.ftc.teamcode.task.RuntimeDrivingTask;
import org.firstinspires.ftc.teamcode.task.SeriesTask;
import org.firstinspires.ftc.teamcode.task.SleepTask;
import org.firstinspires.ftc.teamcode.task.Task;

import kotlin.math.UMathKt;

public abstract class TestAutoPath extends AutoBase {

    @Override
    protected Task createStartTask() {
        state = AutoState.START;
        SeriesTask task = new SeriesTask();
        task.add(
                new RuntimeDrivingTask(
                        robot,
                        builder -> {
                            return builder
                                    .addPath(
                                            new BezierCurve(
                                                    new Pose(0,0),
                                                    new Pose(63.82, 19.15)
                                            )
                                    )
                                    .setLinearHeadingInterpolation(Math.toRadians(0), 0.993)
                                    .build();
                        }
                )
        );
        task.add(new SleepTask(3000));
        task.add(new RuntimeDrivingTask(
                robot,
                builder -> {
                    return builder.addPath(
                                    new BezierCurve(
                                            new Pose(63.82, 19.15),
                                            new Pose(50.34, 32.63)
                                    )
                            )
                            .setLinearHeadingInterpolation(0.993, 2.3508)
                            .build();
                }));
        task.add(new SleepTask(3000));
        task.add(new RuntimeDrivingTask(
                robot,
                builder -> {
                    return builder.addPath(
                                    new BezierCurve(
                                            new Pose(50.34, 32.63),
                                            new Pose(0,0)
                                    )
                            )
                            .setLinearHeadingInterpolation(Math.toRadians(2.3508), Math.toRadians(0))
                            .build();
                }));
        task.add(new SleepTask(1000));
        return task;
    }

    @Override
    protected Task createCycleTask() {
        state = AutoState.CYCLE;
        int cycleNumber = autoStates.getCycleNumber();

        SeriesTask task = new SeriesTask();
        task.add(
                new RuntimeDrivingTask(
                        robot,
                        builder -> {
                            Pose pose = getIntake1Pose();
                            if(cycleNumber==2) pose = getIntake2Pose();
                            else if(cycleNumber==3) pose = getIntake3Pose();
                            return builder
                                    .addPath(
                                        new BezierCurve(
                                                robot.getPose(),
                                                pose
                                        )
                                    )
                                    .setLinearHeadingInterpolation(robot.getHeading(), pose.getHeading())
                                    .addPath(
                                            new BezierCurve(
                                                    pose,
                                                    new Pose(pose.getX(), pose.getY()+17)
                                            )
                                    )
                                    .setConstantHeadingInterpolation(pose.getHeading())
                                    .build();
                        }
                )
        );
        task.add(
                new RuntimeDrivingTask(
                        robot,
                        builder -> {
                            Pose pose = getShoot2Pose();
                            if(cycleNumber==2) pose = getShoot3Pose();
                            else if(cycleNumber==3) pose = getShoot4Pose();
                            return builder
                                    .addPath(
                                            new BezierCurve(
                                                    robot.getPose(),
                                                    pose
                                            )
                                    )
                                    .setLinearHeadingInterpolation(robot.getHeading(), pose.getHeading())
                                    .build();
                        }
                )
        );
        task.add(new SleepTask(1000));
        return task;
    }

    @Override
    protected Task createFinishTask() {
        state = AutoState.FINISH;
        return new SeriesTask();
    }

    protected abstract Pose getShoot1Pose();
    protected abstract Pose getIntake1Pose();
    protected abstract Pose getShoot2Pose();
    protected abstract Pose getIntake2Pose();
    protected abstract Pose getShoot3Pose();
    protected abstract Pose getIntake3Pose();
    protected abstract Pose getShoot4Pose();

}
