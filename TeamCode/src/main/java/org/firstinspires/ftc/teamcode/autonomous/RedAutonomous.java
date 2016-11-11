package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by dchotzen-hartzell19 on 10/28/16.
 */
@Autonomous(name="Red 3", group="Hi")
public class RedAutonomous extends AutonomousUtil {

    @Override
    public void runProgram() throws InterruptedException {
        driveEnc(31, 0, 0.4, 10, true);
        driveEnc(-3.5, 0, 0.4, 10, true);
        singleTurn(0.3, 83);
        driveUntilTouch(0.3);
        resetEncoders();
        driveEnc(-18, 83, 0.4, 10, false);
        singleTurn(0.3, 5);
        senseRed(0.2);
        //driveEnc(25, 0, 0.2, 10);
        //senseRed(0.2);

    }

}
