package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by dchotzen-hartzell19 on 10/27/16.
 */
@TeleOp(name="Color Sensor")
public class ColorSensorTest extends OpMode {
    ColorSensor colorSensor;
    public void init() {
        colorSensor = hardwareMap.colorSensor.get("color_sensor");
    }
    public void loop() {
        String s = "";
        telemetry.addData("RGB","%d %d %d", colorSensor.red(), colorSensor.green(), colorSensor.blue());
    }
}
