package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class FieldCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor br = hardwareMap.dcMotor.get("br");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve the IMU from the hardware map
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Technically this is the default, however specifying it is clearer
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        // Without this, data retrieving from the IMU throws an exception
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Read inverse IMU heading, as the IMU heading is CW positive
            double botHeading = -imu.getAngularOrientation().firstAngle;

            double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
            double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double flp = -(rotY + rotX + rx) / denominator;
            double blp = -(rotY - rotX + rx) / denominator;
            double frp = -(rotY - rotX - rx) / denominator;
            double brp = -(rotY + rotX - rx) / denominator;

            fl.setPower(flp);
            bl.setPower(blp);
            fr.setPower(frp);
            br.setPower(brp);
        }
    }
}