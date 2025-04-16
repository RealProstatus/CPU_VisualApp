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
        }catch (ClassNotFoundException | SQLException e){
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
                        r.getString("Operand2"),
                        r.getInt("ID")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    protected void updateDB(){
        try{
            PreparedStatement pst = connection.prepareStatement("" +
                    "UPDATE AllCommands SET Task = ?, Operand1 = ?," +
                    "Operand2 = ? WHERE ID = ?");
            for(Command com: commands){
                pst.setString(1,com.getTask());
                pst.setInt(2,com.getFst());
                pst.setInt(3,com.getSec());
                pst.setInt(4,com.getId());
                pst.addBatch(); //добавляем запрос по конкретной команде в пакет
            }
            pst.executeBatch(); //выполняем пакет запросов
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
    public void remove(Command c){
        try {
            PreparedStatement pst = connection.prepareStatement("" +
                    "DELETE FROM AllCommands WHERE ID=?");
            pst.setInt(1,c.getId());
            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        updateList();
    }

    public void commandDown(Command c){//достаточно лишь свапнуть id записей в таблице
        int tmp = c.getId();
        c.setId(commands.get(commands.indexOf(c)+1).getId());
        commands.get(commands.indexOf(c)+1).setId(tmp);

        updateDB();//выгрузили новые данные в бд
        updateList();
    }

    public void commandUp(Command c){//достаточно лишь свапнуть id записей в таблице
        int tmp = c.getId();
        c.setId(commands.get(commands.indexOf(c)-1).getId());
        commands.get(commands.indexOf(c)-1).setId(tmp);

        updateDB();//выгрузили новые данные в бд
        updateList();
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
