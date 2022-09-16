package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class changeClimbLegPos extends InstantCommand {
  private Climb climb;
  public changeClimbLegPos(Climb climb) {
    this.climb = climb;
  }
  @Override
  public void initialize() {
    if(!climb.getAngle()) climb.setAngleUp();
      
    else climb.setAngleDown();
    
  }
  public void end(boolean interrupted) {
    
  }


}
