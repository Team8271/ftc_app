
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
    OurRobotHardwareSetup robot = new OurRobotHardwareSetup(); // Use a hardware setup class

    final double LEFT_SERVO_CLOSED = 0.0;
    final double LEFT_SERVO_OPEN = 1.0;
    final double RIGHT_SERVO_CLOSED = 1.0;
    final double RIGHT_SERVO_OPEN = 0.0;

    @Override
    public void runOpMode() {

/* Initialize the hardware from OurRobotHardwareSetup*/
        robot.init(hardwareMap);

// Send telemetry message to signify robot waiting;
        telemetry.addData("Hello Driver", "Waiting for Start..."); //
        telemetry.update();

        boolean xPressed = false;
        boolean yPressed = false;
        boolean topOpen = false;
        boolean bottomOpen = false;


// Wait for the game to start (driver presses PLAY)
        waitForStart();

// run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


// Main servo controls to open and close all 4 servos together
// using pushbot configuration for mirrored servos

            if (gamepad2.a) {
// Set all servos to open position
                robot.servoHandTopLeft.setPosition(LEFT_SERVO_OPEN);
                robot.servoHandBottomLeft.setPosition(LEFT_SERVO_OPEN);
                robot.servoHandTopRight.setPosition(RIGHT_SERVO_OPEN);
                robot.servoHandBottomRight.setPosition(RIGHT_SERVO_OPEN);

                topOpen = true; // sets current position of servo
                bottomOpen = true;
            } else if (gamepad2.b) {
// Set all servos to closed position
                robot.servoHandTopLeft.setPosition(LEFT_SERVO_CLOSED);
                robot.servoHandBottomLeft.setPosition(LEFT_SERVO_CLOSED);
                robot.servoHandTopRight.setPosition(RIGHT_SERVO_CLOSED);
                robot.servoHandBottomRight.setPosition(RIGHT_SERVO_CLOSED);

                topOpen = false;
                bottomOpen = false;
            } else {
                if (gamepad2.x && !xPressed) { // ifx down and wasn't previously
// Toggle top servos only
                    if (topOpen) {
                        robot.servoHandTopLeft.setPosition(LEFT_SERVO_CLOSED);
                        robot.servoHandTopRight.setPosition(RIGHT_SERVO_CLOSED);

                        topOpen = false;
                    } else {
                        robot.servoHandTopLeft.setPosition(LEFT_SERVO_OPEN);
                        robot.servoHandTopRight.setPosition(RIGHT_SERVO_OPEN);

                        topOpen = true;
                    }
                }

                if (gamepad2.y && !yPressed) {
// Toggle bottom servos only
                    if (topOpen) {
                        robot.servoHandBottomLeft.setPosition(LEFT_SERVO_CLOSED);
                        robot.servoHandBottomRight.setPosition(RIGHT_SERVO_CLOSED);

                        bottomOpen = false;
                    } else {
                        robot.servoHandBottomLeft.setPosition(LEFT_SERVO_OPEN);
                        robot.servoHandBottomRight.setPosition(RIGHT_SERVO_OPEN);

                        bottomOpen = true;
                    }
                }
            }

            xPressed = gamepad2.x; // resets so next time thru it know current state of button
            yPressed = gamepad2.y;

//display the button pressed
            telemetry.addData("Claw", "A " + String.valueOf(gamepad2.a));
            telemetry.addData("Single Claw", "x " + String.valueOf(gamepad2.x ));
//telemetry.addData("newPos: ", newY_Pos);
            telemetry.update();

            idle();

        }//While OpMode Active
    }//run opMode
}//Linear OpMode