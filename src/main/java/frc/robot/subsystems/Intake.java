package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final TalonFX inMotor = new TalonFX(7);
    private final TalonFXConfiguration booba = new TalonFXConfiguration();
    private final DoubleSolenoid inSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 4, 11);

    public Intake() {
        booba.supplyCurrLimit = new SupplyCurrentLimitConfiguration(false, 15, 30, .1);
        inMotor.configFactoryDefault();
        inMotor.configAllSettings(booba);
        inSolenoid.set(Value.kReverse);
    }

    public void Down() {
        inSolenoid.set(Value.kReverse);
    }

    public void Up() {
        inSolenoid.set(Value.kForward);
    }

    public void setPower(double power) {
        inMotor.set(ControlMode.PercentOutput, power);
    }

    public boolean getIntake() {
        return inSolenoid.get().equals(DoubleSolenoid.Value.kForward);
    }

}
