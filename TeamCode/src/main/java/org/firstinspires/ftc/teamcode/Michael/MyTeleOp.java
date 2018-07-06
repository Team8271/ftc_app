package org.firstinspires.ftc.teamcode.Michael;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/**
 * Created by user on 4/27/2018.
 * The Program That controls the "Robot" with TeleOp
 */


@TeleOp(name="MyTeleOp", group="PushBot")
@Disabled
    public class MyTeleOp extends LinearOpMode {

        MyHardWareConfigure robot       = new MyHardWareConfigure();

        @Override
        public void runOpMode() {

            robot.init(hardwareMap);

            telemetry.addData("Status", "Initialized");
            telemetry.update();
            // Wait for the game to start (driver presses PLAY)
            waitForStart();

            // run until the end of the match (driver presses STOP)
            double tgtPower_Left = 0;
            double tgtPower_Right = 0;
            double tgtPower_ServoLeft = 0;
            double tgtPower_ServoRight = 0;
            double tgtPower_Lift = 0;


            while (opModeIsActive()) {
                //sets left motor
                tgtPower_Left = this.gamepad1.left_stick_y;
                robot.leftMotor.setPower(tgtPower_Left);

                //set right motor
                tgtPower_Right = this.gamepad1.right_stick_y;
                robot.rightMotor.setPower(tgtPower_Right);

                //control Lift motor


                robot.liftMotor.setPower(-this.gamepad1.right_trigger); //up
                //robot.liftMotor.setPower(-this.gamepad1.left_trigger/3); //down

                if (robot.touch.getState() == true) {
                    telemetry.addData("Digital Touch", "Is Not Pressed");
                    robot.liftMotor.setPower(this.gamepad1.left_trigger/2); //down
                } else {
                    telemetry.addData("Digital Touch", "Is Pressed");
                    robot.liftMotor.setPower(-0.1);
                }


                // smaller # makes right close, and bigger # on the left makes it close
                if (gamepad1.y) {
                    // fully closed
                    robot.leftServo.setPosition(0.9);
                    robot.rightServo.setPosition(0.1);
                } else if (gamepad1.b) {
                    // open
                    robot.leftServo.setPosition(0.45);
                    robot.rightServo.setPosition(0.7);
                } else if (gamepad1.a) {
                    // closed
                    robot.leftServo.setPosition(0.56);
                    robot.rightServo.setPosition(0.52);
                }

                telemetry.addData("Status", "Running");
                telemetry.update();

            }
        }
    }
