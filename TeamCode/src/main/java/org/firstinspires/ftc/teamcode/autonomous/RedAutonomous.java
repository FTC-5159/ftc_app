package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by dchotzen-hartzell19 on 10/28/16.
 */
@Autonomous(name="Red 3", group="Hi")
public class RedAutonomous extends AutonomousUtil {

    @Override
    public void runProgram() throws InterruptedException {
        drive(31, 0, 0.4, 10);
        drive(-3.5, 0, 0.4, 10);
        singleTurn(0.2, 80);
        driveUntilTouch(0.3);
        drive(-15.5, 80, 0.4, 10);
        singleTurn(0.2, 2);
        senseRed(0.2);
        drive(25, 0, 0.4, 10);
        singleTurn(0.2, 80);
        driveUntilTouch(0.1);
        drive(-15.5, 80, 0.4, 10);
        singleTurn(0.2, 2);
        senseRed(0.2);

    }

}
