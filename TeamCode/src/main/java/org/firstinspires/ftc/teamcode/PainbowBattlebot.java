
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
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

@TeleOp(name="Painbow", group="Competition")
//@Disabled
public class PainbowBattlebot extends LinearOpMode {

    /* Declare Hardware */
    painbowHardwareSetup robot = new painbowHardwareSetup();   // Use a hardware setup class

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        /* Initialize the hardware from OurRobotHardwareSetup*/
        robot.init(hardwareMap);


        double LEFT_SERVO_CLOSED = 0.9;// The bigger the number the tighter the grasp of the servo
        double LEFT_SERVO_OPEN = 0.5;

        double RIGHT_SERVO_CLOSED = 0.1;// The smaller the number, the tighter the grasp of the servo
        double RIGHT_SERVO_OPEN = 0.5;

        boolean xpressed = false;
        boolean ypressed = false;
        boolean topOpen = false;
        boolean bottomOpen = false;

        double armMinPos = 0.0;      // encoder position for arm at bottom
        double armMaxPos = -5380.0;   // encoder position for arm at top
        int armHoldPosition;             // reading of arm position when buttons released to hold
        double slopeVal = 3500.0;   // increase or decrease to perfect

        armHoldPosition = robot.motorArm.getCurrentPosition();

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
            double drive = (gamepad1.left_stick_y);
            double turn = (-gamepad1.right_stick_x);

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


            // Display running time and Encoder value
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("ArmPosition: ", +robot.motorArm.getCurrentPosition());
            telemetry.addData("Claw", "A-Open" + String.valueOf(gamepad2.a));
            telemetry.addData("Claw", "B-Close" + String.valueOf(gamepad2.b));
            telemetry.addData("Single Claw", "Y-Top" + String.valueOf(gamepad2.y));
            telemetry.addData("Single Claw", "X-Bottom" + String.valueOf(gamepad2.x));
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);

            telemetry.update();

            // Arm Control - Uses left_stick_y to control motor direction.
            //          Note:   joystick values are reversed to common thought
            //                  Neg value when stick up, Pos when stick down (to reverse this you'd need to make all gamepad values negative)

            // Uses Encoder values to set upper and lower limits to protect motors from over-driving lift
            // and to hold arm position on decent to account for gravitational inertia

            if (gamepad1.right_stick_y > 0.0 ) // joystick down
            {
                robot.motorArm.setPower(-gamepad1.right_stick_y/3); // let stick drive UP (note this is positive value on joystick)
                armHoldPosition = robot.motorArm.getCurrentPosition(); // while the lift is moving, continuously reset the arm holding position

            } else if (gamepad1.right_stick_y < 0.0 ) //joystick up
            {
                robot.motorArm.setPower(-gamepad1.right_stick_y); //let stick drive DOWN (note this is negative value on joystick)
                armHoldPosition = robot.motorArm.getCurrentPosition(); // while the lift is moving, continuously reset the arm holding position
            } else //triggers are released - try to maintain the current position
            {
                robot.motorArm.setPower((double) ( armHoldPosition - robot.motorArm.getCurrentPosition()) / slopeVal);   // Note that if the lift is lower than desired position,
                // the subtraction will be positive and the motor will
                // attempt to raise the lift. If it is too high it will
                // be negative and thus try to lower the lift
                // adjust slopeVal to acheived perfect hold power
            }


            // Servo Controls
            // Uses buttons a,b,x,y
            // to control four 'Hand' Servos
            // together or independently
            if (gamepad2.a) //button 'a' will open servos
                {


                    topOpen = true;
                    bottomOpen = true; //update the current position of the servos

                } else if (gamepad2.b) //button 'b' will close servos
                {


                    topOpen = false;
                    bottomOpen = false; // update current position of the servos.

                } else {
                    if (gamepad2.y && !ypressed) {
                        if (topOpen) {


                            topOpen = false;
                        } else {


                            topOpen = true;
                        }

                    }


            }

            xpressed = gamepad2.x;
            ypressed = gamepad2.y;



            idle();



            // Pace this loop so jaw action is reasonable speed.
            //sleep(50); not sure this is needed


        }//While OpMode Active
    }//run opMode
}//Linear OpMode
