package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.RobotUtil;
import org.firstinspires.ftc.teamcode.util.ToggleServo;

/**
 * Created by dchotzen-hartzell19 on 9/30/16.
 */

@TeleOp(name="Default TeleOp", group="Robot")
public class TeleOpMode extends OpMode {

    public void init() {
        RobotUtil.setGamepads(gamepad1, gamepad2);
    }

    public void start() {

    }

    public void loop() {

    }
}
