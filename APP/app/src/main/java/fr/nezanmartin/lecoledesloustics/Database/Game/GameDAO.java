package fr.nezanmartin.lecoledesloustics.Database.Game;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.nezanmartin.lecoledesloustics.Database.User.User;

@Dao
public interface GameDAO {
    @Query("SELECT g.* from Game g, User u WHERE g.user_id = u.id AND u.current_user == 1")
    List<Game> getCurrentUserGames();

    @Query("SELECT g.* from Game g, Level l WHERE l.id = (:level_id) AND g.level_id = l.id LIMIT 1")
    Game getLevelGame(int level_id);

    @Insert
    void insert(User user);

    @Insert
    long[] insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}
