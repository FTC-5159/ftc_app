package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by dchotzen-hartzell19 on 10/15/16.
 */
@Autonomous(name="Blue", group="Hi")
public class BlueAutonomous extends AutonomousUtil {

    @Override
    public void runProgram() throws InterruptedException {

        driveEnc(31, 0, 0.4, 10, true);
        shoot(4);
        singleTurn(0.2, -90);
        driveUntilTouch(0.3);
        driveEnc(-17.5, 0, 0.4, 10, true);
        singleTurn(0.2, -168);
        senseBlue(-0.3);
        senseBlue(-0.3);


    }
}
