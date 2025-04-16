package org.example.model;

import org.example.src_classes.Command;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class DAO_memory implements Iterable<Command> {

    protected final CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();

    @Override
    public ListIterator<Command> iterator() { return commands.listIterator(); }

    public void add(Command c) { commands.add(c);}
    public void remove(Command c){ commands.remove(c); }

    public void commandDown(Command c){
        try {
            commands.add(commands.indexOf(c)+2,c);
            commands.remove(c);
        } catch (IndexOutOfBoundsException e) {}
    }

    public void commandUp(Command c){
        try {
            commands.add(commands.indexOf(c)-1,c);
            commands.remove(commands.indexOf(c)+2);
        } catch (IndexOutOfBoundsException e) {}
    }

    public Command getCommand(int index){
        try {
            return commands.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("!");
            return commands.getLast();
        }
    }
    public String mostPopCommand(){
        System.out.println("Самая популярная команда:");
        Map.Entry<String,Long> popMark = getFrequencyMap()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        if(popMark != null)
            return popMark.getKey();
        else{
            throw new RuntimeException("Error with mostPopCommand");
        }
    }

    public void rangeOfAddresses(){
        System.out.println("Интервал используемой памяти: ");
        int max = -1, min = 1024;
        List<Command> sample = commands
                .stream()
                .filter((val) -> val.getTask().equals("init") || val.getTask().equals("st"))
                .toList();
        for(Command c: sample){
            int tmp = 0;
            if (c.getTask().equals("init")) tmp = c.getFst();
            if (c.getTask().equals("st")) tmp = c.getSec();

            if (tmp < min) min = tmp;
            if (tmp > max) max = tmp;
        }
        System.out.println("{"+min+","+max+"}");
        System.out.println();

    }

    public List<Map.Entry<String,Long>> sortedListOfCommands(){
        System.out.println("Список команд:");
        Map<String,Long> popMarks = getFrequencyMap();
        return popMarks.entrySet().stream()
                .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                .toList();
    }

    private Map<String,Long> getFrequencyMap(){
        return commands
                .stream()
                .collect(Collectors.groupingBy(Command::getTask,Collectors.counting()));
    }
}
