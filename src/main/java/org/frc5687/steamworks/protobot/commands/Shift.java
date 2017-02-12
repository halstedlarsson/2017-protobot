package org.frc5687.steamworks.protobot.commands;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import static org.frc5687.steamworks.protobot.Robot.shifter;

/**
 * Command for expanding piston of double solenoid
 */
public class Shift extends Command{

    DoubleSolenoid.Value gear = DoubleSolenoid.Value.kOff;

    public Shift(DoubleSolenoid.Value gear) {
        this.gear = gear;
        requires(shifter);
    }

    /**
     * Sets up the command
     * Called just before this Command runs the first time
     */
    @Override
    protected void initialize() {

    }

    /**
     * Executes the command
     * Called repeatedly when this Command is scheduled to run
     */
    @Override
    protected void execute() {
        DriverStation.reportError("Starting shift command ", false);
        shifter.shift(gear);
    }

    /**
     * Check if this command is finished running
     * Make this return true when this Command no longer needs to run execute()
     * @return true if Command is stopped, false otherwise
     */
    @Override
    protected boolean isFinished() {
        return shifter.getGear() == gear;
    }

    /**
     * Command execution clean-up
     * Called once after isFinished returns true
     */
    @Override
    protected void end() {

    }

    /**
     * Handler for when command is interrupted
     * Called when another command which requires one or more of the same
     */
    @Override
    protected void interrupted() {

    }
}
