package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climb;

public class changeClimbLegPos extends InstantCommand {
  private Climb climb;
  public changeClimbLegPos(Climb climb) {
    this.climb = climb;
  }
  @Override
  public void initialize() {
    if(climb.getAngle()) climb.setAngleDown();
    
    else climb.setAngleUp();
    
  }
  public void end(boolean interrupted) {
    
  }


}
