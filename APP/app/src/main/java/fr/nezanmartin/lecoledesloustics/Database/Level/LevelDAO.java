package fr.nezanmartin.lecoledesloustics.Database.Level;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LevelDAO {

    @Query("SELECT DISTINCT activity FROM level")
    List<String> getActivities();

    @Query("SELECT * FROM level WHERE activity = (:activity)")
    List<Level> getActivityLevels(String activity);

    @Insert
    void insert(Level level);

    @Insert
    long[] insertAll(Level... levels);

    @Update
    void update(Level level);
}
