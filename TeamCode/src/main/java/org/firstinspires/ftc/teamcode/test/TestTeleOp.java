package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Team 5159's test TeleOP
 */

@TeleOp(name = "TeleOp", group= "Test")
@SuppressWarnings("unused")
public class TestTeleOp extends OpMode {

    public HardwareMap hwMap;

    @Override
    public void init() {
        this.hwMap = hardwareMap;
    }



    public void loop() {

    }


}
