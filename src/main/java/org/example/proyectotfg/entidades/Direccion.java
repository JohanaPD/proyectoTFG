package org.example.proyectotfg.modelo;

import org.example.proyectotfg.funcionalidades.VerificatorSetter;

public class Direccion {
    private int id_direccion;
    private String calle;
    private String ciudad;
    private  String cod_postal;

    public Direccion(int id_direccion, String calle, String ciudad, String cod_postal) {
        this.id_direccion = id_direccion;
        this.calle = calle;
        this.ciudad = ciudad;
        this.cod_postal = cod_postal;
    }

    public int getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(int id_direccion) {
        this.id_direccion = id_direccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        if(VerificatorSetter.stringVerificator(calle, calle.length()))
            this.calle = calle;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        if(VerificatorSetter.stringVerificator(ciudad,ciudad.length()))
            this.ciudad = ciudad;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        if(VerificatorSetter.stringVerificator(cod_postal, cod_postal.length()))
            this.cod_postal = cod_postal;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }
}
