package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.google.gson.internal.bind.util.ISO8601Utils;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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
import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp(name="servotele", group="none")

public class OGBotTest extends OpMode {
    DcMotor pulley = null;
    @Override
    public void init() {
        pulley = hardwareMap.dcMotor.get("pulley");
    }

    @Override
    public void loop() {
        if (gamepad1.left_stick_y < 0) {
            pulley.setPower(gamepad1.left_stick_y);
        }
        else if (gamepad1.left_stick_y > 0) {
            pulley.setPower(0.25*gamepad1.left_stick_y);
        }
        else {
            pulley.setPower(-0.12);
        }
    }
}