package com.ies.appmeetpshyc.meetphsyc.excepciones;

public class ThereIsNoView extends Throwable {
    public ThereIsNoView(String s) {
        System.out.println(s);
    }

    public ThereIsNoView(Throwable cause) {
        super(cause);
    }

    public ThereIsNoView() {
    }
}
