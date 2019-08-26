/**
 * A command is the most basic part of auton, the command makes one subsystem do one thing, for example, it could make a lift go to a setpoint, or make something open or close, it controls only ONE subsystem
 * 
 */

package org.texastorque.auto.commands;

import org.texastorque.auto.Command;

import edu.wpi.first.wpilibj.Timer;

public class ExampleCommand extends Command{

    private double startTime;
    private double time;

    // private typeOfObject variable; how many ever you need

    public ExampleCommand(double delay, double time /*, extraVariables*/ ){
        super(delay);
        this.time = time;
        // do whatever you need to start off the variable
    } // constructor 

    @Override
    protected void init() {
        startTime = Timer.getFPGATimestamp();
        // what you want to do with the code
    } // what do you want the command to do

    @Override
    protected void continuous() {}

    @Override
    protected boolean endCondition() {
        return false;
        // delete above^ 
        // return whatever you want the end goal to be ex:  
        // if trying to get to a setpoint, if within a small amount of that
        // if trying to do something for a time, when that time has passed
    } // the end condition to exit the command

    @Override
    protected void end() {

    }

} // example command
