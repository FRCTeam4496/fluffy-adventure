/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.OI;
import frc.robot.Robot;
import java.math.*;

public class UltrasonicSensor extends Command {

  Ultrasonic bottomleft = new Ultrasonic(1,1);
  Ultrasonic bottomright = new Ultrasonic(1,1);
  Ultrasonic top = new Ultrasonic(1,1);
  public static double sensorcount;
  boolean motorDirection;
  boolean elevFall;
  boolean elevRise;
  Timer moveTimer = new Timer();
  //public static boolean IsBallHeld = Get Motor Controller readings
  public UltrasonicSensor() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    elevRise = OI.xboxController.getRawButtonPressed(1);
    elevFall = OI.xboxController.getRawButtonPressed(0);
    bottomleft.setAutomaticMode(true); // turns on automatic mode
    bottomright.setAutomaticMode(true);
    top.setAutomaticMode(true);
         // creates the ultra object and assigns ultra to be an ultrasonic sensor which uses
    // DigitalOutput 1 for the echo pulse and DigitalInput 1 for the trigger pulse
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
        
      double bottomrightSensor = 1;
      double bottomleftSensor = 1;
      double topSensor = 1;
      //Counts the total number of sensors whose range is at least 250MM. Allows for cool alignment things :)
      
      if(elevRise == true) {
          //motor sets "braking mode" to false and moves upwards
          if(Math.floor(bottomright.getRangeMM() / 250) > 0 & moveTimer.get() > 0.5) {
              motorDirection = true; //TRUE = LEFT -- FALSE = RIGHT
              while((bottomrightSensor / 250) < 1) {
                //Should theoretically loop here and continuously update until the other sensor is lined up.
                //failure to detect anything results in a break
                bottomrightSensor = bottomright.getRangeMM();
                if (moveTimer.get() > 3.5);
                  break;
                  

              }
            //if(Motor movement to intake was last shown as picking up the ball && IsBallHeld == false) {
            //motor set to 0 and sets "braking mode" to true
            
          } else if(Math.floor(bottomleft.getRangeMM() / 250) > 0 & moveTimer.get() > 0.5) {
              motorDirection = false;
          }
          Robot.CurrentElevLevel++;
        if(elevFall == true) {
          //motor sets braking mode to false
          Robot.CurrentElevLevel = 1;
          try {
          wait(1000 * Robot.CurrentElevLevel);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        }
      }
  }
  
      //driveTime = driveTime - Height Difference between 1st floor ball and 2nd floor hatch
      //Robot.CurrentElevLevel--;
      //IsBallHeld == true;
      //} else (Motor movement to intake was last shown as shooting the ball && motorDrive < 0 && IsBallHeld == true) {
      //driveTime = driveTime * ((Robot.CurrentElevLevel - 1) + Height Difference between 1st floor ball and 1st floor hatch)
      //Robot.CurrentElevLevel = 1;
      //IsBallHeld == false;
      //}
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
