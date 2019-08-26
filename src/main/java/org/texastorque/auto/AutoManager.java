package org.texastorque.auto;

import org.texastorque.auto.sequences.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import java.util.ArrayList;

public class AutoManager {

    private static AutoManager instance;

    private ArrayList<Sequence> autoSequences;
    private SendableChooser<String> autoSelector = new SendableChooser<String>();

    private Sequence currentSequence;
    private boolean sequenceEnded;

    private AutoManager() {
        autoSequences = new ArrayList<Sequence>();
        autoSequences.add(new ExampleSequence());

        //autoSelector.setDefaultOption("ExampleSequence", "ExampleSequence"); can be whatever one you want as default
        autoSelector.addOption("0 ExampleSequence", "0 Example Sequence"); //(doesn't have to be exact same name)
        

        SmartDashboard.putData(autoSelector);
        System.out.println("All auto sequences loaded.");
    } // constructor 

    public void displayChoices() {
        SmartDashboard.putData(autoSelector);
    } // displays auto options on smart dashboard

    public void chooseSequence() {
        String autoChoice = autoSelector.getSelected();
        System.out.println(autoChoice);

        switch(autoChoice) {
            case "0 ExampleSequence":
                currentSequence = autoSequences.get(0); // wherever corresponding sequence is in autoSequences
                break;
            
            // can add more sequences = as many autos as you want 

            default:
                break;
        } // sets the sequence you chose in smart dashboard as the one to run

        currentSequence.reset();
        sequenceEnded = false;
    } // chooses the sequence to run

    public void runSequence() {
        currentSequence.run();
        sequenceEnded = currentSequence.hasEnded();
    } // runs the sequence selected

    public boolean sequenceEnded() {
        return sequenceEnded;
    } // determines whether the sequence ended or not

    public static AutoManager getInstance() {
        if (instance == null) {
            synchronized (AutoManager.class) {
                if (instance == null)
                    instance = new AutoManager();
            }
        }
        return instance;
    } // getInstance
} // AutoManager class