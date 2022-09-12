package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.subsystems.Flywheel;

public class FlywheelShoot extends PIDCommand {

    
    public FlywheelShoot(double speeed, Flywheel f, boolean hg){
        
        super(
        
        new PIDController(.0000625, 0, 0), () -> f.getMainRate(), ()-> speeed, output ->{
            if(hg) f.twoMoterPower(-output);
            if(!hg) f.twoMotorPowerLow(-output);
        });
    }

}