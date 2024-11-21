package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.src_classes.Command;

public class CommandDialogController {
    Command c;
    Stage st;

    CommandDialogController(Stage st){ this.st = st; }

    @FXML
    TextField l_fst_operand;
    @FXML
    TextField l_sec_operand;
    @FXML
    ComboBox<String> cb_choice_command_task;

    @FXML
    void initialize(){
        cb_choice_command_task.setItems(FXCollections.observableArrayList("print",
                "ld","st", "mv", "init", "add", "sub", "mult", "div"));
        cb_choice_command_task.getSelectionModel().selectFirst();
    }

    public Command addingNewCommand(){
        c = new Command(cb_choice_command_task.getValue().toString(),
                l_fst_operand.getText(),
                l_sec_operand.getText());
        st.close();
        return c;
    }
}
