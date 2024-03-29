package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AutoBase;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="test", group="none")


public class AutoLeft extends AutoBase {
    Servo grabber;
    DcMotor lift;
    @Override

    public void runOpMode() {
        initialize();
        DcMotor lift  = hardwareMap.dcMotor.get("lift");
        Servo grabber = hardwareMap.servo.get("grabber");

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(750);
                })
                .addTemporalMarker(1.5, () -> {
                    lift.setPower(-1f);
                })
                .lineToSplineHeading(new Pose2d(55, -31, Math.toRadians(315)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                    telemetry.addLine("1");
                    setStage(5);
                    lift.setPower(-0.15f);
                    this.sleep(350);

                })
                .lineToSplineHeading(new Pose2d(51, 25.5, Math.toRadians(75)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(54, -18, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                    telemetry.addLine("2");
                    setStage(4);
                    lift.setPower(-0.15f);
                    this.sleep(350);

                })
                .lineToSplineHeading(new Pose2d(51, 25.5, Math.toRadians(75)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(55, -18, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);;
                    this.sleep(350);
                    telemetry.addLine("2");
                    setStage(3);
                    lift.setPower(-0.15f);
                    this.sleep(350);

                })
                .lineToSplineHeading(new Pose2d(51, 25.5, Math.toRadians(75)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(54, -18, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                    telemetry.addLine("4");
                    setStage((2));
                    lift.setPower(-0.15f);
                    this.sleep(350);
                })

                .lineToSplineHeading(new Pose2d(51, 27, Math.toRadians(65)))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(53.5, -18, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                    telemetry.addLine("2");
                    setStage(1);
                    lift.setPower(-0.15f);
                    this.sleep(350);

                })

                .build();
        waitForStart();

        if(isStopRequested()) return;
        drive.followTrajectorySequence(traj);
    }
}