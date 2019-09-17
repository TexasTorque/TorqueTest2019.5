package org.texastorque.inputs;

public class State {
    
    private static volatile State instance;

    public enum RobotState {
        AUTO, TELEOP, VISION;
    }
    private RobotState robotState = RobotState.TELEOP;

    public RobotState getRobotState() {
        return robotState;
    }

    public String getRobotStateString(){
        return robotState.toString();
    }

    public void setRobotState(RobotState state) {
        synchronized (this) {
            this.robotState = state;
        }
    }

    public static State getInstance() {
        if (instance == null) {
            synchronized (State.class) {
                if (instance == null)
                    instance = new State();
            }
        }
        return instance;
    }
}