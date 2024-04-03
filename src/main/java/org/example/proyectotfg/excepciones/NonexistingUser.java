package com.ies.appmeetpshyc.meetphsyc.excepciones;

public class NonexistingUser extends Throwable {
    public NonexistingUser(String s) {
        super(s);
    }

    public NonexistingUser(Throwable cause) {
        super(cause);
    }

    public NonexistingUser() {
    }
}
