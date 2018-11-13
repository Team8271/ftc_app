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
 * This file contains an minimal example of a Linear Tele "OpMode".
 *
 * This particular OpMode just executes a basic Tank Drive, Arm and 2 Servos for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Comment out the @Disabled line to add this opmode to the Driver Station OpMode list
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

        /************************
         * TeleOp Code Below://
         *************************/

        while (opModeIsActive()) {  // run until the end of the match (driver presses STOP)
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            //read arm position


            // tank drive set to gamepad1 joysticks
            //(note: The joystick goes negative when pushed forwards)
            robot.motorLeft.setPower(gamepad1.left_stick_y /4);
            robot.motorRight.setPower(gamepad1.right_stick_y /4);

            // Arm Control - Uses dual buttons to control motor direction && encoder to hold position
            robot.armHoldPosition = robot.motorArm.getCurrentPosition();
            
            if(gamepad2.right_bumper)
            {
                robot.motorArm.setPower(-gamepad2.right_trigger); // if both Bumper + Trigger, then negative power, runs arm down
                robot.armHoldPosition = robot.motorArm.getCurrentPosition(); // update position
            }
            else if (!gamepad2.right_bumper)
            {
                robot.motorArm.setPower(gamepad2.right_trigger);  // else trigger positive value, runs arm up
                robot.armHoldPosition = robot.motorArm.getCurrentPosition(); // update position
            }
            else
            {
                //hold motor position
                robot.motorArm.setPower((double)(robot.armHoldPosition - robot.motorArm.getCurrentPosition()) / robot.slopeVal );
            }


            //servo commands
            if(gamepad2.a) //button 'a' will open
            {
                robot.servoHandR.setPosition(robot.OPEN);
            }
            else if (gamepad2.b) //button 'b' will close
            {
                robot.servoHandR.setPosition(robot.OPEN);
            }







            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
