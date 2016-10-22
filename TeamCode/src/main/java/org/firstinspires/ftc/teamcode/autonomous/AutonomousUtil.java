package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by dchotzen-hartzell19 on 10/15/16.
 */
public abstract class AutonomousUtil extends LinearOpMode {

    private final int PULSE_PER_REV         = 1120;
    private final double WHEEL_DIAMETER     = 4.0;
    private final double WHEEL_CIRCUMFERENCE= WHEEL_DIAMETER * Math.PI;
    private final double ENCODERS_PER_INCH  = PULSE_PER_REV / WHEEL_CIRCUMFERENCE;

    private final double TURN_COEFF         = 0.15;
    private final int HEADING_THRESHOLD     = 3;

    private ElapsedTime runtime = new ElapsedTime();


    public DcMotor LEFT_DRIVE,
                   RIGHT_DRIVE,
                   FLYWHEEL,
                   INTAKE;

    public Servo BUTTON_PUSHER;

    public ModernRoboticsI2cGyro GYRO_SENSOR;
    public ColorSensor COLOR_SENSOR;

    public void runOpMode() {
        // Initialize everything
        LEFT_DRIVE = hardwareMap.dcMotor.get("left_drive");
        RIGHT_DRIVE = hardwareMap.dcMotor.get("right_drive");
        FLYWHEEL = hardwareMap.dcMotor.get("flywheel");
        BUTTON_PUSHER = hardwareMap.servo.get("button_pusher");
        GYRO_SENSOR = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro_sensor");
        COLOR_SENSOR = hardwareMap.colorSensor.get("color_sensor");

        RIGHT_DRIVE.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData(">", "Calibrating");
        telemetry.update();

        GYRO_SENSOR.calibrate();

        while (!isStopRequested() && GYRO_SENSOR.isCalibrating())  {
            sleep(50);
            idle();
        }

        LEFT_DRIVE.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RIGHT_DRIVE.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData(">", "Finished Calibrating");
        telemetry.update();

        waitForStart();
        runProgram();
        while (opModeIsActive()) {
            telemetry.addData(">", "Robot Heading = %d", GYRO_SENSOR.getIntegratedZValue());
            telemetry.update();
            idle();
        }

    }

    protected void drive(double inches, double angle, double speed, double timeout) {

        double  error,
                turn,
                leftSpeed,
                rightSpeed,
                max;


        if (opModeIsActive()) {
            inches /= WHEEL_CIRCUMFERENCE;
            inches *= PULSE_PER_REV;

            int left_target = LEFT_DRIVE.getCurrentPosition() + (int)inches;
            int right_target = RIGHT_DRIVE.getCurrentPosition() + (int)inches;

            LEFT_DRIVE.setTargetPosition(left_target);
            RIGHT_DRIVE.setTargetPosition(right_target);

            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            LEFT_DRIVE.setPower(speed);
            RIGHT_DRIVE.setPower(speed);

            while (opModeIsActive() && LEFT_DRIVE.isBusy() && RIGHT_DRIVE.isBusy()) {

                error = getError(angle);
                turn = -getTurnCoef(error);

                if (inches < 0) {
                    speed *= -1;
                }

                leftSpeed = speed + turn;
                rightSpeed = speed - turn;

                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0) {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                LEFT_DRIVE.setPower(leftSpeed);
                RIGHT_DRIVE.setPower(rightSpeed);


                telemetry.addData("Err | Trn", "%5.1f | %5.1f",  error, turn);
                telemetry.addData("Target", "%7d | %7d", left_target, right_target);
                telemetry.addData("Current", "%7d | %7d", LEFT_DRIVE.getCurrentPosition(),
                        RIGHT_DRIVE.getCurrentPosition());
                telemetry.addData("IMotor pwr", "%5.2f | %5.2f", leftSpeed, rightSpeed);
                telemetry.addData("RMotor pwr", "%5.2f | %5.2f", LEFT_DRIVE.getPower(), rightSpeed);
                telemetry.update();
            }

            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);

            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    protected void singleTurn(double speed, int angle) {
        while (opModeIsActive() && !onHeading()) {

        }
    }

    protected void doubleTurn(double speed, )

    private boolean onHeading(double speed, int angle) {

        return true;
    }



    protected void turnToGyro(double heading, double speed) {
        // Decide if left or right turn
        double error = getError(heading);
    }

    private double getError(double targetAngle) {
        double error;
        error = targetAngle - GYRO_SENSOR.getIntegratedZValue();
        while (error > 180) error -= 360;
        while (error <= -180) error += 360;
        return error;
    }

    private double getTurnCoef(double error) {
        return Range.clip(error * TURN_COEFF, -1.0, 1.0);
    }


    protected void senseForward() {

    }

    protected void senseBackward() {

    }

    public abstract void runProgram();
}
