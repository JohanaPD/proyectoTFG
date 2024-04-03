package org.example.proyectotfg.modelo;

import org.example.proyectotfg.funcionalidades.VerificatorSetter;

import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.TreeMap;

public class Usuario_normal extends Persona {
    private String nickname;
    private String pass_cript;
    private TreeMap<Date, Post> post;
    private Deque<Psicologo> busquedas;
    private List<Psicologo> usuario_psicol;
    private boolean en_consulta;
    private Date ultima_actividad;

    public Usuario_normal(String nickname, String pass_cript, Date ultima_actividad) {
        super();
        this.nickname = nickname;
        this.pass_cript = pass_cript;
        this.ultima_actividad = ultima_actividad;
        this.en_consulta = false;
    }

    public Usuario_normal(int id_persona, String nombres, String apellidos, String email, Date birthday, int edad, Date registrationDate, Boolean specifUser, Persona persona, String nickname, String pass_cript, Date ultima_actividad) {
        super(id_persona, nombres, apellidos, email, birthday, edad, registrationDate, specifUser);
        this.nickname = nickname;
        this.pass_cript = pass_cript;
        this.ultima_actividad = ultima_actividad;
        this.en_consulta = false;
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

    public TreeMap<Date, Post> getPost() {
        return post;
    }

    public void setPost(TreeMap<Date, Post> post) {
        this.post = post;
    }

    public Deque<Psicologo> getBusquedas() {
        return busquedas;
    }

    public void setBusquedas(Deque<Psicologo> busquedas) {
        this.busquedas = busquedas;
    }

    public List<Psicologo> getUsuario_psicol() {
        return usuario_psicol;
    }

    public void setUsuario_psicol(List<Psicologo> usuario_psicol) {
        this.usuario_psicol = usuario_psicol;
    }

    public boolean isEn_consulta() {
        return en_consulta;
    }

    public void setEn_consulta(boolean en_consulta) {
        this.en_consulta = en_consulta;
    }

    public Date getUltima_actividad() {
        return ultima_actividad;
    }

    public void setUltima_actividad(Date ultima_actividad) {
        if (VerificatorSetter.isValidBirthday(ultima_actividad))
            this.ultima_actividad = ultima_actividad;
        else throw new IllegalArgumentException("La fecha es incorrecta, revise los datos");
    }

    @Override
    public String toString() {
        return "Usuario_normal{" +
                "nickname='" + nickname + '\'' +
                ", usuario_psicol=" + usuario_psicol +
                ", en_consulta=" + en_consulta +
                ", ultima_actividad=" + ultima_actividad +
                '}';
    }
}
