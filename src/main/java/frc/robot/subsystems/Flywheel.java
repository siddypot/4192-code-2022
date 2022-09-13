package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel extends SubsystemBase {

  private TalonFX shooterMotor = new TalonFX(9); 
  private TalonFX shooterFollower = new TalonFX(18);

  private CANCoder encoder = new CANCoder(1);

  private TalonFXConfiguration mainConfig = new TalonFXConfiguration();
  private TalonFXConfiguration followerConfig = new TalonFXConfiguration();

  public Flywheel() {
    configureMotor(shooterMotor);
    configureMotor(shooterFollower);
    shooterFollower.setInverted(true);

    mainConfig.peakOutputForward = .7;
    mainConfig.peakOutputForward = -.7;
    
    followerConfig.peakOutputForward = .7;
    followerConfig.peakOutputReverse = -.7;

  }

  public void configureMotor(TalonFX s){

    s.configFactoryDefault();
    s.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    s.config_kP(0, .002);
    s.config_kI(0, 0);
    s.config_kD(0, 0);
    s.config_kF(0, .0345);
    s.setNeutralMode(NeutralMode.Coast);
    s.setSensorPhase(true);

  }

  public void setPower(double power){
    shooterMotor.set(ControlMode.PercentOutput, power);
  }

  public void velocityBasedControl(double velo){
    shooterMotor.set(ControlMode.Velocity, velo);
  }

  public double getRate(){
    return encoder.getVelocity();
  }

  public double getMainRate(){
    return shooterMotor.getSelectedSensorVelocity();
  }

  public void twoMotorPower(double power){
    shooterMotor.set(ControlMode.PercentOutput, -power*1);
    shooterFollower.set(ControlMode.PercentOutput, power*1.9);
  }

  public void twoMotorPowerLow(double power){
    shooterMotor.set(ControlMode.PercentOutput, -power*1.4);
    shooterFollower.set(ControlMode.PercentOutput, power*1);
  }

  public void mainPower(double power){
    shooterMotor.set(ControlMode.PercentOutput, power);
  }

  public void followerPower(double power){
    shooterFollower.set(ControlMode.PercentOutput, power);
  }

  public void twoMotorCurrent(double curr){
    shooterMotor.set(ControlMode.Current, curr*1.2);
    shooterFollower.set(ControlMode.Current, curr);
  }

}
