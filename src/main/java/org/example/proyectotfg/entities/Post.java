package org.example.proyectotfg.entities;

import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.functions.VerificatorSetter;

/**
 * Represents a post.
 */
public class Post {
    private int idPost;
    private Person titular;
    private String title;
    private String content;
    private String urlImg;

    /**
     * Constructs a post with the specified details.
     *
     * @param idPost   the ID of the post
     * @param titular  the person who created the post
     * @param title    the title of the post
     * @param content  the content of the post
     * @param urlImg   the URL of the image associated with the post
     * @throws NullArgumentException   if any of the arguments is null
     * @throws IncorrectDataException  if the data provided is incorrect
     */
    public Post(int idPost, Person titular, String title, String content, String urlImg) throws NullArgumentException, IncorrectDataException {
        setIdPost(idPost);
        setTitular(titular);
        setTitle(title);
        setContent(content);
        setUrlImg(urlImg);
    }

    /**
     * Constructs a post with the specified details.
     *
     * @param titular  the person who created the post
     * @param title    the title of the post
     * @param content  the content of the post
     * @throws NullArgumentException   if any of the arguments is null
     * @throws IncorrectDataException  if the data provided is incorrect
     */
    public Post(Person titular, String title, String content) throws NullArgumentException, IncorrectDataException {
        setTitular(titular);
        setTitle(title);
        setContent(content);
    }

    /**
     * Constructs a post with the specified details.
     *
     * @param idPost   the ID of the post
     * @param titular  the person who created the post
     * @param title    the title of the post
     * @param content  the content of the post
     * @throws NullArgumentException   if any of the arguments is null
     * @throws IncorrectDataException  if the data provided is incorrect
     */
    public Post(int idPost, Person titular, String title, String content) throws NullArgumentException, IncorrectDataException {
        setIdPost(idPost);
        setTitular(titular);
        setTitle(title);
        setContent(content);
    }

    /**
     * Returns the ID of the post.
     *
     * @return the ID of the post
     */
    public int getIdPost() {
        return idPost;
    }

    /**
     * Sets the ID of the post.
     *
     * @param idPost the ID of the post
     * @throws IncorrectDataException if the ID provided is not valid
     */
    public void setIdPost(int idPost) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(idPost, 1000000000) && idPost >= 0) {
            this.idPost = idPost;
        } else {
            throw new IncorrectDataException("The ID of the post must be a valid positive number.");
        }
    }

    /**
     * Returns the person who created the post.
     *
     * @return the person who created the post
     */
    public Person getTitular() {
        return titular;
    }

    /**
     * Sets the person who created the post.
     *
     * @param titular the person who created the post
     * @throws NullArgumentException if the person provided is null
     */
    public void setTitular(Person titular) throws NullArgumentException {
        if (titular != null) {
            this.titular = titular;
        } else {
            throw new NullArgumentException("The person cannot be null.");
        }
    }

    /**
     * Returns the title of the post.
     *
     * @return the title of the post
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the post.
     *
     * @param title the title of the post
     * @throws NullArgumentException   if the title is null
     * @throws IncorrectDataException  if the title provided is not valid
     */
    public void setTitle(String title) throws NullArgumentException, IncorrectDataException {
        if (title != null) {
            if (VerificatorSetter.stringVerificatorletterAndNumbers(title, title.length())) {
                this.title = title;
            } else {
                throw new IncorrectDataException("The title must contain only letters and numbers.");
            }
        } else {
            throw new NullArgumentException("The title cannot be null.");
        }
    }

    /**
     * Returns the content of the post.
     *
     * @return the content of the post
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the post.
     *
     * @param content the content of the post
     * @throws NullArgumentException if the content is null
     */
    public void setContent(String content) throws NullArgumentException {
        if (content != null) {
            this.content = content;
        } else {
            throw new NullArgumentException("The content cannot be null.");
        }
    }

    /**
     * Returns the URL of the image associated with the post.
     *
     * @return the URL of the image associated with the post
     */
    public String getUrlImg() {
        return urlImg;
    }

    /**
     * Sets the URL of the image associated with the post.
     *
     * @param urlImg the URL of the image associated with the post
     * @throws NullArgumentException if the URL is null
     */
    public void setUrlImg(String urlImg) throws NullArgumentException {
        if (urlImg != null) {
            this.urlImg = urlImg;
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "idPost=" + idPost +
                ", creator=" + titular.getIdPerson() +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", urlImg='" + urlImg + '\'' +
                '}';
    }
}
