package fr.nezanmartin.lecoledesloustics.Exercices.mathematics.model;

public class Division extends Operation {

    public Division(int difficulty) {
        super(difficulty);
        this.operationCharacter = '/';
    }

    @Override
    public int getResult() { return Math.round(getOperand1() / getOperand2()); }
}
