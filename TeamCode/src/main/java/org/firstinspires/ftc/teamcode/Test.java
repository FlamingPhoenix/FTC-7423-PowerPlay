package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
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
        touch.setMode(DigitalChannel.Mode.INPUT);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(750);
                })
                .addTemporalMarker(1.5, () -> {
                    lift.setPower(-1f);
                })
                .lineToSplineHeading(new Pose2d(50, 0, Math.toRadians(315)),
                        SampleMecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .lineToSplineHeading(new Pose2d(55.5, -8, Math.toRadians(315)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.5,()->{
                    setStage(5);
                    lift.setPower(-0.15f);
                })
                .lineToSplineHeading(new Pose2d(51, 27.5, Math.toRadians(75)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(52, -15, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.5,()->{
                    setStage(5);
                    lift.setPower(-0.15f);
                })
                .lineToSplineHeading(new Pose2d(51, 27.5, Math.toRadians(75)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(52, -15, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.5,()->{
                    setStage(5);
                    lift.setPower(-0.15f);
                })
                .lineToSplineHeading(new Pose2d(51, 27.5, Math.toRadians(75)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(52, -15, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.5,()->{
                    setStage(5);
                    lift.setPower(-0.15f);
                })

                .lineToSplineHeading(new Pose2d(51, 27.5, Math.toRadians(65)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(52, -17, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.5,()->{
                    setStage(5);
                    lift.setPower(-0.15f);
                })
                .lineToSplineHeading(new Pose2d(51, 23.5, Math.toRadians(75)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(250);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(52, -13.5, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(450);
                    telemetry.addLine("6");

                })
                .build();
        waitForStart();

        if(isStopRequested()) return;
        drive.followTrajectorySequence(traj);
    }
}