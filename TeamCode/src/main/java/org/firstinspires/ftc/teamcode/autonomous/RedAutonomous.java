package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Robot;
import org.firstinspires.ftc.teamcode.util.RobotUtil;

/**
 * Autonomous for when we're red
 * @author Devin Chotzen-Hartzell
 */

@Autonomous(name="Red Autonomous", group="Autonomous")
public class RedAutonomous extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        RobotUtil.init(gamepad1, gamepad2, hardwareMap, telemetry, this);
        try {
            robot = new Robot();
        } catch (Exception e) {
            telemetry.addData("ERROR", "NoSuchFieldException");
        }

        waitForStart();

    }
}
