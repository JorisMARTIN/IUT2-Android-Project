package fr.nezanmartin.lecoledesloustics.mathematics.addition.model;

public class Addition {

    private int operande1;
    private int operande2;
    private int resultat;

    public Addition(int operande1, int operande2){
        this.operande1 = operande1;
        this.operande2 = operande2;
        this.resultat = operande1 + operande2;
    }

    public int getOperande1() {
        return operande1;
    }

    public void setOperande1(int operande1) {
        this.operande1 = operande1;
    }

    public int getOperande2() {
        return operande2;
    }

    public void setOperande2(int operande2) {
        this.operande2 = operande2;
    }

    public int getResultat() {
        return resultat;
    }
}
