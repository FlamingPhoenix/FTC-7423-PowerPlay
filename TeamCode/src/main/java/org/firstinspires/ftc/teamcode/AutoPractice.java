package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="AutoPractice", group="Flaming Phoenix")
public class AutoPractice extends LinearOpMode {
    DcMotor fl = null;
    DcMotor fr = null;
    DcMotor bl = null;
    DcMotor br = null;
    DcMotor pulley = null;
    float PPR = 537.7f;
    float diameter = 4f;

    ImageNavigation image;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        String signalLabel = image.ReadSignal();
        telemetry.addData("Signal:", signalLabel);
        telemetry.update();

        this.sleep(30000);
       /** Drive(20);
        this.sleep(1000);
        Drive(10);*/
    }
    public void Drive(float distance){
        double encoderCount = (distance/(diameter*Math.PI))*PPR;

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (fl.getCurrentPosition()<encoderCount){
            fl.setPower(0.5);
            fr.setPower(0.5);
            bl.setPower(0.5);
            br.setPower(0.5);
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            telemetry.addData("Current Pos %d", fl.getCurrentPosition());
            telemetry.update();
        }
        StopAllWheels();

    }
    public void StopAllWheels(){
        br.setPower(0);

    }
    //Initialize motors, servos, and sensors
    public void initialize(){
        fl = hardwareMap.dcMotor.get("fl");
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        br = hardwareMap.dcMotor.get("br");
        pulley = hardwareMap.dcMotor.get("pulley");

        image = new ImageNavigation(this);
        String signalLabelName = image.ReadSignal();
        telemetry.addData("image label", signalLabelName);
        telemetry.update();
    }


}