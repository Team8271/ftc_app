
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
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

public class painbowHardwareSetup {

   /* Declare Public OpMode members.
    *these are the null statements to make sure nothing is stored in the variables.
    */

    //motors
    public DcMotor motorLeft = null;
    public DcMotor motorRight = null;
    public DcMotor motorArm = null;

    //servos

    //public Servo crServo    = null;

    //sensors
    //public GyroSensor gyro  = null;
    public ColorSensor colorsensorL = null;
    public ColorSensor colorsensorR = null;

    /* local OpMode members. */
    HardwareMap hwMap        = null;

    //Create and set default servo positions & MOTOR STOP variables.
    //Possible servo values: 0.0 - 1.0  For CRServo 0.5=stop greater or less than will spin in that direction
    //public static final double MID_SERVO    =  0.5 ;
    final static double CLOSED              = 0.3;
    final static double OPEN                = 0.7;
    final static double MOTOR_STOP          = 0.0; // sets motor power to zero

    //CR servo variables

    double STOP = 0.5;


    double Red = 60;
    double Blue = 20;
   /* Constructor   // this is not required as JAVA does it for you, but useful if you want to add
    * function to this method when called in OpModes.
    */
    public painbowHardwareSetup() {
    }

    //Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        /************************************************************
         * MOTOR SECTION
         ************************************************************/
        // Define Motors to match Robot Configuration File
        motorLeft = hwMap.dcMotor.get("motorL");
        motorRight = hwMap.dcMotor.get("motorR");
        motorArm = hwMap.dcMotor.get("motorArm");

        //colorsensor = hwMap.colorSensor.get("color");

        // Set the drive motor directions:
        motorLeft.setDirection(DcMotor.Direction.FORWARD);  // Set to REVERSE if using AndyMark motors
        motorRight.setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors
        motorArm.setDirection(DcMotor.Direction.REVERSE);   // Can change based on motor configuration
                                                            // armMotor reversed to give pos encoder values
                                                            // required compensating joystick values

        //Keep the motors from moving during initialize.
        motorLeft.setPower(MOTOR_STOP);
        motorRight.setPower(MOTOR_STOP);
        motorArm.setPower(MOTOR_STOP);

        // Set motors to run USING or WITHOUT encoders
        // Depending upon your configuration and use
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       // motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       // motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /************************************************************
         * SERVO SECTION
         ************************************************************/
        // Define Motors to match Robot Configuration File

        //crServo    = hwMap.servo.get("crServo");

        //Set servo hand grippers to open position.






        //Servo color sensor arms


        //Continous Rotation Servo
        //crServo.setPosition(STOP);

        /************************************************************
         * SENSOR SECTION
         ************************************************************/
        //Define sensors
        //gyro = hwMap.gyroSensor.get("gyro");


    }

}

