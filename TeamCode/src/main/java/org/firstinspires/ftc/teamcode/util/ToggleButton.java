package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Updates things when buttons are toggled. Works with Toggleable interface.
 * @see Robot
 * @see Toggleable
 */
public class ToggleButton {

    public static ArrayList<ToggleButton> list = new ArrayList<ToggleButton>();

    public Field button;
    public int gamepad;
    public Toggleable parent;

    public boolean alreadyPressed = false;

    /**
     * @param btn       The name of the gamepad's property
     * @param gamepad   The number (1 or 2) of the gamepad
     * @param parent
     * @throws NoSuchFieldException     Shouldn't happen unless someone screws it up over in
     *                                  `Robot` class.
     */
    public ToggleButton(String btn, int gamepad, Object parent) throws NoSuchFieldException {
        this.parent = (Toggleable)parent;
        this.button = Gamepad.class.getDeclaredField("btn");
        this.gamepad = gamepad;
    }

    /**
     * Static method that is called each hardware cycle to get update feedback from gamepad
     * @throws IllegalAccessException
     */
    public static void pulse() throws IllegalAccessException {
        for (ToggleButton t: list) {
            t.update();
        }
    }

    /**
     * Called every hardware cycle to see if the servo needs switching
     * @throws IllegalAccessException
     */
    public void update() throws IllegalAccessException {
        if (button.getBoolean(RobotUtil.gamepad1)) {
            if (!alreadyPressed) {
                parent.onToggle();
                alreadyPressed = true;
            }
        } else alreadyPressed = false;
    }
}

