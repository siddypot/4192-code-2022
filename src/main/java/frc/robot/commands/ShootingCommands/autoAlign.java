package frc.robot.commands.ShootingCommands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class autoAlign extends CommandBase{

    PIDController pid = new PIDController(0, 0, 0); //tune me for good values
    private Limelight limelight;
    private SwerveMods swerve;
    public autoAlign(Limelight l, SwerveMods s){
        limelight = l;
        swerve = s;
    }
    @Override
    public void execute(){
        swerve.drive(new Translation2d(0 ,0), (pid.calculate(limelight.getOffsetPitch(), 0)), false);
    }

    
}
