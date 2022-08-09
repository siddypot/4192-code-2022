package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class intakeDropRun extends CommandBase {


    

    private final Intake intake;
    private final double power;

    public intakeDropRun(Intake i, double p){
        intake = i;
        power = p;
    }

    @Override
    public void initialize() {
        if (intake.getIntake()) intake.Down();
    }
    @Override
    public void execute(){
        intake.setPower(power);
    }
    @Override
    public void end(boolean interrupted){
        intake.setPower(0);
        intake.Up();
    }
    @Override
    public boolean isFinished() {
        int ballshaha = Constants.BALLS;
        return (ballshaha == ballshaha+2);
    }


    
    
}
