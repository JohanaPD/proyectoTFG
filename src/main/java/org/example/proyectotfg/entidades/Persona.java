package org.example.proyectotfg.modelo;

import org.example.proyectotfg.funcionalidades.VerificatorSetter;

import java.util.Date;

public class Persona {
    private int id_persona;
    private String nombres;
    private String apellidos;
    private Direccion direccion;
    private String email;
    private Date birthday;
    private int edad;
    private Date registrationDate;
    private Boolean specifUser;

    public Persona() {
    }

    public Persona(int id_persona, String nombres, String apellidos, Direccion direccion, String email, Date birthday, int edad, Date registrationDate, Boolean specifUser) {
        this.id_persona = id_persona;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion=direccion;
        this.email = email;
        this.birthday = birthday;
        this.edad = edad;
        this.registrationDate = registrationDate;
        this.specifUser = specifUser;
    }

    public Persona(int idPersona, String nombres, String apellidos, String email, Date birthday, int edad, Date registrationDate, Boolean specifUser) {
        this.id_persona = id_persona;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.birthday = birthday;
        this.edad = edad;
        this.registrationDate = registrationDate;
        this.specifUser = specifUser;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        if (VerificatorSetter.stringVerificator(nombres, nombres.length()))
            this.nombres = nombres;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public String getApellidos() {

        return apellidos;
    }

    public void setApellidos(String apellidos) {
        if (VerificatorSetter.stringVerificator(apellidos, apellidos.length()))
            this.apellidos = apellidos;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public String getEmail() {
        return email;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        if (VerificatorSetter.validarCorreoElectronico(email))
            this.email = email;
        else throw new IllegalArgumentException("El correo introducido es erróneo");
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        if (VerificatorSetter.isValidBirthday(birthday))
            this.birthday = birthday;
        else throw new IllegalArgumentException("La fecha es incorrecta, revise los datos");
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (VerificatorSetter.esMayorDeEdad(edad))
            this.edad = edad;
        else throw new IllegalArgumentException("La edad debe ser 18 o más.");
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        if (VerificatorSetter.isValidBirthday(registrationDate))
            this.registrationDate = registrationDate;
        else throw new IllegalArgumentException("La fecha es incorrecta, revise los datos");
    }

    public Boolean getSpecifUser() {
        return specifUser;
    }

    public void setSpecifUser(Boolean specifUser) {
        this.specifUser = specifUser;
    }

    @Override
    public String toString() {
        return "Usuario : " +
                "id=" + id_persona +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", fecha de nacimiento=" + birthday +
                ", edad=" + edad +
                ", fecha de registro=" + registrationDate +
                ", ¿es usuario específico?=" + specifUser +
                '}';
    }
}
