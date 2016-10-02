package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Class to store information like gamepad etc.
 */
public class RobotUtil {

    public static Gamepad gamepad1;
    public static Gamepad gamepad2;

    public static void setGamepads(Gamepad gp1, Gamepad gp2) {
        gamepad1 = gp1;
        gamepad2 = gp2;
    }

    public static void setGamepad1(Gamepad newgamepad) {
        gamepad1 = newgamepad;
    }

    public static void setGamepad2(Gamepad newgamepad) {
        gamepad2 = newgamepad;
    }

    public static Gamepad getGamepad1() {
        return gamepad1;
    }

    public static Gamepad getGamepad2() {
        return gamepad2;
    }

}
