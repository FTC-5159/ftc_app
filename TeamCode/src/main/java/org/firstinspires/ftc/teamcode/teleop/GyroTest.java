package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by dchotzen-hartzell19 on 11/9/16.
 */
@TeleOp(name="Gyro Test")
public class GyroTest extends OpMode {

    public ModernRoboticsI2cGyro gyro;

    public void init() {
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro_sensor");
        gyro.calibrate();
        while (gyro.isCalibrating()) {
            telemetry.addData(">", "Calibrating");
        }
    }

    public void loop() {
        telemetry.addData(">", "Heading: %3d", gyro.getHeading());
    }
}
