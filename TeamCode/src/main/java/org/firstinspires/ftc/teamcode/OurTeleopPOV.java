package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

@TeleOp(name="Teleop POV", group="Linear")
//@Disabled
public class OurTeleopPOV extends LinearOpMode {

  /* Declare Hardware */
  OurRobotHardwareSetup robot = new OurRobotHardwareSetup();   // Use a hardware setup class

  @Override
  public void runOpMode() {

        /* Initialize the hardware from OurRobotHardwareSetup*/
    robot.init(hardwareMap);

    // Send telemetry message to signify robot waiting;
    telemetry.addData("Say", "Hello Driver");    //
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
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;

      // Clip joystick values to be withing the range of the allowable motor power levels
      leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
      rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

      // Send calculated power to wheels
      robot.motorLeft.setPower(leftPower);
      robot.motorRight.setPower(rightPower);

      //Servo commands
      if(gamepad1.a) //button 'a' will open
      {
        robot.servoHandR.setPosition(robot.OPEN);
        robot.servoHandL.setPosition(robot.OPEN);
      }
      else if (gamepad1.b) //button 'b' will close
      {
        robot.servoHandR.setPosition(robot.CLOSED);
        robot.servoHandL.setPosition(robot.CLOSED);
      }

      // Arm Control - Uses dual buttons to control motor direction
      double armUp = gamepad1.right_trigger;
      double armDown = -gamepad1.right_trigger;

      if(gamepad1.right_bumper)
      {
        robot.motorArm.setPower(armDown); // if both Bumper + Trigger, then negative power, runs arm down
      }
      else
      {
        robot.motorArm.setPower(armUp);  // else trigger positive value, runs arm up
      }

      // Send telemetry message to signify robot running;
      telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
      telemetry.addData("Arm", "%.2f", armUp, armDown);

      telemetry.addData("Claw", "%.2f", gamepad1.a, gamepad1.b);
      telemetry.update();

      // Pace this loop so jaw action is reasonable speed.
      //sleep(50); not sure this is needed
    }
  }
}
