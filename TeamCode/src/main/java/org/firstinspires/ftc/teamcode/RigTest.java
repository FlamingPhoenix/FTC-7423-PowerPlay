package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import android.util.Log;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="rigtest", group="none")

public class RigTest extends AutoBase{
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        Servo grabber = hardwareMap.servo.get("grabber");
        DcMotor lift = hardwareMap.dcMotor.get("lift");

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        grabber.setPosition(1f);
        this.sleep(3000);
        lift.setPower(-0.9f);
        DistanceScore(0.25f);
        grabber.setPosition(0.6f);
        lift.setPower(0);
        while(!isStopRequested()) {
            colorsGrabber = colorSensorGrabber.getNormalizedColors();
            if (colorsGrabber.red >0.003 ^ colorsGrabber.blue>0.003)
                grabber.setPosition(1f);
            StripeStrafe(0.18f);
            telemetry.addData("fr %d", fr.getCurrentPosition());
            telemetry.addData("fl %d", fl.getCurrentPosition());
            telemetry.addData("br %d", br.getCurrentPosition());
            telemetry.addData("bl %d", bl.getCurrentPosition());

            telemetry.addLine()
                    .addData("Red", "%.3f", colorsGrabber.red)
                    .addData("Green", "%.3f", colorsGrabber.green)
                    .addData("Blue", "%.3f", colorsGrabber.blue);
            telemetry.update();
        }

    }
}