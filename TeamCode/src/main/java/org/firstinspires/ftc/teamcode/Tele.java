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
    DcMotor pulley;


    float x1, x2, y1, y2, tr, tl, tr2;
    float maxPower = 0.8f;

    int currentPosition, currentStage;
    int stage;

    public static int autoCurrentPosition, autoCurrentStage;
    int targetEncoderValue = 0;


    float maxEncoderPulley = 420f; //448 is true max

    RemoDrive rd = new RemoDrive(this);

    public void Drive(float x1, float y1, float x2) {

        float frontLeft = y1 + x1 + x2; //new wheel orientation
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
        pulley = hardwareMap.dcMotor.get("pulley");

        pulley.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pulley.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

        Log.i("[phoenix:pulleyPos]", String.format("power = %f", pulley.getPower()));

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