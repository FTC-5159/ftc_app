package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Field;

/**
 * Wrapper for a servo that toggles between two positions at the push
 * of a button on the gamepad.
 * @author Devin Chotzen-Hartzell
 */
public class ToggleServo {

    /**
     *
     */
    public Servo servo;
    public Field field;
    public String button;
    public float[] pos;
    public Gamepad gamepad;

    /**
     * false = pos[0]
     * true = pos[1]
     */
    public boolean currentPos = false;
    public boolean buttonAlreadyPressed = false;


    /**
     * Class constructor that defines the properties of the ToggleServo
     * @param hwMap     opmode's hardware map object
     * @param name      the name of the servo in the config
     * @param gamepad   the gamepad that controls the servo
     * @param button    name of the boolean button on the gamepad
     * @param pos       array of positions:
     *                  pos[0] is default position
     *                  pos[1] is extended position
     * @throws IllegalAccessException
     */
    public ToggleServo(HardwareMap hwMap, String name, Gamepad gamepad,
                       String button, float[] pos, Telemetry telemetry)
            throws NoSuchFieldException {
        // Initialize class members
        this.servo = hwMap.servo.get(name);
        this.button = button;
        this.field = Gamepad.class.getDeclaredField(this.button);
        this.gamepad = gamepad;
        this.pos = pos;

        this.servo.setPosition(pos[0]);
    }


    /**
     * Called every hardware cycle to check if the servo's
     * position needs to be updated, and update it if it does.
     * @throws IllegalAccessException
     */
    public void update() throws IllegalAccessException {
        // Check if button is pressed
        if (getPressed()) {
            // Check if the button is already pressed
            if (!buttonAlreadyPressed) {
                buttonAlreadyPressed = true;
                currentPos = !currentPos;
                servo.setPosition(pos[(currentPos) ? 1 : 0]);
            }
        } else {
            buttonAlreadyPressed = false;
        }
    }

    /**
     * @return The current value of the gamepad button
     * @throws IllegalAccessException
     */
    public boolean getPressed() throws IllegalAccessException {
        try {
            return field.getBoolean(gamepad);
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
