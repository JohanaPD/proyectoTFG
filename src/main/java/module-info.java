module org.example.proyectotfg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.mail;
    // requires javax.mail;
    //requires imap;
    requires java.desktop;
    requires com.jfoenix;
    requires ant;
    requires com.google.auth.oauth2;
    // requires javax.mail.api;


    opens org.example.proyectotfg to javafx.fxml;
    exports org.example.proyectotfg;
    exports org.example.proyectotfg.controllers;
    opens org.example.proyectotfg.controllers to javafx.fxml;
}