package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class DriveUp extends CommandBase{

    PIDController pid = new PIDController(0, 0, 0); //tune me for good values
    private Limelight limelight;
    private SwerveMods swerve;
    
    //turn to target and then drive up to a safe distance to hub
    //tuning PID command, DO NOT USE IN FINAL CODE!!!

    public DriveUp(Limelight l, SwerveMods s){
        
        limelight = l;
        swerve = s;
    }

    @Override
    public void execute(){
        swerve.drive(new Translation2d(0,0), pid.calculate(limelight.getOffsetPitch(), 0 ), true);
    }

    
}
