// to do all vector maths

package org.texastorque.util;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class VectorUtils {

    private static volatile double magX;
    private static volatile double magY;
    private static volatile double bearing;
    private static volatile double mag;

    public static double vectorAddition2DBearing(double magX1, double magY1, double magX2, double magY2){
        try{
            double degrees = 0.0;
            magX = magX1 + magX2;
            magY = magY1 + magY2;
            // degrees = Math.toDegrees(Math.atan2(magY, magX));
            degrees = Math.toDegrees(Math.atan(magX2));

            SmartDashboard.putNumber("VectorUtils.vectorAddition2DBearing degrees", degrees);
            // bearing = toBearing(degrees);
            // SmartDashboard.putNumber("VectorUtils.vectorAddition2DBearing bearing", bearing);
            
            // System.out.println("magX: " + magX + ", magY: " + magY + ", bearing: " + bearing);
            // if(magX1 < 0.0){degrees *= -1;}

            // // if(bearing == 90 && magX == 0.0 && magY == 0.0){bearing = 0.0;}
            bearing = degrees;
            return bearing;
        }catch(Exception e){
            System.out.println("VectorUtils vectorAddition2DBearing:" + e.toString());
            System.out.println("magX: " + magX + ", magX1: " + magX1 + ", magX2: " + magX2);
            System.out.println("magY: " + magY + ", magY1: " + magY1 + ", magY2: " + magY2);
            System.out.println("bearing: " + bearing);
            return 0.0;
        }
    } // vector Addition 2D return bearing of final vector

    public static double vectorAddition2DMagnitude(double magX1, double magY1, double magX2, double magY2){
        magX = magX1 + magX2;
        magY = magY1 + magY2;
        SmartDashboard.putNumber("VectorUtils magX", magX);
        SmartDashboard.putNumber("VectorUtils magY", magY);
        mag = Math.hypot(magX, magY);
        return mag;
    } // vector addition 2d return magnitude of final vector

    //public static double gyroVectorAdd(double offset, )

    public double getMagX(){
        return magX;
    } // return magnitude

    public double getMagY(){
        return magY;
    } // return magnitude

    public double getMag(){
        return mag;
    } // return magnitude

    public double getBearing(){
        return bearing;
    } // return bearing

    // public static double toBearing(double angle) { // came from input with some modifications
    //     double bearing = 0;
    //     bearing = 90 - angle;
    //     if (bearing < 0){
    //         bearing = 360 + bearing;
    //     } // change them all to positive bearings for ease of use
    //     if (bearing > 180) {
    //         bearing = bearing - 180;
    //     } // change so that it goes to 180 on either side
    //     return bearing;
    // } // change value to a bearing

    public static double toBearing(double angle) { // came from input with some modifications
        double bearing = 0.0;
        // bearing = 90.0 - Math.abs(angle);

        // if (bearing < 0.0){
        //     // bearing = 360.0 + bearing;
        //     bearing *= -1;
        // } // change them all to positive bearings for ease of use

        // if (bearing > 180.0) {
        //     bearing = bearing - 180.0;
        // } // change so that it goes to 180 on either side

        
        return bearing;
    } // change value to a bearing
} // VectorUtils
