package org.example.proyectotfg.mediators;

import javafx.scene.Parent;
import org.example.proyectotfg.entities.Post;

import java.util.List;

public interface MediatorPost {

    void makePost(Post nuevo);
    Parent viewPost(List<Post> posts);
    void returnHome();
}
