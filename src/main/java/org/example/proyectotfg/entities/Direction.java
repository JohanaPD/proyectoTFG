package org.example.proyectotfg.entities;

import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.functions.VerificatorSetter;
/**
 * Represents a geographical direction, including street, city, and postal code.
 */
public class Direction {
    private int idDireccion;
    private String street;
    private String city;
    private int postalCode;

    /**
     * Constructs a new Direction object with the specified street, city, and postal code.
     *
     * @param street     The street name.
     * @param city       The city name.
     * @param postalCode The postal code.
     * @throws NullArgumentException   If any argument is null.
     * @throws IncorrectDataException  If data is incorrect.
     */
    public Direction(String street, String city, int postalCode) throws NullArgumentException, IncorrectDataException {
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
    }

    /**
     * Constructs a new Direction object with the specified ID, street, city, and postal code.
     *
     * @param idDireccion The direction ID.
     * @param street      The street name.
     * @param city        The city name.
     * @param postalCode  The postal code.
     * @throws NullArgumentException   If any argument is null.
     * @throws IncorrectDataException  If data is incorrect.
     */
    public Direction(int idDireccion, String street, String city, int postalCode) throws NullArgumentException, IncorrectDataException {
        setIdDireccion(idDireccion);
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
    }

    /**
     * Default constructor.
     */
    public Direction() {

    }

    /**
     * Retrieves the direction ID.
     *
     * @return The direction ID.
     */
    public int getIdDireccion() {
        return idDireccion;
    }

    /**
     * Sets the direction ID.
     *
     * @param idDireccion The direction ID.
     * @throws IncorrectDataException If the ID is incorrect.
     */
    public void setIdDireccion(int idDireccion) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(idDireccion, 1000000000) && idDireccion >= 0) {
            this.idDireccion = idDireccion;
        } else {
            throw new IncorrectDataException("Verifica el id de persona, solo se aceptan números");
        }
    }

    /**
     * Retrieves the street name.
     *
     * @return The street name.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street name.
     *
     * @param street The street name.
     * @throws NullArgumentException  If the street name is null.
     * @throws IncorrectDataException If the street name is incorrect.
     */
    public void setStreet(String street) throws NullArgumentException, IncorrectDataException {
        if (street != null) {
            street = street.trim();
            if (VerificatorSetter.stringVerificatorletterAndNumbers(street, street.length())) {
                this.street = street;
            } else {
                throw new IncorrectDataException("La calle no puede tener simbolos");
            }
        } else {
            throw new NullArgumentException("Hay argumentos nulos al crear la dirección");
        }
    }

    /**
     * Retrieves the city name.
     *
     * @return The city name.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city name.
     *
     * @param city The city name.
     * @throws NullArgumentException  If the city name is null.
     * @throws IncorrectDataException If the city name is incorrect.
     */
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

    /**
     * Retrieves the postal code.
     *
     * @return The postal code.
     */
    public int getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code.
     *
     * @param postalCode The postal code.
     * @throws IncorrectDataException If the postal code is incorrect.
     */
    public void setPostalCode(int postalCode) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(postalCode, 5)) {
            this.postalCode = postalCode;
        } else {
            throw new IncorrectDataException("El código postal solo puede contener hasta 5 números");
        }
    }

    /**
     * Returns a string representation of the Direction object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Direction{" + "idDireccion=" + idDireccion + ", street='" + street + '\'' + ", city='" + city + '\'' + ", postalCode=" + postalCode + '}';
    }
}
