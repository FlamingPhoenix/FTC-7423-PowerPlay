package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

        MoveAround(new Coordinate(0,0), new Coordinate(5, 10));
        //String signalLabel = image.ReadSignal();
        //telemetry.addData("Signal:", signalLabel);
        //telemetry.update();

        //this.sleep(5000);

        /*
        image.switchOnVuforia();

        while(this.opModeIsActive()) {
            WallImageData imageData = image.ReadWallImage();
            if (imageData != null)
                telemetry.addData("ImageData:", String.format("x: %10.2f", imageData.x));
            else
                telemetry.addData("ImageData:", "can't see image");

            telemetry.update();
            this.sleep(1000);
        }
*/
       /*Drive(20);
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
        //pulley = hardwareMap.dcMotor.get("pulley");

        //image = new ImageNavigation(this);
        //String signalLabelName = image.ReadSignal();
        //telemetry.addData("image label", signalLabelName);
        telemetry.update();
    }
    public double Max(double f1, double f2, double f3, double f4) {
        f1 = Math.abs(f1);
        f2 = Math.abs(f2);
        f3 = Math.abs(f3);
        f4 = Math.abs(f4);


        if(f1>=f2 && f1>=f3 && f1>=f4) return f1;
        if(f2>=f1 && f2>=f3 && f2>=f4) return f2;
        if(f3>=f1 && f1>=f2 && f1>=f4) return f3;
        return f4;



    }
    public void MoveAround(Coordinate startPosition, Coordinate targetPosition) {
        double xDistance = (targetPosition.x-startPosition.x);
        double yDistance = (targetPosition.y - startPosition.y);
        double directionalAngle = Math.toDegrees(Math.atan2(xDistance, yDistance));

        double power = 1.0;
        double flp = power, frp = power, brp = power, blp = power;

        flp = yDistance + xDistance;
        frp = yDistance - xDistance;
        blp = yDistance - xDistance;
        brp = yDistance + xDistance;


        double max = Max(flp, frp, blp, brp);

        fl.setPower(flp/max * power);
        fr.setPower(frp/max * power);
        bl.setPower(blp/max * power);
        br.setPower(brp/max * power);

        this.sleep(2000);



    }

}