package org.frc5687.steamworks.protobot.commands.test;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc5687.steamworks.protobot.commands.actions.mandibles.ReceiveMandibles;

import static org.frc5687.steamworks.protobot.Robot.mandibles;
import static org.frc5687.steamworks.protobot.Robot.pdp;

/**
 * Created by Ben Bernard on 4/4/2017.
 */
public class TestCloseMandibles extends ReceiveMandibles {

    private long _endMillis;
    private double _maxAmps;

    public TestCloseMandibles() {
        super();
    }

    @Override
    protected void initialize() {
        _endMillis = System.currentTimeMillis() + 500;
        _maxAmps = 0;
        super.initialize();
    }

    @Override
    protected void execute() {
        _maxAmps = Math.max(_maxAmps, pdp.getMandiblesAmps());
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        return state==State.CLAMP || System.currentTimeMillis() > _endMillis;
    }

    @Override
    protected void end() {
        if (state==State.CLAMP) {
            DriverStation.reportError("Mandible clamp test passed (" + _maxAmps + " amps)", false);
            SmartDashboard.putBoolean("SelfTest/Mandibles/Clamp/Passed", true);
        } else {
            DriverStation.reportError("Mandible clamp amps not reached (" + _maxAmps + " amps)", false);
            SmartDashboard.putBoolean("SelfTest/Mandibles/Clamp/Passed", false);
        }
        SmartDashboard.putNumber("SelfTest/Mandibles/Clamp/Amps", _maxAmps);
        mandibles.stop();
    }
}
