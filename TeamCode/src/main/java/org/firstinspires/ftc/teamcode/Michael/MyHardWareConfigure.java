package org.firstinspires.ftc.teamcode.Michael;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;


/**
 * Created by user on 4/27/2018.
 */

public class MyHardWareConfigure {

        /* Public OpMode members. */
        public DcMotor          leftMotor    = null;
        public DcMotor          rightMotor   = null;
        public DcMotor          liftMotor    = null;
        public Servo            leftServo    = null;
        public Servo            rightServo   = null;
        //public IntegratingGyroscope gyro;
        public ModernRoboticsI2cGyro gyro;
        public DigitalChannel   touch ;  // Hardware Device Object

        /* local OpMode members. */
        HardwareMap hwMap           =  null;
        private ElapsedTime period  = new ElapsedTime();

        /* Constructor */
        public MyHardWareConfigure(){

        }

        /* Initialize standard Hardware interfaces */
        public void init(HardwareMap ahwMap) {
            // Save reference to Hardware map
            hwMap = ahwMap;

            // Define and Initialize Motors
            leftMotor  = hwMap.get(DcMotor.class, "leftMotor");
            rightMotor = hwMap.get(DcMotor.class, "rightMotor");
            liftMotor    = hwMap.get(DcMotor.class, "liftMotor");
            leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
            rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

            // Set all motors to zero power
            leftMotor.setPower(0);
            rightMotor.setPower(0);
            liftMotor.setPower(0);

            // Set all motors to run without encoders.
            // May want to use RUN_USING_ENCODERS if encoders are installed.
            leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            // get a reference to our digitalTouch object.
            touch = hwMap.get(DigitalChannel.class, "touch");

            // set the digital channel to input.
            touch.setMode(DigitalChannel.Mode.INPUT);

            // Define and initialize ALL installed servos.
            leftServo  = hwMap.get(Servo.class, "leftServo");
            rightServo = hwMap.get(Servo.class, "rightServo");
            leftServo.setPosition(0.2);
            rightServo.setPosition(1.0);

            //get a reference to gyro object
            gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("gyro");

        }
}

