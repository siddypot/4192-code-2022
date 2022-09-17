package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climb;

public class climbHooks extends InstantCommand {
  private Climb climb;

  public climbHooks(Climb climb) {
    this.climb = climb;
  }

  @Override
  public void initialize() {
    if(climb.getHooks()) climb.climerDown();
    
    else climb.climerUp();
    
  }
}
