module org.example.cpu_visualapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens org.example.cpu_visualapp to javafx.fxml;
    opens org.example.model to javafx.fxml;
    opens org.example.src_classes to javafx.fxml;
    opens org.example.controller to javafx.fxml;
    exports org.example.cpu_visualapp;
    exports org.example.controller;
    exports org.example.model;
    exports org.example.src_classes;
}