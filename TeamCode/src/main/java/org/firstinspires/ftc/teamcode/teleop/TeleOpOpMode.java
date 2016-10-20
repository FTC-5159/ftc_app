package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Tele Op
 */
@TeleOp(name="Test", group="Stuff")
public class TeleOpOpMode extends OpMode {

    DcMotor LEFT_DRIVE,
            RIGHT_DRIVE,
            FLYWHEEL;

    ModernRoboticsI2cGyro GYRO_SENSOR;

    public void init() {
        LEFT_DRIVE = hardwareMap.dcMotor.get("left_drive");
        RIGHT_DRIVE = hardwareMap.dcMotor.get("right_drive");
        RIGHT_DRIVE.setDirection(DcMotor.Direction.REVERSE);
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
        FLYWHEEL = hardwareMap.dcMotor.get("flywheel");
    }

    public void loop() {
        LEFT_DRIVE.setPower(gamepad1.right_stick_y);
        RIGHT_DRIVE.setPower(gamepad1.left_stick_y);
        if (gamepad1.x) FLYWHEEL.setPower(1);
        else FLYWHEEL.setPower(0);
        telemetry.addData(">", "Current Heading: %d", GYRO_SENSOR.getIntegratedZValue());
    }
}
