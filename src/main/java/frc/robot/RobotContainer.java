package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  private final Index  index = new Index();
  private final Intake intake = new Intake();
  private final SwerveMods swerve = new SwerveMods();
  private final Limelight limelight = new Limelight();
  private final Joystick driver = new Joystick(0);
  private final Joystick operator = new Joystick(1);
  
  private final DriveUp testRun = new DriveUp(limelight, swerve);
  private final RunIntake runForwardIntake = new RunIntake(0.25, intake);
  private final RunIntake runBackwardIntake = new RunIntake(-0.70, intake);
  private final TeleopSwerve teleopSwerve = new TeleopSwerve(swerve, driver, 1);
  private final TeleopSwerve teleopSwerveSlow = new TeleopSwerve(swerve, driver, 0.5);
  private final TankToggle tankToggle = new TankToggle(swerve, driver, 1);

  public RobotContainer() {
    swerve.setDefaultCommand(teleopSwerve);
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // driver Controller
    new JoystickButton(driver, XboxController.Button.kA.value).whenHeld(testRun);
    new JoystickButton(driver, XboxController.Button.kB.value)
        .whenPressed(new InstantCommand(() -> swerve.zeroHeading()));
    new JoystickButton(driver, XboxController.Button.kX.value);
    new JoystickButton(driver, XboxController.Button.kY.value).toggleWhenPressed(new ChangeIntakePos(intake));
    new JoystickButton(driver, XboxController.Button.kRightBumper.value).whenHeld(teleopSwerveSlow);
    new JoystickButton(driver, XboxController.Button.kLeftBumper.value).whenHeld(tankToggle);
    new Trigger(() -> driver.getRawAxis(XboxController.Axis.kRightTrigger.value) > 0.2)
        .whileActiveContinuous(runForwardIntake);
    new Trigger(() -> driver.getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0.2)
        .whileActiveContinuous(runBackwardIntake);

    // Operator Controller
    new JoystickButton(operator, XboxController.Button.kA.value);
    new JoystickButton(operator, XboxController.Button.kB.value).whenHeld(new runIndex(index, intake,.18));
    new JoystickButton(operator, XboxController.Button.kX.value).whenHeld(new runIndex(index, intake, -.18));
    new JoystickButton(operator, XboxController.Button.kY.value);
    new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    new JoystickButton(operator, XboxController.Button.kBack.value);
    new JoystickButton(operator, XboxController.Button.kStart.value);
    new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    new Trigger(() -> operator.getRawAxis(XboxController.Axis.kRightTrigger.value) > 0.2);
    new Trigger(() -> operator.getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0.2);

  }

  public Command getAutonomousCommand() {
    return null;
  }
}
