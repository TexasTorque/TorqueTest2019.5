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

    // modules[0] = front left, modules[1] = front right, modules[2] = back left, modules[3] = back right
    // private WheelModule[] modules = new WheelModule[0];
    private ArrayList<WheelModule> modules = new ArrayList<WheelModule>();

    private double transMagnitude = 0.0;
    private double transAngle = 0.0;
    private double rotMagnitude = 0.0;
    private double rotAngle = 0.0;
    
    public double[] constantAngles = {1, 1, 1, 1};

    private final boolean clockwise = false;
    private Random rand = new Random();


    private Drivebase() {
        modules.add(new WheelModule(Ports.TRANS_1, Ports.ROT_1, 8, 45, 0));
        // modules[1] = new WheelModule(Ports.TRANS_2, Ports.ROT_2, 45, 135, 1);
        // modules[2] = new WheelModule(Ports.TRANS_3, Ports.ROT_2, 45, 225, 2);
        // modules[3] = new WheelModule(Ports.TRANS_4, Ports.TRANS_4, 45, 315, 3);
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
        SmartDashboard.putNumber("Drivebase teleopInit", 20);
        for (WheelModule m : modules){
            SmartDashboard.putNumber("Drivebase for module", 1);
            m.setRotSpeed(0);
            SmartDashboard.putNumber("Drivebase for module 2", 1);
            m.setTransSpeed(0);

        } // set to 0 at start of auton
        SmartDashboard.putNumber("Test11", 21);
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
        int count = 0;
        SmartDashboard.putNumber("Drivebase.run start", rand.nextInt());
        if(state == RobotState.AUTO){
            // put what to do in auto
        }
        else if(state == RobotState.TELEOP){
            try{
                SmartDashboard.putNumber("Drivebase.run teleop", rand.nextInt());
                for (WheelModule m : modules){
                    SmartDashboard.putNumber("Drivebase.run teleop.for", rand.nextInt() );
                    SmartDashboard.putNumber("Drivebase.run transYInput", input.getTransY());
                    SmartDashboard.putNumber("Drivebase.run rotRInput", input.getRotR());
                    m.calc(input.getTransX(), input.getTransY(), input.getRotR(), constantAngles[count]);
                    SmartDashboard.putNumber("Drivebase.run teleop.endfor", rand.nextInt());
                } // calculate values for each module continuously 
            }
            catch(Exception e){
                System.out.println("Run exception tostring: " + e.toString());
                System.out.println("Run exception message: " + e.getMessage());
            }
        } // put what to do in teleop
        else if(state == RobotState.VISION){
            // put what to do in vision
        }
        output();
    } // run

    @Override
    protected void output() {
        SmartDashboard.putNumber("Drivebase output", 26);
        for(WheelModule m : modules){
            m.outputMotorSpeeds();
        } // sets the motors speeds to the necessary values
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