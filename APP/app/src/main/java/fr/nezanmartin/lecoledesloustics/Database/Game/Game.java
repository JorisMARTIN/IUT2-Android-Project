package fr.nezanmartin.lecoledesloustics.Database.Game;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import fr.nezanmartin.lecoledesloustics.Database.Level.Level;
import fr.nezanmartin.lecoledesloustics.Database.User.User;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"),
        @ForeignKey(entity = Level.class, parentColumns = "id", childColumns = "level_id")
})
public class Game implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "level_id")
    private int levelId;

    @ColumnInfo(name = "score")
    private float score;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getLevelId() {
        return levelId;
    }

    public float getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
