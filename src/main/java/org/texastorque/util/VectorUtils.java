// to do all vector maths

package org.texastorque.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VectorUtils {

    private static volatile double magX;
    private static volatile double magY;
    private static volatile double bearing;
    private static volatile double mag;

    public static double vectorAddition2DBearing(double magX1, double magY1, double magX2, double magY2){
        magX = magX1 + magX2;
        magY = magY1 + magY2;
        bearing = Math.toDegrees(Math.atan2(magY, magX));
        SmartDashboard.putNumber("CheckBearing", bearing);
        // try {
        // //    bearing = toBearing(Math.toDegrees(Math.atan2(magY, magX)));   
        //     bearing = Math.atan2(magY, magX);
        //  } catch (Exception e) {
        //      SmartDashboard.putString("vectoradditionexception", "errorBearing");
        // }
        return bearing;
    } // vector Addition 2D return bearing of final vector

    public static double vectorAddition2DMagnitude(double magX1, double magY1, double magX2, double magY2){
        magX = magX1 + magX2;
        magY = magY1 + magY2;
        try {
            mag = Math.hypot(magX, magY);   
        } catch (Exception e) {
            SmartDashboard.putString("vectoradditionexceptionMAG", "errorBearing");
        }
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

    public static double toBearing(double angle) { // came from input with some modifications
        double bearing = 0;
        bearing = 90 - angle;
        if (bearing < 0){
            bearing = 360 + bearing;
        } // change them all to positive bearings for ease of use
        if (bearing > 180) {
            bearing = bearing - 180;
        } // change so that it goes to 180 on either side
        return bearing;
    } // change value to a bearing
} // VectorUtils
