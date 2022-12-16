package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp
public class FieldCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor br = hardwareMap.dcMotor.get("br");
        //TouchSensor touch = hardwareMap.touchSensor.get("touch");

        DigitalChannel touch = hardwareMap.get(DigitalChannel.class, "touch");

        // set the digital channel to input.
        touch.setMode(DigitalChannel.Mode.INPUT);

        DcMotor turret;
        DcMotor lift;
        Servo grabber;
        //turret = hardwareMap.dcMotor.get("turret");
        lift = hardwareMap.dcMotor.get("lift");
        grabber = hardwareMap.servo.get("grabber");
        //turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Reverse the right side motors
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve the IMU from the hardware map
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        boolean goForHigh = false, goForMiddle = false, goForLow = false,
                goReleaseCone = false, processReleaseCone = false;
       int liftReleaseStartPos = 0;

        waitForStart();

        if (isStopRequested()) return;
        //program is active
        while (opModeIsActive()) {
            //constant lift power
            lift.setPower(-0.15f);

            //different servo positions and lift power
            if (gamepad2.x)
                grabber.setPosition(0.7);
            if (gamepad2.a)
                grabber.setPosition(1f);
            if (gamepad2.b)
                lift.setPower(-0.5f);

            if (gamepad2.left_stick_y<-0.1f) { //Make sure grabber is closed when lift is going up
                lift.setPower(gamepad2.left_stick_y);
            }
            if (gamepad2.left_stick_y>0.1f) { //Drive is using the joystick to move lift down
                if (touch.getState())
                    lift.setPower(0.5 * gamepad2.left_stick_y);
            }
            if (gamepad2.right_bumper && !goReleaseCone && !processReleaseCone && lift.getCurrentPosition()<-125){
                goReleaseCone = true;
            }
            float liftCurrentPos = lift.getCurrentPosition();
            telemetry.addData("Current Pos %b", touch.getState());

            telemetry.addData("Current Pos %d", liftCurrentPos);
            telemetry.update();

            if (gamepad2.dpad_up) {
                goForHigh = true;
                goForMiddle = false;
                goForLow = false;
            }
            else if (gamepad2.dpad_right) {
                goForHigh = false;
                goForMiddle = true;
                goForLow = false;
            }
            else if (gamepad2.dpad_down) {
                goForHigh = false;
                goForMiddle = false;
                goForLow = true;
            }
            else if (gamepad2.left_stick_y > 0.1 || gamepad2.left_stick_y < -0.1)
            {
                goForHigh = false;
                goForMiddle = false;
                goForLow = false;
            }

            if (goForHigh) {
                if (liftCurrentPos>-910){
                    lift.setPower(-1f);
                }
                else{
                    lift.setPower(-0.15f);
                }
            } else if (goForMiddle) {
                if (liftCurrentPos>-665){
                    lift.setPower(-1f);
                }
                else if (liftCurrentPos<-690){
                    lift.setPower(0.3f);
                }
                else{
                    lift.setPower(-0.15f);
                }
            } else if (goForLow) {
                if (liftCurrentPos>-425){
                    lift.setPower(-1f);
                }
                else if (liftCurrentPos<-455){
                    lift.setPower(0.3f);
                }
                else{
                    lift.setPower(-0.15f);
                }
            }else if (goReleaseCone){
                processReleaseCone = true;
                goReleaseCone = false;
                liftReleaseStartPos = lift.getCurrentPosition();
            } else if (processReleaseCone) {
                if (liftCurrentPos < (liftReleaseStartPos + 100))
                    lift.setPower(0.5f);
                else {
                    grabber.setPosition(0.7);
                    processReleaseCone = false;
                    lift.setPower(-0.15f);
                }
            }

            //math for field centric
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
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
            /*if (gamepad1.left_trigger>0.2){
                flp = ((1.2-gamepad1.left_trigger) *flp);
                blp = ((1.2-gamepad1.left_trigger) *blp);
                frp = ((1.2-gamepad1.left_trigger) *frp);
                brp = ((1.2-gamepad1.left_trigger)  *brp);
            }*/
            flp = flp * (1 - gamepad1.right_trigger);
            blp = blp * (1 - gamepad1.right_trigger);
            frp = frp * (1 - gamepad1.right_trigger);
            brp = brp * (1 - gamepad1.right_trigger);
            flp = flp * (1 + 2*gamepad1.left_trigger);
            blp = blp * (1 + 2*gamepad1.left_trigger);
            frp = frp * (1 + 2*gamepad1.left_trigger);
            brp = brp * (1 + 2*gamepad1.left_trigger);

            fl.setPower(0.49*flp);
            bl.setPower(0.49*blp);
            fr.setPower(0.49*frp);
            br.setPower(0.49*brp);

            //turret limits + control
            /*telemetry.addData("turret pos:", String.format(" %d, x:%10.3f", turretPosition, gamepad2.left_stick_x));
            telemetry.update();
            if(gamepad2.left_stick_x > 0.1 || gamepad2.left_stick_x < -0.10){
                if (gamepad2.left_stick_x < -0.1 && turretPosition >= -300) {
                    turret.setPower(0.4* (gamepad2.left_stick_x+0.03)* (gamepad2.left_stick_x+0.03)* (gamepad2.left_stick_x+0.03));
                }
                else if (gamepad2.left_stick_x >  0.1 && turretPosition <= 0)
                    turret.setPower(0.4* (gamepad2.left_stick_x-0.05)* (gamepad2.left_stick_x-0.05)* (gamepad2.left_stick_x-0.05));
                else
                    turret.setPower(0);
            }
            else{
                turret.setPower(0);


             */

        }


    }
}