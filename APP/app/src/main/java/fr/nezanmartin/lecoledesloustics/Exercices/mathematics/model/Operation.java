package fr.nezanmartin.lecoledesloustics.Exercices.mathematics.model;

import java.util.Random;

public abstract class Operation {

    private int difficulty;

    private int operand1;
    private int operand2;

    protected char operationCharacter;

    public Operation(int difficulty){
        this.difficulty = difficulty;

        Random random = new Random();

        if(difficulty == 1){
            this.operand1 = random.nextInt(9-1) + 1;
            this.operand2 = random.nextInt(Math.max(this.operand1-1, 1)) + 1;
        }else if(difficulty == 2){
            this.operand1 = random.nextInt(9-1) + 1;
            this.operand2 = random.nextInt(99-10) + 10;
        }else{
            this.operand1 = random.nextInt(99-10) + 10;
            this.operand2 = random.nextInt(this.operand1-10) + 10;
        }
    }

    public int getDifficulty(){ return this.difficulty; }

    public int getOperand1(){ return  this.operand1; }

    public int getOperand2(){ return  this.operand2; }

    public char getOperationCharacter(){ return this.operationCharacter; }

    public abstract int getResult();

}
