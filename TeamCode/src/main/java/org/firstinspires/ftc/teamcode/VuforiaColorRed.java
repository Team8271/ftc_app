package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * This OpMode illustrates the basics of using the Vuforia engine to determine
 * the identity of Vuforia VuMarks encountered on the field. The code is structured as
 * a LinearOpMode.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained in {@link ConceptVuforiaNavigation}.
 */

@Autonomous(name="VuforiaColorRed", group ="Concept")
@Disabled
public class VuforiaColorRed extends LinearOpMode {

    OurRobotHardwareSetup robot = new OurRobotHardwareSetup(); //get hardware members from HardwareSetUp class

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;


    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

        double LEFT_SERVO_CLOSED = 0.8;// The bigger the number the tighter the grasp of the servo
        double LEFT_SERVO_OPEN = 0.5;

        double RIGHT_SERVO_CLOSED = 0.2;// The smaller the number, the tighter the grasp of the servo
        double RIGHT_SERVO_OPEN = 0.5;

        robot.init(hardwareMap); // get initializatin of hardware from HardwareSetUp class

        /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // OR...  Do Not Activate the Camera Monitor View, to save power
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        /*
         * licence key for rreynolds
         */
        parameters.vuforiaLicenseKey = "AYNNRt//////AAAAGfpIRuDyZ0LbtXFg3QTQrAB9l5fi8Ibg+wAf+xbO7sGn7r+YlAoco9KJt/BpcqO/lKY5CCvsUUN6WMfFl3SliLMd/m6hyR5gbRBBmEjrpw6BT4pgGlRdJEl5svrqOi+LqaQm2oil4GvVbUZDKU5za1NU5dgjP0dBtgVSKh49bgZCRWzlZAZkpTCLCZ0Gu30jZ3SUD0ixrez5AaKREZDdhByG17DVx25W/br9PUY9jXqkNfHUnh7Xs6f5JtUqZOSjkOJzLmn7ChjVHY6AFeQoucJeZwcz3Bg/Cmk1UEB/X9YGL+iY+TN0SjVyfSjfL/XooxfaGPS1aeIiOLYg+JMnzeHgwqzzqvLiY1W0kTFZ9k54";

        /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        /**
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        //create telemetry that provides information whether or not the sensors are
        //seeing the jewels.
        while (!opModeIsActive()) {
            telemetry.addLine("Red Alliance Sensor--");
            telemetry.addData("Red Value:  ", robot.colorsensorR.red());
            telemetry.addData("Blue Value: ", robot.colorsensorR.blue());


            telemetry.addLine("Blue Alliance Sensor--");
            telemetry.addData("Red Value:  ", robot.colorsensorL.red());
            telemetry.addData("Blue Value: ", robot.colorsensorL.blue());

            telemetry.update();
        }


        waitForStart();

        relicTrackables.activate();


        //Have sensor position to be up
        robot.colorservoLeft.setPosition(.5);
        sleep(500);
        robot.colorservoRight.setPosition(.5);
        sleep(500);
        //Positions Right sensor to be sensing the jewel
        robot.colorservoRight.setPosition(.01);//Down
        sleep(3000);

        // Set threshold values for red/blue color sensor readings
        double REDTHRESHOLD = 60;
        double BLUETHRESHOLD = 50;

        if (robot.colorsensorR.red() > robot.colorsensorR.blue() && robot.colorsensorR.red() >= REDTHRESHOLD) {
            //"act on red"

            // display all reading data
            telemetry.addLine("saw RED ");
            telemetry.addLine("Red Alliance Sensor--");
            telemetry.addData("Red Value:  ", robot.colorsensorR.red());
            telemetry.addData("Blue Value: ", robot.colorsensorR.blue());

            telemetry.update();
            //if it the sensor sees red, then the robot will do the following

            TurnLeft(.25, 300);
            StopDrivingTime(500);
            TurnRight(.25, 300);
            StopDrivingTime(1000);
        }
        // else if (robot.colorsensorR.blue() > robot.colorsensorR.red() && robot.colorsensorR.blue() >= BLUETHRESHOLD + thresholdadjust){
        else if (robot.colorsensorR.blue() > robot.colorsensorR.red() && robot.colorsensorR.blue() >= BLUETHRESHOLD) {
            // "act on blue"
            // display all reading data
            telemetry.addLine("saw BLUE ");
            telemetry.addLine("Red Alliance Sensor--");
            telemetry.addData("Red Value:  ", robot.colorsensorR.red());
            telemetry.addData("Blue Value: ", robot.colorsensorR.blue());

            telemetry.update();

            //if the sensor sees blue, then the robot will do the following

            TurnRight(.25,500);
            StopDrivingTime(500);
            TurnRight(.25, 300);
            StopDrivingTime(1000);
        } else {
            // reading un-reliable so do no harm
            // display all reading data
            telemetry.addLine("READING UN-RELIABLE ");
            telemetry.addLine("Red Alliance Sensor--");
            telemetry.addData("Red Value:  ", robot.colorsensorR.red());
            telemetry.addData("Blue Value: ", robot.colorsensorR.blue());

            telemetry.update();
        }


        // wait two seconds then reposition servos up
        sleep(2000);

    while (opModeIsActive()) {
        //create a timer for the vuforia
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        while (opModeIsActive() && vuMark == RelicRecoveryVuMark.UNKNOWN && timer.seconds() < 10.0) // if vuMark is NOT UNKNOWN run autoCode for value seen
        {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
        }
            // in case that the vuforia does not work
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) // if vuMark is NOT UNKNOWN run autoCode for value seen
            {

                telemetry.addData("VuMark", "%s visible", vuMark);
                telemetry.update();


                if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    // autonomous code here...
                    //close
                    robot.servoHandBottomLeft.setPosition(LEFT_SERVO_CLOSED);
                    robot.servoHandBottomRight.setPosition(RIGHT_SERVO_CLOSED);
                    robot.servoHandTopLeft.setPosition(LEFT_SERVO_CLOSED);
                    robot.servoHandTopRight.setPosition(RIGHT_SERVO_CLOSED);

                    DriveForwardTime(DRIVE_POWER, 1900);
                    StopDrivingTime(500);
                    TurnRight(0.5, 800);
                    StopDrivingTime(1000);
                    DriveForwardTime(DRIVE_POWER, 500);
                    //open
                    robot.servoHandTopLeft.setPosition(LEFT_SERVO_OPEN);
                    robot.servoHandTopRight.setPosition(RIGHT_SERVO_OPEN);
                    robot.servoHandBottomLeft.setPosition(LEFT_SERVO_OPEN);
                    robot.servoHandBottomRight.setPosition(RIGHT_SERVO_OPEN);
                    DriveForwardTime(-0.25, 20);

                } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    // autonomous code here..
                    //close
                    robot.servoHandTopLeft.setPosition(LEFT_SERVO_CLOSED);
                    robot.servoHandTopRight.setPosition(RIGHT_SERVO_CLOSED);
                    robot.servoHandBottomLeft.setPosition(LEFT_SERVO_CLOSED);
                    robot.servoHandBottomRight.setPosition(RIGHT_SERVO_CLOSED);

                    DriveForwardTime(DRIVE_POWER, 2400);
                    StopDrivingTime(500);
                    TurnRight(0.5, 800);
                    StopDrivingTime(500);
                    DriveForwardTime(DRIVE_POWER, 500);
                    //open
                    robot.servoHandTopLeft.setPosition(LEFT_SERVO_OPEN);
                    robot.servoHandTopRight.setPosition(RIGHT_SERVO_OPEN);
                    robot.servoHandBottomLeft.setPosition(LEFT_SERVO_OPEN);
                    robot.servoHandBottomRight.setPosition(RIGHT_SERVO_OPEN);
                    DriveForwardTime(-0.25, 20);


                } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                    //close servos
                    robot.servoHandTopLeft.setPosition(LEFT_SERVO_CLOSED);
                    robot.servoHandTopRight.setPosition(RIGHT_SERVO_CLOSED);
                    robot.servoHandBottomLeft.setPosition(LEFT_SERVO_CLOSED);
                    robot.servoHandBottomRight.setPosition(RIGHT_SERVO_CLOSED);


                    DriveForwardTime(DRIVE_POWER, 2150);
                    StopDrivingTime(500);
                    TurnRight(0.5, 400);
                    StopDrivingTime(200);
                    DriveForwardTime(0.5, 350);
                    StopDrivingTime(500);
                    TurnRight(0.5, 400);
                    DriveForwardTime(DRIVE_POWER, 500);
                    //open servos
                    robot.servoHandTopLeft.setPosition(LEFT_SERVO_OPEN);
                    robot.servoHandTopRight.setPosition(RIGHT_SERVO_OPEN);
                    robot.servoHandBottomLeft.setPosition(LEFT_SERVO_OPEN);
                    robot.servoHandBottomRight.setPosition(RIGHT_SERVO_OPEN);
                    DriveForwardTime(-0.25, 20);

                }

                break; //exit opModeIsActive loop
            } else {
                telemetry.addData("VuMark", "NOT VISIBLE"); // else if vuMark IS UNKNOWN display NOT VISABLE
            }


            telemetry.update();

        }//opModeisActive
    } //RunOpMode


        //create  various functions for the robot during autonomous
    double DRIVE_POWER = .5;

    public void DriveForward(double power) {
        robot.motorLeft.setPower(-power);
        robot.motorRight.setPower(-power);
    }

    public void DriveForwardTime(double power, long time) throws InterruptedException {
        DriveForward(power);
        Thread.sleep(time);
    }

    public void StopDriving() {
        DriveForward(0);
    }

    public void StopDrivingTime(long time) throws InterruptedException {
        DriveForwardTime(0, time);
    }

    public void TurnLeft(double power, long time) throws InterruptedException {
        robot.motorLeft.setPower(power);
        robot.motorRight.setPower(-power);
        Thread.sleep(time);
    }

    public void TurnRight(double power, long time) throws InterruptedException {
        TurnLeft(power, time);
    }

    public void RaiseArm() {
        robot.motorArm.setPower(.8); //note: uses servo instead of motor.
    }

    public void LowerArm() {
        robot.motorArm.setPower(.2);
    }

} //ColorReaderTest





