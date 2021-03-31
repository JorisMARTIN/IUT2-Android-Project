package fr.nezanmartin.lecoledesloustics.mathematics.model;

public class Sosutraction extends Operation {

    public Sosutraction(int difficulty) {
        super(difficulty);
        this.operationCharacter = '-';
    }

    @Override
    public int getResult() {
        return 0;
    }
}
