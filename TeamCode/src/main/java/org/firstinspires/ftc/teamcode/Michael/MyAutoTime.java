package org.firstinspires.ftc.teamcode.Michael;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by user on 4/27/2018.
 */

@Autonomous(name="Pushbot: Auto Drive By Time", group="Pushbot")
//@Disabled
public class MyAutoTime extends LinearOpMode {

    /* Declare OpMode members. */
    MyHardWareConfigure robot   = new MyHardWareConfigure();   // Use a Pushbot's hardware

    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Close Servos
        robot.leftServo.setPosition(0.56);
        robot.rightServo.setPosition(0.52);
        sleep(100);

        // run Lift up
        robot.liftMotor.setPower(-FORWARD_SPEED);
        sleep(100);
        robot.liftMotor.setPower(0.0);
        sleep(3000);

        //Drive forward for 3 seconds, and then stops for 1 second
        robot.leftMotor.setPower(-FORWARD_SPEED);
        robot.rightMotor.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Legk1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        robot.leftMotor.setPower(0.0);
        robot.rightMotor.setPower(0.0);
        sleep(1000);

        // Spin right for 1.3 seconds
        robot.leftMotor.setPower(TURN_SPEED);
        robot.rightMotor.setPower(-TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        sleep(1000);

        // Step 3:  Drive Backwards for 1 Second
        robot.leftMotor.setPower(FORWARD_SPEED);
        robot.rightMotor.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.5)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 4:  Stop and close the claw.
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        sleep(1000);
        robot.leftServo.setPosition(0.45);
        robot.rightServo.setPosition(0.7);
        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}