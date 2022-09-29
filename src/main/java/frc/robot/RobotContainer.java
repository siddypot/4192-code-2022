package frc.robot;

import frc.robot.commands.ClimbCommands.*;
import frc.robot.commands.DriveCommands.*;
import frc.robot.commands.ShootingCommands.*;
import frc.robot.commands.IntakeCommands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.SwerveMods;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  
  private final Index  index = new Index();
  private final Intake intake = new Intake();
  private final SwerveMods swerve = new SwerveMods();
  private final Limelight limelight = new Limelight();
  private final Joystick driver = new Joystick(0);
  private final Joystick operator = new Joystick(1);
  private final Flywheel flywheel = new Flywheel();
  private final Climb climb = new Climb();

  //private final intakeDropRun runForwardIntake = new intakeDropRun(intake , .25);
  //private final intakeDropRun runBackwardIntake = new intakeDropRun(intake , -.25);
  
  private final RunIntake runForwardIntake = new RunIntake(0.25, intake);
  private final RunIntake runBackwardIntake = new RunIntake(-0.70, intake);

  private final FlywheelShoot highgoal = new FlywheelShoot(-7050, flywheel, true);

  private final autoAlign align = new autoAlign(limelight, swerve);
  private final TeleopSwerve teleopSwerve = new TeleopSwerve(swerve, driver, 1);
  private final TeleopSwerve teleopSwerveSlow = new TeleopSwerve(swerve, driver, 0.5);
  private final TankToggle tankToggle = new TankToggle(swerve, driver, 1);

  public RobotContainer() {
    swerve.setDefaultCommand(teleopSwerve);
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // driver Controller
    new JoystickButton(driver, XboxController.Button.kA.value).whenHeld(align);
    new JoystickButton(driver, XboxController.Button.kB.value)
        .whenPressed(new InstantCommand(() -> swerve.zeroHeading()));
    new JoystickButton(driver, XboxController.Button.kX.value);
    new JoystickButton(driver, XboxController.Button.kY.value).toggleWhenPressed(new ChangeIntakePos(intake));
    new JoystickButton(driver, XboxController.Button.kRightBumper.value).whenHeld(tankToggle);
    new JoystickButton(driver, XboxController.Button.kLeftBumper.value).whenHeld(teleopSwerveSlow);
    new Trigger(() -> driver.getRawAxis(XboxController.Axis.kRightTrigger.value) > 0.2)
        .whileActiveContinuous(runBackwardIntake);
    new Trigger(() -> driver.getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0.2)
        .whileActiveContinuous(runForwardIntake);

    // Operator Controller
    new JoystickButton(operator, XboxController.Button.kA.value).toggleWhenPressed(highgoal);
    new JoystickButton(operator, XboxController.Button.kB.value).whenHeld(new runIndex(index, intake,.18));
    new JoystickButton(operator, XboxController.Button.kX.value).whenHeld(new runIndex(index, intake, -.18));
    new JoystickButton(operator, XboxController.Button.kY.value).whenPressed(new climbHooks(climb));
    new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    new JoystickButton(operator, XboxController.Button.kBack.value).whenPressed(new changeClimbLegPos(climb));
    new JoystickButton(operator, XboxController.Button.kStart.value);
    new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    new Trigger(() -> operator.getRawAxis(XboxController.Axis.kRightTrigger.value) > 0.2);
    new Trigger(() -> operator.getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0.2);

  }

  public Command getAutonomousCommand() {


    

    /*PIDController xControl, yControl;
    ProfiledPIDController angularControl;

    xControl = new PIDController(0, 0, 0);
    yControl = new PIDController(0, 0, 0);
    angularControl = new ProfiledPIDController(.15, 0, .000005, Constants.angleConstraints);
    angularControl.enableContinuousInput(-Math.PI, Math.PI);



    
    TrajectoryConfig autonConfig = new TrajectoryConfig(Constants.maxSpeed, Constants.maxAccel);
    autonConfig.setKinematics(Constants.swerveKinematics);
  
    PathPlannerTrajectory trajectory = PathPlanner.loadPath("goodPath", 1.0, 1.00);

    SwerveControllerCommand autonCommand0 = new SwerveControllerCommand(
      trajectory, 
      swerve::getPose,
      Constants.swerveKinematics, 
      xControl, 
      yControl, 
      angularControl, 
      swerve::setModuleStates, 
      swerve
    );
    SequentialCommandGroup finalAutonCommand = new SequentialCommandGroup(
      new InstantCommand(() -> swerve.resetOdometry(trajectory.getInitialPose())), 
      autonCommand0,

      
      new InstantCommand(()-> swerve.stopModules()));

    return finalAutonCommand;*/
    return null;
  }
}
