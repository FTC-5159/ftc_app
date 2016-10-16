package org.firstinspires.ftc.teamcode.zoldstuff.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Storage unit to store information like gamepads, hardware map, telemetry, and running op mode.
 * This prevents having to pass this information to each class every time.
 * @author Devin Chotzen-Hartzell
 */
public class RobotUtil {

    public static Gamepad gamepad1;
    public static Gamepad gamepad2;
    public static HardwareMap hardwareMap;
    public static Telemetry telemetry;
    public static OpMode opMode;
    /**
     * Called once during init() method in OpMode to initialize all variables
     * @param gp1       Gamepad #1
     * @param gp2       Gamepad #2
     * @param hwmap     Hardware Map Object
     * @param tele      Telemetry Object
     * @param op_mode   Current op mode
     */
    public static void init(Gamepad gp1, Gamepad gp2, HardwareMap hwmap, Telemetry tele, OpMode op_mode) {
        gamepad1 = gp1;
        gamepad2 = gp2;
        hardwareMap = hwmap;
        telemetry = tele;
        opMode = op_mode;
    }
}
