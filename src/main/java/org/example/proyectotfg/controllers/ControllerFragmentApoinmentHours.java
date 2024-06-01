package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.example.proyectotfg.mediators.Callback;

public class ControllerFragmentApoinmentHours {
    @FXML
    private Text hourText;

    @FXML
    private Text hourText1;

    @FXML
    private Text minuteText;

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Text getHourText() {
        return hourText;
    }

    public void setHourText(Text hourText) {
        this.hourText = hourText;
    }

    public Text getHourText1() {
        return hourText1;
    }

    public void setHourText1(Text hourText1) {
        this.hourText1 = hourText1;
    }

    public Text getMinuteText() {
        return minuteText;
    }

    public void setMinuteText(Text minuteText) {
        this.minuteText = minuteText;
    }

    public void confirmar(ActionEvent actionEvent) {
    }
}
