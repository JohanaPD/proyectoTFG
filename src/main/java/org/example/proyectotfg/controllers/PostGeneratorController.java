package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.Post;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorPost;
import org.example.proyectotfg.mediators.ViewController;

public class PostGeneratorController implements ViewController {
    @FXML
    private TextArea description;

    @FXML
    private Text nameTitular;

    @FXML
    private ImageView patImage;

    @FXML
    private TextFlow textDescripton;

    @FXML
    private TextField titlePost;
    MediatorPost mediator;
    Person person;

    /**
     * Sets the mediator.
     *
     * @param mediador the mediator to set.
     */
    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorPost) mediador;
    }

    /**
     * Gets the person.
     *
     * @return the person.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the person.
     *
     * @param person the person to set.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Sets the title of the post.
     *
     * @param titlePost the title of the post.
     */
    public void setTitlePost(String titlePost) {
        this.nameTitular.setText(titlePost);
    }

    /**
     * Generates a new post.
     *
     * @param event the action event.
     * @throws IncorrectDataException if there is incorrect data.
     * @throws NullArgumentException if there is a null argument.
     */
    @FXML
    void generarPost(ActionEvent event) throws IncorrectDataException, NullArgumentException {
        String title = titlePost.getText();
        if (title.isEmpty()) {
            ((MainController) mediator).showInfo("Rellena todos los datos", "El titulo no puede estar vacio");
        }
        String nameTit = nameTitular.getText();
        String descrip = description.getText();
        if (descrip.isEmpty()) {
            ((MainController) mediator).showInfo("Rellena todos los datos", "El contenido no puede estar vacio");
        }
        if (!title.isEmpty() && !descrip.isEmpty()) {
            Post nuevo = new Post(person, title, descrip);
            mediator.makePost(nuevo);
        }
    }

    /**
     * Returns to the home view.
     *
     * @param actionEvent the action event.
     */
    public void volverHome(ActionEvent actionEvent) {
        mediator.returnHome();
    }

}
