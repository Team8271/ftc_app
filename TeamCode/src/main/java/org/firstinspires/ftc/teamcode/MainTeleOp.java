/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file is our main teleOp
 */

@TeleOp(name="Main TeleOp", group="Competition")  // @Autonomous(...) is the other common choice
//@Disabled
public class MainTeleOp extends LinearOpMode { /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    //Declare robot hardware
    Our_HardwareSetup robot      = new Our_HardwareSetup();

    @Override
    public void runOpMode() throws InterruptedException {

        //initialize robot hardware
        robot.init(hardwareMap);

        //adds feedback telemetry to DS
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        // initialize current position of arm motor
        robot.liftHoldPosition = robot.motorLift.getCurrentPosition();


        /************************
         * TeleOp Code Below://
         *************************/
        while (opModeIsActive()) {  // run until the end of the match (driver presses STOP)
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("LiftPosition: ", + robot.motorLift.getCurrentPosition());
            telemetry.addData("LiftHoldPosition: ", + robot.liftHoldPosition);

            telemetry.update();

            // tank drive set to gamepad1 joysticks
            //(note: The joystick goes negative when pushed forwards)
            robot.motorLeft.setPower(gamepad1.left_stick_y /4);
            robot.motorRight.setPower(gamepad1.right_stick_y /4);


            // Sweep Control - Uses a,b,y, to contol

            if (gamepad2.a)  // using 0.2 instead of 0.0 as a threshold in case the trigger does not fully release
            {
                robot.motorSweep.setPower(1.0); //foward
            }
            if (gamepad2.b)
            {
                robot.motorSweep.setPower(-1.0); //reverse
            }
            if (gamepad2.y)
            {
                robot.motorSweep.setPower(0.0); //stop
            }
            // Lift Control - Uses dual buttons to control motor direction && encoder to hold position

            if (gamepad2.left_bumper && gamepad2.left_trigger > 0.2)  // using 0.2 instead of 0.0 as a threshold in case the trigger does not fully release
            {
                robot.motorLift.setPower(-gamepad2.left_trigger); // if both Bumper + Trigger, then negative power, runs lift down
                robot.liftHoldPosition = robot.motorLift.getCurrentPosition(); // update position
            }
            else if (!gamepad2.left_bumper && gamepad2.left_trigger > 0.2)
            {
                robot.motorLift.setPower(gamepad2.left_trigger);  // else trigger positive value, runs arm up
                robot.liftHoldPosition = robot.motorLift.getCurrentPosition(); // update position
            }
            else
            {
                //hold motor position
                robot.motorLift.setPower((double)(robot.liftHoldPosition - robot.motorLift.getCurrentPosition()) / robot.slopeVal );
            }


            /*//servo commands
            if(gamepad2.a) //button 'a' will open
            {
                robot.servoHand.setPosition(robot.OPEN);
            }
            else if (gamepad2.b) //button 'b' will close
            {
                robot.servoHand.setPosition(robot.CLOSED);
            }
*/
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
