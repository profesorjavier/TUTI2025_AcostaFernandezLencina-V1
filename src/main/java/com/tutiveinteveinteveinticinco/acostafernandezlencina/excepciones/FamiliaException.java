package com.tutiveinteveinteveinticinco.acostafernandezlencina.excepciones;

public class FamiliaException extends Exception {

    public FamiliaException() {
        super();
    }

    public FamiliaException(String mensaje) {
        super(mensaje);
    }

    public FamiliaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}