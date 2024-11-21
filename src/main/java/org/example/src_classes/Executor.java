package org.example.src_classes;

import java.util.Map;

public class Executor {
    ICPU cpu1;

    public Executor(ICPU cpu){
        cpu1=cpu;
    }

    public void execute(Command c){
        cpu1.run(c);
    }
    public void execute(Command[] program){
        for(Command c: program)
            cpu1.run(c);
    }
    public Map<Integer,Integer> getRegisterInfo(){return cpu1.getRegisterInfo();}
    public Map<Integer,Integer> getRamInfo(){return cpu1.getRamInfo();}
}
