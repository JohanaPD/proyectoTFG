package org.example.proyectotfg.modelo;

import org.example.proyectotfg.funcionalidades.VerificatorSetter;

public class Post {
    private int id_post;
    private Persona creador;
    private String titulo;
    private String contenido;
    private String url_img;

    public Post(int id_post, Persona creador, String titulo, String contenido, String url_img) {
        this.id_post = id_post;
        this.creador = creador;
        this.titulo = titulo;
        this.contenido = contenido;
        this.url_img = url_img;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public Persona getCreador() {
        return creador;
    }

    public void setCreador(Persona creador) {
        this.creador = creador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(VerificatorSetter.stringVerificator(titulo, titulo.length()))
            this.titulo = titulo;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        if(VerificatorSetter.stringVerificator(contenido, contenido.length()))
            this.contenido = contenido;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        if(VerificatorSetter.stringVerificator(url_img, url_img.length()))
        this.url_img = url_img;
        else throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
    }

    @Override
    public String toString() {
        return "Post{" +
                "id_post=" + id_post +
                ", creador=" + creador.getId_persona() +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", url_img='" + url_img + '\'' +
                '}';
    }
}
