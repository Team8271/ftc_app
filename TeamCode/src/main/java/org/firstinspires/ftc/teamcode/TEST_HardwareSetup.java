
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


/**
 * TEST Config
 */

public class TEST_HardwareSetup {

   /* Declare Public OpMode members.
    *these are the null statements to make sure nothing is stored in the variables.
    */

    //motors

    public DcMotor motorArm = null;
    public DcMotor motorLift = null;

    /* local OpMode members. */
    HardwareMap hwMap        = null;


    int     armHoldPosition;// reading of arm position when buttons released to hold
    int     liftHoldPosition;
    double  slopeVal         = 2000.0;   // increase or decrease to perfect


   /* Constructor   // this is not required as JAVA does it for you, but useful if you want to add
    * function to this method when called in OpModes.
    */
    public TEST_HardwareSetup() {
    }

    //Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        /************************************************************
         * MOTOR SECTION
         ************************************************************/
        // Define Motors to match Robot Configuration File

        motorArm = hwMap.dcMotor.get("Arm"); //port 0
        motorLift = hwMap.dcMotor.get("Lift"); //port 3
        // Set the drive motor directions:

        motorArm.setDirection(DcMotor.Direction.FORWARD);
        motorLift.setDirection(DcMotor.Direction.FORWARD);
        //Keep the motors from moving during initialize.

        motorArm.setPower(0.0);
        motorLift.setPower(0.0);
        // Set motors to run USING or WITHOUT encoders Depending upon your configuration and use

        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
   }

}

