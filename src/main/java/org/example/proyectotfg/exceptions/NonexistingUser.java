package org.example.proyectotfg.exceptions;

public class NonexistingUser extends Exception {
    public NonexistingUser(String s) {
        super(s);
    }

    public NonexistingUser(Throwable cause) {
        super(cause);
    }


}
