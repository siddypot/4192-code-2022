package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class DriveUp extends CommandBase{

    private Limelight limelight;
    private SwerveMods swerve;
    
    //turn to target and then drive up to a safe distance to hub
    //tuning PID command, DO NOT USE IN FINAL CODE!!!

    public DriveUp(Limelight l, SwerveMods s){
        limelight = l;
        swerve = s;
    }
    
}
