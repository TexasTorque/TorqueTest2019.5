package org.texastorque.subsystems;

import org.texastorque.torquelib.component.TorqueMotor;
import org.texastorque.torquelib.controlLoop.LowPassFilter;
import org.texastorque.torquelib.controlLoop.ScheduledPID;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelModule {

    private TorqueMotor rotMot;
    private CANSparkMax transMot;
    private final CANEncoder DB_trans;

    private double constM;
    private double constA;

    private double setMag;
    private double setAng;
    private double currentMag;
    private double currentAng;
    private double prevMag;
    private double prevAng;

    private ScheduledPID rotationalPID;
    private ScheduledPID translationalPID;

    private final LowPassFilter lowPassRot;
    private final LowPassFilter lowPassTrans;

    private double rotSpeed;
    private double transSpeed;

    private int arrayValue;

    boolean clockwise = false;

    // moduleMagnitude is the magnitude of the distance from the center of mass to the module
    // moduleAngle is the bearing of the wheel module from robot front
    WheelModule(int portTrans, int portRot, double moduleMagnitude, double moduleAngle, int arrayValue) {
        rotMot = new TorqueMotor(new VictorSP(portTrans), clockwise);
        transMot = new CANSparkMax(portRot, MotorType.kBrushless);
        DB_trans = transMot.getEncoder();
        
        constM = moduleMagnitude;
        constA = moduleAngle;
        this.arrayValue = arrayValue;

        rotationalPID = new ScheduledPID.Builder(0, 0, 0, 0)
            .setPGains(0)
            .setIGains(0)
            //.setDGains(0)
            .build();

        translationalPID = new ScheduledPID.Builder(0, 0, 0, 0)
            .setPGains(0)
            .setIGains(0)
            //.setDGains(0)
            .build();

        lowPassRot = new LowPassFilter(0.5);  // if lag, change this number?  
        lowPassTrans = new LowPassFilter(0.5);
    } // constructor

    public void calc(double transMag, double transTheta, double rotR){
        
        //setMag = 
        // angle needs to be calculated by the end
        // magnitude needs to be calculated by the end
    } // calculate what values need to be, must be running continously 

    public void setRotSpeed(double speed){
        rotSpeed = speed;
    } // set rotational speed from outside, DO NOT USE IN TELEOP

    public void setTransSpeed(double speed){
        transSpeed = speed;
    } // set translational speed from the outside, DO NOT USE IN TELEOP

    public void runRotationalPID(double angle){ // NOT GOOD <- FIX LATER
        currentAng = lowPass.filter() // CHANGE TO GET THE CURRENT POSITION
        if (angle != currentAng){
            rotationalPID.changeSetpoint(angle);
            prevAng = angle;
        } // if not already there, go to the correct position
        rotSpeed = rotationalPID.calculate(currentAng);
    } // setWheelAngle

    // private void runLiftPID(int position) {
    //     setpoint = input.calcLFSetpoint(position);
    //     currentPos = lowPass.filter(feedback.getLFPosition());
    //     if (setpoint != prevSetpoint) {
    //         liftPID.changeSetpoint(setpoint);
    //         prevSetpoint = setpoint;
    //     }

    //     speed = liftPID.calculate(currentPos);
    // }

    public void runTranslationalPID(double distance){
    } // FINISH THIS

} // WheelModule
