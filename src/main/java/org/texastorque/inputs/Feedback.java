package org.texastorque.inputs;

import org.texastorque.constants.*;
import org.texastorque.torquelib.component.TorqueEncoder;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;
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

public class Feedback {

    private static volatile Feedback instance;

    // Conversions
    public final double DISTANCE_PER_PULSE = Math.PI * Constants.WHEEL_DIAMETER / Constants.PULSES_PER_ROTATION;
    public final double ANGLE_PER_PULSE = 360.0 / Constants.PULSES_PER_ROTATION;
    public final double FEET_CONVERSION = Math.PI * (1.0/40) / Constants.PULSES_PER_ROTATION; // Using approximate shaft diameter

    // Sensors
    private final TorqueEncoder[] DB_rot_encoders= new TorqueEncoder[1];

    private final AHRS NX_gyro;

    private final boolean clockwise = false;

    // NetworkTables
    private NetworkTableInstance NT_instance;
    private NetworkTableEntry NT_offsetEntry;
    
    private Feedback() {
        DB_rot_encoders[0] = new TorqueEncoder(Ports.DB_ROT_1_A, Ports.DB_ROT_1_B, clockwise, EncodingType.k4X);
        // DB_rot_encoders[1] = new TorqueEncoder(Ports.DB_ROT_2_A, Ports.DB_ROT_2_B, clockwise, EncodingType.k4X);
        // DB_rot_encoders[2] = new TorqueEncoder(Ports.DB_ROT_3_A, Ports.DB_ROT_3_B, clockwise, EncodingType.k4X);
        // DB_rot_encoders[3] = new TorqueEncoder(Ports.DB_ROT_4_A, Ports.DB_ROT_4_B, clockwise, EncodingType.k4X);

        NX_gyro = new AHRS(SPI.Port.kMXP);
        
        NT_instance = NetworkTableInstance.getDefault();
        NT_offsetEntry = NT_instance.getTable("limelight").getEntry("tx");
        resetDriveEncoders();
    } // constructor 

    public void update() {
        updateEncoders();
        updateNavX();
        updateNetworkTables();
    } // update


    // ==================== Encoders ====================

    // DB = drivebase, R = rotation
    // robot front left = 1, front right = 2, back left = 3, back right = 4
    private double[] DB_Rot_Raw = new double[1];
    private double[] DB_Rot_Speed = new double[1];
    private double[] DB_Rot_Angle = new double[1];

    public void resetEncoders() {
        resetDriveEncoders();
    } // reset encoders ONLY at beginning of match

    public void updateEncoders() {
        updateDriveEncoders();
    } // update all Encoders

    // ---- Drivebase Rotation Encoders ----
    
    public void resetDriveEncoders(){
        // for(TorqueEncoder e : DB_rot_encoders){
        //     e.reset();
        // }
        DB_rot_encoders[0].reset();
    } // reset drive encoders

    public void updateDriveEncoders(){

        for(int x = 0; x < 1; x++){
            DB_rot_encoders[x].calc();
        } // encoder.calc for all drive rotation encoders

        // rotation gearing = 60:1, drive gearing = 44.4:1
        
        for(int x = 0; x < 1; x++){
            try{
                DB_Rot_Raw[x] = DB_rot_encoders[x].get();
            }
            catch(Exception e){
                System.out.println("Feedback updateDriveEncoders db_rot_raw: " + x);
            }
        } // encoder.get() for all drive rotation encoders

        for(int x = 0; x < 1; x++){
            try{
                DB_Rot_Speed[x] = DB_rot_encoders[x].getRate() * DISTANCE_PER_PULSE;
            }
            catch(Exception e){
                System.out.println("Feedback updateDriveEncoders db_rot_speed: " + x);
            }
            
        } // update speeds for all drive rotation encoders
        
        // NEED SPECIFICS FROM BEN ON WHERE ENCODER IS GOING TO GO!!! THIS IS NOT FINAL!! NEED TO ADD MORE BASED ON THAT
        for(int x = 0; x < 1; x++) {
            try{
                DB_Rot_Angle[x] = (DB_Rot_Raw[x]) / 8.5;// * ANGLE_PER_PULSE * 2.8);//%360;
                // if(DB_Rot_Angle[x] > 180){
                //     DB_Rot_Angle[x] -= 360;
                // }
                // else if (DB_Rot_Angle[x] < -180){
                //     DB_Rot_Angle[x] += 360;
                // }
                SmartDashboard.putNumber("DB_Rot_Angle Feedback", DB_Rot_Angle[0]);
            }
            catch(Exception e){
                System.out.println("Feedback updateDriveEncoders db_rot_angle: " + x);
            }
            SmartDashboard.putNumber("Encoder1", DB_Rot_Angle[0]);
        } // get angle at which each wheel has turned 

    } // update drive encoders

        // -------- module 0 = front left, 1 = front right, 2 = back left, 3 = back right --------
    public double getRotSpeed(int module) {
        return DB_Rot_Speed[module];
    } // return rotation speed

    public double getRotAngle(int module) {
        //System.out.println("Feedback getRotAngle: " + DB_Rot_Angle.length);
        SmartDashboard.putNumber("WheelModule.RotationalPID rotAngle: ", DB_Rot_Angle[module]);
        return DB_Rot_Angle[module];
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

    public double getYaw() {
        return NX_yaw;
    } // getYaw

    public double getRoll() {
        return NX_roll;
    } // getRoll

    public void zeroYaw() {
        NX_gyro.zeroYaw();
    } // zeroYaw    

    // ===== RPi feedback from NetworkTables =====

    private double NT_targetOffset;
    private boolean NT_targetExists;

    public void updateNetworkTables() {
        NT_targetOffset = NT_offsetEntry.getDouble(0);
        smartDashboard();
        // NT_targetExists = NT_existsEntry.getBoolean(false);
    }

    public double getNTTargetOffset() {
        return NT_targetOffset;
    }

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