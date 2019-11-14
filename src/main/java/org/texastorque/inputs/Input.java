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

    private double transX = 0;
	private double transY = 0;
	private double transMag = 0;
	private double transThetaRaw = 0;
    private double transTheta = 0;
    private double rotMag = 0;
    
    private volatile boolean snapTo = false; 
    
    public void updateDrive() {
        transX = driver.getLeftXAxis();
		transY = -driver.getLeftYAxis();
        rotMag = driver.getRightXAxis();
        transThetaRaw = Math.toDegrees(Math.atan2(transY, transX));
        transTheta = toBearing(transThetaRaw);
    } // updateDrive

    public double getTransX(){
        return transX;
    } // return the translational value in the x direction

    public void setTransX(double transX){
        this.transX = transX;
    } // set the translational value in the x direction

    public double getTransY(){
        return transY;
    } // return the translational value in the y direction

    public void setTransY(double transY){
        this.transY = transY;
    } // set the translational value in the y direction

    public double getRotMag(){
        return rotMag;
    } // return the rotational magnitude value

    public void setRotR(double rotR){
        this.rotMag = rotR;
    } // set the rotatioinal magnitude value

    public double getTransTheta(){
        return transTheta;
    } // return the translational theta value

    public void resetDrive() {
    } // resetDrive

    public boolean getSnapTo(){
        return snapTo;
    } // return whether or not the robot is in snap to state (snap to = just turn to the correct direction)

    public double toBearing(double angle) { // to use with input from controller // 1_comment
        double bearing = 0;
		angle = angle % 360;
		if (transX == 0 && transY == 0){
			return 1477;
		} 
		if ((angle <= 180 && angle >= 0 ) || (angle < 0 && angle >= -90)) {
			bearing = 90 - angle;
		} else {
			bearing = 90 - angle -360;
		}
        return bearing;
	} // change value to a bearing

    public void SmartDashboard(){
    }
    
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