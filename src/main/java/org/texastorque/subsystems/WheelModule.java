package org.texastorque.subsystems;

import org.texastorque.torquelib.component.TorqueMotor;
import org.texastorque.torquelib.controlLoop.ScheduledPID;
import org.texastorque.torquelib.controlLoop.LowPassFilter;
import org.texastorque.constants.Ports;
import org.texastorque.inputs.Feedback;
import org.texastorque.inputs.Input;
import org.texastorque.util.VectorUtils;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelModule {

    private Feedback feedback = Feedback.getInstance();

    // == components ==
    private TorqueMotor rotMot;
    private CANSparkMax transMot;
    private CANEncoder DB_trans;
    private double constAng;
    private int arrayValue;

    // == calculated values ==
    private double transMag = 0;
    private double transTheta = 0;

    // == PID variables ==
    private ScheduledPID rotationalPID; // MAKE THIS FINAL IN THE ACTUAL THING
 	private double rotSpeed = 0;
 	private double currentPos = 0;
	private double setpoint = 0;
    private double prevSetpoint = 0;

    // == output variables == 
    private double resultMag = 0;
    
    // DEFINITIONS:
    // moduleMagnitude is the magnitude of the distance from the center of mass to the module
    // moduleAngle is the bearing of the wheel module from robot front
    WheelModule(double moduleMagnitude, double constAng, int arrayValue) {
        rotMot = new TorqueMotor(new VictorSP(Ports.ROTMOT[arrayValue]), false);
        transMot = new CANSparkMax(Ports.TRANSMOT[arrayValue], MotorType.kBrushless);
        DB_trans = transMot.getEncoder(EncoderType.kHallSensor, 4096);

        this.arrayValue = arrayValue;
        this.constAng = constAng;

        this.rotationalPID = new ScheduledPID.Builder(setpoint, -.8, .8, 1)
			.setPGains(0.02)
			// .setIGains(0)
			// .setDGains(0)
			.build();
    } // constructor

    public void calc(double transX, double transY, double rotR){
        transMag = Math.hypot(transX, transY);
        resultMag = VectorUtils.vectorAddition2DMagnitude(transX, transY, rotR, 0);
        centerOfMassAddition(rotR);
        transTheta -= feedback.getYaw();
        if ((Math.abs(transTheta-feedback.getRotAngle(arrayValue)) > 90) && (Math.abs(feedback.getRotAngle(arrayValue)+transTheta) > 90)){
			if(feedback.getRotAngle(arrayValue) < 0){
				transTheta+= 180;
			}
			else {
				transTheta -= 180;
			}
			resultMag *= -1;
        } // make angle fall in range of (-180,180]
    } // calculate what values need to be, must be running continously

    public void setRotSpeed(double speed){
    } // set rotational speed from outside, DO NOT USE IN TELEOP

    public void setTransSpeed(double speed){
    } // set translational speed from the outside, DO NOT USE IN TELEOP

    public void outputMotorSpeeds(){
        runRotationalPID();
        rotMot.set(rotSpeed);
        transMot.set(-resultMag);
    } // set the motor speeds to the correct numbers

    // ------- METHODS TO USE IN STUFF ----------
    public void runRotationalPID(){
		if (transTheta <= 180 && transTheta >= -180){
			setpoint = transTheta;
		}
		currentPos = feedback.getRotAngle(arrayValue);
		if (setpoint != prevSetpoint){
			SmartDashboard.putNumber("difference", Math.abs(setpoint-currentPos));
			rotationalPID.changeSetpoint(setpoint);
			prevSetpoint = setpoint;
		}
		rotSpeed = rotationalPID.calculate(currentPos);
    }
    
    public void centerOfMassAddition(double rotR){
        switch (arrayValue) {
            case 0:
                if (rotR >= 0){
                    transTheta += (90 - constAng);
                } else {
                    transTheta -= (90 + constAng);
                }
                break;
            case 1:
                if (rotR >= 0){
                    transTheta += (90 + constAng);
                } else {
                    transTheta -= (90 - constAng);
                }
                break;
            case 2:
                if (rotR >= 0){
                    transTheta -= (90 - constAng);
                } else {
                    transTheta += (90 + constAng);
                }
                break;
            case 3: 
                if (rotR >= 0){
                    transTheta -= (90 + constAng);
                } else {
                    transTheta += (90 - constAng);
                }
                break;
        } // end of switch statement
            

    } // calculate rotation angle based on module number 
} // WheelModule
