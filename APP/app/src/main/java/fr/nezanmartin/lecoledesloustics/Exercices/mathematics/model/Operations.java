package fr.nezanmartin.lecoledesloustics.Exercices.mathematics.model;

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

    public static Operations getOperationFromName(String name){
        if(name.equalsIgnoreCase("addition")) return ADDITION;
        else if(name.equalsIgnoreCase("soustraction")) return SOUSTRACTION;
        else if(name.equalsIgnoreCase("multiplication")) return MULTIPLICATION;
        else if(name.equalsIgnoreCase("division")) return DIVISION;
        else return null;
    }

}
