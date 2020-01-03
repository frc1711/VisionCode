package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.pixy2api.*;
import frc.pixy2api.Pixy2CCC.*;
import frc.pixy2api.links.*;
import frc.robot.RobotMap;

public class PixyCam extends Subsystem{
    private Pixy2 pixy;
    public WPI_TalonSRX frontLeft;
    public WPI_TalonSRX rearLeft;
    public WPI_TalonSRX frontRight;
    public WPI_TalonSRX rearRight;
    public SpeedControllerGroup leftDrive; 
    public SpeedControllerGroup rightDrive; 
    public DifferentialDrive rDrive; 

    public PixyCam () {
        pixy = Pixy2.createInstance(new SPILink());
        pixy.init();

        frontLeft = new WPI_TalonSRX(RobotMap.frontLeftTalon);
        frontRight = new WPI_TalonSRX(RobotMap.frontRightTalon);
        rearLeft = new WPI_TalonSRX(RobotMap.rearLeftTalon);
        rearRight = new WPI_TalonSRX(RobotMap.rearRightTalon);
        
        rightDrive = new SpeedControllerGroup(frontRight, rearRight); 
        leftDrive = new SpeedControllerGroup(frontLeft, rearLeft); 
        
        rDrive = new DifferentialDrive(leftDrive, rightDrive); 
        rDrive.setSafetyEnabled(false);
    }

    private Block getBlock() {
        ArrayList<Block> blocks = pixy.getCCC().getBlocks();
        Block largestBlock = null;
        for (Block block : blocks) {
            if (largestBlock == null) {
                largestBlock = block;
            } else if (largestBlock.getWidth() < block.getWidth()) {
                largestBlock = block;
            }
        }
        System.out.println(pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, 25));
        return largestBlock;
    }

    public int getPoint() {
        Block block = getBlock();
        //The bottom left point is 315, 207
        //The center point is 158, 103
        
        if(block != null) {
            int x = block.getX();
            if (Math.abs(x - 158) < 45) {
                System.out.println("Near the center.");
                return 2;
            } else {
                System.out.println("Not in center");
                if (x - 158 > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        } else {
            System.out.println("Doesn't see block.");
            return 0;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }
}