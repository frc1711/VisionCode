/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.subsystems.PixyCam;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TurnCommand extends Command {

  public PixyCam pixy;

  public TurnCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    pixy = new PixyCam();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    int move = Robot.lastMoveDir;
    Boolean shouldUpdate = false;
    if (Robot.millisTimer > 100) {
      System.out.println("UPDATING MOVE");
      move = pixy.getPoint();
      Robot.lastMoveDir = move;
      Robot.millisTimer = 0;
      shouldUpdate = true;
    }
    double turnSpeed = 0.2; 
    double moveSpeed = 0.5;


    if (shouldUpdate) {
      if (move == 2) {
        System.out.println("STRAIGHT."); 
        pixy.frontLeft.set(moveSpeed);
        pixy.frontRight.set(moveSpeed);
        pixy.rearLeft.set(moveSpeed);
        pixy.rearRight.set(moveSpeed);
      } else if (move == -1) {
        System.out.println("DIR 1"); 
        pixy.frontLeft.set(0);
        pixy.rearLeft.set(0);
        pixy.frontRight.set(-turnSpeed);
        pixy.rearRight.set(-turnSpeed);
      } else if (move == 1) {
        System.out.println("DIR 2"); 
        pixy.frontLeft.set(turnSpeed);
        pixy.rearLeft.set(turnSpeed);
        pixy.frontRight.set(0);
        pixy.rearRight.set(0);
      } else {
        pixy.frontLeft.set(0); 
        pixy.frontRight.set(0); 
        pixy.rearRight.set(0); 
        pixy.rearLeft.set(0);
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
