package org.texastorque.auto.sequences;

import org.texastorque.auto.Sequence;
import org.texastorque.auto.Command;
import org.texastorque.auto.commands.*;

import java.util.ArrayList;

public class ExampleSequence extends Sequence{

    @Override
    protected void init() {
        ArrayList<Command> block = new ArrayList<>();
        block.add(new ExampleCommand(0, 0));
        // you can add more commands within the block as long as they are differnet commands, if you want to use the same command again, su
        
        addBlock(block);
    } // what to do

} // example Sequence
