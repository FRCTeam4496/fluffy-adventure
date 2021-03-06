/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot;

//import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.GenericHID;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.cscore.*;
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

/* public static XboxController xboxController = new XboxController(0);
public static GenericHID.Hand kleft;
public static GenericHID.Hand kright;
public static boolean Abutton = xboxController.getAButton();
public static boolean Bbutton = xboxController.getBButton();
public static boolean XButton = xboxController.getXButton();
public static boolean YButton = xboxController.getYButton();
public static int YAxis = xboxController.getAxisType(1);
public static int XAxis = xboxController.getAxisType(2);
public static boolean test = true; */
 
public static Joystick joystickL = new Joystick(0);
public static Joystick joystickR = new Joystick(1);
//public UsbCamera usbCamera1 = new UsbCamera("Main Camera", 0);

/* NINTENDO SWITCH PRO CONTROLLER MAPPING ^.^

BUTTONS---
B BUTTON IS 0
A BUTTON IS 1
X BUTTON IS 3
Y BUTTON IS 2
L TRIGGER IS 4
ZL TRIGGER IS 6
R TRIGGER IS 5
ZR TRIGGER IS 7
MINUS BUTTON IS 8
PLUS BUTTON IS 9
LCLICKSTICK IS 10
RCLICKSTICK IS 11
CAPTURE BUTTON IS 13
HOME BUTTON IS 12

AXES---
LEFT STICK X-AXIS IS AXIS 0
LEFT STICK Y-AXIS IS AXIS 1
RIGHT STICK X-AXIS IS AXIS 2
RIGHT STICK Y-AXIS IS AXIS 3

MISC---
DPAD CHANGES POV??
NO BUTTON 15 OR 16 DESPITE APPEARING IN PROGRAM? */
}