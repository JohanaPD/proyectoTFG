package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.example.proyectotfg.mediators.Callback;

public class ControllerFragmentApoinmentHours {
    @FXML
    private Text dataAppointment;
    @FXML
    private Text hourText;


    @FXML
    private Text minuteText;

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Text getHourText() {
        return hourText;
    }

    public void setHourText(String  hourText) {
        this.hourText.setText(hourText);
    }


    public Text getMinuteText() {
        return minuteText;
    }

    public void setMinuteText(String minuteText) {
        this.minuteText.setText(minuteText);
    }

    public void confirmar(ActionEvent actionEvent) {
        callback.doAction();
    }
}
