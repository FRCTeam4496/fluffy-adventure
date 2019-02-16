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

public class UltrasonicSensor extends Command {

  Ultrasonic bottomleft = new Ultrasonic(1,1);
  Ultrasonic bottomright = new Ultrasonic(1,1);
  Ultrasonic top = new Ultrasonic(1,1);
  public static double sensorcount;
  boolean elevFall;
  boolean elevRise;
  Timer classTimer = new Timer();
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
      //Counts the total number of sensors whose range is at least 250MM. Allows for cool alignment things :)
      
      if(elevRise == true) {
          //motor sets "braking mode" to false and moves upwards
          if(Math.floor(bottomleft.getRangeMM() / 250) > 0 & classTimer.get() > 0.8) { //If the robot is too far to the right
              while((bottomrightSensor / 250) < 1) {
                //Should theoretically loop here and continuously update until the other sensor is lined up.
                //failure to detect anything results in a break
                bottomrightSensor = bottomright.getRangeMM();
                if((bottomrightSensor / 250) >= 1) {
                  moveTimer.start();
                  while(bottomleft.getRangeMM() / 250 > 0) {

                   /*  try {
                  wait(1);
                  } catch (InterruptedException e) {
                  e.printStackTrace();
                  } */

                    //Moves to the left until the bottomleft sensor is no longer detecting the edge of the circle
                    Robot.m_hdrive.arcadeDrive(0.1, 0); //move robot to the left
                      if (classTimer.get() > 4);
                        break;
                  }
                  moveTimer.stop();
                  double temp = moveTimer.get() / 2; //Temp value used so the robot knows how long to go back to the right.
                  moveTimer.reset();
                  moveTimer.start();
                  while(moveTimer.get() < temp) {
                    Robot.m_hdrive.arcadeDrive(-0.1, 0); //move robot to the right (moveTimer.get() / 2);
                  }
                  
                  Robot.CurrentElevLevel++;

                }
                Robot.m_hdrive.stopMotor();
                if (classTimer.get() > 4);
                  break;
              }

            //if(Motor movement to intake was last shown as picking up the ball && IsBallHeld == false) {
            //motor set to 0 and sets "braking mode" to true
            
          } else if (Math.floor(bottomright.getRangeMM() / 250) > 0 & classTimer.get() > 0.8) { //If the robot is too far to the right
            while((bottomrightSensor / 250) < 1) {
              //Should theoretically loop here and continuously update until the other sensor is lined up.
              //failure to detect anything results in a break
              bottomleftSensor = bottomleft.getRangeMM();
              if((bottomleftSensor / 250) >= 1) {
                moveTimer.start();
                while(bottomright.getRangeMM() / 250 > 0) {
                  //Moves to the right until the bottomright sensor is no longer detecting the edge of the circle
                  Robot.m_hdrive.arcadeDrive(-0.1, 0); //move robot to the right
                    if (classTimer.get() > 4);
                      break;
                }
                moveTimer.stop();
                double temp = moveTimer.get() / 2; //Temp value used so the robot knows how long to go back to the right.
                moveTimer.reset();
                moveTimer.start();
                while(moveTimer.get() < temp) {
                  Robot.m_hdrive.arcadeDrive(0.1, 0); //move robot to the left (moveTimer.get() / 2);
                }
                
                Robot.CurrentElevLevel++;

              }
              Robot.m_hdrive.stopMotor();
              if (classTimer.get() > 4);
                break;
            }

          //if(Motor movement to intake was last shown as picking up the ball && IsBallHeld == false) {
          //motor set to 0 and sets "braking mode" to true
          
        
          Robot.ElevatorMotor.set(0.5);
      }
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
    if(classTimer.get() > 0.8 & Math.floor(bottomright.getRangeMM() / 250) > 0 & Math.floor(bottomleft.getRangeMM() / 250) > 0 & Math.floor(top.getRangeMM() / 250) < 1){
      moveTimer.reset();
      classTimer.reset();
      return true;
    } else {
      return false;
    }
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
