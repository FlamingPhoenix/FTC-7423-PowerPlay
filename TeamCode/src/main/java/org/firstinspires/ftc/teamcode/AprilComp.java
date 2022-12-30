package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.ArrayList;

@Autonomous(name="April 4 CONES - COP", group="none")

public class AprilComp extends AutoBase
{
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;


    // UNITS ARE METERS
    double tagsize = 0.166;
    int LEFT = 0;
    int CENTER = 1;
    int RIGHT = 2;
    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode(){
        initialize();
        DcMotor lift  = hardwareMap.dcMotor.get("lift");
        Servo grabber = hardwareMap.servo.get("grabber");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(1000);
                })
                .addTemporalMarker(1.5, () -> {
                    lift.setPower(-1f);
                })
                .lineToSplineHeading(new Pose2d(55.5, -9, Math.toRadians(315)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(40))
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
                        SampleMecanumDrive.getAccelerationConstraint(40))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(54, -17, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(40))
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
                        SampleMecanumDrive.getAccelerationConstraint(40))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(55, -17, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(40))
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
                        SampleMecanumDrive.getAccelerationConstraint(40))

                .addDisplacementMarker(() -> {
                    grabber.setPosition(1.1f);
                    this.sleep(400);
                    lift.setPower(-1f);
                    this.sleep(250);
                })
                .lineToSplineHeading(new Pose2d(55, -17, Math.toRadians(0)),
                        SampleMecanumDrive.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(40))
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(350);
                    telemetry.addLine("4");
                    setStage((2));
                    lift.setPower(-0.15f);
                    this.sleep(350);
                })
                .addDisplacementMarker(() -> {
                    grabber.setPosition(0.7f);
                    this.sleep(1000);
                    telemetry.addLine("2");
                })
                .addDisplacementMarker(()->{
                    lift.setPower(0.5f);
                })

                .build();
        DigitalChannel touch = hardwareMap.get(DigitalChannel.class, "touch");

        // set the digital channel to input.
        touch.setMode(DigitalChannel.Mode.INPUT);




        if(isStopRequested()) return;


        //set up camera and April Tag math
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
        telemetry.addLine("YES");
        telemetry.update();
        telemetry.setMsTransmissionInterval(50);
        //initialization phase until start pressed
        while (!isStarted() && !isStopRequested())
        {
            //reading signal and finding parking location, also repeated image processing
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();
            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;
                for(AprilTagDetection tag : currentDetections )
                {
                    if(tag.id == LEFT || tag.id == CENTER || tag.id == RIGHT)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }
                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");
                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }
            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");
                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }
            }

            telemetry.update();
            sleep(20);
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the telemetry */
        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }


        //driving based on signal reading; if none found, left selected
        if(tagOfInterest == null || tagOfInterest.id == LEFT){
            telemetry.addLine("LEFT");
            telemetry.update();
            grabber.setPosition(1.1f);
            drive.followTrajectorySequence(traj);
            lift.setPower(1f);
            Strafe(0.8f, 30, Direction.RIGHT);
        }else if (tagOfInterest.id == CENTER){
            telemetry.addLine("CENTER");
            telemetry.update();
            grabber.setPosition(1.1f);

            drive.followTrajectorySequence(traj);
            Strafe(0.8f, 10, Direction.RIGHT);
        }else{
            telemetry.addLine("RIGHT");
            telemetry.update();
            grabber.setPosition(1.1f);
            drive.followTrajectorySequence(traj);
            Strafe(0.8f, 8, Direction.LEFT);

        }
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}