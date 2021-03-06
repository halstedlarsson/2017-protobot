package org.frc5687.steamworks.protobot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.frc5687.steamworks.protobot.Constants;
import org.frc5687.steamworks.protobot.commands.actions.drive.AutoAlign;
import org.frc5687.steamworks.protobot.commands.actions.drive.AutoDrive;
import org.frc5687.steamworks.protobot.commands.actions.drive.DriveArc;

/**
 * Created by Baxter on 3/23/2017.
 */
public class AutoTraverseNeutralZoneLeftFromCenterArc extends CommandGroup {

        public AutoTraverseNeutralZoneLeftFromCenterArc() {
            addSequential(new DriveArc(-1.0, -0.367088608, Constants.Auto.AnglesAndDistances.ESCAPE_CENTER_ANGLE_LEFT, 2000, true));
            addSequential(new AutoDrive(Constants.Auto.AnglesAndDistances.ESCAPE_CENTER_DISTANCE, 1.0, false, false, Constants.Auto.AnglesAndDistances.STRAIGHT_ANGLE, 6000, ""));
            addSequential(new DriveArc(1.0, 0.367088608, Constants.Auto.AnglesAndDistances.STRAIGHT_ANGLE, 2000, true));
            addSequential(new AutoDrive(Constants.Auto.AnglesAndDistances.TRAVERSE_NEUTRAL_ZONE_FROM_CENTER_DISTANCE, 1.0, true, true, Constants.Auto.AnglesAndDistances.STRAIGHT_ANGLE, 5000, ""));
        }

}
