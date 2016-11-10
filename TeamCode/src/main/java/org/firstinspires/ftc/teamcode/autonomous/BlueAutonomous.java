package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by dchotzen-hartzell19 on 10/15/16.
 */
@Autonomous(name="Blue", group="Hi")
public class BlueAutonomous extends AutonomousUtil {

    @Override
    public void runProgram() throws InterruptedException {

        drive(31, 0, 0.4, 10);
        shoot(4);
        singleTurn(0.2, -90);
        driveUntilTouch(0.3);
        drive(-17.5, 0, 0.4, 10);
        singleTurn(0.2, -168);
        senseBlue(-0.3);
        senseBlue(-0.3);


    }
}
