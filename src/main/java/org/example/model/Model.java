package org.example.model;

import org.example.src_classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Model {
    Executor executor;
    Program program;
    ArrayList<IObserver> allObservers = new ArrayList<>();
    ListIterator<Command> iterator;
    Command cur_command;

    private void call(){ allObservers.forEach((o)->o.event(this)); }
    private void resetExecution(){ cur_command = null; }

    public Model() {
        ICPU cpu = BCpu.build();
        executor = new Executor(cpu);
        program = new Program();
        iterator = program.iterator();
        cur_command = null;
    }


    public void addObserver(IObserver obs){ allObservers.add(obs); call(); }

    public void hardResetExecution(){
        ICPU cpu = BCpu.build();
        executor = new Executor(cpu);
        cur_command = null;
        call();
    }
    public void addCommand(Command c){ program.add(c); resetExecution(); call(); }
    public void deleteCommand(Command c){ program.remove(c); resetExecution(); call(); }
    public void executeCommand(){
        if (cur_command == null){
            iterator = program.iterator();
        }
        if(iterator.hasNext()){
            cur_command = iterator.next();
            executor.execute(cur_command);
            call();
        }
    }
    public void moveCommandUp(Command c){ program.commandUp(c); resetExecution(); call(); }
    public void moveCommandDown(Command c){ program.commandDown(c); resetExecution(); call(); }


    public Map<Integer,Integer> getRegister(){
        return executor.getRegisterInfo();
    }
    public Map<Integer,Integer> getRam(){
        return executor.getRamInfo();
    }
    public List<Map.Entry<String,Long>> getSortedListOfCommands(){
        return program.sortedListOfCommands();
    }

    public Program getProgram(){ return program; }
    public Command getCur_command(){return cur_command;}
}
