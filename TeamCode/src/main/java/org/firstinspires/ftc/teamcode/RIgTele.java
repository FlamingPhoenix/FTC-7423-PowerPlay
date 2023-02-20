package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import android.util.Log;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.util.Encoder;

@TeleOp(name="rigtele", group="none")

public class RIgTele extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        Encoder leftEncoder, rightEncoder, frontEncoder;

        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "fr"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "bl"));
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "br"));
        NormalizedColorSensor colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()){
            telemetry.addData("Left VALUE %d", leftEncoder.getCurrentPosition());
            telemetry.addData("MID VALUE %d", frontEncoder.getCurrentPosition());
            telemetry.addData("RIGHT VALUE %d", rightEncoder.getCurrentPosition());
            telemetry.update();
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            telemetry.addLine()
                    .addData("Red", "%.3f", colors.red)
                    .addData("Green", "%.3f", colors.green)
                    .addData("Blue", "%.3f", colors.blue);
        }

    }
}