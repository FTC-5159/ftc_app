package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public abstract class AutonomousUtil extends LinearOpMode {

    private final int PULSE_PER_REV         = 1120;
    private final double WHEEL_DIAMETER     = 4.0;
    private final double WHEEL_CIRCUMFERENCE= WHEEL_DIAMETER * Math.PI;
    //private final double ENCODERS_PER_INCH  = PULSE_PER_REV / WHEEL_CIRCUMFERENCE;

    private final double TURN_COEFF         = 0.1;
    private final int HEADING_THRESHOLD     = 5;

    private ElapsedTime runtime = new ElapsedTime();


    public DcMotor LEFT_DRIVE,
                   RIGHT_DRIVE,
                   FLYWHEEL,
                   INTAKE;

    public Servo BUTTON_PUSHER;

    public ModernRoboticsI2cGyro GYRO_SENSOR;
    public ColorSensor COLOR_SENSOR;

    public TouchSensor TOUCH_SENSOR;

    public void runOpMode() {
        // Initialize everything
        LEFT_DRIVE = hardwareMap.dcMotor.get("left_drive");
        RIGHT_DRIVE = hardwareMap.dcMotor.get("right_drive");
        FLYWHEEL = hardwareMap.dcMotor.get("flywheel");
        INTAKE = hardwareMap.dcMotor.get("intake");
        BUTTON_PUSHER = hardwareMap.servo.get("button_pusher");
        GYRO_SENSOR = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro_sensor");
        COLOR_SENSOR = hardwareMap.colorSensor.get("color_sensor");
        TOUCH_SENSOR = hardwareMap.touchSensor.get("touch_sensor");

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
        try {
            runProgram();
        } catch (Exception e) {}
        while (opModeIsActive()) {
            telemetry.addData(">", "Robot Heading %s", GYRO_SENSOR.getIntegratedZValue());
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
            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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


                int posDiff = LEFT_DRIVE.getCurrentPosition() - RIGHT_DRIVE.getCurrentPosition();
                turn = Range.clip(posDiff * 0.005, -1, 1);

                LEFT_DRIVE.setPower(speed - turn);
                RIGHT_DRIVE.setPower(speed + turn);


                //telemetry.addData("Err | Trn", "%5.1f | %5.1f",  error, turn);
                telemetry.addData("Target", "%7d | %7d", left_target, right_target);
                telemetry.addData("Current", "%7d | %7d", LEFT_DRIVE.getCurrentPosition(),
                        RIGHT_DRIVE.getCurrentPosition());
                //telemetry.addData("IMotor pwr", "%5.2f | %5.2f", leftSpeed, rightSpeed);
                telemetry.addData("RMotor pwr", "%5.2f | %5.2f", LEFT_DRIVE.getPower(), RIGHT_DRIVE.getPower());
                telemetry.update();
            }

            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);

            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    protected void singleTurn(double speed, int angle) {
        double startErr = getError(angle);
        while (opModeIsActive() && !onHeadingSingle(speed, angle, startErr)) {
            telemetry.update();
        }
    }

    protected void doubleTurn(double speed, int angle) {
        while (opModeIsActive() && !onHeadingDouble(speed, angle)) {
            telemetry.update();
        }
    }

    private boolean onHeadingSingle(double speed, int angle, double startErr) {
        double error = getError(angle);

        if ((startErr > 0 && error < 5) || (startErr < 0 && error > -5)) {
            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);
            return true;
        }

        if (error < 0) {
            LEFT_DRIVE.setPower(speed);
            RIGHT_DRIVE.setPower(0);
        } else {
            RIGHT_DRIVE.setPower(speed);
            LEFT_DRIVE.setPower(0);
        }
        telemetry.addData("Error", error);
        return false;

    }

    private boolean onHeadingDouble(double speed, int angle) {
        double error = getError(angle);

        // Stop motors and stuff
        if (Math.abs(error) < HEADING_THRESHOLD) {
            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);
            return true;
        }

        if (error < 0) {
            LEFT_DRIVE.setPower(speed);
            RIGHT_DRIVE.setPower(-speed);
            telemetry.addData("Turning", "Right");
        } else {
            RIGHT_DRIVE.setPower(speed);
            LEFT_DRIVE.setPower(-speed);
            telemetry.addData("Turning", "Left");
        }
        return false;

    }

    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - GYRO_SENSOR.getIntegratedZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;

    }

    public void gyroHold( double speed, double angle, double holdTime) {

        if (opModeIsActive()) {
            ElapsedTime holdTimer = new ElapsedTime();

            // keep looping while we have time remaining.
            holdTimer.reset();
            double startErr = getError(angle);
            while (opModeIsActive() && (holdTimer.time() < holdTime)) {
                // Update telemetry & Allow time for other processes to run.
                onHeadingSingle(speed, (int)angle, getError(angle));
                telemetry.update();
            }

            // Stop all motion;
            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);
        }
    }

    private double getTurnCoef(double error) {
        return Range.clip(error * TURN_COEFF, -1.0, 1.0);
    }

    protected void driveUntilTouch(double pwr) {
        if (opModeIsActive()) {
            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            LEFT_DRIVE.setPower(pwr);
            RIGHT_DRIVE.setPower(pwr);

            while (!TOUCH_SENSOR.isPressed() && opModeIsActive()) {
                telemetry.addData("Motor Pwr", "%5.2f | %5.2f", LEFT_DRIVE.getPower(), RIGHT_DRIVE.getPower());
                telemetry.addLine("Driving until touch");
                telemetry.update();
            }

            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);

            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            telemetry.clearAll();
        }
    }

    protected void senseRed(double pwr) throws InterruptedException {
        runtime.reset();
        if (opModeIsActive()) {
            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            LEFT_DRIVE.setPower(pwr);
            RIGHT_DRIVE.setPower(pwr);

            while (!(COLOR_SENSOR.blue() <= 1 && COLOR_SENSOR.red() > COLOR_SENSOR.blue() && COLOR_SENSOR.red() >= 2 && opModeIsActive())) {
                telemetry.addData("Color", "%s | %s | %s", COLOR_SENSOR.red(), COLOR_SENSOR.green(), COLOR_SENSOR.blue());

            }
            //telemetry.addData("Time", runtime.seconds());

            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);
            telemetry.addLine("Time until seen " + (float)runtime.seconds());
            driveByTime(-0.2, 0.25);

            extendServo(1);

        }
    }

    protected void senseBlue(double pwr) throws InterruptedException {
        if (opModeIsActive()) {
            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            LEFT_DRIVE.setPower(pwr);
            RIGHT_DRIVE.setPower(pwr);

            while (!(COLOR_SENSOR.red() <= 1 && COLOR_SENSOR.blue() > COLOR_SENSOR.red() && COLOR_SENSOR.blue() > 2
            )) {
                telemetry.addData("Color", "%2d | %2d | %2d", COLOR_SENSOR.red(), COLOR_SENSOR.green(), COLOR_SENSOR.blue());
            }

            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);

            //driveByTime(-0.2, 0.4);

            extendServo(3);


            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    public void driveByTime(double pwr, double time) {
        if (opModeIsActive()) {
            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            LEFT_DRIVE.setPower(pwr);
            RIGHT_DRIVE.setPower(pwr);
            runtime.reset();
            while (opModeIsActive() && time > runtime.seconds()) {
                telemetry.addData("Time left", time - runtime.seconds());
            }
            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);

            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }


    public void driveNoGyro(double l_dist, double r_dist, double pwr) {
        if (opModeIsActive()) {
            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            int left_target = LEFT_DRIVE.getCurrentPosition() + (int)(l_dist * PULSE_PER_REV / WHEEL_CIRCUMFERENCE);
            int right_target = RIGHT_DRIVE.getCurrentPosition() + (int)(r_dist * PULSE_PER_REV / WHEEL_CIRCUMFERENCE);

            LEFT_DRIVE.setTargetPosition(left_target);
            RIGHT_DRIVE.setTargetPosition(right_target);

            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            LEFT_DRIVE.setPower(pwr);
            RIGHT_DRIVE.setPower(pwr);

            while (opModeIsActive() && (LEFT_DRIVE.isBusy() || RIGHT_DRIVE.isBusy())) {
                telemetry.addData("Target", "%7d | %7d", left_target, right_target);
                telemetry.addData("Current", "%7d | %7d", LEFT_DRIVE.getCurrentPosition(),
                        RIGHT_DRIVE.getCurrentPosition());
                telemetry.addData("Motor pwr", "%5.2f | %5.2f", LEFT_DRIVE.getPower(), RIGHT_DRIVE.getPower());
                telemetry.update();
            }

            LEFT_DRIVE.setPower(0);
            RIGHT_DRIVE.setPower(0);

            LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void extendServo(double time) {
        if (opModeIsActive()) {
            runtime.reset();
            double pos = 0;
            while (opModeIsActive() && runtime.seconds() < 0.5 && pos < 1) {
                runtime.reset();
                pos += 0.1;
                BUTTON_PUSHER.setPosition(pos);
                telemetry.addData("Servo", "%s %5.2f", "extending", runtime.seconds());
            }
            while (runtime.seconds() < time) {
                telemetry.addData("Servo", "%s %5.2f", "extended", runtime.seconds());
            }
            BUTTON_PUSHER.setPosition(0);
        }
    }

    public void shoot(double time) throws InterruptedException {
        if (opModeIsActive()) {
            runtime.reset();
            FLYWHEEL.setPower(1);
            sleep(750);
            INTAKE.setPower(1);
            sleep((long)((time) * 1000) - 750);
            FLYWHEEL.setPower(0);
            INTAKE.setPower(0);
        }
    }

    public abstract void runProgram() throws InterruptedException;
}
