package org.firstinspires.ftc.teamcode.disregard.old.autonomous.test.util;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Class to store robot hardware
 */

public class RobotHardware {

    public HardwareMap hardwareMap;
    public Telemetry telemetry;

    public DcMotor LEFT_DRIVE;
    public DcMotor RIGHT_DRIVE;
    public Servo BEACON_PUSHER;
    public ColorSensor COLOR_SENSOR;

    public final static String LEFT_DRIVE_NAME = "left_drive";
    public final static String RIGHT_DRIVE_NAME = "right_drive";
    public final static String BEACON_PUSHER_NAME = "beacon_pusher";
    public final static String COLOR_SENSOR_NAME = "color_sensor";


    public RobotHardware(HardwareMap hardwareMap, Telemetry telemetry) {

        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        LEFT_DRIVE = hardwareMap.dcMotor.get(LEFT_DRIVE_NAME);
        RIGHT_DRIVE = hardwareMap.dcMotor.get(RIGHT_DRIVE_NAME);

        COLOR_SENSOR = hardwareMap.colorSensor.get(COLOR_SENSOR_NAME);

        LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        COLOR_SENSOR.enableLed(false);

        LEFT_DRIVE.setPower(0.0);
        RIGHT_DRIVE.setPower(0.0);

        //RIGHT_DRIVE.setDirection(DcMotor.Direction.REVERSE);
    }

    public void update(Gamepad gamepad1) {

        float l_pwr = gamepad1.left_stick_y;
        float r_pwr = gamepad1.right_stick_y;

        LEFT_DRIVE.setPower(l_pwr);
        RIGHT_DRIVE.setPower(r_pwr);

        telemetry.addData("LStick:", l_pwr);
        telemetry.addData("RStick:", r_pwr);
        telemetry.addData("", String.format("Red: %s - Blue: %s - Green: %s"));


    }
}
