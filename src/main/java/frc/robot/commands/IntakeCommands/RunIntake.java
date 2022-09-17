package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class RunIntake extends CommandBase {
    private double power;
    private Intake intake;

    public RunIntake(double power, Intake intake) {
        this.power = power;
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        intake.setPower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
