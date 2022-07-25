package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveMods;

//import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopSwerve extends CommandBase {

    private final SwerveMods swerve;
    private Translation2d translation;
    private final Joystick driver;

    private double x, y, turn, overallSpeed;

    public TeleopSwerve(SwerveMods swerve, Joystick controller, double overallSpeed) {
 
        this.swerve = swerve;
        this.driver = controller;
        
        this.overallSpeed = overallSpeed;
        addRequirements(this.swerve);

    }

    @Override
    public void execute() {
        this.x = driver.getRawAxis(XboxController.Axis.kLeftY.value) * 1;
        this.y = driver.getRawAxis(XboxController.Axis.kLeftX.value) * 1;
        this.turn = driver.getRawAxis(XboxController.Axis.kRightX.value) * .3;
        x = (Math.abs(x) < 0.1) ? 0 : x;
        y = (Math.abs(y) < 0.1) ? 0 : y;
        turn = (Math.abs(turn) < 0.1) ? 0 : turn;
        //x = xLimiter.calculate(x) * Constants.maxSpeed * overallSpeed;
        //y = yLimiter.calculate(y) * Constants.maxSpeed * overallSpeed;
        //turn = turnLimiter.calculate(turn) * Constants.maxAngular * overallSpeed;
        turn = turn * Constants.maxAngular;
        /*if (Constants.fieldOriented)
            speeds = ChassisSpeeds.fromFieldRelativeSpeeds(x, y, turn, swerve.getRotation());
        else
            speeds = new ChassisSpeeds(x, y, turn);*/
        translation = new Translation2d(x, y).times(Constants.maxSpeed).times(overallSpeed);

        swerve.drive(translation, turn * .7, true, true);
        

    }

    @Override
    public void end(boolean interrupted) {
        swerve.stopModules();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
