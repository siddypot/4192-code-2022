package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class moveArms extends CommandBase {
  private Climb climb;
  private double velo;
  public moveArms(Climb climb, double velo) {
    this.climb = climb;
    this.velo = velo;
    addRequirements(climb);
  }

  @Override
  public void execute() {
    climb.extendClimbRight(-velo);
    climb.extendClimbLeft(velo);
  }


  @Override
  public void end(boolean interrupted) {
        climb.extendClimbLeft(0);
        climb.extendClimbRight(-0);
  }
  @Override
  public boolean isFinished() {
    return false;
  }
}
