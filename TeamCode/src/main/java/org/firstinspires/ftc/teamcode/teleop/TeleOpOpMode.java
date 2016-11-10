package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * Tele Op
 * Controls:
 * Joysticks to move
 * X - Switch Direction
 * Y - Push button
 * A -
 */
@TeleOp(name="Main TeleOP", group="Stuff")
public class TeleOpOpMode extends OpMode {

    DcMotor LEFT_DRIVE,
            RIGHT_DRIVE,
            LIFT,
            FLYWHEEL;

    Servo   BUTTON,
            STOPPER;

    //ModernRoboticsI2cGyro GYRO_SENSOR;

    boolean driveReversed = false;

    boolean X_alreadyPressed,
            A_alreadyPressed,
            Y_alreadyPressed;

    private ElapsedTime runtime = new ElapsedTime();

    public void init() {
        LEFT_DRIVE = hardwareMap.dcMotor.get("left_drive");
        RIGHT_DRIVE = hardwareMap.dcMotor.get("right_drive");
        RIGHT_DRIVE.setDirection(DcMotor.Direction.REVERSE);

        LIFT = hardwareMap.dcMotor.get("intake");

        FLYWHEEL = hardwareMap.dcMotor.get("flywheel");

        BUTTON = hardwareMap.servo.get("button_pusher");
        STOPPER = hardwareMap.servo.get("stopper");
        telemetry.addLine("Test");

        /*
        GYRO_SENSOR = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro_sensor");
        GYRO_SENSOR.calibrate();
        telemetry.addData(">", "Calibrating");
        telemetry.update();
        while(GYRO_SENSOR.isCalibrating()) {
            try {
                wait(50);
            } catch(Exception e) {}
        }
        telemetry.addData(">", "Finished Calibrating");
        telemetry.update();
        */

    }

    public void loop() {
        try {
            // Drive / reversed?
            if (gamepad1.x) {
                if (!X_alreadyPressed) {
                    driveReversed = !driveReversed;
                    X_alreadyPressed = true;
                }
            } else {
                X_alreadyPressed = false;
            }

            if (!driveReversed) {
                LEFT_DRIVE.setPower(gamepad1.right_stick_y);
                RIGHT_DRIVE.setPower(gamepad1.left_stick_y);
            } else {
                LEFT_DRIVE.setPower(-gamepad1.left_stick_y);
                RIGHT_DRIVE.setPower(-gamepad1.right_stick_y);
            }

            if (gamepad2.right_trigger > 0.3)
                STOPPER.setPosition(1);
            else
                STOPPER.setPosition(0.7);

            if (gamepad2.left_trigger > 0.3) BUTTON.setPosition(1);
            else BUTTON.setPosition(0);

            if (gamepad2.a) {
                if (!A_alreadyPressed) {

                    if (FLYWHEEL.getPower() < 0.2)
                        FLYWHEEL.setPower(1);
                    else
                        FLYWHEEL.setPower(0);

                    A_alreadyPressed = true;
                }
            } else {
                A_alreadyPressed = false;
            }

            if (gamepad2.dpad_up) {
                LIFT.setPower(Range.clip(LIFT.getPower() + 1, -1, 1));
            } else if (gamepad2.dpad_down) {
                LIFT.setPower(Range.clip(LIFT.getPower() - 1, -1, 1));
            } else {
                LIFT.setPower(0);
            }

            telemetry.addData("Drive Pwr", "%5.2f | %5.2f", LEFT_DRIVE.getPower(), RIGHT_DRIVE.getPower());
            telemetry.addData("F | I", "%5.2f | %5.2f", FLYWHEEL.getPower(), LIFT.getPower());

            try {
                if (gamepad2.y) {
                    if (!Y_alreadyPressed) {
                        // shoot
                        FLYWHEEL.setPower(1);
                        Thread.sleep(500L);
                        STOPPER.setPosition(1);
                        Thread.sleep(200L);
                        LIFT.setPower(1);

                        Y_alreadyPressed = true;
                    }
                } else {
                    Y_alreadyPressed = false;
                }
            } catch (InterruptedException e) {
            }
        } catch (Exception e) {}
    }



}
