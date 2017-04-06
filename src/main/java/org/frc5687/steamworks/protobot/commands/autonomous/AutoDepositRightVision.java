package org.frc5687.steamworks.protobot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.frc5687.steamworks.protobot.Constants;
import org.frc5687.steamworks.protobot.commands.actions.drive.AutoAlign;
import org.frc5687.steamworks.protobot.commands.actions.drive.AutoDrive;
import org.frc5687.steamworks.protobot.commands.actions.drive.AutoVisualApproachTarget;
import org.frc5687.steamworks.protobot.commands.actions.drive.DriveArc;
import org.frc5687.steamworks.protobot.commands.actions.mandibles.HoldMandiblesOpen;
import org.frc5687.steamworks.protobot.commands.composite.EjectMandibles;

/**
 * Created by Baxter on 2/25/2017.
 */
public class AutoDepositRightVision extends CommandGroup {

    public AutoDepositRightVision() {
        super();
        addSequential(new DriveArc(0.367088608, 1, -60.0, 2000, true));

        addSequential(new AutoVisualApproachTarget(0.7, -Constants.Auto.AnglesAndDistances.DEPOSIT_GEAR_FAR_ANGLE));

        addSequential(new EjectMandibles());

        // Retreat
        addSequential(new HoldMandiblesOpen(Constants.Auto.AnglesAndDistances.PAUSE_AT_SPRING_TIME));
        addParallel(new HoldMandiblesOpen(Constants.Auto.AnglesAndDistances.MANDIBLE_HOLD_TIME));
        addSequential(new AutoDrive(-Constants.Auto.AnglesAndDistances.RETREAT_DISTANCE, Constants.Auto.Drive.SPEED, false, true, 5000));

        // Traverse
        // addSequential(new DriveArc(1.0, 0.367088608, Constants.Auto.AnglesAndDistances.STRAIGHT_ANGLE, 2000, true));
        // addSequential(new AutoDrive(Constants.Auto.AnglesAndDistances.TRAVERSE_NEUTRAL_ZONE_FROM_SIDE_DISTANCE, 0.7, true, true, 0, 5000));

    }

}
