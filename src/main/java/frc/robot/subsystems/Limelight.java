package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.lang.Math;

public class Limelight extends SubsystemBase {
  private final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");  

  public double getOffsetYaw() { //returns the angle of the yaw(x) from the crosshair (center of lens) to the target
    return table.getEntry("tx").getDouble(0.0);
  }
  public double getOffsetPitch() { //returns the angle of the pitch(y) from the crosshair (center of lens) to the target
    return table.getEntry("ty").getDouble(0.0);
  }
  public double getDistance(){

    //god shooting distance 150 inches
    //god limelight angle is 25.6 degrees

    //height of hub = 96
    //height of limelight (to center of lens) = 24.122
    //angle of limelight = 15.89

    double boobalasmooga = (96 - 24.122);
    double banana = Math.tan((getOffsetPitch() + 25.603));
    double distance = (boobalasmooga) / (banana); 

    //if(getOffsetPitch() < 0 ) return -1; //target not found or too close

    return distance;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Distance", getDistance());
  }

}
