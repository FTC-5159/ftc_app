package org.firstinspires.ftc.teamcode.zoldstuff.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Class to handle the drive train. Encoders part not yet tested.
 * @author Devin Chotzen-Hartzell
 */
public class DriveMotors implements Toggleable {

    private DcMotor LEFT_DRIVE;
    private DcMotor RIGHT_DRIVE;

    private final int PULSE_PER_REV = 1120;
    private final float WHEEL_DIAMETER = 4.0F;
    private final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

    public boolean reversed = false;

    /**
     * Constructor for the object. Initializes variables and theoretically screams at you if you
     * did something bad in the configuration.
     */
    public DriveMotors() {
        LEFT_DRIVE = RobotUtil.hardwareMap.dcMotor.get("left_drive");
        RIGHT_DRIVE = RobotUtil.hardwareMap.dcMotor.get("right_drive");

        LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        try {
            new ToggleButton("y", 1, this);
        } catch (NoSuchFieldException e) {
            RobotUtil.telemetry.addData("Error:", e.toString());
        }
    }

    /**
     * Changes the status of encoders in the motors.
     * @param b Whether or not to enable encoders
     */
    public void setEncoders(boolean b) throws InterruptedException {
        if (!(RobotUtil.opMode instanceof LinearOpMode)) return;
        LinearOpMode opMode = (LinearOpMode)RobotUtil.opMode;
        if (b) {
            LEFT_DRIVE.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            opMode.idle();
            runToPosition(false);
        } else {
            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Turns on/off run to position mode
     * @param b     Mode on or off
     */
    public void runToPosition(boolean b) {
        if (!b) {
            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    /**
     * Drive with encoders
     * @param pwr           Power at which the motors will run
     * @param l_distance    Distance (in) for the left motor
     * @param r_distance    Distance (in) for the right motor
     */
    public void drive(float pwr, double l_distance, double r_distance, double timeout)
            throws InterruptedException {
        if (!(RobotUtil.opMode instanceof LinearOpMode)) return;
        LinearOpMode opMode = (LinearOpMode)RobotUtil.opMode;
        // turn encoders on
        setEncoders(true);
        opMode.idle();
        runToPosition(true);
        opMode.resetStartTime();
        // set motor pwr to correct values
        LEFT_DRIVE.setPower(Range.clip(pwr, 0.0, 1.0));
        RIGHT_DRIVE.setPower(Range.clip(pwr, 0.0, 1.0));
        // set target positions for encoders
        LEFT_DRIVE.setTargetPosition(calcEncoders(l_distance) + LEFT_DRIVE.getCurrentPosition());
        RIGHT_DRIVE.setTargetPosition(calcEncoders(r_distance) + RIGHT_DRIVE.getCurrentPosition());
        // wait until everything is done before returning, update telemetry
        while (isBusy() && opMode.opModeIsActive() && opMode.getRuntime() < timeout) {
            RobotUtil.telemetry.addData("Path1", "Running to %7d, %7d", l_distance, r_distance);
            RobotUtil.telemetry.addData("Path2", "Running at %7d %7d",
                    LEFT_DRIVE.getCurrentPosition(), RIGHT_DRIVE.getCurrentPosition());
            RobotUtil.telemetry.update();
            opMode.idle();
        }

        // stop motors
        LEFT_DRIVE.setPower(0);
        RIGHT_DRIVE.setPower(0);

        runToPosition(false);
        // turn off encoders
        setEncoders(false);
    }

    /**
     * Calculates the encoder value for the specified distance
     * @param dist  Distance to calculate the encoder value for
     * @return      The number of encoder pulses needed
     */
    public int calcEncoders(double dist) {
        return (int)(dist / WHEEL_CIRCUMFERENCE) * PULSE_PER_REV;
    }

    /**
     * Drives the motors without encoders
     * @param l_pwr     Power of the left motor
     * @param r_pwr     Power of the right motor
     */
    public void drive(float l_pwr, float r_pwr) throws InterruptedException {
        setEncoders(false);
        LEFT_DRIVE.setPower(l_pwr);
        RIGHT_DRIVE.setPower(r_pwr);
    }

    /**
     * Reverses the direction of the drive motors for teleOP mode
     */
    public void onToggle() {
        reversed = !reversed;
    }

    /**
     * Called each hardware cycle to get the data from the joysticks
     * and update the motor power relative to them. Reverses if needed.
     */
    public void update() throws InterruptedException {
        drive(reversed? -RobotUtil.gamepad1.left_stick_x: RobotUtil.gamepad1.left_stick_x,
                reversed? -RobotUtil.gamepad1.right_stick_x: RobotUtil.gamepad1.right_stick_x);
    }

    /**
     * For autonomous: Checks if motors are running already
     * @return Whether the motors are doing something or not
     */
    public boolean isBusy() {
        return LEFT_DRIVE.isBusy() || RIGHT_DRIVE.isBusy();
    }
}
