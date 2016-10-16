package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.util.Robot;
import org.firstinspires.ftc.teamcode.util.RobotUtil;

/**
 * Main TeleOp mode for team 5159
 * @author Devin Chotzen-Hartzell
 */
@TeleOp(name="Default TeleOp", group="Robot")
public class TeleOpMode extends OpMode {

    public Robot hardware;

    /**
     * The `init` method is called when the button by the same name is pressed
     * on the Driver Station
     */
    public void init() {

        // Initialize Robot
        RobotUtil.init(gamepad1, gamepad2, hardwareMap, telemetry, this);
        try {
            hardware = new Robot();
        } catch (Exception e) {
            telemetry.addData("Error", e.toString());
        }
    }

    /**
     * The `start` method is called when the opmode is begun on the Driver Station
     */
    public void start() {
        // Nothing here yet.
    }

    /**
     * The `loop` method is called several times each second. How many? We have no clue.
     */
    public void loop() {
        try {
            hardware.update();
        } catch (Exception e) {
            telemetry.addData("Error", e.toString());
        }
    }
}
