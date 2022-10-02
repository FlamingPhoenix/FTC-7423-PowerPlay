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

@TeleOp(name="rigtele", group="none")

public class RIgTele extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor motor = hardwareMap.dcMotor.get("motor");
        Servo servo;
        double x;
        servo = hardwareMap.servo.get("servo");
        DistanceSensor sensor;
        sensor = hardwareMap.get(DistanceSensor.class, "sensor");
        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()){
            double triggerValue = gamepad1.right_trigger;
            x = sensor.getDistance(DistanceUnit.INCH);
            Log.i("[phoenix:angleInfo]", String.format("Distance = %f,", x));
            motor.setPower(triggerValue);
            if (gamepad1.b){
                servo.setPosition(1);
            }
            if (gamepad1.a){
                servo.setPosition(0);
            }


        }

    }
}