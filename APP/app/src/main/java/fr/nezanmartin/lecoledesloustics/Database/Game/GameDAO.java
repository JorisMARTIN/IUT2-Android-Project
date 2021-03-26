package fr.nezanmartin.lecoledesloustics.Database.Game;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDAO {
    @Query("SELECT * from Game WHERE user_id = (:userId)")
    List<Game> getUserGames(int userId);
}
