
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
 * It raises and lowers the arm using the Gampad2 joystick.y
 * used armMotor encoder to hold arm position when up/down command released
 * It also opens and closes the claws slowly using the a/b buttons
 * and toggles open/close each upper/lower set of servos using x/y buttons
 *
 */

@TeleOp(name="MrR TeleOp", group="Concept")
//@Disabled
public class MrRTeleopPOV_ToggleGrabbers extends LinearOpMode {

    /* Declare Hardware */
    OurRobotHardwareSetup robot = new OurRobotHardwareSetup();   // Use a hardware setup class

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        /* Initialize the hardware from OurRobotHardwareSetup*/
        robot.init(hardwareMap);


        double LEFT_SERVO_CLOSED = 0.8;// The bigger the number the tighter the grasp of the servo
        double LEFT_SERVO_OPEN = 0.2;

        double RIGHT_SERVO_CLOSED = 0.2;// The smaller the number, the tighter the grasp of the servo
        double RIGHT_SERVO_OPEN = 0.8;

        //variables used in toggle button setup
        boolean xpressed = false;
        boolean ypressed = false;
        boolean topOpen = false;
        boolean bottomOpen = false;

        // variables used to monitor arm position to maintain limits and hold position
        double armMinPos = -50.0;     // encoder position for arm at bottom
        double armMaxPos = 1600.0; // encoder position for arm at top
        int armHoldPosition;        // reading of arm position when buttons released to hold
        double slopeVal = 3500.0;   // increase or decrease to perfect

        armHoldPosition = robot.motorArm.getCurrentPosition(); // set hold position to current position

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Hello Driver", "Waiting for Start...");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
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
            double turn  = ( gamepad1.right_stick_x);

            // Clip joystick values to be withing the range of the allowable motor power levels
            // and sets left/rightPower based on joystick x/y combinations
            leftPower = Range.clip(drive + turn, -1.0, 1.0);
            rightPower = Range.clip(drive - turn, -1.0, 1.0);

            //Create button that halves values in which makes for more accurate movements.
            int div = 0;
            if (gamepad1.right_bumper) { // while R-Trigger pressed motor power is cut in half
                div = 2;
            } else {
                div = 1;
            }

            // Finall, Set motor powers from above calculations
            robot.motorLeft.setPower(leftPower / div);
            robot.motorRight.setPower(rightPower / div);


            // Arm Control -    Uses left_stick_y to control motor direction.
            //          Note:   joystick values are reversed to common thought
            //                  Neg value when stick up, Pos when stick down (to reverse this you'd need to make all gamepad values negative)
            // Uses Encoder values to set upper and lower limits to protect motors from over-driving lift  ** not working
            // and to hold arm position on decent to account for gravitational inertia
            //** joystick reads neg when forward therefore values are changed to neg in order to match reversed motor configuration

            if (gamepad2.left_stick_y < 0.0 ) // && robot.motorArm.getCurrentPosition() > armMinPos
            {
                robot.motorArm.setPower(-gamepad2.left_stick_y); // let stick drive UP (note this is neg value on joystick)
                armHoldPosition = robot.motorArm.getCurrentPosition(); // while the lift is moving, continuously reset the arm holding position
            } else if (gamepad2.left_stick_y > 0.0 ) //&& robot.motorArm.getCurrentPosition() < armMaxPos
            {
                robot.motorArm.setPower(-gamepad2.left_stick_y/4); //let stick drive DOWN (note this is pos value on joystick)
                armHoldPosition = robot.motorArm.getCurrentPosition(); // while the lift is moving, continuously reset the arm holding position

            } else //triggers are released - set power to maintain the current position
            {
                robot.motorArm.setPower((double) ( armHoldPosition - robot.motorArm.getCurrentPosition()) / slopeVal);   // Note that if the lift is lower than desired position,
                // the subtraction will be positive and the motor will
                // attempt to raise the lift. If it is too high it will
                // be negative and thus try to lower the lift
                // adjust slopeVal to achieve perfect hold power
            }


            // Servo Controls
            // Uses buttons a,b,x,y
            // to control four 'Hand' Servos
            // together or independently
            if (gamepad2.a) //button 'a' will open servos
            {
                robot.servoHandTopRight.setPosition(RIGHT_SERVO_OPEN);
                robot.servoHandTopLeft.setPosition(LEFT_SERVO_OPEN);
                robot.servoHandBottomRight.setPosition(RIGHT_SERVO_OPEN);
                robot.servoHandBottomLeft.setPosition(LEFT_SERVO_OPEN);

                topOpen = true;
                bottomOpen = true; //update the current position of the servos

            } else if (gamepad2.b) //button 'b' will close servos
            {
                robot.servoHandTopRight.setPosition(RIGHT_SERVO_CLOSED);
                robot.servoHandTopLeft.setPosition(LEFT_SERVO_CLOSED);
                robot.servoHandBottomRight.setPosition(RIGHT_SERVO_CLOSED);
                robot.servoHandBottomLeft.setPosition(LEFT_SERVO_CLOSED);

                topOpen = false;
                bottomOpen = false; // update current position of the servos.

            } else {
                if (gamepad2.y && !ypressed) {
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

                if (gamepad2.x && !xpressed) {
                    if (bottomOpen) {
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
            // reset variables to current button reading
            xpressed = gamepad2.x;
            ypressed = gamepad2.y;

            // Display running time and Encoder value
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("ArmPosition: ", + robot.motorArm.getCurrentPosition());
            telemetry.addData("ArmPower: ","(%.2f)", + robot.motorArm.getPower());
            telemetry.addData("Claw", "A-Open" + String.valueOf(gamepad2.a));
            telemetry.addData("Claw", "B-Close" + String.valueOf(gamepad2.b));
            telemetry.addData("Single Claw", "Y-Top" + String.valueOf(gamepad2.y));
            telemetry.addData("Single Claw", "X-Bottom" + String.valueOf(gamepad2.x));
            telemetry.addData("DriveMotors", "left (%.2f), right (%.2f)", leftPower, rightPower);

            telemetry.update();

            idle();

        }//While OpMode Active loop
    }//run opMode
}//Linear OpMode
