package org.texastorque.subsystems;

import java.util.ArrayList;
import java.util.Random;
import org.texastorque.inputs.State.RobotState;
import org.texastorque.inputs.Input;
import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueMotor;
import org.texastorque.torquelib.controlLoop.ScheduledPID;
import org.texastorque.subsystems.WheelModule;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase extends Subsystem{

    private static volatile Drivebase instance;
    
    ArrayList<WheelModule> modules = new ArrayList<>();
    
    private Drivebase() {
    } // constructor

    @Override
    public void autoInit() {
        for (WheelModule m : modules){
            m.setRotSpeed(0);
            m.setTransSpeed(0);
        } // set to 0 at start of auton
    } //autoInit

    @Override
    public void teleopInit() {
    } // what to do when teleop is initialized

    @Override
    public void disabledInit() {
    } // what to do when disabled 

    @Override
    public void run(RobotState state) {
        if(state == RobotState.AUTO){
        } // put what to do in auto
        else if(state == RobotState.TELEOP){
        } // put what to do in teleop
        else if(state == RobotState.VISION){
        } // put what to do in vision
        output();
    } // run

    @Override
    protected void output() {} // output

    @Override
    public void disabledContinuous() {}

    @Override
    public void autoContinuous() {}

    @Override
    public void teleopContinuous() {}

    @Override
    public void smartDashboard() {} // whatever stuff you want to display

    public static Drivebase getInstance() {
        if (instance == null) {
            synchronized (Drivebase.class) {
                if (instance == null)
                    instance = new Drivebase();
            }
        }
        return instance;
    } // getInstance 

} // drivebase