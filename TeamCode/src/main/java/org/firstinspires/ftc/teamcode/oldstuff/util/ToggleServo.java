package org.firstinspires.ftc.teamcode.oldstuff.util;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.reflect.Field;

/**
 * Wrapper for a servo that toggles between two positions at the push
 * of a button on the gamepad.
 * @author Devin Chotzen-Hartzell
 */
public class ToggleServo implements Toggleable {

    public Servo servo;
    public Field field;
    public String button;
    public float[] pos;

    /**
     * false = pos[0] - default position
     * true = pos[1]  - extended position
     */
    public boolean currentPos = false;


    /**
     * Class constructor that defines the properties of the ToggleServo
     * @param name      the name of the servo in the config
     * @param gamepad   number of the gamepad that controls the servo
     * @param button    name of the boolean button on the gamepad
     * @param positions array of positions:
     *                  pos[0] is default position
     *                  pos[1] is extended position
     * @throws NoSuchFieldException
     */
    public ToggleServo(String name, int gamepad, String button, float[] positions)
            throws NoSuchFieldException {
        // Initialize class members
        this.servo = RobotUtil.hardwareMap.servo.get(name);
        servo.setPosition(pos[0]);
        this.field = Gamepad.class.getDeclaredField(this.button);
        this.pos = positions;
        new ToggleButton(button, gamepad, this);
    }

    /**
     * Toggles the servo.
     * Triggered by the ToggleButton object for the specific instance.
     */
    public void onToggle() {
        currentPos = !currentPos;
        servo.setPosition(pos[currentPos?1:0]);
        RobotUtil.telemetry.addData(servo.getDeviceName(), servo.getPosition());
    }

    /**
     * Manually sets the position of the servo
     * @param b     Position of the servo after method is executed
     */
    public void setExtended(boolean b) {
        currentPos = b;
        servo.setPosition(pos[currentPos?1:0]);
        RobotUtil.telemetry.addData(servo.getDeviceName(), servo.getPosition());
    }
}
