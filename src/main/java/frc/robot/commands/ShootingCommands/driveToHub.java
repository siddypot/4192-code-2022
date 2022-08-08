package frc.robot.commands.ShootingCommands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveMods;

public class driveToHub extends CommandBase{
    private final Limelight limelight;
    private final SwerveMods swerve;

    public driveToHub(Limelight l, SwerveMods s){

        limelight = l;
        swerve = s;

    }

    @Override
    public void execute(){
        double distanceToDrive = 0;
        if((limelight.getDistanceInch() > 150.0)) distanceToDrive = Constants.inchesToMeters((limelight.getDistanceInch() - 150));
        swerve.drive(new Translation2d(distanceToDrive,0), 0, false); 
    }
    
}
