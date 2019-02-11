/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.ElevatorAuto;
import frc.robot.commands.ExampleCommand;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.OI;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI m_oi;
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  Spark m_frontLeft = new Spark(1);
	Spark m_rearLeft = new Spark(2);
	SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

	Spark m_frontRight = new Spark(3);
	Spark m_rearRight = new Spark(4);
  SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
  
  Spark m_center = new Spark(5);

  DifferentialDrive m_hdrive = new DifferentialDrive(m_center, m_center);
  DifferentialDrive m_4drive = new DifferentialDrive(m_left, m_right);

  double SlowMode;
  double Totalforwardspeed = 0;
  double TotalRotationspeed = 0;
  public static int CurrentElevLevel = 1;

  public static Spark IntakeWheel1 = new Spark(6);
  public static Spark IntakeWheel2 = new Spark(7);
  public static Spark ElevatorMotor = new Spark(8);
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    double lXVal = OI.xboxController.getRawAxis(0);
    double lYVal = OI.xboxController.getRawAxis(1);
    double rXVal = OI.xboxController.getRawAxis(2);
    double rYVal = OI.xboxController.getRawAxis(3);
    boolean SlowMode = OI.xboxController.getRawButton(7);
    boolean Quickturn = OI.xboxController.getRawButton(8);
    boolean ElevFall = OI.xboxController.getRawButtonPressed(0);
    boolean ElevRise = OI.xboxController.getRawButtonPressed(1);
  

    //Insert controls with Axes here
    Totalforwardspeed = (lYVal / 2.5) + (rYVal / 4);
    TotalRotationspeed = (rXVal / 4);

    if (SlowMode == true) {
      Totalforwardspeed = Totalforwardspeed * 0.1;
      TotalRotationspeed = TotalRotationspeed * 0.1;
    }
    if (Quickturn == true) {
      TotalRotationspeed = TotalRotationspeed * 1.5;
      Totalforwardspeed = 0;
    }

    m_4drive.curvatureDrive(Totalforwardspeed,  TotalRotationspeed, Quickturn);
    m_hdrive.arcadeDrive((lXVal / 2), 0);

    if (ElevRise == true && CurrentElevLevel < 3) {
        new ElevatorAuto();
        CurrentElevLevel++;
    }
    if (ElevFall == true && CurrentElevLevel > 1) {
        new ElevatorAuto();
        CurrentElevLevel--;
    }
    if (OI.xboxController.getRawButtonPressed(5) == true) {
      Robot.IntakeWheel1.set(-0.2);
      Robot.IntakeWheel2.set(0.2);
    } else if (OI.xboxController.getRawButtonPressed(6) == true) {
      Robot.IntakeWheel1.set(0.2);
      Robot.IntakeWheel2.set(-0.2);
    } else {
      Robot.IntakeWheel1.set(0);
      Robot.IntakeWheel2.set(0);
    }
    SmartDashboard.putNumber("Total Forward Speed", Totalforwardspeed);
    SmartDashboard.putNumber("Total Rotation Speed", TotalRotationspeed);
    SmartDashboard.putBoolean("Fine Tuning Enabled?", SlowMode);
    SmartDashboard.putBoolean("Quickturning Enabled?", Quickturn);
    SmartDashboard.putNumber("Current Elevator Level", CurrentElevLevel);
    
  }

  

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}