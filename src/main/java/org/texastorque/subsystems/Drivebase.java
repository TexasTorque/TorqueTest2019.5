package org.texastorque.subsystems;

import org.texastorque.inputs.State.RobotState;
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

    // modules[0] = front left, modules[1] = front right, modules[2] = back left, modules[3] = back right
    private WheelModule[] modules = new WheelModule[4];

    private double transMagnitude = 0.0;
    private double transAngle = 0.0;
    private double rotMagnitude = 0.0;
    private double rotAngle = 0.0;

    private final boolean clockwise = false;


    private Drivebase() {
        modules[0] = new WheelModule(Ports.TRANS_1, Ports.ROT_1, 45, 45, 0);
        modules[1] = new WheelModule(Ports.TRANS_2, Ports.ROT_2, 45, 135, 1);
        modules[2] = new WheelModule(Ports.TRANS_3, Ports.ROT_2, 45, 225, 2);
        modules[3] = new WheelModule(Ports.TRANS_4, Ports.TRANS_4, 45, 315, 3);
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
        for (WheelModule m : modules){
            m.setRotSpeed(0);
            m.setTransSpeed(0);
        } // set to 0 at start of auton
    } // what to do when teleop is initialized

    @Override
    public void disabledInit() {
        for (WheelModule m : modules){
            m.setRotSpeed(0);
            m.setTransSpeed(0);
        } // set to 0 at start of disabled period
    } // what to do when disabled 

    @Override
    public void run(RobotState state) {
        if(state == RobotState.AUTO){
            // put what to do in auto
        }
        else if(state == RobotState.TELEOP){

            // put what to do in teleop
        }
        else if(state == RobotState.VISION){
            // put what to do in vision
        }
    } // run

    @Override
    protected void output() {
        
    } // output

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