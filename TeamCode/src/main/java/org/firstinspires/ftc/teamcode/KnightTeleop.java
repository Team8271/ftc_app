
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;




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

@TeleOp(name="KnightTeleop", group="Competition")
//@Disabled
public class KnightTeleop extends LinearOpMode {

  /* Declare Hardware */
  OurRobotHardwareSetup robot = new OurRobotHardwareSetup();   // Use a hardware setup class

  @Override
  public void runOpMode() {

        /* Initialize the hardware from OurRobotHardwareSetup*/
    robot.init(hardwareMap);

    // Send telemetry message to signify robot waiting;
    telemetry.addData("Hello Driver", "Waiting for Start...");    //
    telemetry.update();

        double LEFT_SERVO_CLOSED = 0.1;
        double LEFT_SERVO_OPEN  = 0.9;
        double RIGHT_SERVO_CLOSED = 0.7;
        double RIGHT_SERVO_OPEN = 0.3;

        boolean xpressed = false;
        boolean ypressed = false;
        boolean topOpen = false;
        boolean bottomOpen = false;



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

        //drive and turn
        double drive = (-gamepad1.left_stick_y);
        double turn = (gamepad1.right_stick_x);

        // Clip joystick values to be withing the range of the allowable motor power levels
        leftPower = Range.clip(drive + turn, -1.0, 1.0);
        rightPower = Range.clip(drive - turn, -1.0, 1.0);
            //button that halves values in which makes for more accurate movements.
            int div = 0;
            if (gamepad1.right_bumper) {
                div = 2;
            } else {
                div = 1;
            }

            // Send calculated power to wheels
            robot.motorLeft.setPower(leftPower / div);
            robot.motorRight.setPower(rightPower / div);

            //Servo commands
            if (gamepad2.a) //button 'a' will open top servos
            {
                robot.servoHandTopRight.setPosition(RIGHT_SERVO_OPEN);
                robot.servoHandTopLeft.setPosition(LEFT_SERVO_OPEN);
                robot.servoHandBottomRight.setPosition(RIGHT_SERVO_OPEN);
                robot.servoHandBottomLeft.setPosition(LEFT_SERVO_OPEN);

            } else if (gamepad2.b) //button 'b' will close top servos
            {
                robot.servoHandTopRight.setPosition(RIGHT_SERVO_CLOSED);
                robot.servoHandTopLeft.setPosition(LEFT_SERVO_CLOSED);
                robot.servoHandBottomRight.setPosition(RIGHT_SERVO_CLOSED);
                robot.servoHandBottomLeft.setPosition(LEFT_SERVO_CLOSED);

            }














            //Arm Control - Uses right joystick to control motor direction
            robot.motorArm.setPower(-gamepad2.right_stick_y / 4);


            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);


            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            //sleep(50); not sure this is needed

      /*
   * This method scales the joystick input so for low joystick values, the
   * scaled value is less than linear.  This is to make it easier to drive
   * the robot more precisely at slower speeds.
   */


    }//While OpMode Active
  }//run opMode
}//Linear OpMode