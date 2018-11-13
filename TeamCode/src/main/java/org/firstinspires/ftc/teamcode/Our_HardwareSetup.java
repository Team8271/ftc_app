
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by TeameurekaRobotics on 12/30/2016
 *
 * This file contains an example Hardware Setup Class.
 *
 * It can be customized to match the configuration of your Bot by adding/removing hardware, and then used to instantiate
 * your bot hardware configuration in all your OpModes. This will clean up OpMode code by putting all
 * the configuration here, needing only a single instantiation inside your OpModes and avoid having to change configuration
 * in all OpModes when hardware is changed on robot.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 *
 */

public class Our_HardwareSetup {

   /* Declare Public OpMode members.
    *these are the null statements to make sure nothing is stored in the variables.
    */

    //motors
    public DcMotor motorLeft = null;
    public DcMotor motorRight = null;
    public DcMotor motorArm = null;

    //servos

    public Servo servoHandR = null;
    //public Servo servoHandR = null;
    //public Servo crServo    = null;

    //sensors
    //public GyroSensor gyro  = null;

    /* local OpMode members. */
    HardwareMap hwMap        = null;

    //Create and set default servo positions & MOTOR STOP variables.
    //Possible servo values: 0.0 - 1.0  For CRServo 0.5=stop greater or less than will spin in that direction
    final static double CLOSED = 0.2;
    final static double OPEN = 0.8;
    final static double MOTOR_STOP = 0.0; // sets motor power to zero

    int     armHoldPosition;             // reading of arm position when buttons released to hold
    double  slopeVal         = 2000.0;   // increase or decrease to perfect
    //CR servo variables
    //double SpinLeft = 0.1;
    //double SpinRight = 0.6;
    //double STOP = 0.5;

   /* Constructor   // this is not required as JAVA does it for you, but useful if you want to add
    * function to this method when called in OpModes.
    */
    public Our_HardwareSetup() {
    }

    //Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        /************************************************************
         * MOTOR SECTION
         ************************************************************/
        // Define Motors to match Robot Configuration File
        motorLeft = hwMap.dcMotor.get("ML");
        motorRight = hwMap.dcMotor.get("MR");
        motorArm = hwMap.dcMotor.get("MA");

        // Set the drive motor directions:
        motorLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        motorRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        motorArm.setDirection(DcMotor.Direction.FORWARD); // Can change based on motor configuration

        //Keep the motors from moving during initialize.
        motorLeft.setPower(MOTOR_STOP);
        motorRight.setPower(MOTOR_STOP);
        motorArm.setPower(MOTOR_STOP);

        // Set motors to run USING or WITHOUT encoders
        // Depending upon your configuration and use
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /************************************************************
         * SERVO SECTION
         ************************************************************/
        // Define Motors to match Robot Configuration File

        servoHandR = hwMap.servo.get("SHR");
        //servoHandR = hwMap.servo.get("servoHandR");
        //crServo    = hwMap.servo.get("crServo");



        //Set servo hand grippers to open position.
        servoHandR.setPosition(OPEN);
        servoHandR.setPosition(CLOSED);
        //servoHandR.setPosition(OPEN);

        //Continous Rotation Servo
        //crServo.setPosition(STOP);

        /************************************************************
         * SENSOR SECTION
         ************************************************************/
        //Define sensors
        //gyro = hwMap.gyroSensor.get("gyro");
   }

}

