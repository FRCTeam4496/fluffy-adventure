/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.OI;
import frc.robot.Robot;

public class UltrasonicSensor extends Command {

  Ultrasonic ultra = new Ultrasonic(1,1);
  public static double Ultrasonicreading;
  public boolean isFinished;
  boolean ElevFall;
  boolean ElevRise;

  public UltrasonicSensor() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    ElevRise = OI.xboxController.getRawButtonPressed(1);
    ElevFall = OI.xboxController.getRawButtonPressed(0);
    ultra.setAutomaticMode(true); // turns on automatic mode

         // creates the ultra object and assigns ultra to be an ultrasonic sensor which uses
    // DigitalOutput 1 for the echo pulse and DigitalInput 1 for the trigger pulse
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
        
      double range = ultra.getRangeInches(); // reads the range on the ultrasonic sensor
      Ultrasonicreading = range;
      if(ElevRise == true) {
          //motor sets "braking mode" to false and moves upwards
          if(Ultrasonicreading > 10) {
            //motor set to 0 and sets "braking mode" to true
            Robot.CurrentElevLevel++;
          }
        if(ElevFall == true) {
          //motor sets braking mode to false
          Robot.CurrentElevLevel = 1;
          try {
          wait(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
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
      //motor sets braking mode to true
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
