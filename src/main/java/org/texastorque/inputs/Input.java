package org.texastorque.inputs;

import org.texastorque.inputs.State.RobotState;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;
import org.texastorque.auto.AutoManager;

/**
 * All forms of input, including driver/operator controllers and input from the code itself.
 * 
 * Setters should only be used by Commands. Subsystems should only use getters.
 */
public class Input {

    private static volatile Input instance;

    private volatile State state;
	private GenericController driver;
    private GenericController operator;
    // private GenericController tester;

    
    private Input() {
        state = State.getInstance();
		driver = new GenericController(0, .1);
        operator = new GenericController(1, .1);
        // tester = new GenericController(2, .1);
    } // constructor
    
    public void updateControllers() {

        if (!driver.getName().equals("")) {
            updateDrive();
            // update other driver stuff
        }

        if (!operator.getName().equals("")) {
            // update operator stuff
        }
    } // updateControllers

    public void resetAll() {
        resetDrive();
    } // resetAll 

    // ========== DriveBase ==========

    // private volatile double L_F_Speed = 0;
    // private volatile double R_F_Speed = 0;
    // private volatile double L_B_Speed = 0;
    // private volatile double R_B_Speed = 0;

    private volatile double transX = 0;
    private volatile double transY = 0;
    private volatile double transMag = 0;
    private volatile double transTheta = 0;

    private volatile double rotX = 0;
    private volatile double rotY = 0;
    private volatile double rotR = 0; 

    public void updateDrive() {
        transX = driver.getLeftXAxis();
        transY = -driver.getLeftYAxis();
        transMag = Math.hypot(transX, transY);
        transTheta = toBearing(Math.toDegrees(Math.atan2(transY, transX)));

        rotX = driver.getRightXAxis();
        rotY = -driver.getRightYAxis();
        rotR = maintainDirection(Math.hypot(rotX, rotY), rotX);
    } // updateDrive

    public void resetDrive() {
        transX = 0;
        transY = 0;
        rotX = 0;
        rotY = 0;
    } // resetDrive

    public double getTransMag() {
        return transMag;
    } // return translational vector magnitude

    public double getTransTheta() {
        return transTheta;
    } // return translational angle as a bearing

    public double getRotR(){
        return rotR;
    } // return the magnitude of rotation

    public double toBearing(double angle) {
        double bearing = 0;
        bearing = 90 - angle;
        if (bearing < 0){
            bearing = 360 + bearing;
        } // change them all to positive bearings for ease of use
        return bearing;
    } // change value to a bearing

    public double maintainDirection(double mag, double rotX) {
        double r = mag;
        if (rotX < 0){
            r = - mag;
        } // if x value of joystick is negative, change magnitude to negative so that it turns counterclockwise
        return r;
    } // ensure that direction of turning is the correct one

    // ========== NetworkTables ==========
    
    // private int NT_pipeline = 0;

    // public void updateNetworkTables() {
    //     if (driver.getYButtonPressed()) {
    //         NT_pipeline = 1 - NT_pipeline;
    //     }
    //     Feedback.getInstance().getNTPipelineEntry().setNumber(NT_pipeline);
    // }
    
    public static Input getInstance() {
        if (instance == null) {
            synchronized (Input.class) {
                if (instance == null)
                    instance = new Input();
            }
        }
        return instance;
    } // getInstance
    
} // Input