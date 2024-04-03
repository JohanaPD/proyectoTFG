package com.ies.appmeetpshyc.meetphsyc.excepciones;

public class IncompleteDataInRecord extends Throwable {
    public IncompleteDataInRecord(String s) {
        System.out.println(s);
    }

    public IncompleteDataInRecord(Throwable cause) {
        super(cause);
    }

    public IncompleteDataInRecord() {
    }
}
