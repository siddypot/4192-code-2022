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

  public double getDistanceInch(){

    //god shooting distance 150 inches
    //god limelight angle is 25.6 degrees
    int hubheight = 96;
    double angleOfLL = 43.125;
    double heightOfLL = 27.217738618 ;
    //height of hub = 96
    //height of limelight (to center of lens) = 24.122
    //angle of limelight = 15.89

    double boobalasmooga = (hubheight - heightOfLL);
    double banana = Math.tan((getOffsetPitch() + angleOfLL));
    double distance = (boobalasmooga) / (banana); 


    return distance;
  }


  @Override
  public void periodic() {
    SmartDashboard.putNumber("Distance", getDistanceInch());
    SmartDashboard.putNumber("Limelight X dist", getOffsetYaw());
    SmartDashboard.putNumber("Limelight Y dist", getOffsetPitch());
  }

}
