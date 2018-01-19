
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Testing 4 servo grabber
 * with added toggle buttons to run top/bottom servos separately
 */

@TeleOp(name="MrR TeleopPOV Toggle", group="Concept")
//@Disabled
public class MrRTeleopPOV_ToggleGrabbers extends LinearOpMode {

  /* Declare Hardware */
  OurRobotHardwareSetup robot = new OurRobotHardwareSetup();   // Use a hardware setup class

    double          MID_SERVO   = 0.5 ;     // Servo mid position
    double          clawOffset  = 0.5 ;
    final double    CLAW_SPEED  = 0.02 ;    // sets rate to move servo

  @Override
  public void runOpMode() {

        /* Initialize the hardware from OurRobotHardwareSetup*/
    robot.init(hardwareMap);

    // Send telemetry message to signify robot waiting;
    telemetry.addData("Hello Driver", "Waiting for Start...");    //
    telemetry.update();

        boolean last_x = false;
        boolean last_y = false;
        boolean direction_state_y = false;
        boolean direction_state_x = false;
       // double trPosOpen = 0.2;
        //ouble trPosClose = 0.85;
        double PosOpen = 0.8;
        double PosClose = 0.2;



      // Wait for the game to start (driver presses PLAY)
      waitForStart();

    // run until the end of the match (driver presses STOP)
    while (opModeIsActive()) {


        // Main servo controls to open and close all 4 servos together
        // using pushbot configuration for mirrored servos

        if (gamepad2.a)
            clawOffset += CLAW_SPEED;
        else if (gamepad2.b)
            clawOffset -= CLAW_SPEED;

        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        robot.servoHandTopLeft.setPosition(MID_SERVO + clawOffset);
        robot.servoHandBottomLeft.setPosition(MID_SERVO + clawOffset);
        robot.servoHandTopRight.setPosition(MID_SERVO - clawOffset);
        robot.servoHandBottomRight.setPosition(MID_SERVO - clawOffset);

        // Upper/Lower Servo Control Toggle
            boolean y_pressed = gamepad2.y;

            if(y_pressed && !last_y) {
                direction_state_y = !direction_state_y;
                double newY_Pos = PosClose;
                if (direction_state_y == false) {
                    newY_Pos = PosClose;
                }
                else {
                    newY_Pos = -PosOpen;
                }
                robot.servoHandTopLeft.setPosition(MID_SERVO + newY_Pos);
                robot.servoHandBottomRight.setPosition(MID_SERVO + newY_Pos);
            }

            last_y = y_pressed;

            //display the button pressed
        telemetry.addData("Claw", "A ",  gamepad2.a );
        telemetry.addData("Single Claw", "x ",   gamepad2.x );
        //telemetry.addData("newPos: ", newY_Pos);
        telemetry.update();

        idle();

    }//While OpMode Active
  }//run opMode
}//Linear OpMode