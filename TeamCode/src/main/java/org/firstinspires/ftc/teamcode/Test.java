package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="test13", group="none")


public class Test extends AutoBase {
    Servo grabber;
    DcMotor lift;
    @Override

    public void runOpMode() {
        initialize();
        DcMotor lift  = hardwareMap.dcMotor.get("lift");
        Servo grabber = hardwareMap.servo.get("grabber");
        DigitalChannel touch = hardwareMap.get(DigitalChannel.class, "touch");

        // set the digital channel to input.

        TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(7.00, 0, Math.toRadians(0.00)))
                .addDisplacementMarker(()->{
                    grabber.setPosition(1f);
                    this.sleep(750);
                })
                .lineToSplineHeading(new Pose2d(63, 0, Math.toRadians(-45.00)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(20))

                .addDisplacementMarker(()->{
                    setStage(10);
                    //DistanceScore(0.18f);
                    grabber.setPosition(0.6f);
                    setStage(5);
                })
                .lineToSplineHeading(new Pose2d(60.00, 24.00, Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(20))

                .addDisplacementMarker(()->{
                    StripeStrafe(0.18f);
                })
                .lineTo(new Vector2d(60.00,26.00))
                .addDisplacementMarker(()->{
                    grabber.setPosition(1);
                })
                .waitSeconds(0.75f)

                .lineToSplineHeading(new Pose2d(61, -2, Math.toRadians(-45)))
                .UNSTABLE_addTemporalMarkerOffset(1, ()->{
                    setStage(10);
                })
                .addDisplacementMarker(()->{
                    grabber.setPosition(0.6f);
                })

                .build();
        waitForStart();

        if(isStopRequested()) return;
        drive.setPoseEstimate(traj.start());
        drive.followTrajectorySequence(traj);
    }
}