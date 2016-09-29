package org.firstinspires.ftc.teamcode.prototype.colorsensor;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by dchotzen-hartzell19 on 9/28/16.
 */
@TeleOp(name="Color Sensor Test", group="Prototypes")
@SuppressWarnings("unused")
public class ColorSensorTest extends OpMode {

    public ColorSensor colorSensor;

    public void init() {
        colorSensor = hardwareMap.colorSensor.get("color_sensor");
        colorSensor.enableLed(false);
    }

    public void loop() {
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue", colorSensor.blue());

    }
}
