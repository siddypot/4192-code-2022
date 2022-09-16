package frc.robot.commands.ShootingCommands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class autoAlign extends CommandBase{

    // PIDController pid = new PIDController(0.05, 0,.000005); //tune me (Sid's code)
    // PIDController pid = new PIDController(.07, 0, .000005);
    //PIDController pid = new PIDController(.06,0.05,0.005);
    PIDController pid = new PIDController(.15, 0, .000005);

    private Limelight limelight;
    private SwerveMods swerve;
    public autoAlign(Limelight l, SwerveMods s){
        limelight = l;
        swerve = s;
    }
    @Override
    public void execute(){
        // stop moving the robot if it's within +- 1 degree of target
        double offset = -limelight.getOffsetYaw();
        swerve.drive(new Translation2d(0 ,0), (Math.abs(offset) > 1 ? pid.calculate(offset, 0) : 0), false);
    }
    @Override
    public boolean isFinished(){


        return (-limelight.getOffsetYaw() == swerve.getYawAngle());

    }

    
}
