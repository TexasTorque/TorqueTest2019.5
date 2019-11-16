// /**
//  * A command is the most basic part of auton, the command makes one subsystem do one thing, for example, it could make a lift go to a setpoint, or make something open or close, it controls only ONE subsystem
//  * 
//  */

// package org.texastorque.auto.commands;

// import org.texastorque.auto.Command;
// import org.texastorque.torquelib.controlLoop.ScheduledPID;

// import edu.wpi.first.wpilibj.Timer;

// public class Swerve extends Command{

//     boolean hitAngle = false;
//     double transX;
//     double transY;
//     double distance;
//     double angle;
//     double rotAngle;
//     double distanceTraveled;
//     ScheduledPID rotPID;

//     public Swerve(double delay, double distance, double angle, double rotAngle){
//         super(delay);
//         this.distance = distance;
//         this.angle = angle;
//         transX = Math.sin(angle);
//         transY = Math.cos(angle);
//         this.rotAngle = rotAngle;
//         rotPID = new ScheduledPID.Builder(angle, 0.5)
//             .setPGains(0.023)
//             .build();
//     }//initializing all values

//     public void init(){
//         feedback.zeroYaw();
//         feedback.resetDriveEncoders();
//     }

//     public void continuous(){
//         input.setTransX(transX);
//         input.setTransY(transY);
//         distanceTraveled = feedback.getRotRaw(0);
//     }

//     public void end(){

//     }

//     public boolean endCondition(){
//         return true;
//     }

// }