package frc.robot.commands.ShootingCommands;

import java.sql.DriverPropertyInfo;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.Wait;
import frc.robot.commands.ShootingCommands.*;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveMods;


public class AutoAlignThenShoot extends SequentialCommandGroup{
    Limelight l;
    SwerveMods s;

    private final autoAlign align = new autoAlign(l,s);
    private final Wait wait = new Wait(2);
    private final driveToHub drive = new driveToHub(l, s);

    public AutoAlignThenShoot(Limelight l, SwerveMods s){
        this.l = l;
        this.s = s;

        addCommands(align , wait , drive);

    }
    
}
