package org.firstinspires.ftc.teamcode.test.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Class to store robot hardware
 */


public class RobotHardware {

    public HardwareMap hardwareMap;

    public DcMotor LEFT_DRIVE;
    public DcMotor RIGHT_DRIVE;

    public final static String LEFT_DRIVE_NAME = "left_drive";
    public final static String RIGHT_DRIVE_NAME = "right_drive";

    public RobotHardware(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        LEFT_DRIVE = hardwareMap.dcMotor.get(LEFT_DRIVE_NAME);
        RIGHT_DRIVE = hardwareMap.dcMotor.get(RIGHT_DRIVE_NAME);

        LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        LEFT_DRIVE.setPower(0.0);
        RIGHT_DRIVE.setPower(0.0);

        RIGHT_DRIVE.setDirection(DcMotor.Direction.REVERSE);
    }

    public void update(Gamepad gamepad1) {
        float l_pwr = gamepad1.left_stick_y;
        float r_pwr = gamepad1.right_stick_y;

        LEFT_DRIVE.setPower(l_pwr);
        RIGHT_DRIVE.setPower(r_pwr);

    }
}
