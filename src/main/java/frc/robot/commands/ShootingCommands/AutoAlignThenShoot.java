package frc.robot.commands.ShootingCommands;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.DriveCommands.driveToHub;
import frc.robot.commands.ShootingCommands.*;
import frc.robot.commands.IntakeCommands.*;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveMods;


public class AutoAlignThenShoot extends SequentialCommandGroup{
    Limelight l;
    SwerveMods s;
    Flywheel f;
    Index i;
    Intake i1;

    private final autoAlign align = new autoAlign(l,s);
    private final driveToHub drive = new driveToHub(l, s);
    private final FlywheelShoot shoot = new FlywheelShoot(-7050, f , true);
    private final runIndex index = new runIndex(i, i1, .18);

    public AutoAlignThenShoot(Limelight l, SwerveMods s, Flywheel f, Index i, Intake i1){
        this.l = l;
        this.s = s;
        this.f = f;
        this.i = i;
        this.i1 = i1;

        addCommands(align , drive, 
        new ParallelCommandGroup(shoot, index));

    }
    
}
