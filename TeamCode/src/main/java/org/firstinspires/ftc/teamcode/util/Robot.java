package org.firstinspires.ftc.teamcode.util;

/**
 * Wrapper for all of the robot's hardware classes
 */
public class Robot {

    public DriveMotors driveMotors;

    public Robot() throws NullPointerException {
        driveMotors = new DriveMotors(RobotUtil.hardwareMap);
    }
}
