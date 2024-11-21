package org.example.model;

import org.example.src_classes.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

public class Model {
    Executor executor;
    Program program;
    ArrayList<IObserver> allObservers = new ArrayList<>();
    ListIterator<Command> iterator;

    private void call(){ allObservers.forEach((o)->o.event(this)); }

    public Model() {
        ICPU cpu = BCpu.build();
        executor = new Executor(cpu);
        program = new Program();
        iterator = program.listIterator();
    }
    public void addObserver(IObserver obs){ allObservers.add(obs); call(); }
    private void resetExecution(){ iterator = program.reset(); call(); }

    public void addCommand(Command c){resetExecution(); program.add(c); call(); }
    public void deleteCommand(Command c){resetExecution(); program.remove(c); call(); }
    public void executeCommand(){
        if(iterator.hasNext()) executor.execute(iterator.next());
        call();
    }
    public Command getCurrentCommand(){  return program.getCommand(iterator.nextIndex());}
    public void moveCommandUp(Command c){resetExecution(); program.commandUp(c); call();}
    public void moveCommandDown(Command c){ resetExecution();program.commandDown(c); call();}

    public Map<Integer,Integer> getRegister(){
        return executor.getRegisterInfo();
    }
    public Map<Integer,Integer> getRam(){
        return executor.getRamInfo();
    }


    public Program getProgram(){ return program; }
    public Iterator<Command> getIterator(){ return iterator; }

}
