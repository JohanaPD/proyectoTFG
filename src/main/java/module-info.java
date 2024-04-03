module org.example.proyectotfg {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.proyectotfg to javafx.fxml;
    exports org.example.proyectotfg;
}