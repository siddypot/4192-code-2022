package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Index extends SubsystemBase {
    private final TalonFX indexMotor = new TalonFX(8);
    private final CANCoder encoder = new CANCoder(0);

    public Index() {
        

    }

    public void runMotor(double power) {
        indexMotor.set(ControlMode.PercentOutput, power);
    }

    public void setVelocity(double velo) {
        indexMotor.set(ControlMode.Velocity, velo);
    }

    public double getEncoderRate() {
        return encoder.getVelocity();
    }

    public double getInternalEncoder() {
        return indexMotor.getSelectedSensorVelocity();
    }

    public void magicMotion() {
        indexMotor.set(TalonFXControlMode.MotionMagic, .2);
    }

}
