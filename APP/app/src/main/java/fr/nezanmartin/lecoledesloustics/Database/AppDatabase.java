package fr.nezanmartin.lecoledesloustics.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import fr.nezanmartin.lecoledesloustics.Database.Game.Game;
import fr.nezanmartin.lecoledesloustics.Database.Game.GameDAO;
import fr.nezanmartin.lecoledesloustics.Database.Level.Level;
import fr.nezanmartin.lecoledesloustics.Database.Level.LevelDAO;
import fr.nezanmartin.lecoledesloustics.Database.User.User;
import fr.nezanmartin.lecoledesloustics.Database.User.UserDAO;

@Database(entities = {User.class, Level.class, Game.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract LevelDAO levelDAO();
    public abstract GameDAO gameDAO();
}
