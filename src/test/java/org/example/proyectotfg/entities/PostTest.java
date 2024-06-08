package org.example.proyectotfg.entities;

import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    private Post post;
    private NormalUser titular;
    private Direction direction;

    @BeforeEach
    public void setUp() throws NullArgumentException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException {
        direction = new Direction("Calle Falsa", "Madrid", 28080);
        titular = new NormalUser(1, "Juan", "Pérez", "password123", new Date(), new Date(), "juan@example.com", TypeUser.USUARIO_NORMAL, StatesUser.VERIFIED, direction, new Date(), "juanito");
        post = new Post(123, titular, "Título del Post", "Contenido del post", "http://example.com/image.jpg");
    }

    @Test
    public void testSetIdPostValid() throws IncorrectDataException {
        post.setIdPost(456);
        assertEquals(456, post.getIdPost());
    }

    @Test
    public void testSetIdPostInvalid() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            post.setIdPost(-1);
        });
        assertEquals("El valor del id del post tiene que ser un número", exception.getMessage());
    }

    @Test
    public void testSetTitularValid() throws NullArgumentException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException {
        NormalUser newTitular = new NormalUser(2, "Ana", "López", "password456", new Date(), new Date(), "ana@example.com", TypeUser.USUARIO_NORMAL, StatesUser.VERIFIED, direction, new Date(), "anita");
        post.setTitular(newTitular);
        assertEquals(newTitular, post.getTitular());
    }

    @Test
    public void testSetTitularNull() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            post.setTitular(null);
        });
        assertEquals("Has introducido valores nulos a la hora de crear el post", exception.getMessage());
    }

    @Test
    public void testSetTitleValid() throws NullArgumentException, IncorrectDataException {
        post.setTitle("Nuevo Título");
        assertEquals("Nuevo Título", post.getTitle());
    }

    @Test
    public void testSetTitleNull() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            post.setTitle(null);
        });
        assertEquals("Has introducido valores nulos a la hora de crear el post", exception.getMessage());
    }

    @Test
    public void testSetTitleInvalid() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            post.setTitle("Título @#$");
        });
        assertEquals("Verifica los datos introducidos, solo se aceptan letras", exception.getMessage());
    }

    @Test
    public void testSetContentValid() throws NullArgumentException {
        post.setContent("Nuevo Contenido");
        assertEquals("Nuevo Contenido", post.getContent());
    }

    @Test
    public void testSetContentNull() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            post.setContent(null);
        });
        assertEquals("Has introducido valores nulos a la hora de crear el post", exception.getMessage());
    }

    @Test
    public void testSetUrlImgValid() throws NullArgumentException {
        post.setUrlImg("http://example.com/newimage.jpg");
        assertEquals("http://example.com/newimage.jpg", post.getUrlImg());
    }

    @Test
    public void testSetUrlImgNull() throws NullArgumentException {
        post.setUrlImg(null);
        assertNull(post.getUrlImg());
    }

    @Test
    public void testConstructorValidArguments() throws NullArgumentException, IncorrectDataException {
        Post newPost = new Post(789, titular, "Título Constructor", "Contenido Constructor", "http://example.com/constructor.jpg");
        assertEquals(789, newPost.getIdPost());
        assertEquals("Título Constructor", newPost.getTitle());
        assertEquals("Contenido Constructor", newPost.getContent());
        assertEquals("http://example.com/constructor.jpg", newPost.getUrlImg());
    }

    @Test
    public void testConstructorInvalidTitle() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            new Post(789, titular, "Título @#$", "Contenido Constructor", "http://example.com/constructor.jpg");
        });
        assertEquals("Verifica los datos introducidos, solo se aceptan letras", exception.getMessage());
    }

    @Test
    public void testConstructorNullTitular() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            new Post(789, null, "Título Constructor", "Contenido Constructor", "http://example.com/constructor.jpg");
        });
        assertEquals("Has introducido valores nulos a la hora de crear el post", exception.getMessage());
    }

    @Test
    public void testToString() {
        String expected = "Post{id_post=123, creador=1, titulo='Título del Post', contenido='Contenido del post', url_img='http://example.com/image.jpg'}";
        assertEquals(expected, post.toString());
    }
}
