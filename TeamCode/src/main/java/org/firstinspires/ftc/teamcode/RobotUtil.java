package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by dchotzen-hartzell19 on 10/15/16.
 */
public class RobotUtil {

    // Variables from opMode
    public static OpMode opMode;
    public static HardwareMap hardwareMap;
    public static Gamepad gamepad1;
    public static Gamepad gamepad2;

    // Robot hardware
    public static DcMotor RIGHT_DRIVE;
    public static DcMotor LEFT_DRIVE;
    public static Servo ARM_SERVO;


}
