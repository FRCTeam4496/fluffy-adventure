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
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.commands.*;
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
  static Spark m_frontLeft = new Spark(0);
	// static Spark m_rearLeft = new Spark(3);
	// static SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

	static Spark m_frontRight = new Spark(1);
  // static Spark m_rearRight = new Spark(4);
  // static SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);

  static Talon m_center1 = new Talon(2);
  static Talon m_center2 = new Talon(3);
  static SpeedControllerGroup m_center = new SpeedControllerGroup(m_center1, m_center2);

  public static DifferentialDrive m_hdrive = new DifferentialDrive(m_center, m_center);
  public static DifferentialDrive m_4drive = new DifferentialDrive(m_frontLeft, m_frontRight);

  double Totalforwardspeed = 0;
  double Totalrotationspeed = 0;
  double Totalhorizontalspeed = 0;
  public static int CurrentElevLevel = 1;

  public static Talon IntakeWheel1 = new Talon(6);
  public static Talon IntakeWheel2 = new Talon(7);

  public static Talon ElevatorMotor1 = new Talon(4);
  public static Victor ElevatorMotor2 = new Victor(5);
  public static SpeedControllerGroup ElevatorMotor = new SpeedControllerGroup(ElevatorMotor1, ElevatorMotor2);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    m_chooser.setDefaultOption("Default Auto", new ElevatorAuto());
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
    //Friendly reminder that robots will take over the world.
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    Totalforwardspeed = 0;
    Totalrotationspeed = 0;
    Totalhorizontalspeed = 0;
    CurrentElevLevel = 1;

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
    boolean ManualElevFall = OI.xboxController.getRawButton(5);
    boolean ManualElevRise = OI.xboxController.getRawButton(6);
    boolean SlowMode = OI.xboxController.getRawButton(7);
    boolean Quickturn = OI.xboxController.getRawButton(8);
    boolean ElevFall = OI.xboxController.getRawButtonPressed(0);
    boolean ElevRise = OI.xboxController.getRawButtonPressed(1);

    m_center1.setSafetyEnabled(true);
    m_center2.setSafetyEnabled(true);
    //Insert controls with Axes here
    Totalforwardspeed = (-lYVal / 1.3);
    Totalrotationspeed = (rXVal / 4);

    if (SlowMode == true) {
      Totalforwardspeed = Totalforwardspeed * 0.4;
      Totalrotationspeed = Totalrotationspeed * 0.45;
      Totalhorizontalspeed = Totalhorizontalspeed * 0.25;
    }
    if (Quickturn == true) {
      Totalrotationspeed = Totalrotationspeed * 1.5;
      //Totalforwardspeed = 0;
    }

    m_4drive.curvatureDrive(Totalforwardspeed,  Totalrotationspeed, Quickturn);
    m_hdrive.arcadeDrive((lXVal / 2), 0);
    //m_center.set((lXVal / 2));

    if (ElevRise == true && CurrentElevLevel < 3) {
        //new ElevatorAuto();
        new UltrasonicSensor();
        CurrentElevLevel++;
    }
    if (ElevFall == true && CurrentElevLevel > 1) {
        //new ElevatorAuto();
        new UltrasonicSensor();
        CurrentElevLevel--;


    }
    IntakeWheel1.set(Math.pow(rYVal, 3));
    IntakeWheel2.set(-Math.pow(rYVal, 3));
    if (ManualElevRise == true && ManualElevFall == false) {
      ElevatorMotor.set(0.8);
    } else if (ManualElevRise == false && ManualElevFall == true) {
      ElevatorMotor.set(-0.8);
    } else {
      ElevatorMotor.stopMotor();
    }

    SmartDashboard.putNumber("Total Forward Speed", Totalforwardspeed);
    SmartDashboard.putNumber("Total Rotation Speed", Totalrotationspeed);
    SmartDashboard.putNumber("Total Sideways Speed", Totalhorizontalspeed);
    SmartDashboard.putBoolean("Fine Tuning Enabled?", SlowMode);
    SmartDashboard.putBoolean("Quickturning Enabled?", Quickturn);
    SmartDashboard.putNumber("Current Elevator Level", CurrentElevLevel);
    SmartDashboard.putBoolean("Manual Elevator Lift Enabled?", ManualElevRise);
    SmartDashboard.putBoolean("Manual Elevator Fall Enabled?", ManualElevFall);
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
