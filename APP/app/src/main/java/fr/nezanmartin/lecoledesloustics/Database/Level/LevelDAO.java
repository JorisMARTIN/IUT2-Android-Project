package fr.nezanmartin.lecoledesloustics.Database.Level;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LevelDAO {

    @Query("SELECT DISTINCT name FROM level")
    List<String> getActivities();

    @Query("SELECT * FROM level WHERE name = (:activity)")
    List<Level> getLevels(String activity);

    @Query("SELECT * FROM level")
    List<Level> getAllLevels();

    @Query("SELECT * FROM level WHERE id = (:id)")
    Level getLevelById(int id);

    @Insert
    void insert(Level level);

    @Insert
    long[] insertAll(Level... levels);

    @Update
    void update(Level level);
}
