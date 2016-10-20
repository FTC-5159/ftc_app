package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by dchotzen-hartzell19 on 10/15/16.
 */
@Autonomous(name="Test Gyro", group="Hi")
public class RedAutonomous extends AutonomousUtil {

    @Override
    public void runProgram() {
        drive(20, 15, 0.5, 10);
    }
}
