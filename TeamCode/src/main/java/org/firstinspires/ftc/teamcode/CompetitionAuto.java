package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="compAuto", group="none")


public class CompetitionAuto extends AutoBase {
    Servo grabber;
    DcMotor lift;
    @Override

    public void runOpMode() {
        initialize();
        DcMotor lift  = hardwareMap.dcMotor.get("lift");
        Servo grabber = hardwareMap.servo.get("grabber");
        double y;
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(7, 0, 0);

        drive.setPoseEstimate(startPose);

        TrajectorySequence firstScore = drive.trajectorySequenceBuilder(startPose)
                .splineTo(new Vector2d(39.00, 0), Math.toRadians(-14),
                        SampleMecanumDrive.getVelocityConstraint(50, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(20f))
                .splineTo(new Vector2d(59, -3), Math.toRadians(-45.0),
                        SampleMecanumDrive.getVelocityConstraint(40, 5, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(15f))

                .build();

        Trajectory firstPickup = drive.trajectoryBuilder(firstScore.end().plus(new Pose2d(0.75, 0.75, Math.toRadians(0))))
                        .lineToSplineHeading(new Pose2d(60, 20, Math.toRadians(90.00)))
                .build();
        Trajectory secondScore = drive.trajectoryBuilder(firstPickup.end().plus(new Pose2d(0, 5, Math.toRadians(0))))
                        .lineToSplineHeading(new Pose2d(61, -5, Math.toRadians(-45)))
                                .build();




        grabber.setPosition(1f);
        waitForStart();

        if(isStopRequested()) return;
        grabber.setPosition(1f);
        this.sleep(250);
        drive.followTrajectorySequence(firstScore);
        setStage(10);
        DistanceScore(0.15f);
        grabber.setPosition(0.6f);
        setStage(5);
        drive.followTrajectory(firstPickup);
        StripeAlign(0.3f);
        grabber.setPosition(1f);
        drive.followTrajectory(secondScore);
        setStage(10);
        DistanceScore(0.15f);
        drive.turn(Math.toRadians(45));


    }
}