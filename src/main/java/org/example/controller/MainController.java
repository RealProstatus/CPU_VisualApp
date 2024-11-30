package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.cpu_visualapp.CPUApp;
import org.example.model.BModel;
import org.example.model.IObserver;
import org.example.model.Model;
import org.example.src_classes.Command;

import java.io.IOException;

public class MainController implements IObserver {
    Model m = BModel.build();

    @FXML
    Pane pane_ram;
    @FXML
    Pane pane_reg;
    @FXML
    VBox vBox;
    @FXML
    Button b_add_command;
    @FXML
    Button b_stop_command;
    @FXML
    Button b_execute;

    @FXML
    void initialize() {
        m.addObserver(this);

        FXMLLoader ramld = new FXMLLoader(CPUApp.class.getResource("ram_view.fxml"));
        try {
            pane_ram.getChildren().add(ramld.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader regld = new FXMLLoader(CPUApp.class.getResource("reg_view.fxml"));
        try {
            pane_reg.getChildren().add(regld.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

        @FXML
    public void addCommand(){
        FXMLLoader loader = new FXMLLoader(CPUApp.class.getResource(
                "command_dialog.fxml"));
        try {
            Stage dialog = new Stage();
            CommandDialogController c = new CommandDialogController(dialog);
            loader.setController(c);
            BorderPane bp = loader.load();
            dialog.setTitle("New command");
            dialog.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(bp);
            dialog.setScene(scene);
            dialog.showAndWait();

            m.addCommand(c.addingNewCommand());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void showReport(){
        FXMLLoader loader = new FXMLLoader(CPUApp.class.getResource(
                "report_dialog.fxml"));
        try{
            Stage dialog = new Stage();
            ReportController rc = new ReportController();
            loader.setController(rc);
            BorderPane bp = loader.load();
            dialog.setTitle("Report");
            dialog.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(bp);
            dialog.setScene(scene);
            dialog.showAndWait();

            rc.show();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void resetExecuting(){
        m.hardResetExecution();
    }

    @FXML
    public void executeCommand(){
        m.executeCommand();
    }

    @Override
    public void event(Model m) {
        vBox.getChildren().clear();
        for(Command c: m.getProgram()){
            CommandController cc = new CommandController();
            FXMLLoader commandLoader = new FXMLLoader(CPUApp.class.getResource("command_view.fxml"));
            commandLoader.setController(cc);
            try{
                if(c == m.getCur_command()){
                    cc.itsCurrentCommand();
                }
                Pane pane = commandLoader.load();
                cc.setCommand(c);
                vBox.getChildren().add(pane);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }

    }
}
