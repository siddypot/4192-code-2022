package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Index;

public class runIndex extends CommandBase {
    private Index index;
    private double power;
    private Intake intake;
    private Timer timer = new Timer();

    public runIndex(Index index, Intake intake, double power) {
        this.index = index;
        this.power = power;
        this.intake = intake;
        addRequirements(index, intake);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        index.runMotor(power);
        intake.setPower(-.4);
    }

    @Override
    public void end(boolean interrupted) {
        index.runMotor(0);
        intake.setPower(0);

    }

    @Override
    public boolean isFinished(){
        if(timer.get() > 3) return true;
        return false;
    }

}
