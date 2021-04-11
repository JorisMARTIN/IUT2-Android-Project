package fr.nezanmartin.lecoledesloustics.Exercices.mathematics.model;

public class Soustraction extends Operation {

    public Soustraction(int difficulty) {
        super(difficulty);
        this.operationCharacter = '-';
    }

    @Override
    public int getResult() {
        return getOperand1() - getOperand2();
    }
}
