// enable teleop 2019 GAME ONLY 

package org.texastorque.auto.commands;

import org.texastorque.auto.Command;
import org.texastorque.inputs.State.RobotState;

/**
 * DO NOT USE, EXIT CONDITION NOT PRESENT
 */

public class EnableTeleop extends Command {
    
    public EnableTeleop(double delay) {
        super(delay);
    }

    @Override
    protected void init() {}

    @Override
    protected void continuous() {
        input.updateControllers();
    }

    @Override
    protected boolean endCondition() {
        return true; // THIS IS NOT ACTUALLY GOOD FOR THIS, NEEDS TO BE FIXED
    }

    @Override
    protected void end() {}
}