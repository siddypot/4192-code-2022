package frc.robot.commands.ClimbCommands;

//important !! get encoder values of the final length
//remind sidd pls

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class moveArms extends CommandBase {
  private Climb climb;
  private double pow;
  
  
  public moveArms(Climb climb, double power) {
    this.climb = climb;
    pow = power;
  }

  @Override
  public void initialize(){
      climb.resetInternalEncoder();
  } 

  @Override
  public void execute() {

    climb.extendClimbLeft(pow);

    

    climb.extendClimbRight(pow);

  }

  @Override
  public void end(boolean interrupted) {
    climb.extendClimbLeft(0);
    climb.extendClimbRight(0);
  }

}
