package org.texastorque.subsystems;

import org.texastorque.torquelib.component.TorqueMotor;
import org.texastorque.torquelib.controlLoop.LowPassFilter;
import org.texastorque.torquelib.controlLoop.ScheduledPID;
import org.texastorque.inputs.Feedback;
import org.texastorque.util.VectorUtils;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelModule {

    private Feedback feedback;

    private TorqueMotor rotMot;
    private CANSparkMax transMot;
    private final CANEncoder DB_trans;

    private double constM;
    private double constA;

    private double setMag; // what magnitude needs to be set to
    private double setAng; // what the angle needs to be set to
    private double currentMag; // what the magnitude currently is
    private double currentAng; // what the angle currently is
    private double prevMag;
    private double prevAng;

    private double transConversionFactor = 1;

    private ScheduledPID rotationalPID;
    private ScheduledPID translationalPID;

    private final LowPassFilter lowPassRot;
    private final LowPassFilter lowPassTrans;

    private double rotSpeed;
    private double transSpeed;

    private double gryoOffset;

    private int arrayValue;

    boolean clockwise = false;

    // moduleMagnitude is the magnitude of the distance from the center of mass to the module
    // moduleAngle is the bearing of the wheel module from robot front
    WheelModule(int portTrans, int portRot, double moduleMagnitude, double moduleAngle, int arrayValue) {
        rotMot = new TorqueMotor(new VictorSP(portTrans), clockwise);
        transMot = new CANSparkMax(portRot, MotorType.kBrushless);
        DB_trans = transMot.getEncoder(EncoderType.kHallSensor, 4096);
        
        constM = moduleMagnitude;
        constA = moduleAngle;
        this.arrayValue = arrayValue;

        rotationalPID = new ScheduledPID.Builder(0, 0, 1, 1)
            .setPGains(0)
            .setIGains(0)
            //.setDGains(0)
            .build();

        // translationalPID = new ScheduledPID.Builder(0, 0, 0, 0)
        //     .setPGains(0)
        //     .setIGains(0)
        //     //.setDGains(0)
        //     .build();

        lowPassRot = new LowPassFilter(0.5);  // if lag, change this number?  
        lowPassTrans = new LowPassFilter(0.5);
    } // constructor

    public void calc(double transX, double transY, double rotR, double constAng){
        double rotRX = 0.0, rotRY = 0.0;
        
        try{

            rotRX = calcRotRX(rotR, constAng);
            
            rotRY = calcRotRY(rotR, constAng);

        }
        catch(Exception e){
            System.out.println("WheelModule.calc exception tostring: " + e.toString());
            System.out.println("WheelModule.calc exception message: " + e.getMessage());
        }

        SmartDashboard.putNumber("rotRX", rotRX);
        SmartDashboard.putNumber("rotRY", rotRY);
        SmartDashboard.putNumber("transX", transX);
        SmartDashboard.putNumber("transY", transY);

        try{

            setMag = VectorUtils.vectorAddition2DMagnitude(transX, transY, rotRX, rotRY);
            SmartDashboard.putNumber("setMag", setMag);
            setAng = VectorUtils.vectorAddition2DBearing(transX, transY, rotRX, rotRY);// + feedback.getYaw();
            SmartDashboard.putNumber("setAng", setAng);
            RotationalPID(setAng);

            transSpeed = setMag*.5;
        }
        catch(Exception e){
            System.out.println("WheelModule.calc set e tostring: " + e.toString());
            System.out.println("WheelModule.calc set e message: " + e.getMessage());
        }


    } // calculate what values need to be, must be running continously

    public double calcRotRX(double rotR, double constAng){ // questionable math, look at this later (check the signs of results)
        double rotRcompX = 0;
        if (arrayValue == 0 || arrayValue == 3){
            rotRcompX = rotR*Math.toDegrees(Math.cos(constAng));
        }
        if (arrayValue == 1 || arrayValue == 2){
            rotRcompX = rotR*Math.toDegrees(Math.sin(constAng));
        }
        return rotRcompX;
    } // return the x component of the rotational vector

    public double calcRotRY(double rotR, double constAng){ // questionable math pt. 2
        double rotRcompY = 0;
        if (arrayValue == 0 || arrayValue == 3){
            rotRcompY = rotR*Math.toDegrees(Math.sin(constAng));
        }
        if (arrayValue == 1 || arrayValue == 2){
            rotRcompY = rotR*Math.toDegrees(Math.cos(constAng));
        }
        return rotRcompY;
    } // return the y component of the rotational vector

    public void setRotSpeed(double speed){
        rotSpeed = speed;
    } // set rotational speed from outside, DO NOT USE IN TELEOP

    public void setTransSpeed(double speed){
        transSpeed = speed;
    } // set translational speed from the outside, DO NOT USE IN TELEOP

    public void outputMotorSpeeds(){
        SmartDashboard.putNumber("WheelModule.outputMotorSpeeds - rotSpeedInput", rotSpeed);
        SmartDashboard.putNumber("WheelModule.outputMotorSpeeds - transSpeedInput", transSpeed);
        rotMot.set(rotSpeed);
        transMot.set(transSpeed);
        SmartDashboard.putNumber("WheelModule.outputMotorSpeeds - Complete", 27);
    } // set the motor speeds to the correct numbers

    public void RotationalPID(double angle){ // NOT GOOD <- FIX LATER
        double rotAngle = 0.0;
        try{
            SmartDashboard.putNumber("WheelModule.RotationalPID angle", angle);
            SmartDashboard.putNumber("WheelModule.RotationalPID arrayValue", arrayValue);
            
            // TODO: fix everything below
            try{
                rotAngle = feedback.getRotAngle(arrayValue);
            } 
            catch(Exception e){
                rotAngle = 0;
            }

            SmartDashboard.putNumber("WheelModule.RotationalPID rotAngle", rotAngle);
            try{
                currentAng = lowPassRot.filter(rotAngle);
            }
            catch(Exception e){
                currentAng = 0;
            }
            
            SmartDashboard.putNumber("WheelModule.RotationalPID currentAng", currentAng);

            if (angle != currentAng){
                rotationalPID.changeSetpoint(angle);
                prevAng = angle;
            } // if not already there, go to the correct position
            
            SmartDashboard.putNumber("WheelModule.RotationalPID currentAng after", currentAng);
            rotSpeed = rotationalPID.calculate(currentAng);
            SmartDashboard.putNumber("WheelModule.RotationalPID rotSpeed", rotSpeed);
        }
        catch(Exception e){
            System.out.println("WheelModule.RotationalPID: " + e.toString());
        }
    } // setWheelAngle

    // public void runTranslationalPID(double distance){ // NEED TO FIX/WRITE THIS PID !!!!!!!!!
    // } // FINISH THIS

} // WheelModule
