package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class changeClimbLegPos extends CommandBase {
  private Climb climb;
  public changeClimbLegPos(Climb climb) {
    this.climb = climb;
    addRequirements(climb);
  }
  @Override
  public void initialize() {
    if(!climb.getAngle()) climb.setAngleUp();
      
    else climb.setAngleDown();
    
  }
  @Override
  public void execute() {

  }
  @Override
  public void end(boolean interrupted) {
  }
  @Override
  public boolean isFinished() {
    return false;
  }
}
