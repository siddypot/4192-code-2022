package frc.robot.commands.DriveCommands;



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
        if((limelight.getDistanceInch() > 150.0)) distanceToDrive = Constants.inchesToMeters(150-limelight.getDistanceInch());
        swerve.drive(new Translation2d(Constants.inchesToMeters(distanceToDrive),0), 0, false); 
    }
    
}
