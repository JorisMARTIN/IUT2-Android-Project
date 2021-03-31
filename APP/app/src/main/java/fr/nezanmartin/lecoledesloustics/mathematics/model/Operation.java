package fr.nezanmartin.lecoledesloustics.mathematics.operation;

public abstract class Operation {

    private int operand1;
    private int operand2;

    protected char operationCharacter;

    public Operation(int operand1, int operand2){
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public int getOperand1(){ return  this.operand1; }

    public int getOperand2(){ return  this.operand2; }

    public char getOperationCharacter(){ return this.operationCharacter; }

    public abstract int getResult();

}
