package org.firstinspires.ftc.teamcode.oldstuff.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.oldstuff.test.util.RobotHardware;

/**
 * Team 5159's test TeleOP
 */

@TeleOp(name = "TeleOp", group= "Test")
@Disabled
@SuppressWarnings("unused")
public class TestTeleOp extends OpMode {

    public HardwareMap hwMap;
    public RobotHardware hardware;

    public void init() {
        this.hwMap = hardwareMap;
        this.hardware = new RobotHardware(hwMap, telemetry);
    }

    public void loop() {
        hardware.update(gamepad1);
    }
}
