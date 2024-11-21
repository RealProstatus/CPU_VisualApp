package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.model.BModel;
import org.example.model.IObserver;
import org.example.model.Model;

import java.util.Map;

public class RamController implements IObserver {
    Model m = BModel.build();
    @FXML
    Label l_ram;

    @FXML
    void initialize(){ m.addObserver(this); }

    @Override
    public void event(Model m) {
        String str = new String();
        for(Map.Entry<Integer,Integer> item: m.getRam().entrySet()){
            str += item.toString();
            str += " ";
        }
        l_ram.setText(str);
    }
}
