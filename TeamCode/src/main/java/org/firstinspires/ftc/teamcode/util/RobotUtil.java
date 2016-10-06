package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Class to store information like gamepad etc.
 */
public class RobotUtil {

    public static Gamepad gamepad1;
    public static Gamepad gamepad2;
    public static HardwareMap hardwareMap;
    public static Telemetry telemetry;


    public static void init(Gamepad gp1, Gamepad gp2, HardwareMap hwmap, Telemetry tele) {
        gamepad1 = gp1;
        gamepad2 = gp2;
        hardwareMap = hwmap;
        telemetry = tele;
    }
}
