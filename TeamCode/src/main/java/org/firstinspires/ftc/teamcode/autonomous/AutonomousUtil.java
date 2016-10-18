package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by dchotzen-hartzell19 on 10/15/16.
 */
public abstract class AutonomousUtil extends LinearOpMode {

    private final int PULSE_PER_REV = 420;
    private final double WHEEL_DIAMETER = 4.0;


    public DcMotor LEFT_DRIVE,
                   RIGHT_DRIVE,
                   FLYWHEEL,
                   INTAKE;

    public ModernRoboticsI2cGyro GYRO_SENSOR;
    public ColorSensor COLOR_SENSOR;

    public void runOpMode() {
        // Initialize everything
        LEFT_DRIVE = hardwareMap.dcMotor.get("left_drive");
        RIGHT_DRIVE = hardwareMap.dcMotor.get("right_drive");
        FLYWHEEL = hardwareMap.dcMotor.get("flywheel");
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


    }

    protected void driveEncoders(double inches) {

    }

    protected void turnUntilGyro() {}

    protected void senseForward() {

    }

    protected void senseBackward() {

    }

    public abstract void runProgram();
}
