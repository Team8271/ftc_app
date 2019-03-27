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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear Autonomous "OpMode".
 *
 * This particular OpMode just executes a basic Autonomous driving for time, not using encoders
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Don't forget to comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Depot AutoByTime", group="comp")  // @TeleOp(...) is the other common choice
//@Disabled
public class OurAutoDriveByTimev2 extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
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
         * Autonomous Code Below://
         *************************/
        RaiseArm(1.0, 2760);
        StopDriving();

        DriveForwardTime(1.0, 2000);
        StopDriving();
        DriveForwardTime(-1.0, 1500);
        Sweeper(1.0, 1000);



        StopDriving();

    }

    /** Below: Basic Drive Methods used in Autonomous code...**/
    //set Drive Power variable
    double DRIVE_POWER = 1.0;

    public void DriveForward(double power)
    {
        robot.motorLeft.setPower(power);
        robot.motorRight.setPower(power);
    }

    public void DriveForwardTime(double power, long time) throws InterruptedException
    {
        DriveForward(power);
        Thread.sleep(time);
    }

    public void StopDriving()
    {
        DriveForward(0);
    }

    public void StopDrivingTime(long time) throws InterruptedException
    {
        DriveForwardTime(0, time);
    }

    public void TurnLeft(double power, long time) throws InterruptedException
    {
        robot.motorLeft.setPower(power);
        robot.motorRight.setPower(-power);
        Thread.sleep(time);
    }

    public void TurnRight(double power, long time) throws InterruptedException
    {
        TurnLeft(-power, time);
    }

    public void RaiseArm(double power, long time)throws InterruptedException
    {
        robot.motorLift.setPower(power);
        Thread.sleep(time);
        robot.motorLift.setPower(0) ;
    }

    public void LowerArm(double power, long time)throws InterruptedException
    {
        robot.motorLift.setPower(-power);
        Thread.sleep(time);
        robot.motorLift.setPower(0) ;
    }

    public void Sweeper(double power, long time)throws InterruptedException
    {
        robot.motorSweep.setPower(-power);
        Thread.sleep(time);
        robot.motorSweep.setPower(0);
    }

}//TestAutoDriveByTime
