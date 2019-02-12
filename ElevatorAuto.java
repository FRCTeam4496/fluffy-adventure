/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.OI;
import frc.robot.Robot;

public class ElevatorAuto extends Command {

  double motorDrive = 0;
  double driveTime = 0;
  Timer moveTimer = new Timer();

  public ElevatorAuto() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    boolean ElevFall = OI.xboxController.getRawButtonPressed(0);
    boolean ElevRise = OI.xboxController.getRawButtonPressed(1);
    //public static boolean IsBallHeld = Get Motor Controller readings
    if (ElevFall == true) {
        motorDrive = -0.5;//Most likely will change
        driveTime = 2;    //Subject to change with tests
    }
    if (ElevRise == true) {
        motorDrive = 0.5; //Subject to change with tests
        driveTime = 2;    //Subject to change with tests
    }
    moveTimer.start();
  }
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

      //if(Motor movement to intake was last shown as picking up the ball && IsBallHeld == false) {
      //driveTime = driveTime - Height Difference between 1st floor ball and 2nd floor hatch
      Robot.CurrentElevLevel--;
      //IsBallHeld == true;
      //} else (Motor movement to intake was last shown as shooting the ball && motorDrive < 0 && IsBallHeld == true) {
      //driveTime = driveTime * ((Robot.CurrentElevLevel - 1) + Height Difference between 1st floor ball and 1st floor hatch)
      Robot.CurrentElevLevel = 1;
      //IsBallHeld == false;
      //}
      if (moveTimer.get() <= driveTime) {  
      //Motor moves = motorDrive

      }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (moveTimer.get() <= driveTime) {
      return false;
    } else {
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    moveTimer.stop();
    moveTimer.reset();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
