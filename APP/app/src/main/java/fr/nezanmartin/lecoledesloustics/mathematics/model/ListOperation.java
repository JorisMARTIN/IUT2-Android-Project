package fr.nezanmartin.lecoledesloustics.mathematics.model;


import java.util.ArrayList;

public class ListOperation {

    private int difficulty;
    private ArrayList<Operation> operations;
    private String operation;

    public ListOperation(int difficulty, String operation){
        this.difficulty = difficulty;
        this.operations = new ArrayList<>();
        this.operation = operation;

        initOperation();
    }

    private void initOperation() {
        for(int i=0; i<10; i++){

            if(this.operation.equalsIgnoreCase("Addition")){
                this.operations.add(new Addition(this.difficulty));
            }else if(this.operation.equalsIgnoreCase("Multiplication")){
                this.operations.add(new Multiplication(this.difficulty));
            }else if(this.operation.equalsIgnoreCase("Division")){
                this.operations.add(new Division(this.difficulty));
            }else if(this.operation.equalsIgnoreCase("Soustraction")){
                this.operations.add(new Sosutraction(this.difficulty));
            }

        }
    }

    public ArrayList<Operation> getOperations(){ return this.operations; }

    public int getDifficulty(){ return this.difficulty; }

    public String getOperation(){ return this.operation; }

}
