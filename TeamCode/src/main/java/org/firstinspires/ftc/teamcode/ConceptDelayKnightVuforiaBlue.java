/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//package org.firstinspires.ftc.teamcode;
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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



    @Autonomous(name = "ConceptDelayKnightVuforiaBlue", group = "Concept")
//@Disabled
    public class ConceptDelayKnightVuforiaBlue extends LinearOpMode {

    private  ElapsedTime timer = new ElapsedTime(); //create timer to monitor elapsed time

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

            telemetry.addData(">", "Press Play to start");
            telemetry.update();
            waitForStart();

            relicTrackables.activate();

            //timer.reset();
            this.resetStartTime();

            while (opModeIsActive()) {

                /**
                 * See if any of the instances of {@link relicTemplate} are currently visible.
                 * {@link RelicRecoveryVuMark} is an enum which can have the following values:
                 * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
                 * UNKNOWN will be returned, else 'NOT VISIBLE' will display
                 */

                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate); // vuMark gets value from relicTemplate

                if (vuMark != RelicRecoveryVuMark.UNKNOWN || time > 18000) // if vuMark is NOT UNKNOWN run autoCode for value seen
                {

                    telemetry.addData("VuMark", "%s visible", vuMark);
                    telemetry.update();

                    // This simple example code runs a different motor for 1 sec then turns it off
                    // for each TemplateID found.
                    // the run motor test can be replaced by your desired autonomous code.

                    if (vuMark == RelicRecoveryVuMark.LEFT) {
                        // autonomous code here...
                        //close
                        robot.servoHandL.setPosition(0.8);
                        robot.servoHandR.setPosition(0.2);

                        DriveForwardTime(DRIVE_POWER, 1900);
                        StopDrivingTime(500);
                        TurnLeft(0.5, 800);
                        StopDrivingTime(1000);
                        DriveForwardTime(DRIVE_POWER, 500);
                        //open
                        robot.servoHandL.setPosition(0.5);
                        robot.servoHandR.setPosition(0.5);
                        DriveForwardTime(-0.25, 20);

                    } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                        // autonomous code here..
                        //close
                        robot.servoHandL.setPosition(0.8);
                        robot.servoHandR.setPosition(0.2);

                        DriveForwardTime(DRIVE_POWER, 2400);
                        StopDrivingTime(500);
                        TurnLeft(0.5, 800);
                        StopDrivingTime(500);
                        DriveForwardTime(DRIVE_POWER, 500);
                        //open
                        robot.servoHandL.setPosition(0.5);
                        robot.servoHandR.setPosition(0.5);
                        DriveForwardTime(-0.25, 20);


                    } else if (vuMark == RelicRecoveryVuMark.RIGHT) {

                        DriveForwardTime(DRIVE_POWER, 2150);
                        StopDrivingTime(500);
                        TurnLeft(0.5, 400);
                        StopDrivingTime(200);
                        DriveForwardTime(0.5, 350);
                        StopDrivingTime(500);
                        TurnLeft(0.5, 400);
                        DriveForwardTime(DRIVE_POWER, 500);
                        //open
                        robot.servoHandL.setPosition(0.5);
                        robot.servoHandR.setPosition(0.5);
                        DriveForwardTime(-0.25, 20);
                    }

                    else if (vuMark != RelicRecoveryVuMark.UNKNOWN  || timer.milliseconds() > 20000) {
                        // Default
                        //Place in Center
                        robot.servoHandL.setPosition(0.8);
                        robot.servoHandR.setPosition(0.2);

                        DriveForwardTime(DRIVE_POWER, 2400);
                        StopDrivingTime(500);
                        TurnLeft(0.5, 800);
                        StopDrivingTime(500);
                        DriveForwardTime(DRIVE_POWER, 500);
                        //open
                        robot.servoHandL.setPosition(0.5);
                        robot.servoHandR.setPosition(0.5);
                        DriveForwardTime(-0.25, 20);


                    }


                    break; //exit opModeIsActive loop
                } else {
                    telemetry.addData("VuMark", "NOT VISIBLE"); // else if vuMark IS UNKNOWN display NOT VISABLE
                }

                telemetry.update();

            }//OpModeIsActive
        }//runOpMode

        /**
         * Below: Basic Drive Methods used in Autonomous code...
         **/
        //set Drive Power variable
        double DRIVE_POWER = .5;

        public void DriveForward(double power) {
            robot.motorLeft.setPower(power);
            robot.motorRight.setPower(power);
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
            robot.motorLeft.setPower(-power);
            robot.motorRight.setPower(power);
            Thread.sleep(time);
        }

        public void TurnRight(double power, long time) throws InterruptedException {
            TurnLeft(-power, time);
        }

        public void RaiseArm() {
            robot.motorArm.setPower(.8); //note: uses servo instead of motor.
        }

        public void LowerArm() {
            robot.motorArm.setPower(.2);
        }

    }//MyConceptVuforia
