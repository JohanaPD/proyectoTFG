package com.ies.appmeetpshyc.meetphsyc.Mediator;

import com.ies.appmeetpshyc.meetphsyc.excepciones.ThereIsNoView;
import javafx.event.ActionEvent;

public interface MediatorAcceso {

    //Entrada primer view
    void openLogin() throws ThereIsNoView;
     void haciaAtras();

}
