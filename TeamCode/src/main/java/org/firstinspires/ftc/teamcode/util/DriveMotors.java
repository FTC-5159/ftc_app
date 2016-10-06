package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * @author Devin Chotzen-Hartzell
 */
public class DriveMotors {

    private DcMotor LEFT_DRIVE;
    private DcMotor RIGHT_DRIVE;

    private int PULSE_PER_REV = 280;
    private float WHEEL_DIAMETER = 4.0F;
    private double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

    public DriveMotors(HardwareMap hardwareMap) {
        LEFT_DRIVE = hardwareMap.dcMotor.get("left_drive");
        RIGHT_DRIVE = hardwareMap.dcMotor.get("left_drive");

        LEFT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RIGHT_DRIVE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setEncoders(boolean b) {
        LEFT_DRIVE.setMode(b? DcMotor.RunMode.RUN_USING_ENCODER : DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RIGHT_DRIVE.setMode(b? DcMotor.RunMode.RUN_USING_ENCODER : DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void drive(float l_pwr, double l_distance, float r_pwr, double r_distance) {
        setEncoders(true);
        LEFT_DRIVE.setPower(l_pwr);
        RIGHT_DRIVE.setPower(r_pwr);
        LEFT_DRIVE.setTargetPosition(calcEncoders(l_distance));
        RIGHT_DRIVE.setTargetPosition(calcEncoders(r_distance));
        while (isBusy()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
    }

    public void drive(float l_pwr, float r_pwr) {

    }

    public void reverseDrive() {

    }

    public boolean isBusy() {
        return LEFT_DRIVE.isBusy() || RIGHT_DRIVE.isBusy();
    }

    public int calcEncoders(double dist) {
        return (int)(dist / WHEEL_CIRCUMFERENCE) * PULSE_PER_REV;
    }
}
