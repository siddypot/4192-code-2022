package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class singleArm extends CommandBase{
    private final Climb climb;
    private final boolean b;
    private double velocity;

    public singleArm(Climb c, double p, boolean rightOrLeft){
        climb = c;
        b = rightOrLeft;
        velocity = p;
    }
    @Override 
    public void execute(){
        if(b){ //left side
            climb.extendClimbLeft(velocity);
        }
        else climb.extendClimbRight(-velocity);


    }
    public void end(){
        climb.extendClimbLeft(0);
        climb.extendClimbRight(0);
    }    
}
