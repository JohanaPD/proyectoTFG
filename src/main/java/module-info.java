module org.example.proyectotfg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.mail;
    requires java.desktop;
    requires com.jfoenix;

    opens org.example.proyectotfg to javafx.fxml;
    exports org.example.proyectotfg;
    exports org.example.proyectotfg.controllers;
    opens org.example.proyectotfg.controllers to javafx.fxml;
    opens org.example.proyectotfg.controllers.appointmentNotifiers to javafx.fxml;

}
