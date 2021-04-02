package fr.nezanmartin.lecoledesloustics.mathematics.model;


import java.util.ArrayList;

public class ListOperation {

    private int difficulty;
    private ArrayList<Operation> operations;
    private Operations operation;

    public ListOperation(int difficulty, Operations operation){
        this.difficulty = difficulty;
        this.operations = new ArrayList<>();
        this.operation = operation;

        initOperation();
    }

    private void initOperation() {
        for(int i=0; i<10; i++){

            if(this.operation == Operations.ADDITION){
                this.operations.add(new Addition(this.difficulty));
            }else if(this.operation == Operations.MULTIPLICATION){
                this.operations.add(new Multiplication(this.difficulty));
            }else if(this.operation == Operations.DIVISION){
                this.operations.add(new Division(this.difficulty));
            }else if(this.operation == Operations.SOUSTRACTION){
                this.operations.add(new Soustraction(this.difficulty));
            }

        }
    }

    public ArrayList<Operation> getOperations(){ return this.operations; }

    public int getDifficulty(){ return this.difficulty; }

    public Operations getOperation(){ return this.operation; }

}
