// skeleton of Robot.java
package org.texastorque;

import org.texastorque.subsystems.*;
import org.texastorque.auto.AutoManager;
import org.texastorque.inputs.*;
import org.texastorque.inputs.State.RobotState;

import org.texastorque.torquelib.base.TorqueIterative;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Robot extends TorqueIterative {

    private ArrayList<Subsystem> subsystems;
	private Subsystem driveBase = Drivebase.getInstance();
	// private Subsystem subsystemName = SubsystemName.getInstance();
	
	private State state = State.getInstance();
	private Input input = Input.getInstance();
	private Feedback feedback = Feedback.getInstance();
	// private AutoManager autoManager = AutoManager.getInstance();

	public void robotInit() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("Init time: " + dateFormat.format(date));

		initSubsystems();
		feedback.resetNavX();
		feedback.resetEncoders();
		// UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		// camera.setResolution(320, 240);
		// camera.setFPS(16);
	} // robot is enabled

	private void initSubsystems() {
		subsystems = new ArrayList<Subsystem>();
		subsystems.add(driveBase);
		//subsystems.add(subsystemName);
	} // initialize subsystems 

	// @Override
	// public void autoInit() {
	// 	state.setRobotState(RobotState.AUTO);
	// 	autoManager.chooseSequence();
	// 	feedback.resetEncoders();
	// 	input.resetAll();

	// 	for (Subsystem system : subsystems) {
	// 		system.autoInit();
	// 	} // set all subsystems to auton
	// } // auton start
	
	@Override
	public void teleopInit() {

		state.setRobotState(RobotState.TELEOP);

		for (Subsystem system : subsystems) {
			system.teleopInit();
		} // set all subsystems to teleop
		
	} // teleop start

	@Override
	public void disabledInit() {
		for (Subsystem system : subsystems) {
			system.disabledInit();
		} // set all subsystems to disabled
	} // disabled robot

	@Override
	public void autoContinuous() {
		// if (state.getRobotState() == RobotState.AUTO) {
		// 	autoManager.runSequence();

		// 	if (autoManager.sequenceEnded()) {
		// 		state.setRobotState(RobotState.TELEOP); // should this be left 2020 or deleted 
		// 	} // when done with auton set back to teleop
		// } // if in auto mode

		for (Subsystem system : subsystems) {
			system.run(state.getRobotState());
		} // run auto state in all subsystems
	} // run at all times in state auto 

	@Override
	public void teleopContinuous() {
		input.updateControllers();
		for (Subsystem system : subsystems) {
			system.run(state.getRobotState());
		}
	} // run at all times in state teleop

	@Override
	public void disabledContinuous() {
		for (Subsystem system : subsystems) {
			system.disabledContinuous();
		} // run disabled state in all subsystems
	} // run at all times when disabled

	@Override
	public void alwaysContinuous() {
		feedback.update();
		feedback.smartDashboard();
		
		for (Subsystem system : subsystems) {
			system.smartDashboard();
		} // display smart dashboard from all subsystems
	} // run at all times when robot has power
} // Robot 