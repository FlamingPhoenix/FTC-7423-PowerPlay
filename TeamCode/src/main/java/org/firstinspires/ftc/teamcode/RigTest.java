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

public class RigTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        DcMotor motor = hardwareMap.dcMotor.get("motor");
        Servo servo;
        double x;
        servo = hardwareMap.servo.get("servo");
        DistanceSensor sensor;
        sensor = hardwareMap.get(DistanceSensor.class, "sensor");
        while (opModeIsActive()){
            x = sensor.getDistance(DistanceUnit.INCH);
            double y = 0.5;
            x = sensor.getDistance(DistanceUnit.INCH);
            Log.i("[phoenix:angleInfo]", String.format("Distance = %f,", x));
            motor.setPower(y);
            if (0==0){
                servo.setPosition(1);
                sleep(1000);
            }
            if (0==0){
                servo.setPosition(0);
                sleep(1000);
            }


        }

    }
}