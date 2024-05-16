package org.example.proyectotfg.exceptions;

public class ThereIsNoView extends Exception {
    public ThereIsNoView(String s) {
        System.out.println(s);
    }

    public ThereIsNoView(Throwable cause) {
        super(cause);
    }
}
