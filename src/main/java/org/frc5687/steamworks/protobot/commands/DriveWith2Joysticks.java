package org.frc5687.steamworks.protobot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc5687.steamworks.protobot.Constants;
import static org.frc5687.steamworks.protobot.Robot.driveTrain;
import static org.frc5687.steamworks.protobot.Robot.imu;
import static org.frc5687.steamworks.protobot.Robot.oi;

/**
 * Created by Ben Bernard on 1/13/2017.
 */
public class DriveWith2Joysticks extends Command implements PIDOutput, PIDSource {

    /*
     * Constructor
     */
    public DriveWith2Joysticks() {
        requires(driveTrain);
    }
    public static PIDController turnController;
    private static final double kP = SmartDashboard.getNumber("DB/Slider 0", 0);
    private static final double kI = SmartDashboard.getNumber("DB/Slider 1", 0);
    private static final double kD = SmartDashboard.getNumber("DB/Slider 2", 0);
    private static final double kF = 0.0;
    private static final double kToleranceDegrees = 2.0f;
    private static double targetAngle = 0;
    private boolean isReversed;


    /*
     * Sets up the command
     * Called just before this Command runs the first time(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#initialize()
     */
    protected void initialize() {
        driveTrain.resetDriveEncoders();
        turnController = new PIDController(kP, kI, kD, kF, imu, this);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-0.1, 0.1);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        turnController.setSetpoint(0);
    }

    /*
     * Executes the command
     * Called repeatedly when this Command is scheduled to run(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#execute()
     */
    protected void execute() {
        if (oi.isLeftTriggerPressed()) {
            isReversed = false;
            if(!turnController.isEnabled()) {
                turnController.enable();
            }

        } else if (oi.isRightTriggerPressed()){
            isReversed = true;
            if(!turnController.isEnabled()){
                turnController.enable();
            }
        } else {

            if(turnController.isEnabled()) {
                turnController.disable();
                turnController.reset();
            }
            driveTrain.tankDrive(oi.getLeftSpeed(), oi.getRightSpeed());
            targetAngle = imu.getAngle();

        }
        turnController.setPID(SmartDashboard.getNumber("DB/Slider 0", 0),SmartDashboard.getNumber("DB/Slider 1", 0),SmartDashboard.getNumber("DB/Slider 3", 0));
        SmartDashboard.putNumber("PID/proportional",turnController.getP());
        SmartDashboard.putNumber("PID/integral", turnController.getI());
        SmartDashboard.putNumber("PID/derivative", turnController.getD());
        SmartDashboard.putNumber("PID/setpoint", turnController.getSetpoint());

    }

    /*
     * Check if this command is finished running
     * Make this return true when this Command no longer needs to run execute()(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#isFinished()
     */
    @Override
    protected boolean isFinished() {
        return false;
    }

    /*
     * Command execution clean-up
     * Called once after isFinished returns true(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#end()
     */
    protected void end() {
    }

    /*
     * Handler for when command is interrupted
     * Called when another command which requires one or more of the same(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#interrupted()
     */
    protected void interrupted() {
    }

    @Override
    public void pidWrite(double output) {
        synchronized (this) {
            SmartDashboard.putNumber("PID/output", output);

            if(isReversed) {
                driveTrain.tankDrive(output + Constants.Drive.FULL_BACKWARDS_SPEED, Constants.Drive.FULL_BACKWARDS_SPEED - output);
            }else{
                driveTrain.tankDrive(output + Constants.Drive.FULL_FORWARDS_SPEED, Constants.Drive.FULL_FORWARDS_SPEED - output);
            }
        }
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return null;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
    }

    @Override
    public double pidGet() {
        return imu.getAngle() - targetAngle - 180;
    }
}