package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Flywheel;

public class FlywheelShoot extends PIDCommand {

    Flywheel f;
    public FlywheelShoot(double spd, Flywheel f, boolean hg){
        
        super(
        new PIDController(.0000625, 0, 0), () -> f.getMainRate(), ()-> spd, output ->{
            if(hg) f.twoMotorPower(-output);
            if(!hg) f.twoMotorPowerLow(-output);
        }
        );

        this.f=f;
    }

    @Override
    public void end(boolean interrupted){
         f.setPower(0);
         f.twoMotorPower(0);
    }

}