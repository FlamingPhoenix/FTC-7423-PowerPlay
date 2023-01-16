package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp", group="none")
public class Tele extends OpMode{

    DcMotor fr;
    DcMotor fl;
    DcMotor br;
    DcMotor bl;

    DcMotor turret;
    DcMotor lift;

    Servo grabber;
    float x1, x2, y1, y2, tr, tl, tr2;
    float maxPower = 0.8f;

    int currentPosition, currentStage;
    int stage;

    public static int autoCurrentPosition, autoCurrentStage;
    int targetEncoderValue = 0;


    float maxEncoderPulley = 420f; //448 is true max

    RemoDrive rd = new RemoDrive(this);

    public void Drive(float x1, float y1, float x2) {

        float frontLeft = y1 + x1 + x2;
        float frontRight = y1 - x1 - x2;
        float backLeft = y1 - x1 + x2;
        float backRight = y1 + x1 - x2;

        frontLeft = Range.clip(frontLeft, -1, 1);
        frontRight = Range.clip(frontRight, -1, 1);
        backLeft = Range.clip(backLeft, -1, 1);
        backRight = Range.clip(backRight, -1, 1);
        fl.setPower(frontLeft * maxPower);
        fr.setPower(frontRight * maxPower);
        bl.setPower(backLeft * maxPower);
        br.setPower(backRight * maxPower);
    }

    @Override
    public void init() {
        currentPosition = autoCurrentPosition;
        currentStage = autoCurrentStage;

        fr = hardwareMap.dcMotor.get("fr");
        fl = hardwareMap.dcMotor.get("fl");
        br = hardwareMap.dcMotor.get("br");
        bl = hardwareMap.dcMotor.get("bl");
        //grabber = hardwareMap.servo.get("grabber");
        /*turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);*/


        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("Ready for start %f", 0);
        telemetry.update();

    }

    @Override
    public void start() {
        rd.run();
    }

    @Override
    public void loop() {

        x1 = gamepad1.left_stick_x;
        y1 = gamepad1.left_stick_y;
        x2 = gamepad1.right_stick_x;
        y2 = gamepad1.right_stick_y;
        /*lift.setPower(-0.25f);
        if (gamepad2.dpad_left)
            grabber.setPosition(0.3);
        if (gamepad2.a)
            lift.setPower(-1f);
        if (gamepad2.x)
            grabber.setPosition(0.5);
        if (gamepad2.dpad_right)
            grabber.setPosition(0.6);
        if(gamepad2.right_stick_y<0)
            lift.setPower(gamepad2.right_stick_y);
        else if(gamepad2.right_stick_y>0)
            lift.setPower(gamepad2.right_stick_y*0.33);

        telemetry.addData("lift pos:", lift.getCurrentPosition());

        int turretPosition = turret.getCurrentPosition();
        telemetry.addData("turret pos:", String.format(" %d, x:%10.3f", turretPosition, gamepad2.left_stick_x));*/

        /*if(gamepad2.left_stick_x > 0.1 || gamepad2.left_stick_x < -0.10){
            if (gamepad2.left_stick_x < -0.1 && turretPosition >= -203) {
                turret.setPower(-0.35);
            }
            else if (gamepad2.left_stick_x >  0.1 && turretPosition <= 0)
                turret.setPower(0.35);
            else
                turret.setPower(0);
        }
        else{
            turret.setPower(0);
        }
        */
        double joystickLeftDistance = Math.pow(x1, 2) + Math.pow(y1, 2);
        if (joystickLeftDistance < 0.9) {
            x1 = x1 / 2;
            y1 = y1 / 2;
        }
        double joystickRightDistance = Math.pow(x2, 2) + Math.pow(y2, 2);
        if (joystickRightDistance < 0.9) {
            x2 = x2 / 2;
        }


        Drive(x1, y1 * -1, x2);


    }

    @Override
    public void stop() {
        rd.stop();
    }
}