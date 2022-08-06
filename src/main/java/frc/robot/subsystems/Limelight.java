package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.lang.Math;

public class Limelight extends SubsystemBase {
  private final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");  

  public double yawToTarget() { //returns the angle of the yaw(x) from the crosshair (center of lens) to the target
    return table.getEntry("tx").getDouble(0.0);
  }
  public double pitchToTarget() { //returns the angle of the pitch(y) from the crosshair (center of lens) to the target
    return table.getEntry("ty").getDouble(0);
  }
  public double getDistance(){

    //height of hub = 96
    //height of limelight (to center of lens) = 24.122
    //angle of limelight = 15.89

    double boobalasmooga = (96 - 24.122);
    double banana = Math.tan((pitchToTarget() + 15.89));
    double distance = (boobalasmooga) / (banana); //(96 - 24.122) / tan(ty + 15.89)

    if(pitchToTarget() < 0 ) return -1; //target not found

    return distance;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Distance", getDistance());
  }

}
