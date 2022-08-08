package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Wait extends CommandBase {

  private Timer timer;
  private double seconds;

  public Wait(double seconds) {
    this.seconds = seconds;
    timer = new Timer();
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(seconds);
  }
}