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

    // WHEN TRYING ACTUAL SWERVE UNCOMMENT ALL THE MODULE STUFF
    private static volatile Drivebase instance;
    
    // ArrayList<WheelModule> modules = new ArrayList<>();
    private TorqueMotor rot0;
    private TorqueMotor rot1;
    private TorqueMotor rot2;
    private TorqueMotor rot3;
    
    private CANSparkMax trans0;
    private CANSparkMax trans1;
    private CANSparkMax trans2;
    private CANSparkMax trans3;
    // 0 is front left, 1 is front right, 2 is back left, 3 is back right
    private Drivebase() {
        rot0 =  new TorqueMotor(new VictorSP(Ports.ROTMOT[0]), false);
        rot1 =  new TorqueMotor(new VictorSP(Ports.ROTMOT[1]), false);
        rot2 =  new TorqueMotor(new VictorSP(Ports.ROTMOT[2]), false);
        rot3 =  new TorqueMotor(new VictorSP(Ports.ROTMOT[3]), false);

        trans0 = new CANSparkMax(Ports.TRANSMOT[0], MotorType.kBrushless);
        trans1 = new CANSparkMax(Ports.TRANSMOT[1], MotorType.kBrushless);
        trans2 = new CANSparkMax(Ports.TRANSMOT[2], MotorType.kBrushless);
        trans3 = new CANSparkMax(Ports.TRANSMOT[3], MotorType.kBrushless);
        // modules.add(new WheelModule(13, -45.0, 0));
        // modules.add(new WheelModule(13, 45.0, 1));
        // modules.add(new WheelModule(13, -135.0, 2));
        // modules.add(new WheelModule(13, 135.0, 3));
    } // constructor

    @Override
    public void autoInit() {
        // for (WheelModule m : modules){
        //     m.setRotSpeed(0);
        //     m.setTransSpeed(0);
        // } // set to 0 at start of auton
    } //autoInit

    @Override
    public void teleopInit() {
        // for (WheelModule m : modules){
        //     m.setRotSpeed(0);
        //     m.setTransSpeed(0);
        // } // set to 0 at start of teleop
    } // what to do when teleop is initialized

    @Override
    public void disabledInit() {
    } // what to do when disabled 

    @Override
    public void run(RobotState state) {
        if(state == RobotState.AUTO){
        } // put what to do in auto
        else if(state == RobotState.TELEOP){
            // for (WheelModule m : modules){
            //     m.calc(input.getTransX(), input.getTransY(), input.getRotMag());
            // } // what to do in teleop
            if (input.getModule0()){
                trans0.set(input.getLeftYAxis());
                rot0.set(input.getRightXAxis());
            }
            if (input.getModule1()){
                trans1.set(input.getLeftYAxis());
                rot1.set(input.getRightXAxis());
            }
            if (input.getModule2()){
                trans2.set(input.getLeftYAxis());
                rot2.set(input.getRightXAxis());
            }
            if (input.getModule3()){
                trans3.set(input.getLeftYAxis());
                rot3.set(input.getRightXAxis());
            }
        } // put what to do in teleop
        else if(state == RobotState.VISION){
        } // put what to do in vision
        output();
    } // run

    @Override
    protected void output() {
        // for (WheelModule m : modules) {
        //     m.outputMotorSpeeds();
        // } // output the motor speeds to the actual motors
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