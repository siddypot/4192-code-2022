package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;

public class SwerveModules {
    private double lastAngle;
    private double angleOffset, angGearRatio = (12.8 / 1.0), driveGearRatio = (6.75 / 1.0);
    private final CANCoder angleEncoder;
    private final TalonFX driveMotor, turnMotor;
    private final TalonFXConfiguration sDC, sAC; // swerve drive configuration , swerve angle configuration
    private final CANCoderConfiguration ccc;
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward((0.667 / 12), (2.44 / 12), (0.27 / 12));

    public SwerveModules(double angleOffset, int driveMotorID, int turnMotorID, int canCoderID) {
        this.angleOffset = angleOffset;
        sAC = new TalonFXConfiguration();
        ccc = new CANCoderConfiguration();
        sDC = new TalonFXConfiguration();
        driveMotor = new TalonFX(driveMotorID);
        
        turnMotor = new TalonFX(turnMotorID);
        
        angleEncoder = new CANCoder(canCoderID);
        noDont();
        pleaseDont();
        doAFlip();
        lastAngle = getState().angle.getDegrees();
    }

    public void setDesiredState(SwerveModuleState state, boolean loop) {

        state = optimize(state, getState().angle);
        if (loop) {
            double percentOutput = state.speedMetersPerSecond / Constants.maxSpeed;
            driveMotor.set(ControlMode.PercentOutput, percentOutput);
        } else {
            double velocity = MPSToFalcon(state.speedMetersPerSecond, (Math.PI * (Units.inchesToMeters(3.94))),
                    driveGearRatio);
            driveMotor.set(ControlMode.Velocity, velocity, DemandType.ArbitraryFeedForward,
                    feedforward.calculate(state.speedMetersPerSecond));
        }
        double angle = (Math.abs(state.speedMetersPerSecond) <= (Constants.maxSpeed * 0.01)) ? lastAngle
                : state.angle.getDegrees();
        turnMotor.set(ControlMode.Position, degreesToFalcon(angle, angGearRatio));
        lastAngle = angle;
    }

    public void pleaseDont() {
        angleEncoder.configFactoryDefault();
        angleEncoder.configAllSettings(ccc);
        ccc.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        ccc.sensorDirection = false;
        ccc.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        ccc.sensorTimeBase = SensorTimeBase.PerSecond;
    }

    public void noDont() { // turn motor configuration
        SupplyCurrentLimitConfiguration driverSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35, 65, 0.1);

        sDC.slot0.kP = 0.10;
        sDC.slot0.kI = 0.0;
        sDC.slot0.kD = 0.0;
        sDC.slot0.kF = 0.0;
        sDC.supplyCurrLimit = driverSupplyLimit;
        sDC.initializationStrategy = SensorInitializationStrategy.BootToZero;
        driveMotor.configFactoryDefault();
        driveMotor.configAllSettings(sDC);
        driveMotor.setInverted(false);
        driveMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.setSelectedSensorPosition(0);
    }

    public void doAFlip() { // drive motor configuration
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(true, 15, 30, 0.1);

        sAC.slot0.kP = 0.6;
        sAC.slot0.kI = 0.0;
        sAC.slot0.kD = 12;
        sAC.slot0.kF = 0.0;
        sAC.supplyCurrLimit = angleSupplyLimit;
        sAC.initializationStrategy = SensorInitializationStrategy.BootToZero;
        sAC.openloopRamp = 0.25;
        sAC.closedloopRamp = 0.0;
        turnMotor.configFactoryDefault();
        turnMotor.configAllSettings(sAC);
        turnMotor.setInverted(false);
        turnMotor.setNeutralMode(NeutralMode.Coast);
        double absolutePosition = degreesToFalcon(getCanCoder().getDegrees() - angleOffset, angGearRatio);
        turnMotor.setSelectedSensorPosition(absolutePosition);
    }

    public void resetHeading() {
        double absolutePosition = degreesToFalcon(getCanCoder().getDegrees() - angleOffset,
                angGearRatio);
        turnMotor.setSelectedSensorPosition(absolutePosition);
    }

    public double degreesToFalcon(double degrees, double gearRatio) {
        return degrees / (360.0 / (gearRatio * 2048.0));

    }

    public Rotation2d getCanCoder() {
        return Rotation2d.fromDegrees(angleEncoder.getAbsolutePosition());
    }

    public SwerveModuleState getState() {
        double velocity = falconToMPS(turnMotor.getSelectedSensorVelocity(), (Math.PI * (Units.inchesToMeters(3.94))),
                driveGearRatio);
        Rotation2d angle = Rotation2d.fromDegrees(falconToDegrees(turnMotor.getSelectedSensorPosition(), angGearRatio));
        return new SwerveModuleState(velocity, angle);
    }

    public static double RPMToFalcon(double RPM, double gearRatio) {
        double motorRPM = RPM * gearRatio;
        double sensorCounts = motorRPM * (2048.0 / 600.0);
        return sensorCounts;
    }

    public static double falconToMPS(double velocitycounts, double circumference, double gearRatio) {
        double wheelRPM = falconToRPM(velocitycounts, gearRatio);
        double wheelMPS = (wheelRPM * circumference) / 60;
        return wheelMPS;
    }

    public static double falconToRPM(double velocityCounts, double gearRatio) {
        double motorRPM = velocityCounts * (600.0 / 2048.0);
        double mechRPM = motorRPM / gearRatio;
        return mechRPM;
    }

    public static double falconToDegrees(double counts, double gearRatio) {
        return counts * (360.0 / (gearRatio * 2048.0));
    }

    public static double MPSToFalcon(double velocity, double circumference, double gearRatio) {
        double wheelRPM = ((velocity * 60) / circumference);
        double wheelVelocity = RPMToFalcon(wheelRPM, gearRatio);
        return wheelVelocity;
    }

    public static SwerveModuleState optimize(SwerveModuleState desiredState, Rotation2d currentAngle) {
        double targetAngle = placeInAppropriate0To360Scope(currentAngle.getDegrees(), desiredState.angle.getDegrees());
        double targetSpeed = desiredState.speedMetersPerSecond;
        double delta = targetAngle - currentAngle.getDegrees();
        if (Math.abs(delta) > 90) {
            targetSpeed = -targetSpeed;
            targetAngle = delta > 90 ? (targetAngle -= 180) : (targetAngle += 180);
        }
        return new SwerveModuleState(targetSpeed, Rotation2d.fromDegrees(targetAngle));
    }

    private static double placeInAppropriate0To360Scope(double scopeReference, double newAngle) {
        double lowerBound;
        double upperBound;
        double lowerOffset = scopeReference % 360;
        if (lowerOffset >= 0) {
            lowerBound = scopeReference - lowerOffset;
            upperBound = scopeReference + (360 - lowerOffset);
        } else {
            upperBound = scopeReference - lowerOffset;
            lowerBound = scopeReference - (360 + lowerOffset);
        }
        while (newAngle < lowerBound) {
            newAngle += 360;
        }
        while (newAngle > upperBound) {
            newAngle -= 360;
        }
        if (newAngle - scopeReference > 180) {
            newAngle -= 360;
        } else if (newAngle - scopeReference < -180) {
            newAngle += 360;
        }
        return newAngle;
    }
}
