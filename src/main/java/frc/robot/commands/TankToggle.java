
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class TankToggle extends CommandBase {
    public TankToggle() {
        addRequirements();
    }

    @Override
    public void initialize() {
        Constants.fieldOriented = false;
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        Constants.fieldOriented = true;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
