package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.example.model.BModel;
import org.example.model.Model;
import org.example.src_classes.Command;

public class CommandController {
    Command c;
    boolean flag = false;
    Model m = BModel.build();

    @FXML
    Label l_command_task;
    @FXML
    Label l_command_1st_op;
    @FXML
    Label l_command_2nd_op;

    void setCommand(Command _c){
        c = _c;
        if(flag){
            l_command_task.setTextFill(Color.RED);
            l_command_1st_op.setTextFill(Color.RED);
            l_command_2nd_op.setTextFill(Color.RED);
        }else{
            l_command_task.setTextFill(Color.BLACK);
            l_command_1st_op.setTextFill(Color.BLACK);
            l_command_2nd_op.setTextFill(Color.BLACK);
        }
        l_command_task.setText(c.getTask());
        switch(c.getTask()){
            case "print":
            case "add":
            case "mult":
            case "sub":
            case "div": l_command_1st_op.setText(""); l_command_2nd_op.setText(""); break;
            default:
                l_command_1st_op.setText(Integer.toString(c.getFst()));
                l_command_2nd_op.setText(Integer.toString(c.getSec()));
                break;
        }
    }

    void itsCurrentCommand(){ flag = true;}

    @FXML
    void delCommand(){ m.deleteCommand(c); }
    @FXML
    void upCommand(){ m.moveCommandUp(c); }
    @FXML
    void downCommand(){ m.moveCommandDown(c); }
}
