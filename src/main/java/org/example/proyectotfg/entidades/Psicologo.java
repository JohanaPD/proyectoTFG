package org.example.proyectotfg.modelo;

import org.example.proyectotfg.funcionalidades.VerificatorSetter;

import java.util.Date;
import java.util.List;

public class Psicologo extends Persona {
    private String nickname;
    private String pass_cript;
    private String cod_colegiado;
    private String especialidad;
    private String desccripcion;
    private List<Usuario_normal> pacientes;
    private Date ultima_actividad;

    public Psicologo(String nickname, String pass_cript, String cod_colegiado, String especialidad, String desccripcion, Date ultima_actividad) {
        this.nickname = nickname;
        this.pass_cript = pass_cript;
        this.cod_colegiado = cod_colegiado;
        this.especialidad = especialidad;
        this.desccripcion = desccripcion;
        this.ultima_actividad = ultima_actividad;
    }

    public Psicologo(int id_persona, String nombres, String apellidos, String email, Date birthday, int edad, Date registrationDate, Boolean specifUser, String nickname, String pass_cript, String cod_colegiado, String especialidad, String desccripcion, Date ultima_actividad) {
        super(id_persona, nombres, apellidos, email, birthday, edad, registrationDate, specifUser);
        this.nickname = nickname;
        this.pass_cript = pass_cript;
        this.cod_colegiado = cod_colegiado;
        this.especialidad = especialidad;
        this.desccripcion = desccripcion;
        this.ultima_actividad = ultima_actividad;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (VerificatorSetter.stringVerificator(nickname, nickname.length()))
            this.nickname = nickname;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public String getPass_cript() {
        return pass_cript;
    }

    public void setPass_cript(String pass_cript) {
        this.pass_cript = pass_cript;
    }

    public String getCod_colegiado() {
        return cod_colegiado;
    }

    public void setCod_colegiado(String cod_colegiado) {
        if (VerificatorSetter.stringVerificator(cod_colegiado, cod_colegiado.length()))
            this.cod_colegiado = cod_colegiado;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        if (VerificatorSetter.stringVerificator(especialidad, especialidad.length()))
            this.especialidad = especialidad;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public String getDesccripcion() {
        return desccripcion;
    }

    public void setDesccripcion(String desccripcion) {
        if (VerificatorSetter.stringVerificator(desccripcion, desccripcion.length()))
            this.desccripcion = desccripcion;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public List<Usuario_normal> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Usuario_normal> pacientes) {
        this.pacientes = pacientes;
    }

    public Date getUltima_actividad() {
        return ultima_actividad;
    }

    public void setUltima_actividad(Date ultima_actividad) {
        if (VerificatorSetter.isValidBirthday(ultima_actividad))
            this.ultima_actividad = ultima_actividad;
        else throw new IllegalArgumentException("Verifica los datos introducidos, la fecha no es correcta");
    }

    @Override
    public String toString() {
        return "Psicologo{" +
                "nickname='" + nickname + '\'' +
                ", pass_cript='" + pass_cript + '\'' +
                ", cod_colegiado='" + cod_colegiado + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", desccripcion='" + desccripcion + '\'' +
                ", ultima_actividad=" + ultima_actividad +
                '}';
    }
}
