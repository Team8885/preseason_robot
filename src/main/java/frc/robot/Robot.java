/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class Robot extends TimedRobot {
  private DifferentialDrive m_myDrive;
  private XboxController m_joyStick;
  private Spark m_frontLeftMotor;
  private Spark m_rearLeftMotor;
  private Spark m_frontRightMotor;
  private Spark m_rearRightMotor;
  private MotorControllerGroup m_leftGroupMotor;
  private MotorControllerGroup m_rightGroupMotor;
  private ADXRS450_Gyro m_gyro;

  @Override
  public void robotInit() {
  
    m_frontLeftMotor  = new Spark(0);
    m_rearLeftMotor   = new Spark(1);
    m_frontRightMotor = new Spark(2);
    m_rearRightMotor  = new Spark(3);

    m_leftGroupMotor  = new MotorControllerGroup(m_frontLeftMotor,  m_rearLeftMotor);
    m_rightGroupMotor = new MotorControllerGroup(m_frontRightMotor, m_rearRightMotor);
    m_gyro            = new ADXRS450_Gyro();

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. For our gearbox and chassis 
    // orientation, we need to invert the left side.
    m_leftGroupMotor.setInverted(true);
    m_myDrive    = new DifferentialDrive(m_leftGroupMotor, m_rightGroupMotor);
    m_joyStick   = new XboxController(0);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    double speed = m_joyStick.getRawAxis(1)*0.6;
    double turn  = m_joyStick.getRawAxis(4)*0.3;

    double left  = speed + turn;
    double right = speed - turn;
    // We introduced a damping factor because full-power made the robot
    // erratic and crab-like, due to unequal power from each side at
    // maximum joystick travel.  The damping factor of 0.3 resulted in
    // NO movement, 0.6 was a slow crawl, and 0.7 to 1.0 was the sweet
    // spot.  With both sides at 0.7, the robot slowly pulls to the right
    // moving away, but "calibration" will have to wait for some time
    // on the Provo High carpeted field (in the wrestling gym).
    m_myDrive.tankDrive(left, right);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

}
