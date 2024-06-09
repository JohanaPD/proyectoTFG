package org.example.proyectotfg.mediators;

import javafx.scene.Parent;
import org.example.proyectotfg.entities.Post;

import java.util.List;

/**
 * MediatorPost interface for post mediator functionality.
 *
 * <p>Authors: [Author Name]</p>
 * <p>Version: [Version Number]</p>
 * <p>Since: [Date of Creation]</p>
 */
public interface MediatorPost {

    /**
     * Makes a new post.
     *
     * @param newPost The new post to be made.
     */
    void makePost(Post newPost);

    /**
     * Views the list of posts.
     *
     * @param posts The list of posts to be viewed.
     * @return The parent component for viewing the posts.
     */
    Parent viewPost(List<Post> posts);

    /**
     * Returns to the home interface.
     */
    void returnHome();
}
