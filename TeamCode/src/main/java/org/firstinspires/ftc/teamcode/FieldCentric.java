package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class FieldCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor br = hardwareMap.dcMotor.get("br");
        DcMotor turret;
        DcMotor lift;
        Servo grabber;
        turret = hardwareMap.dcMotor.get("turret");
        lift = hardwareMap.dcMotor.get("lift");
        grabber = hardwareMap.servo.get("grabber");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

        waitForStart();

        if (isStopRequested()) return;
        //program is active
        while (opModeIsActive()) {
            //constant lift power

            //different servo positions and lift power
            if (gamepad1.dpad_left) {
                fl.setPower(0.3f);
                fr.setPower(-0.3f);
                bl.setPower(-0.3f);
                br.setPower(0.3f);
            }
            if (gamepad1.dpad_right) {
                fl.setPower(-0.3f);
                fr.setPower(0.3f);
                bl.setPower(0.3f);
                br.setPower(-0.3f);
            }
            if (gamepad1.dpad_up){
                fl.setPower(-0.3f);
                fr.setPower(-0.3f);
                bl.setPower(-0.3f);
                br.setPower(-0.3f);
            }
            if (gamepad1.dpad_down){
                fl.setPower(0.3f);
                fr.setPower(0.3f);
                bl.setPower(0.3f);
                br.setPower(0.3f);
            }
            if (gamepad2.x)
                grabber.setPosition(0.6);
            if (gamepad2.a)
                grabber.setPosition(0.3);
            lift.setPower(gamepad2.right_stick_y);
            float liftCurrentPos = lift.getCurrentPosition();
            telemetry.addData("Current Pos %d", liftCurrentPos);
            telemetry.update();
            if (gamepad2.dpad_up){
                while(liftCurrentPos>-11750f) {
                    liftCurrentPos = lift.getCurrentPosition();
                    float liftPower = (liftCurrentPos + 12050f) / -12050f;
                    if (liftPower>-0.6)
                        liftPower = liftPower*1.5f;
                    lift.setPower(liftPower);
                }
                lift.setPower(0);
            }
            telemetry.update();
            if (gamepad2.dpad_right){
                while(liftCurrentPos>-7750f) {
                    liftCurrentPos = lift.getCurrentPosition();
                    float liftPower = (liftCurrentPos + 8000f) / -8000f;
                    if (liftPower>-0.6)
                        liftPower = liftPower*1.5f;
                    lift.setPower(liftPower);
                }
                lift.setPower(0);
            }
            if (gamepad2.dpad_left){
                while(liftCurrentPos>-5750f) {
                    liftCurrentPos = lift.getCurrentPosition();
                    float liftPower = (liftCurrentPos + 6000f) / -6000f;
                    if (liftPower>-0.6)
                        liftPower = liftPower*1.5f;
                    lift.setPower(liftPower);
                }
                lift.setPower(0);
            }

            //math for field centric
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            double tr = -gamepad2.left_stick_x;
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
            if (gamepad1.left_trigger>0.2){
                flp = ((1.2-gamepad1.left_trigger) *flp);
                blp = ((1.2-gamepad1.left_trigger) *blp);
                frp = ((1.2-gamepad1.left_trigger) *frp);
                brp = ((1.2-gamepad1.left_trigger)  *brp);
            }
            fl.setPower(0.75*flp);
            bl.setPower(0.75*blp);
            fr.setPower(0.75*frp);
            br.setPower(0.75*brp);

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