package org.texastorque.inputs;

import org.texastorque.constants.*;
import org.texastorque.torquelib.component.TorqueEncoder;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;

import java.util.ArrayList;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Retrieve values from all sensors and NetworkTables
 */

/* TODO:
change rotation angle so that it goes 0-180 both ways
tobearing method ^
*/


<<<<<<< HEAD
    // Conversions
    public final double DISTANCE_PER_PULSE = Math.PI * Constants.WHEEL_DIAMETER / Constants.PULSES_PER_ROTATION;
    public final double ANGLE_PER_PULSE = 360.0 / Constants.PULSES_PER_ROTATION;
    public final double FEET_CONVERSION = Math.PI * (1.0/20) / Constants.PULSES_PER_ROTATION; // Using approximate shaft diameter

    // Sensors
    private final TorqueEncoder[] DB_rot_encoders= new TorqueEncoder[4];
=======
public class Feedback {
>>>>>>> a6d9c55020c87e455902f76b896d60a2976434fb

    private static volatile Feedback instance;

    ArrayList<TorqueEncoder> driveEncoders = new ArrayList<>();

    private AHRS NX_gyro;
    private double pitch = 0;
	private double roll = 0;
	private double yaw = 0;
    
    private Feedback() {
        driveEncoders.add(new TorqueEncoder(Ports.DB_ROT_0_A, Ports.DB_ROT_0_B, false, EncodingType.k4X));
        driveEncoders.add(new TorqueEncoder(Ports.DB_ROT_1_A, Ports.DB_ROT_1_B, false, EncodingType.k4X));
        driveEncoders.add(new TorqueEncoder(Ports.DB_ROT_2_A, Ports.DB_ROT_2_B, false, EncodingType.k4X));
        driveEncoders.add(new TorqueEncoder(Ports.DB_ROT_3_A, Ports.DB_ROT_3_B, false, EncodingType.k4X));

        resetEncoders();
        resetNavX();
    } // constructor 

    public void update() {
        updateEncoders();
        updateNavX();
        //updateNetworkTables();
    } // update


    // ==================== Encoders ====================

    // DB = drivebase, R = rotation
    // module 0 = front left, 1 = front right, 2 = back left, 3 = back right
    
    public void resetEncoders() {
        resetDriveEncoders();
    } // reset encoders ONLY at beginning of match

    public void updateEncoders() {
        updateDriveEncoders();
    } // update all Encoders

    // ---- Drivebase Rotation Encoders ----
    ArrayList<Double> rotAngle = new ArrayList<>();

    public void resetDriveEncoders(){
        for (TorqueEncoder e : driveEncoders){
            e.reset();
        } // reset every encoder in driveEncoders
    } // reset drive encoders

    public void updateDriveEncoders(){
<<<<<<< HEAD

        for(TorqueEncoder e : DB_rot_encoders){
            e.calc();
        } // encoder.calc for all drive rotation encoders

        // rotation gearing = 60:1, drive gearing = 44.4:1
        
        for(int x = 0; x < 4; x++){
            DB_Rot_Raw[x] = DB_rot_encoders[x].get();
        } // encoder.get() for all drive rotation encoders

        for(int x = 0; x < 4; x++){
            DB_Rot_Speed[x] = DB_rot_encoders[x].getRate() * DISTANCE_PER_PULSE;
        } // update speeds for all drive rotation encoders
        
        // NEED SPECIFICS FROM BEN ON WHERE ENCODER IS GOING TO GO!!! THIS IS NOT FINAL!! NEED TO ADD MORE BASED ON THAT
        for(int x = 0; x < 4; x++) {
            DB_Rot_Angle[x] = DB_Rot_Raw[x] * 2.8;
        } // get angle at which each wheel has turned 

=======
        for (int i = 0; i < driveEncoders.size(); i++){
            driveEncoders.get(i).calc();
            rotAngle.set(i,driveEncoders.get(i).get()/8.5); // EXPERIIMENTALLY DERIVED VALUE
        }
>>>>>>> a6d9c55020c87e455902f76b896d60a2976434fb
    } // update drive encoders

    public double getRotAngle(int module) {
<<<<<<< HEAD
        return DB_Rot_Angle[module];
=======
        return rotAngle.get(module);
>>>>>>> a6d9c55020c87e455902f76b896d60a2976434fb
    } // return rotation angle



    // ========== Gyro ==========

    private double NX_pitch;
    private double NX_yaw;
    private double NX_roll;

    public void resetNavX() {
        NX_gyro.reset();
    } // resetNavX

    public void updateNavX() {
        NX_pitch = NX_gyro.getPitch();
        NX_yaw = NX_gyro.getYaw();
        NX_roll = NX_gyro.getRoll();
    } // updateNavX (ONLY at BEGINNING of match, otherwise DO NOT USE)

    public double getPitch() {
        return NX_pitch;
    } // getPitch

    public double getYaw() { // THIS IS THE ONE TO USE FOR OFFSET
        return NX_yaw;
    } // getYaw

    public double getRoll() {
        return NX_roll;
    } // getRoll

    public void zeroYaw() {
        NX_gyro.zeroYaw();
    } // zeroYaw    

    // ========= Other Stuff=============
    
    public void smartDashboard() {
        // add whatever you want to display 
        SmartDashboard.putNumber("Pitch", getPitch());
        SmartDashboard.putNumber("Roll", getRoll());
        SmartDashboard.putNumber("Yaw", getYaw());
    } // send stuff to smart dashboard

    public static Feedback getInstance() {
        if (instance == null) {
            synchronized (Feedback.class) {
                if (instance == null)
                    instance = new Feedback();
            }
        }
        return instance;
    } // getInstance 
} // Feedback