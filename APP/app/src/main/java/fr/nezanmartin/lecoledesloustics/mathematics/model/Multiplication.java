package fr.nezanmartin.lecoledesloustics.mathematics.operation;

public class Multiplication extends Operation{


    public Multiplication(int operand1, int operand2) {
        super(operand1, operand2);
        this.operationCharacter = 'x';
    }

    @Override
    public int getResult() {
        return getOperand1() * getOperand2();
    }
}
