package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.example.proyectotfg.mediators.Callback;
/**
 * Manages appointment hours within a fragment.
 * <p>Authors: Johana Pardo, Daniel Ocaña</p>
 * <p>Version: Java 21</p>
 * <p>Since: 2024-06-08</p>
 */
public class ControllerFragmentApoinmentHours {
    @FXML
    private Text dataAppointment;
    @FXML
    private Text hourText;


    @FXML
    private Text minuteText;

    private Callback callback;

    /**
     * Sets the callback.
     *
     * @param callback the callback to set.
     */
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * Gets the hour text.
     *
     * @return the hour text.
     */
    public Text getHourText() {
        return hourText;
    }

    /**
     * Sets the hour text.
     *
     * @param hourText the hour text to set.
     */
    public void setHourText(String hourText) {
        this.hourText.setText(hourText);
    }

    /**
     * Gets the minute text.
     *
     * @return the minute text.
     */
    public Text getMinuteText() {
        return minuteText;
    }

    /**
     * Sets the minute text.
     *
     * @param minuteText the minute text to set.
     */
    public void setMinuteText(String minuteText) {
        this.minuteText.setText(minuteText);
    }

    /**
     * Sets the date of the appointment.
     *
     * @param date the date to set.
     */
    public void setDataAppointment(String date) {
        dataAppointment.setText(date);
    }

    /**
     * Confirms the action.
     *
     * @param actionEvent the action event.
     */
    public void confirmar(ActionEvent actionEvent) {
        callback.doAction();
    }

}
