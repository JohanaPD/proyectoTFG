package com.ies.appmeetpshyc.meetphsyc.Mediator;

import com.ies.appmeetpshyc.meetphsyc.excepciones.DataAccessException;
import com.ies.appmeetpshyc.meetphsyc.excepciones.IncompleteDataInRecord;
import com.ies.appmeetpshyc.meetphsyc.excepciones.NonexistingUser;
import com.ies.appmeetpshyc.meetphsyc.excepciones.ThereIsNoView;
import com.ies.appmeetpshyc.meetphsyc.model.UsuarioEspecifico;


import java.io.IOException;

public interface MediatorProfile {

    void userPassVerificator(String usuario, String pass) throws NonexistingUser, IOException, DataAccessException;
    void userRegister() throws IOException, ThereIsNoView;

    void makeRegister(UsuarioEspecifico user) throws IOException, DataAccessException, IncompleteDataInRecord;

    void volverIncio() throws ThereIsNoView;

    Object cargarValores(Object nuevo);

    void areaUser() throws ThereIsNoView;
}
