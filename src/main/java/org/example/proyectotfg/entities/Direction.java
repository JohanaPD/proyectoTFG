package org.example.proyectotfg.entities;

import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.functions.VerificatorSetter;

public class Direction {
    private int idDireccion;
    private String street;
    private String city;
    private int postalCode;

    public Direction(String street, String city, int postalCode) throws NullArgumentException, IncorrectDataException {
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
    }

    public Direction(int idDireccion, String street, String city, int postalCode) throws NullArgumentException, IncorrectDataException {
        setIdDireccion(idDireccion);
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
    }

    public Direction() {

    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(idDireccion, 1000000000) && idDireccion >= 0) {
            this.idDireccion = idDireccion;
        } else {
            throw new IncorrectDataException("Verifica el id de persona, solo se aceptan números");
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) throws NullArgumentException, IncorrectDataException {
        if (street != null) {
            street = street.trim(); // Eliminar espacios en blanco al principio y al final
            if (VerificatorSetter.stringVerificatorletterAndNumbers(street, street.length())) {
                this.street = street;
            } else {
                throw new IncorrectDataException("La calle no puede tener simbolos");
            }
        } else {
            throw new NullArgumentException("Hay argumentos nulos al crear la dirección");
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws NullArgumentException, IncorrectDataException {
        if (city != null) {
            if (VerificatorSetter.stringVerificator(city, city.length())) {
                this.city = city;
            } else {
                throw new IncorrectDataException("La ciudad solo puede contener letras");
            }
        } else {
            throw new NullArgumentException("Hay argumentos nulos al crear la dirección");
        }
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(postalCode, 5)) {
            this.postalCode = postalCode;
        } else {
            throw new IncorrectDataException("El código postal solo puede contener hasta 5 números");
        }
    }

    @Override
    public String toString() {
        return "Direction{" + "idDireccion=" + idDireccion + ", street='" + street + '\'' + ", city='" + city + '\'' + ", postalCode=" + postalCode + '}';
    }
}
