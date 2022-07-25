package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SwerveMods extends SubsystemBase {

    private final SwerveModules mod0 = new SwerveModules(29.0, 14, 15, 3);

    private final SwerveModules mod1 = new SwerveModules(193.0,3,2, 1);

    private final SwerveModules mod2 = new SwerveModules(363.0, 12, 13, 2);

    private final SwerveModules mod3 = new SwerveModules(300, 1, 0, 0);

    private final AHRS gyro = new AHRS(SPI.Port.kMXP);

    public SwerveMods() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                zeroHeading();
            } catch (Exception e) {
            }
        }).start();
    }

    public Rotation2d getYaw() {
        return Rotation2d.fromDegrees(360.0 - gyro.getYaw());
    }

    public void zeroHeading() {
        gyro.reset();
    }

    public double getHeading() {
        return Math.IEEEremainder(gyro.getAngle(), 360);
    }

    public Rotation2d getRotation() {
        return Rotation2d.fromDegrees(getHeading());
    }

    public void stopModules() {
        mod0.resetHeading();
        mod1.resetHeading();
        mod2.resetHeading();
        mod3.resetHeading();
    }

    public void setModulesStates(SwerveModuleState[] desiredState) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredState, 4.5);
        mod0.setDesiredState(desiredState[0], false);
        mod1.setDesiredState(desiredState[1], false);
        mod2.setDesiredState(desiredState[2], false);
        mod3.setDesiredState(desiredState[3], false);

    }

    @Override
    public void periodic() {

    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates = Constants.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                        translation.getX(),
                        translation.getY(),
                        rotation,
                        getYaw())
                        : new ChassisSpeeds(
                                translation.getX(),
                                translation.getY(),
                                rotation));
        // SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates,
        // Constants.Swerve.maxSpeed);
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.maxSpeed);

        setModulesStates(swerveModuleStates);
    }

}
