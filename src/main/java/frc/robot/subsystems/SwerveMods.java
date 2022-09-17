package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SwerveMods extends SubsystemBase {

    private final SwerveModules mod0 = new SwerveModules(29.0, 14, 15, 3); // front left

    private final SwerveModules mod1 = new SwerveModules(193.0,3,2, 1); //back right

    private final SwerveModules mod2 = new SwerveModules(363.0, 12, 13, 2); //back left

    private final SwerveModules mod3 = new SwerveModules(300, 1, 0, 0); //front right

    private final AHRS gyro = new AHRS(SPI.Port.kMXP);

    private final SwerveDriveOdometry odometer = new SwerveDriveOdometry(Constants.swerveKinematics, new Rotation2d(0));

    public SwerveMods() {
        /*new Thread(() -> {
            try {
                Thread.sleep(1000);
                zeroHeading();
            } catch (Exception e) {
            }
        }).start();*/
    }

    public Rotation2d getYaw() {
        return Rotation2d.fromDegrees(360.0 - gyro.getYaw());
    }

    public double getYawAngle(){
        return gyro.getYaw();
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

    


    public void setModuleStates(SwerveModuleState[] desiredState) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredState, 4.5);
        mod0.setDesiredState(desiredState[0], false);
        mod1.setDesiredState(desiredState[1], false);
        mod2.setDesiredState(desiredState[2], false);
        mod3.setDesiredState(desiredState[3], false);

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("angle", gyro.getYaw());
        odometer.update(getRotation(),mod0.getState(),mod3.getState(),mod2.getState(),mod1.getState());
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative) {
        SwerveModuleState[] swerveModuleStates = Constants.swerveKinematics.toSwerveModuleStates(

        fieldRelative ? 
            ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(),translation.getY(),rotation,getYaw()):
             
            new ChassisSpeeds(translation.getX(),translation.getY(),rotation));


        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.maxSpeed);

        setModuleStates(swerveModuleStates);
    }

    public Pose2d getPose(){
        return odometer.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose){
        odometer.resetPosition(pose, getRotation());
    } 

}
