package fr.nezanmartin.lecoledesloustics.mathematics.operation;

public class Division extends Operation {

    public Division(int operand1, int operand2) {
        super(operand1, operand2);
        this.operationCharacter = '/';
    }

    @Override
    public int getResult() {
        return 0;
    }
}
