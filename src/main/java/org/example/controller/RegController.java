package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.example.model.BModel;
import org.example.model.IObserver;
import org.example.model.Model;

import java.util.Map;

public class RegController implements IObserver {
    Model m = BModel.build();
    @FXML
    GridPane grid;

    @FXML
    void initialize(){
        m.addObserver(this);
    }

    @Override
    public void event(Model m) {
        grid.getChildren().clear();
        Map<Integer,Integer> res = m.getRegister();
        int inc = 0;
        for(Map.Entry<Integer,Integer> val: res.entrySet()){
            Label label = new Label();
            label.setText(val.toString());
            grid.addColumn(inc,label);
            inc++;
        }
    }
}
