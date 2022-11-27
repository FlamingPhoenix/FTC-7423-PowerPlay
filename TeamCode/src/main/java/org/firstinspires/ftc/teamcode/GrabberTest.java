package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Autonomous(name="grabberTest", group="none")
public class GrabberTest extends LinearOpMode {
    CRServo grabber1;
    CRServo grabber2;
    TouchSensor grabberTouch;
    public void initialize(){
        grabber1 = hardwareMap.crservo.get("grabber1");
        grabber2 = hardwareMap.crservo.get("grabber2");
        grabberTouch = hardwareMap.touchSensor.get("grabberTouch");
    }
    @Override
    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        while(opModeIsActive()) {
            if(grabberTouch.isPressed()){
                telemetry.addLine("pressed");
                telemetry.update();
                grabber1.setPower(0);
                grabber2.setPower(0);
            }else{
                grabber1.setPower(-1);
                grabber2.setPower(1);
            }
        }
    }
}
