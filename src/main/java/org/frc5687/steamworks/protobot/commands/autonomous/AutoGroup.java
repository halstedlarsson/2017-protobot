package org.frc5687.steamworks.protobot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Created by Ben Bernard on 3/26/2017.
 */
public class AutoGroup extends SteamworksBaseCommandGroup {
    public AutoGroup(int spring, int gear, int hopper) {
        super();
        // Add the initial operations based on position switch...
        switch (spring) {
            case 0:
                break;
            case 1:
                DriverStation.reportError("Adding AutoDepositLeftFromFarLeft", false);
                addSequential(new AutoDepositLeftFromFarLeft());
                break;
            case 2:
                DriverStation.reportError("Adding AutoDepositLeftVision", false);
                addSequential(new AutoDepositLeftVision());
                break;
            case 3:
                DriverStation.reportError("Adding AutoDepositGear", false);
                addSequential(new AutoDepositGear());
                break;
            case 4:
                DriverStation.reportError("Adding AutoDepositRightVision", false);
                addSequential(new AutoDepositRightVision());
                break;
            case 5:
                DriverStation.reportError("Adding AutoDepositRightFromFarRight", false);
                addSequential(new AutoDepositRightFromFarRight());
                break;
            default:
                break;
        }

        // Now add follow-on operation based on the hopper switch
        switch (hopper) {
            case 0:
                // Don't do anything!
                break;
            case 1:
                // Traverse the neutral zone as expediently as possible!
                switch (spring) {
                    case 0:
                        DriverStation.reportError("Adding AutoCrossBaseline", false);
                        addSequential(new AutoCrossBaseline());
                        break;
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                        // For left side, traverse left
                        DriverStation.reportError("Adding AutoTraverseNeutralZoneFromSide", false);
                        addSequential(new AutoTraverseNeutralZoneFromRightSide());
                        break;
                    case 3:
                        if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
                            DriverStation.reportError("Adding AutoTraverseNeutralZoneLeftFromCenter", false);
                            addSequential(new AutoTraverseNeutralZoneLeftFromCenter());
                        } else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
                            DriverStation.reportError("Adding AutoTraverseNeutralZoneRightFromCenter", false);
                            addSequential(new AutoTraverseNeutralZoneRightFromCenter());
                        }
                        break;
                }
                break;
            case 2:
                // Traverse the neutral zone on the left side
                switch (spring) {
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                        // For left side, traverse left
                        DriverStation.reportError("Adding AutoTraverseNeutralZoneFromSide", false);
                        addSequential(new AutoTraverseNeutralZoneFromRightSide());
                        break;
                    case 3:
                        DriverStation.reportError("Adding AutoTraverseNeutralZoneLeftFromCenter", false);
                        addSequential(new AutoTraverseNeutralZoneLeftFromCenter());
                        break;
                }
                break;
            case 3:
                // Traverse the neutral zone on the right side
                switch (spring) {
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                        // For left side, traverse left
                        DriverStation.reportError("Adding AutoTraverseNeutralZoneFromSide", false);
                        addSequential(new AutoTraverseNeutralZoneFromRightSide());
                        break;
                    case 3:
                        DriverStation.reportError("Adding AutoTraverseNeutralZoneRightFromCenter", false);
                        addSequential(new AutoTraverseNeutralZoneRightFromCenter());
                        break;
                }
                break;
        }

    }

    public static String getDescription(int spring, int gear, int hopper) {
        StringBuffer result = new StringBuffer();
        switch (spring) {
            case 0:
                result.append("No Spring ");
                break;
            case 1:
                result.append("Left Spring ");
                break;
            case 2:
                result.append("Left Spring Using Vision ");
                break;
            case 3:
                result.append("Center Spring ");
                break;
            case 4:
                result.append("Right Spring Using Vision ");
                break;
            case 5:
                result.append("Right Spring ");
                break;
            default:
                break;
        }

        // Now add follow-on operation based on the hopper switch
        switch (hopper) {
            case 0:
                result.append(" then Do Nothing");
                break;
            case 1:
                // Traverse the neutral zone as expediently as possible!
                switch (spring) {
                    case 0:
                        result.append(" then Cross Baseline");
                        break;
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                        result.append(" then Traverse Neutral Zone");
                        break;
                    case 3:
                        if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
                            result.append(" then Traverse Neutral Zone on Left Side");
                        } else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
                            result.append(" then Traverse Neutral Zone on Right Side");
                        }
                }
                break;
            case 2:
                // Traverse the neutral zone on the left side
                switch (spring) {
                    case 1:
                    case 2:
                        result.append(" then Traverse Neutral Zone on Left Side");
                        break;
                    case 4:
                    case 5:
                        result.append(" then Traverse Neutral Zone on Right Side");
                        break;
                    case 3:
                        result.append(" then Traverse Neutral Zone on Left Side");
                        break;
                }
                break;
            case 3:
                // Traverse the neutral zone on the right side
                switch (spring) {
                    case 1:
                    case 2:
                        result.append(" then Traverse Neutral Zone on Left Side");
                        break;
                    case 4:
                    case 5:
                        result.append(" then Traverse Neutral Zone on Right Side");
                        break;
                    case 3:
                        result.append(" then Traverse Neutral Zone on Right Side");
                        break;
                }
                break;
        }

        return result.toString();
    }
}
