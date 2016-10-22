package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by dchotzen-hartzell19 on 10/15/16.
 */
@Autonomous(name="Test Gyro", group="Hi")
public class RedAutonomous extends AutonomousUtil {

    @Override
    public void runProgram() {

        // drive(double inches, double angle, double speed, double timeout)
        drive(60, 0, 0.3, 10);
    }
}
