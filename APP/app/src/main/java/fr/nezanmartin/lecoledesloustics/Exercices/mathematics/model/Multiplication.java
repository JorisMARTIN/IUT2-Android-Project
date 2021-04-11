package fr.nezanmartin.lecoledesloustics.Exercices.mathematics.model;

public class Multiplication extends Operation{


    public Multiplication(int difficulty) {
        super(difficulty);
        this.operationCharacter = 'x';
    }

    @Override
    public int getResult() {
        return getOperand1() * getOperand2();
    }
}
