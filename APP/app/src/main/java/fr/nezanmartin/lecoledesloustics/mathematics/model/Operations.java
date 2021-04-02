package fr.nezanmartin.lecoledesloustics.mathematics.model;

public enum Operations {

    ADDITION("Résoud les additions suivantes :"),
    SOUSTRACTION("Résoud les soustractions suivantes :"),
    MULTIPLICATION("Résoud les multiplication suivantes :"),
    DIVISION("Résoud les divisions suivantes (arrondi à l'unité près) :");

    private String message;

    Operations(String message){
        this.message = message;
    }

    public String getMessage(){ return this.message; }

}
