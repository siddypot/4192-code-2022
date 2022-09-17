package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {

    private final DoubleSolenoid angleClimb = new DoubleSolenoid(PneumaticsModuleType.REVPH, 6, 9);// placeholders

    private final DoubleSolenoid climber = new DoubleSolenoid(PneumaticsModuleType.REVPH, 5, 10);
    private TalonFX climbMotorLeft = new TalonFX(5);
    private TalonFX climbMotorRight = new TalonFX(6);

    public Climb() {
        climbMotorRight.setInverted(true);
        climbMotorLeft.setNeutralMode(NeutralMode.Brake);
        climbMotorRight.setNeutralMode(NeutralMode.Brake);
        Constants.climberStartPos = getRightEncoder();
        angleClimb.set(Value.kReverse);
    }

    public void setAngleDown() {
        angleClimb.set(Value.kReverse);
    }

    public void setAngleUp() {
        angleClimb.set(Value.kForward);
    }

    public void climerDown() {
        climber.set(Value.kForward);
    }

    public void climerUp() {
        climber.set(Value.kReverse);
    }


    public void extendClimbLeft(double velo) {
        climbMotorLeft.set(ControlMode.PercentOutput, velo);
    }

    public void extendClimbRight(double power) {
        climbMotorRight.set(ControlMode.PercentOutput, power);
    }

    public void extendLeftVelo(double velocity) {
        climbMotorLeft.set(ControlMode.Velocity, velocity);
    }

    public void extendRightVelo(double velocity) {
        climbMotorRight.set(ControlMode.Velocity, velocity);
    }

    public void resetInternalEncoder() {
        climbMotorLeft.setSelectedSensorPosition(0);
    }

    public double rightSpinRate() {
        return climbMotorRight.getSelectedSensorVelocity();
    }

    public double leftSpinRate() {
        return climbMotorLeft.getSelectedSensorVelocity();
    }

    public void setMotionMagic(double setpoint) {
        climbMotorRight.follow(climbMotorLeft);
        climbMotorLeft.set(ControlMode.MotionMagic, setpoint, DemandType.ArbitraryFeedForward, 0.1);
    }

    public boolean getAtHeightLimit(double setpoint) {
        return getLeftEncoder() >= setpoint;
    }

    public double getRightEncoder() {
        return climbMotorRight.getSelectedSensorPosition();
    }

    public double getLeftEncoder() {
        return climbMotorLeft.getSelectedSensorPosition();
    }

    public boolean getAngle() {
        return angleClimb.get().equals(DoubleSolenoid.Value.kForward);
    }

    public boolean getHooks() {
        return climber.get().equals(DoubleSolenoid.Value.kForward);
    }

}
