package org.example.proyectotfg;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.proyectotfg.DAO.SqliteConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try(SqliteConnector connector= new SqliteConnector()){
            connector.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}