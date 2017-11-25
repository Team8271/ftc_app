package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


/**
 * All device access is managed through the OurRobotHardwareSetup class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for the robot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the arm using the Gampad trigger and bumper
 * It also opens and closes the claws slowly using the a/b buttons
 *
 */

@TeleOp(name="Scaled Teleop POV", group="Competition")
//@Disabled
public class OurTeleopPOV_ScaleInput extends LinearOpMode {

  /* Declare Hardware */
  OurRobotHardwareSetup robot = new OurRobotHardwareSetup();   // Use a hardware setup class

  @Override
  public void runOpMode() {

        /* Initialize the hardware from OurRobotHardwareSetup*/
    robot.init(hardwareMap);

    // Send telemetry message to signify robot waiting;
    telemetry.addData("Hello Driver", "Waiting for Start...");    //
    telemetry.update();

    // Wait for the game to start (driver presses PLAY)
    waitForStart();

    // run until the end of the match (driver presses STOP)
    while (opModeIsActive()) {

      //Run Drive Motors
      // Setup a variable for each drive wheel to save power level for telemetry
      double leftPower;
      double rightPower;

      // POV Mode uses left stick to go forward, and right stick to turn.
      // - This uses basic math to combine motions and is easier to drive straight.
        double drive = scaleInput(-gamepad1.left_stick_y);
        double turn  = scaleInput(gamepad1.right_stick_x);

      // Clip joystick values to be withing the range of the allowable motor power levels
      leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
      rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

      // Send calculated power to wheels
      robot.motorLeft.setPower(leftPower);
      robot.motorRight.setPower(rightPower);

      //Servo commands
      if(gamepad2.a) //button 'a' will open
      {
        robot.servoHandR.setPosition(0.6);
        robot.servoHandL.setPosition(0.4);
      }
      else if (gamepad2.b) //button 'b' will close
      {
        robot.servoHandR.setPosition(0.2);
        robot.servoHandL.setPosition(0.8);
      }
      else if (gamepad2.x) //button 'x' will grab relic
      {
          robot.servoHandR.setPosition(.1);
          robot.servoHandL.setPosition(.9);
      }
      // Arm Control - Uses dual buttons to control motor direction
/*      double armUp = gamepad2.right_trigger/2.0;
      double armDown = -gamepad2.right_trigger/2.0;

      if(gamepad2.right_bumper)
      {
        robot.motorArm.setPower(armDown); // if both Bumper + Trigger, then negative power, runs arm down
      }
      else
      {
        robot.motorArm.setPower(armUp);  // else trigger positive value, runs arm up
      }
*/
      //Arm Control - Uses right joystick to control motor direction
        robot.motorArm.setPower(-gamepad2.right_stick_y/2);

      // Send telemetry message to signify robot running;
      telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
      //telemetry.addData("Arm", "Up (%.2f), Down (%.2f)", armUp, armDown);

      //telemetry.addData("Claw", "%.2f", gamepad1.a, gamepad1.b);
      telemetry.update();

      // Pace this loop so jaw action is reasonable speed.
      //sleep(50); not sure this is needed
    }
  }
    /*
   * This method scales the joystick input so for low joystick values, the
   * scaled value is less than linear.  This is to make it easier to drive
   * the robot more precisely at slower speeds.
   */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}
