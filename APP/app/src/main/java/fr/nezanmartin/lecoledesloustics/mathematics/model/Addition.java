package fr.nezanmartin.lecoledesloustics.mathematics.operation;

public class Addition extends Operation{

    public Addition(int operand1, int operand2){
        super(operand1, operand2);
        this.operationCharacter = '/';
    }

    @Override
    public int getResult() {
        return getOperand1() + getOperand2();
    }
}
