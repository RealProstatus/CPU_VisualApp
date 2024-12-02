package org.example.model;

import org.example.src_classes.Command;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DAO_DB extends DAO_memory {
    Connection connection;

    void connect() {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:coms.db");
            System.out.println("DB opened successfully");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public DAO_DB() {
        connect();
        updateList();
    }

    protected void updateList(){
        commands.clear();
        try{
            Statement st = connection.createStatement();
            ResultSet r = st.executeQuery("select * from AllCommands");
            while(r.next()){
                commands.add(new Command(r.getString("Task"),
                        r.getString("Operand1"),
                        r.getString("Operand2")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void add(Command c) {
        try {
            PreparedStatement pst = connection.prepareStatement("" +
                    "INSERT INTO AllCommands(Task, Operand1, Operand2) VALUES (?,?,?)");
            pst.setString(1,c.getTask());
            pst.setInt(2,c.getFst());
            pst.setInt(3,c.getSec());
            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        updateList();
    }
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
