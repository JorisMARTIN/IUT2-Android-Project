package fr.nezanmartin.lecoledesloustics.Database.Level;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Level implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "activity")
    private String activity;

    @ColumnInfo(name = "difficulty")
    private int difficulty;

    @ColumnInfo(name = "hasBeenPlayed")
    private boolean hasBeenPlayed;

    @ColumnInfo(name = "score")
    private int score;

    public int getId() {
        return id;
    }

    public String getActivity() {
        return activity;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean hasBeenPlayed() {
        return hasBeenPlayed;
    }

    public void setScore(int score) {
        this.hasBeenPlayed = true;

        if (score > this.score) {
            this.score = score;
        }
    }
}
