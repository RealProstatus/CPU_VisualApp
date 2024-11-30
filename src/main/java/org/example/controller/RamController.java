package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.model.BModel;
import org.example.model.IObserver;
import org.example.model.Model;

import java.util.Iterator;
import java.util.Map;

public class RamController implements IObserver {
    Model m = BModel.build();
    @FXML
    GridPane grid;

    @FXML
    void initialize(){ m.addObserver(this); }

    @Override
    public void event(Model m) {
        grid.getChildren().clear();
        Iterator iterator = m.getRam().entrySet().iterator();
        for(int i =0;i<5;i++){
            for(int j = 0;j<6;j++){
                Label lbl = new Label();
                if(iterator.hasNext()){
                    lbl.setText(iterator.next().toString());
                }
                Pane pane = new Pane();
                pane.getChildren().add(lbl);
                grid.add(pane, i,j);
            }
        }
    }
}
