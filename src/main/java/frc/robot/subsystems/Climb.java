package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {



    private final DoubleSolenoid activeHooks = new DoubleSolenoid(PneumaticsModuleType.REVPH, 6, 9);
    

    private final DoubleSolenoid pHooks = new DoubleSolenoid(PneumaticsModuleType.REVPH, 5, 10);

    private TalonFX climbMotorLeft = new TalonFX(5);
    private TalonFX climbMotorRight = new TalonFX(6);

    public Climb() {
        climbMotorRight.setInverted(true);
        climbMotorLeft.setNeutralMode(NeutralMode.Brake);
        climbMotorRight.setNeutralMode(NeutralMode.Brake);
        Constants.climberStartPos = getRightEncoder();
        activeHooks.set(Value.kReverse);
    }

    public void setAngleDown() {
        activeHooks.set(Value.kReverse);
    }

    public void setAngleUp() {
        activeHooks.set(Value.kForward);
    }

    public void climerDown() {
        pHooks.set(Value.kForward);
    }

    public void climerUp() {
        pHooks.set(Value.kReverse);
    }

    public boolean leftAtHeight(double h){
        return getLeftEncoder() >= h;
    }

    public boolean rightAtHeight(double h){
        return getRightEncoder() >= h;
    }

    public void extendClimbLeft(double power) {
        climbMotorLeft.set(ControlMode.PercentOutput, power);
    }

    public void extendClimbRight(double power) {
        climbMotorRight.set(ControlMode.PercentOutput, power);
    }

    public void resetInternalEncoder() {
        climbMotorLeft.setSelectedSensorPosition(0);
        climbMotorRight.setSelectedSensorPosition(0);
    }

    public double rightSpinRate() {
        return climbMotorRight.getSelectedSensorVelocity();
    }

    public double leftSpinRate() {
        return climbMotorLeft.getSelectedSensorVelocity();
    }


    public double getRightEncoder() {
        return climbMotorRight.getSelectedSensorPosition();
    }

    public double getLeftEncoder() {
        return climbMotorLeft.getSelectedSensorPosition();
    }

    public boolean getAngle() {
        return activeHooks.get().equals(DoubleSolenoid.Value.kForward);
    }

    public boolean getHooks() {
        return pHooks.get().equals(DoubleSolenoid.Value.kForward);
    }

    public void periodic(){
        SmartDashboard.putNumber("LeftClimbArm", getLeftEncoder());
        SmartDashboard.putNumber("RightClimbArm", getRightEncoder());
    }
}
