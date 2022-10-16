package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XZY;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.SwitchableCamera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

public class ImageNavigation {

    /* constructor */
    public ImageNavigation(LinearOpMode opMode) {
        this.opMode = opMode;

        initVuforia();
        initTfod();
    }

    /*
     * Specify the source for the Tensor Flow Model.
     * If the TensorFlowLite object model is included in the Robot Controller App as an "asset",
     * the OpMode must to load it using loadModelFromAsset().  However, if a team generated model
     * has been downloaded to the Robot Controller's SD FLASH memory, it must to be loaded using loadModelFromFile()
     * Here we assume it's an Asset.    Also see method initTfod() below .
     */
    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";
    // private static final String TFOD_MODEL_FILE  = "/sdcard/FIRST/tflitemodels/CustomTeamModel.tflite";

    private static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };

    private LinearOpMode opMode;

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AXP0qdP/////AAABmd+xVOpK/03TvCAdK3cvnSgGdQmOttBCc3pmocZSE9yWgl48m1ReoXq6evxcB6+kgYtGLh+pMa4v1bYzY/Tb81h6BEDlnffT0deGISp8cjjieR0CXpBWNmqw0X0uiTa0A7R1gfyy1aW3mVCddmfrLmhcL8D8bI4x9vEPlLvNHX86LpkQQ8xhaKWb2o7qMEUm2qPaeaYm6hQpywMdIotcJ5R3G9aedM6jcUfsOywbUT4lwg9NMB3dYxDS8a3X4VjiGfJjA/8Ytvr2aFy9jSwx/Yal3BXD7E00q4Tw8ZVQD6Op7yN6fLcV41UtZGKH6K7eETfqy7ZRxO+8lsGge38Ehr5EY6p/5NPH/IS/r4e5f1SF";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * Variables used for switching cameras.
     */
    private WebcamName webcam1, webcam2;
    private SwitchableCamera switchableCamera;
    private boolean oldLeftBumper;
    private boolean oldRightBumper;

    private VuforiaTrackables targets   = null ;
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = 6 * mmPerInch;          // the height of the center of the target image above the floor
    private static final float halfField        = 72 * mmPerInch;
    private static final float halfTile         = 12 * mmPerInch;
    private static final float oneAndHalfTile   = 36 * mmPerInch;
    private boolean targetVisible       = false;

    List<VuforiaTrackable> allTrackables;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    /** Read Signal and return the label name */
    public String  ReadSignal() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can increase the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.0, 16.0/9.0);
        }

        /** Wait for the game to begin */
        opMode.telemetry.addData(">", "Press Play to start op mode");
        opMode.telemetry.update();

        if (opMode.opModeIsActive()) {

            long startTime = System.currentTimeMillis();
            while (opMode.opModeIsActive()) {
                if (tfod != null) {
                    //doCameraSwitching();
                    List<Recognition> recognitions = tfod.getRecognitions();
                    opMode.telemetry.addData("# Objects Detected", recognitions.size());

                    if (recognitions.size() > 0) {
                        for (Recognition recognition : recognitions) {
                            double col = (recognition.getLeft() + recognition.getRight()) / 2 ;
                            double row = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                            double width  = Math.abs(recognition.getRight() - recognition.getLeft()) ;
                            double height = Math.abs(recognition.getTop()  - recognition.getBottom()) ;

                            opMode.telemetry.addData(""," ");
                            opMode.telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100 );
                            opMode.telemetry.addData("- Position (Row/Col)","%.0f / %.0f", row, col);
                            opMode.telemetry.addData("- Size (Width/Height)","%.0f / %.0f", width, height);
                            opMode.telemetry.update();

                            return recognition.getLabel();
                        }
                    }

                    if ((System.currentTimeMillis()-startTime)>30000) {
                        return "";
                    }
                }
            }
        }
        return "";
    }

    public void switchOnVuforia() {
        tfod.deactivate();
        targets.activate();
    }

    public WallImageData ReadWallImage() {
        targets.activate();
        targetVisible = false;
        OpenGLMatrix lastLocation = null;

        WallImageData imageData = new WallImageData();

        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                targetVisible = true;

                // getUpdatedRobotLocation() will return null if no new information is available since
                // the last time that call was made, or if the trackable is not currently visible.
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                    imageData.ImageName = trackable.getName();
                }
                break;
            }
        }
        if (targetVisible) {
            // express position (translation) of robot in inches.
            VectorF translation = lastLocation.getTranslation();
            imageData.x = translation.get(0);
            imageData.y = translation.get(1);
            imageData.z = translation.get(2);

            return imageData;
        }

        return null;
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        // Indicate that we wish to be able to switch cameras.
        webcam1 = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");
        webcam2 = opMode.hardwareMap.get(WebcamName.class, "Webcam 2");
        parameters.cameraName = ClassFactory.getInstance().getCameraManager().nameForSwitchableCamera(webcam1, webcam2);

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        targets = this.vuforia.loadTrackablesFromAsset("PowerPlay");
        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targets);

        identifyTarget(0, "Red Audience Wall",   -halfField,  -oneAndHalfTile, mmTargetHeight, 90, 0,  90);
        identifyTarget(1, "Red Rear Wall",        halfField,  -oneAndHalfTile, mmTargetHeight, 90, 0, -90);
        identifyTarget(2, "Blue Audience Wall",  -halfField,   oneAndHalfTile, mmTargetHeight, 90, 0,  90);
        identifyTarget(3, "Blue Rear Wall",       halfField,   oneAndHalfTile, mmTargetHeight, 90, 0, -90);

        final float CAMERA_FORWARD_DISPLACEMENT  = 0.0f * mmPerInch;   // eg: Enter the forward distance from the center of the robot to the camera lens
        final float CAMERA_VERTICAL_DISPLACEMENT = 6.0f * mmPerInch;   // eg: Camera is 6 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0.0f * mmPerInch;   // eg: Enter the left distance from the center of the robot to the camera lens

        OpenGLMatrix cameraLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XZY, DEGREES, 90, 90, 0));

        /**  Let all the trackable listeners know where the camera is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setCameraLocationOnRobot(parameters.cameraName, cameraLocationOnRobot);
        }


        // Set the active camera to Webcam 1.
        switchableCamera = (SwitchableCamera) vuforia.getCamera();
        switchableCamera.setActiveCamera(webcam1);
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        // tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }

    private void doCameraSwitching() {
        // If the left bumper is pressed, use Webcam 1.
        // If the right bumper is pressed, use Webcam 2.
        boolean newLeftBumper = opMode.gamepad1.left_bumper;
        boolean newRightBumper = opMode.gamepad1.right_bumper;
        if (newLeftBumper && !oldLeftBumper) {
            switchableCamera.setActiveCamera(webcam1);
        } else if (newRightBumper && !oldRightBumper) {
            switchableCamera.setActiveCamera(webcam2);
        }
        oldLeftBumper = newLeftBumper;
        oldRightBumper = newRightBumper;

        if (switchableCamera.getActiveCamera().equals(webcam1)) {
            opMode.telemetry.addData("activeCamera", "Webcam 1");
            opMode.telemetry.addData("Press RightBumper", "to switch to Webcam 2");
        } else {
            opMode.telemetry.addData("activeCamera", "Webcam 2");
            opMode.telemetry.addData("Press LeftBumper", "to switch to Webcam 1");
        }
    }

    public void    identifyTarget(int targetIndex, String targetName, float dx, float dy, float dz, float rx, float ry, float rz) {
        VuforiaTrackable aTarget = targets.get(targetIndex);
        aTarget.setName(targetName);
        aTarget.setLocation(OpenGLMatrix.translation(dx, dy, dz)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, rx, ry, rz)));

    }
}
