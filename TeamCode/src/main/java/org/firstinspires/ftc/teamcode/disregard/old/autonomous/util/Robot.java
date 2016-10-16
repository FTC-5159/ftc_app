package org.firstinspires.ftc.teamcode.disregard.old.autonomous.util;

/**
 * Wrapper for all of the robot's hardware classes
 */
public class Robot {

    public static final float[] BEACON_SERVO_POSITIONS = {0.0F, 1.0F};

    public DriveMotors driveMotors;
    public ToggleServo beaconPusher;

    public Robot() throws NullPointerException, NoSuchFieldException {
        driveMotors = new DriveMotors();
        beaconPusher = new ToggleServo("beacon_servo", 1, "a", BEACON_SERVO_POSITIONS);

    }

    public void update() throws IllegalAccessException, InterruptedException {
        // Pulse the toggle buttons
        ToggleButton.pulse();

        driveMotors.update();
        // Update drive motor positions
    }
}
