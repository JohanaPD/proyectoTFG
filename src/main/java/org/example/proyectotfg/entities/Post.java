package org.example.proyectotfg.entities;

import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.functions.VerificatorSetter;

public class Post {
    private int idPost;
    private Person titular;
    private String title;
    private String content;
    private String urlImg;

    public Post(int idPost, Person titular, String title, String content, String urlImg) throws NullArgumentException, IncorrectDataException {
        setIdPost(idPost);
        setTitular(titular);
        setTitle(title);
        setContent(content);
        setUrlImg(urlImg);
    }

    public Post(Person titular, String title, String content) throws NullArgumentException, IncorrectDataException {
        setTitular(titular);
        setTitle(title);
        setContent(content);
    }
    public Post(int idPost,Person titular, String title, String content) throws NullArgumentException, IncorrectDataException {
        setIdPost(idPost);
        setTitular(titular);
        setTitle(title);
        setContent(content);
    }



    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(idPost, 1000000000) && idPost >= 0) {
            this.idPost = idPost;
        } else {
            throw new IncorrectDataException("El valor del id del post tiene que ser un número");
        }

    }

    public Person getTitular() {
        return titular;
    }

    public void setTitular(Person titular) throws NullArgumentException {
        if (titular != null) {
            this.titular = titular;
        } else {
            throw new NullArgumentException("Has introducido valores nulos a la hora de crear el post");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws NullArgumentException, IncorrectDataException {
        if (title != null) {
            if (VerificatorSetter.stringVerificatorletterAndNumbers(title, title.length())) {
                this.title = title;
            } else {
                throw new IncorrectDataException("Verifica los datos introducidos, solo se aceptan letras");
            }
        } else {
            throw new NullArgumentException("Has introducido valores nulos a la hora de crear el post");
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) throws NullArgumentException {
        if (content != null) {
            this.content = content;

        } else {
            throw new NullArgumentException("Has introducido valores nulos a la hora de crear el post");
        }
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) throws NullArgumentException {
        if (urlImg != null) {
            this.urlImg = urlImg;
        }
    }

    @Override
    public String toString() {
        return "Post{" + "id_post=" + idPost + ", creador=" + titular.getIdPerson() + ", titulo='" + title + '\'' + ", contenido='" + content + '\'' + ", url_img='" + urlImg + '\'' + '}';
    }
}
