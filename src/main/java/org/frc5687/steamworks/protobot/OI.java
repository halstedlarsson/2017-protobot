package org.frc5687.steamworks.protobot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.frc5687.steamworks.protobot.commands.*;
import org.frc5687.steamworks.protobot.utils.Gamepad;
import org.frc5687.steamworks.protobot.utils.Helpers;

/**
 * The operator interface class handles communication with the driver station
 */
public class OI {

    public static final int GP_OPEN_GEAR = 8;
    public static final int GP_CLOSE_GEAR = 7;

    public static final int OC_OPEN_GEAR = 8;
    public static final int OC_CLOSE_GEAR = 7;

    public static final int RAISE_PINCERS = 4;
    public static final int LOWER_PINCERS = 3;

    public static final int OPEN_PINCERS = 5;
    public static final int CLOSE_PINCERS = 6;

    public static final int CATCH_GEAR = 10;

    public static final int RINGLIGHT_ON = 11;
    public static final int RINGLIGHT_OFF = 12;

    private Gamepad gamepad;
    private Joystick operatorConsole;

    private JoystickButton gpOpenGearButton;
    private JoystickButton gpCloseGearButton;

    private JoystickButton ocOpenGearButton;
    private JoystickButton ocCloseGearButton;

    private JoystickButton catchGearButton;

    private JoystickButton ascendClimber;
    private JoystickButton descendClimber;

    private JoystickButton shiftLow;
    private JoystickButton shiftHigh;

    private JoystickButton raisePincers;
    private JoystickButton lowerPincers;

    private JoystickButton openPincers;
    private JoystickButton closePincers;

    private JoystickButton ringLightOn;
    private JoystickButton ringLightOff;

    private JoystickButton gearWiggle;

    public OI() {
        gamepad = new Gamepad(0);
        operatorConsole = new Joystick(1);

        /*
         * X Box Gamepad Buttons
         */

        ascendClimber = new JoystickButton(gamepad, Gamepad.Buttons.Y.getNumber());
        descendClimber = new JoystickButton(gamepad, Gamepad.Buttons.X.getNumber());

        shiftLow = new JoystickButton(gamepad, Gamepad.Buttons.LEFT_BUMPER.getNumber());
        shiftHigh = new JoystickButton(gamepad, Gamepad.Buttons.RIGHT_BUMPER.getNumber());

        gpOpenGearButton = new JoystickButton(gamepad, GP_OPEN_GEAR);
        gpCloseGearButton = new JoystickButton(gamepad, GP_CLOSE_GEAR);

        gearWiggle = new JoystickButton(gamepad, Gamepad.Buttons.A.getNumber());

        /*
         * Operator Console Buttons
         */

        raisePincers = new JoystickButton(operatorConsole, RAISE_PINCERS);
        lowerPincers = new JoystickButton(operatorConsole, LOWER_PINCERS);

        openPincers = new JoystickButton(operatorConsole, OPEN_PINCERS);
        closePincers = new JoystickButton(operatorConsole, CLOSE_PINCERS);

        ringLightOn = new JoystickButton(operatorConsole, RINGLIGHT_ON);
        ringLightOff = new JoystickButton(operatorConsole, RINGLIGHT_OFF);

        ocCloseGearButton = new JoystickButton(operatorConsole, OC_CLOSE_GEAR);
        ocOpenGearButton = new JoystickButton(operatorConsole, OC_OPEN_GEAR);

        catchGearButton = new JoystickButton(operatorConsole, CATCH_GEAR);

        /*
         * Button Functions
         */

        shiftHigh.whenPressed(new Shift(DoubleSolenoid.Value.kForward));
        shiftLow.whenPressed(new Shift(DoubleSolenoid.Value.kReverse));

        gpCloseGearButton.whenPressed(new CloseMandibles());
        gpOpenGearButton.whenPressed(new OpenMandibles());

        ocCloseGearButton.whenPressed(new CloseMandibles());
        ocOpenGearButton.whenPressed(new OpenMandibles());

        raisePincers.whenPressed(new RaisePincers());
        lowerPincers.whenPressed(new LowerPincers());

        openPincers.whenPressed(new ClosePincers());
        closePincers.whenPressed(new OpenPincers());

        catchGearButton.whenPressed(new CatchGear());

        ringLightOn.whenPressed(new EnableRingLight());
        ringLightOff.whenPressed(new DisableRingLight());

    }

    private double transformStickToSpeed(Gamepad.Axes stick) {
        double result = gamepad.getRawAxis(stick);
        result = Helpers.applyDeadband(result, Constants.Deadbands.DRIVE_STICK);
        result = Helpers.applySensitivityTransform(result);
        return result;
    }

    public double getLeftSpeed() {
        return transformStickToSpeed(Gamepad.Axes.LEFT_Y);
    }

    public double getRightSpeed() {
        return transformStickToSpeed(Gamepad.Axes.RIGHT_Y);
    }

    public boolean isLeftTriggerPressed() {
        return (gamepad.getRawAxis(Gamepad.Axes.LEFT_TRIGGER) > Constants.OI.TRIGGER_THRESHHOLD);
    }

    public boolean isRightTriggerPressed() {
        return (gamepad.getRawAxis(Gamepad.Axes.RIGHT_TRIGGER) > Constants.OI.TRIGGER_THRESHHOLD);
    }

    public boolean isGearInPressed() {
        return gpCloseGearButton.get();
    }

    public boolean isGearOutPressed() {
        return gpOpenGearButton.get();
    }

    public boolean isAscendClimberPressed() {
        return ascendClimber.get();
    }

    public boolean isDescendClimberPressed() {
        return descendClimber.get();
    }

    public boolean isGearWigglePressed() {
        return gearWiggle.get();
    }

    public boolean isCatchGearPressed() {
        return catchGearButton.get();
    }

    public double getPincerSpeed() {
        double result = -operatorConsole.getAxis(Joystick.AxisType.kY);
        return Helpers.applyDeadband(result, Constants.Deadbands.DRIVE_STICK);
    }

}
