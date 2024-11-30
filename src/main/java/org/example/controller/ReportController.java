package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.model.BModel;
import org.example.model.Model;

import java.util.Map;

public class ReportController {
    Model m = BModel.build();

    @FXML
    Label l_report;

    @FXML
    void initialize(){
        show();
    }

    public void show(){
        String str="";
        for(Map.Entry<String,Long> var: m.getSortedListOfCommands()){
            str += var.toString();
            str += "\n";
        }
        l_report.setText(str);
    }
}
