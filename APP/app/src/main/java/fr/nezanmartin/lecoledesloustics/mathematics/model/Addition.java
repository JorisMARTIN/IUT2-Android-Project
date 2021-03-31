package fr.nezanmartin.lecoledesloustics.mathematics.model;

public class Addition extends Operation{

    public Addition(int difficulty){
        super(difficulty);
        this.operationCharacter = '+';
    }

    @Override
    public int getResult() {
        return getOperand1() + getOperand2();
    }
}
